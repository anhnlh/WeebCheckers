package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.webcheckers.app.PlayerLobby;

import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;

import spark.*;
import spark.http.matching.Halt;

/**
 * Test class for {@link PostSignOutRoute}
 */
@Tag("UI-tier")
@Testable
public class TestPostSignOutRoute {
    /**
     * The component-under-test (CuT).
     */
    private PostSignOutRoute CuT;

    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    private Request request;
    private Response response;

    private Session session;
    private String name;

    /**
     * Setup new mock objects for each test.
     */
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
        playerLobby.addPlayer(name);

        // create a unique CuT for each test
        CuT = new PostSignOutRoute(playerLobby, templateEngine);
    }

    /**
     * Tests {@link PostSignOutRoute#PostSignOutRoute(PlayerLobby, TemplateEngine)}
     */
    @Test
    public void constructorTest() {
        new PostSignOutRoute(playerLobby, templateEngine);
        assertNotNull(playerLobby);
        assertNotNull(templateEngine);
    }

    /**
     * Tests {@link PostSignOutRoute#handle(Request, Response)}
     */
    @Test
    public void handleTest() {
        // simulate as player's name
        when(session.attribute(PostSignInRoute.SESSION_ATTR)).thenReturn(name);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // player is in lobby before logout
        int expected = 1;
        int actual = playerLobby.size();

        assertEquals(expected, actual);

        // method will fail because the route halts
        try {
            CuT.handle(request, response);
        } catch (HaltException ignored) {}

        // player is not in lobby after logout
        expected = 0;
        actual = playerLobby.size();

        assertEquals(expected, actual);

        // verify redirect to home page
        verify(response).redirect(WebServer.HOME_URL);
    }

    
}
