package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.model.*;
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

/**
 * Tests {@link PostBackupMoveRoute}
 * @author Anh Nguyen
 */
@Tag("UI-Tier")
@Testable
public class TestPostGetHintRoute {
    private PostGetHintRoute CuT;

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
        CuT = new PostGetHintRoute(gameMap, gson);
    }

    /**
     * Tests {@link PostBackupMoveRoute#handle(Request, Response)}
     */
    @Test
    public void testHandle() {
        when(request.queryParams(GetGameRoute.GAME_ID_PARAM)).thenReturn(String.valueOf(game.getID()));

        Object value = CuT.handle(request, response);
        Message message = gson.fromJson(value.toString(), Message.class);
        // checks if message has the string "[Hint]"
        String expected = "[Hint]";
        assertEquals(expected, message.getText().substring(0, expected.length()));

        BoardView board = game.redPlayerBoard();
        for (Row row : board) {
            for (Space space : row) {
                space.setPiece(null);
            }
        }

        value = CuT.handle(request, response);
        // should NOT exist (no pieces on the board)
        message = gson.fromJson(value.toString(), Message.class);;
        assertEquals(message.getText(), Message.error("No move can be found.").getText());

        // mock pieces on board
        Piece redPiece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        board.getRow(5).getSpace(0).setPiece(redPiece);
        Piece whitePiece = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        board.getRow(4).getSpace(3).setPiece(whitePiece);

        value = CuT.handle(request, response);
        message = gson.fromJson(value.toString(), Message.class);
        // checks if message has the string "[Hint]", which is now a jump move
        assertEquals(expected, message.getText().substring(0, expected.length()));

    }
}
