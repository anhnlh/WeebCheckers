package com.webcheckers.ui;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Phil Ganem
 * @author Anh Nguyen
 */
@Tag("UI-tier")
public class TestPostSignInRoute {

    private PostSignInRoute CuT;
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;
    private Request request;
    private Response response;
    private Session session;
    private String name;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        name = "Player";
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);

        // simulate the player lobby
        playerLobby = new PlayerLobby();
//        playerLobby.addPlayer(name);

        CuT = new PostSignInRoute(playerLobby, templateEngine);
    }


    /**
     * Test the constructor for PostSignInRoute with null values
     */
    @Test
    public void testConstructor_Null() {
        boolean isThrown = false;

        try {
            CuT = new PostSignInRoute(null, null);
        } catch (NullPointerException e) {
            isThrown = true;
        }

        assertTrue(isThrown);
    }


    /**
     * Test the constructor for PostSignInRoute
     */
    @Test
    public void testConstructor() {

        boolean isThrown = false;

        try {
            CuT = new PostSignInRoute(playerLobby, templateEngine);
        } catch (Error e) {
            isThrown = true;
        }

        assertFalse(isThrown);
    }

    /**
     * Tests handle to see if it takes player signin
     */
    @Test
    public void testGoodHandle() {
        when(request.queryParams("userID")).thenReturn("Player");
        when(session.attribute(PostSignInRoute.SESSION_ATTR)).thenReturn(name);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // only redirects to home and throws a halt() exception
        try {
            CuT.handle(request, response);
        } catch (HaltException ignored) {
        }

        verify(response).redirect(WebServer.HOME_URL);

        int expected = 1;
        int actual = playerLobby.size();

        assertEquals(expected, actual);
    }

    /**
     * Tests the faulty case of handle
     */
    @Test
    public void testBadHandle() {
        when(request.queryParams("userID")).thenReturn("()#!&()!#&()!#&())");
        when(session.attribute(PostSignInRoute.SESSION_ATTR)).thenReturn(name);
        when(session.attribute("error")).thenReturn(Message.error("Invalid Request. Please try again."));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // renders /signin again
        CuT.handle(request, response);

        int expected = 0;
        int actual = playerLobby.size();

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title",  "Sign in error.");

        Message testErrorMessage = (Message) session.attribute("error");
        Message actualMessage = Message.error("Invalid Request. Please try again.");

        assertEquals(testErrorMessage.getText(), actualMessage.getText());
        assertEquals(expected, actual);
    }
}

