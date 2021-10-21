package com.webcheckers.ui;

import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import static org.junit.jupiter.api.Assertions.*;
import spark.*;

import java.util.HashMap;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link GetSignInRoute}
 */
public class GetSignInTest {
    
    /**
     * The component-under-test (CuT).
     */
    private GetSignInRoute CuT;

    /**
     * Attributes holding mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;

    /**
     * Friendly objects
     */
    private Player player;  

    /**
     * Setup mock objects before each test
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        
        // Player, PlayerLobby, and HashMap are friendly
        //playerName = "player1";
        //player = new Player(playerName);

        // Create a unique CuT for each test
        CuT = new GetSignInRoute(templateEngine);
    }

    /**
     * Tests {@link GetSignInRoute#GetSignInRoute(TemplateEngine)}
     */
    @Test
    public void constructorTest() {
        new GetSignInRoute(templateEngine);
        assertNotNull(templateEngine);
    }

    /**
     * Tests {@link GetSignInRoute#handle(Request, Response)}
     */
    @Test
    public void handleTest() {
        // When the player isn't signed in
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, "Welcome!");

        // When the player is signed in
        when(session.attribute(GetHomeRoute.CURRENT_USER_ATTR)).thenReturn(player);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, "Welcome!");
        assertEquals(GetSignInRoute.VIEW_NAME, "signin.ftl");
    }
}
