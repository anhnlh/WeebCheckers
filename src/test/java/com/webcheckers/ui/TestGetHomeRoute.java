package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;

import spark.*;

/**
 * Test class for {@link GetHomeRoute}
 *
 * @author Anh Nguyen
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
        playerLobby.addPlayer(player.getName());
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
    public void playerSignInHandleTest() {
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

    /**
     * Tests when player is challenged by someone else
     */
    @Test
    public void playerIsPlayingHandleTest() {
        // When the player is signed in
        when(session.attribute(GetHomeRoute.CURRENT_USER_ATTR)).thenReturn(player);
        player.setPlaying(true);

        // user is the white player and gets redirected to the game
        Player opponent = new Player("player2");
        Game game = new Game(opponent, player);
        gameMap.put(String.valueOf(game.getID()), game);

        // redirects and halts
        try {
            CuT.handle(request, response);
        } catch (HaltException ignored) {
        }

        verify(response).redirect(WebServer.GAME_URL + "?gameID=" + game.getID());

    }

    /**
     * Tests when player returns from the game to the home page
     */
    @Test
    public void playerFinishesGameHandleTest() {
        when(session.attribute(GetHomeRoute.CURRENT_USER_ATTR)).thenReturn(player);
        player.setPlaying(false);

        // user is the white player and gets redirected to the game
        Player opponent = new Player("player2");
        playerLobby.addPlayer(opponent.getName());

        // players still in lobby
        assertNotNull(playerLobby.getPlayer("player1"));
        assertNotNull(playerLobby.getPlayer("player2"));

        // players leaves lobby (GetGameRoute does this)
        // and joins the game
        Game game = new Game(opponent, player);
        gameMap.put(String.valueOf(game.getID()), game);
        assertEquals(gameMap.size(), 1);
        assertTrue(gameMap.containsKey(String.valueOf(game.getID())));
        assertFalse(game.isGameOver());

        // game is done
        game.setGameOver();
        assertFalse(player.isPlaying());
        assertFalse(opponent.isPlaying());
        assertTrue(game.isGameOver());

        // runs handle which pulls players back into lobby
        // and removes game from the gameMap
        CuT.handle(request, response);

        assertNotNull(playerLobby.getPlayer("player1"));
        assertNotNull(playerLobby.getPlayer("player2"));

        assertEquals(gameMap.size(), 0);
        assertFalse(gameMap.containsKey(String.valueOf(game.getID())));
    }

    /**
     * Tests error message popup in home page
     */
    @Test
    public void errorMessageTest() {
        when(session.attribute(GetHomeRoute.ERROR_ATTR)).thenReturn(GetGameRoute.OPPONENT_IN_GAME);
        when(session.attribute(GetHomeRoute.CURRENT_USER_ATTR)).thenReturn(player);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());


        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetHomeRoute.ERROR_ATTR, GetGameRoute.OPPONENT_IN_GAME);
    }
}
