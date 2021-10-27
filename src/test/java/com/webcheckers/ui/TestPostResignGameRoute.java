package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import static org.junit.jupiter.api.Assertions.*;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
@Testable
public class TestPostResignGameRoute {
    private PostResignGameRoute CuT;

    private Game game;
    private HashMap<String, Game> gameMap;
    private Gson gson;

    private Request request;
    private Response response;
    private Session session;

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

        p1 = new Player("player1");
        p2 = new Player("player2");
        game = new Game(p1, p2);
        gameMap = new HashMap<>();
        gameMap.put(String.valueOf(game.getID()), game);

        gson = new Gson();

        // create a unique CuT for each test
        CuT = new PostResignGameRoute(gameMap, gson);
    }

    /**
     * Tests {@link PostResignGameRoute#handle(Request, Response)}
     */
    @Test
    public void testHandle() {
        when(session.attribute(GetHomeRoute.CURRENT_USER_ATTR)).thenReturn(p1);
        when(request.queryParams(GetGameRoute.GAME_ID_PARAM)).thenReturn(String.valueOf(game.getID()));

        Object value = CuT.handle(request, response);

        assertTrue(game.isGameOver());
        assertEquals(game.getGameOverMessage(), p1 + " has resigned.");
        assertEquals(value, gson.toJson(Message.info("Resignation successful")));
    }
}
