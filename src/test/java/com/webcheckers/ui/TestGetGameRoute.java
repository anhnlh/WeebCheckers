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
 * Test class for {@link GetGameRoute}
 */
@Tag("UI-tier")
@Testable
public class TestGetGameRoute {
    /**
     * The component-under-test (CuT).
     */
    private GetGameRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;
    private HashMap<String, Game> gameMap;
    private PlayerLobby playerLobby;
    private Player p1;
    private Player p2;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);

        // simulate the player lobby
        playerLobby = new PlayerLobby();
        playerLobby.addPlayer("Player_1");
        playerLobby.addPlayer("Player_2");

        // simulate getting opponent from playerLobby
        when(playerLobby.getPlayer(request.queryParams(GetGameRoute.OPPONENT_ATTR))).thenReturn(p2);

        // simulate a game created
        p1 = playerLobby.getPlayer("Player_1");
        p2 = playerLobby.getPlayer("Player_2");
        gameMap = new HashMap<>();

        // create a unique CuT for each test
        CuT = new GetGameRoute(gameMap, playerLobby, templateEngine);
    }

    @Test
    public void constructorTest() {
        new GetGameRoute(gameMap, playerLobby, templateEngine);
        assertNotNull(gameMap);
        assertNotNull(playerLobby);
        assertNotNull(templateEngine);
    }

    /**
     * Tests {@link GetGameRoute#handle(Request, Response)}
     */
    @Test
    public void handleTest() {
        // simulate current user as Player_1
        when(session.attribute(GetHomeRoute.CURRENT_USER)).thenReturn(p1);
        // simulate "opponent" param to be "Player_2"
        when(request.queryParams(GetGameRoute.OPPONENT_ATTR)).thenReturn("Player_2");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // not in game
        assertFalse(p1.isPlaying());
        assertFalse(p2.isPlaying());

        // first handle creates the game
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER, p1);
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, "Welcome!");

        // simulate game created
        String gameID = String.valueOf(Objects.hash(p1, p2));
        when(request.queryParams(GetGameRoute.GAME_ID_PARAM)).
                thenReturn(gameID);

        // should redirect to /game with gameID params
        verify(response).redirect(WebServer.GAME_URL + "?gameID=" + gameID);

        // game is created
        Game game = gameMap.get(gameID);
        assertEquals(gameMap.size(), 1);
        assertNotNull(game);
        assertNotNull(request.queryParams(GetGameRoute.GAME_ID_PARAM));

        // players are now in game
        assertTrue(p1.isPlaying());
        assertTrue(p2.isPlaying());

        // second handle builds the game view
        CuT.handle(request, response);

        // checks the Model-View
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR, GetGameRoute.mode.PLAY);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER_ATTR, game.getRedPlayer());
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER_ATTR, game.getWhitePlayer());
        testHelper.assertViewModelAttribute(GetGameRoute.BOARD_ATTR, game.redPlayerBoard());
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR_ATTR, GetGameRoute.activeColor.RED);
    }

    /**
     * Tests a faulty case with {@link GetGameRoute#handle(Request, Response)}
     */
    @Test
    public void faultyHandleTest() {
        when(session.attribute(GetHomeRoute.CURRENT_USER)).thenReturn(null);

        // tests handle
        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException ignored) {}

        // should redirect to homepage
        verify(response).redirect(WebServer.HOME_URL);
    }
}
