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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private TemplateEngine engine;
    private HashMap<String, Game> gameMap;
    private PlayerLobby playerLobby;
    private Player p1;
    private Player p2;
    private Game game;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        // simulate the player lobby
        playerLobby = new PlayerLobby();
        playerLobby.addPlayer("Player_1");
        playerLobby.addPlayer("Player_2");
        // simulate getting opponent from playerLobby
        when(playerLobby.getPlayer(request.queryParams(GetGameRoute.OPPONENT_ATTR))).thenReturn(p2);

        // simulate a game created
        p1 = playerLobby.getPlayer("Player_1");
        p2 = playerLobby.getPlayer("Player_2");
        game = new Game(p1, p2);
        gameMap = new HashMap<>();
        gameMap.put(String.valueOf(game.getID()), game);

        // create a unique CuT for each test
        CuT = new GetGameRoute(gameMap, playerLobby, engine);
    }

    @Test
    public void newGame() {
        // simulate current user as Player_1
        when(session.attribute(GetHomeRoute.CURRENT_USER)).thenReturn(p1);
        // simulate "opponent" param to be "Player_2"
        when(request.queryParams(GetGameRoute.OPPONENT_ATTR)).thenReturn("Player_2");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER, p1);
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, "Welcome!");

    }
}
