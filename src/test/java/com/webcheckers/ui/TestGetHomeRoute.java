package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;

import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;

import spark.*;

/**
 * Test class for {@link GetHomeRoute}
 */
@Tag("UI-tier")
@Testable
public class TestGetHomeRoute {
    /**
     * The component-under-test (CuT).
     */
    private GetHomeRoute CuT;

    /**
     * Friendly objects
     */
    private PlayerLobby playerLobby;
    private HashMap<String, Game> gameMap;
    private Player player;    

    /**
     * Attributes holding mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;

    private String playerName;

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
        playerName = "player1";
        player = new Player(playerName);
        playerLobby = new PlayerLobby();
        gameMap = new HashMap<>();

        // Create a unique CuT for each test
        CuT = new GetHomeRoute(gameMap, playerLobby, templateEngine);
    }

    /**
     * Tests {@link GetHomeRoute#GetHomeRoute(HashMap, PlayerLobby, TemplateEngine)}
     */
    @Test
    public void constructorTest() {
        new GetHomeRoute(gameMap, playerLobby, templateEngine);
        assertNotNull(gameMap);
        assertNotNull(playerLobby);
        assertNotNull(templateEngine);
    }

    /**
     * Tests {@link GetHomeRoute#handle(Request, Response)}
     */
    @Test
    public void handleTest() {
        // When the player isn't signed in
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, "Welcome!");
        testHelper.assertViewModelAttribute(GetHomeRoute.ACTIVE_PLAYER_COUNT_ATTR, playerLobby.activePlayersMessage());

        // When the player is signed in
        when(session.attribute(GetHomeRoute.CURRENT_USER_ATTR)).thenReturn(player);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, "Welcome!");
        testHelper.assertViewModelAttribute(GetHomeRoute.ACTIVE_PLAYERS_ATTR, playerLobby.getOtherActivePlayers(player));
    }
}
