package com.webcheckers.ui;

import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.platform.commons.annotation.Testable;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;

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
        Game game = new Game(new Player("Player 1"), new Player("Player 2"));
        gameMap.put(String.valueOf(game.getID()), game);
        playerLobby = new PlayerLobby();

        // create a unique CuT for each test
        CuT = new GetGameRoute(gameMap, playerLobby, engine);
    }
}
