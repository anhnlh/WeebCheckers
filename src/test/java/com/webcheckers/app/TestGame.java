package com.webcheckers.app;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Tests the Application-tier Game class
 * @author Anh Nguyen
 */
@Tag("Application-tier")
@Testable
public class TestGame {

    /**
     * The component-under-test (CuT)
     */
    private Game CuT;

    // Values for testing purposes
    private Player redPlayer;
    private Player whitePlayer;
    private BoardView board;

    private static Message validSimpleMoveMessage;
    private static Message validJumpMoveMessage;
    private static Message invalidMoveMessage;
    private static Message possibleJumpMoveMessage;

    @BeforeEach
    public void setup() {
        redPlayer = new Player("Player 1");
        whitePlayer = new Player("Player 2");
        board = new BoardView();

        validSimpleMoveMessage = Message.info("Valid simple move.");
        validJumpMoveMessage = Message.info("Valid jump move.");
        invalidMoveMessage = Message.error("Invalid move.");
        possibleJumpMoveMessage = Message.error("Jump move available. Must make jump moves.");

        CuT = new Game(redPlayer, whitePlayer);
    }

    /**
     * Tests Game's constructor {@link Game#Game(Player, Player)}
     */
    @Test
    public void testCreateGame() {
        new Game(redPlayer, whitePlayer);
    }

    /**
     * Tests {@link Game#getRedPlayer()}
     */
    @Test
    public void testGetRedPlayer() {
        assertEquals(CuT.getRedPlayer(), redPlayer);
    }

    /**
     * Tests {@link Game#getWhitePlayer()}
     */
    @Test
    public void testGetWhitePlayer() {
        assertEquals(CuT.getWhitePlayer(), whitePlayer);
    }

    /**
     * Tests {@link Game#getID()}
     */
    @Test
    public void testGetID() {
        assertEquals(CuT.getID(), Objects.hash(redPlayer, whitePlayer));
    }

    /**
     * Tests {@link Game#isRedPlayer(Player)}
     */
    @Test
    public void testIsRedPlayer() {
        assertTrue(CuT.isRedPlayer(redPlayer));
        assertFalse(CuT.isRedPlayer(whitePlayer));
    }

    /**
     * Tests {@link Game#redPlayerBoard()}
     */
    @Test
    public void testRedPlayerBoard() {
        assertEquals(CuT.redPlayerBoard(), board);
    }

    /**
     * Tests {@link Game#whitePlayerBoard()}
     */
    @Test
    public void testWhitePlayerBoard() {
        List<Row> whiteBoard = new ArrayList<>();
        // iterate backward to build board backward for orientation
        for (int r = BoardView.BOARD_LENGTH - 1; r >= 0; r--) {
            Row row = new Row(board.getRow(r).getIndex());
            for (int c = BoardView.BOARD_LENGTH - 1; c >= 0; c--) {
                Space space = board.getRow(r).getSpace(c);
                row.addSpace(space);
            }
            whiteBoard.add(row);
        }
        BoardView whitePlayerBoard = new BoardView(whiteBoard);

        assertEquals(CuT.whitePlayerBoard(), whitePlayerBoard, "White's boards don't match");
    }

    /**
     * Tests {@link Game#isRedPlayerTurn()}
     */
    @Test
    public void testIsRedPlayerTurn() {
        // first turn red player
        assertTrue(CuT.isRedPlayerTurn());
        CuT.setPlayerInTurn(whitePlayer);
        assertFalse(CuT.isRedPlayerTurn());
    }

    /**
     * Tests {@link Game#isGameOver()}
     */
    @Test
    public void testIsGameOver() {
        assertFalse(CuT.isGameOver());
        CuT.setGameOver();
        assertTrue(CuT.isGameOver());
    }

    /**
     * Tests {@link Game#setGameOverMessage(String)}
     */
    @Test
    public void testGameOverMessage() {
        // message is null in the beginning
        assertNull(CuT.getGameOverMessage());
        CuT.setGameOverMessage("test");
        assertEquals(CuT.getGameOverMessage(), "test");
    }

    /**
     * Tests simple move check in {@link Game}
     */
    @Test
    public void testIsSimpleMove() {
        // Moves may be impossible, so only purpose is for coverage
        // red player's turn
        // type is null because validateMove() sets it
        Move validMove1 = new Move(new Position(5, 0), new Position(4, 1), null);
        Move validMove2 = new Move(new Position(5, 2), new Position(4, 1), null);

        // violates rule
        Move invalidMove1 = new Move(new Position(5, 0), new Position(4, 0), null);
        Move invalidMove2 = new Move(new Position(5, 0), new Position(5, 0), null);
        Move invalidMove3 = new Move(new Position(5, 0), new Position(5, 1), null);
        Move invalidMove4 = new Move(new Position(5, 0), new Position(6, 1), null);

        // exists a piece at the end position
        Move invalidMove5 = new Move(new Position(5, 0), new Position(2,1), null);

        // not a piece at the start position
        Move invalidMove6 = new Move(new Position(6, 0), new Position(2,1), null);

        // validateMove() is a friendly here
        assertEquals(CuT.validateMove(validMove1).getText(), validSimpleMoveMessage.getText());
        assertEquals(CuT.validateMove(validMove2).getText(), validSimpleMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove1).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove2).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove3).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove4).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove5).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove6).getText(), invalidMoveMessage.getText());

        // white player's turn
        CuT.setPlayerInTurn(whitePlayer);

        validMove1 = new Move(new Position(2, 1), new Position(3,0), null);
        validMove2 = new Move(new Position(2, 1), new Position(3,2), null);

        // violates rule
        invalidMove1 = new Move(new Position(2, 1), new Position(1, 1), null);
        invalidMove2 = new Move(new Position(2, 1), new Position(2, 1), null);
        invalidMove3 = new Move(new Position(2, 1), new Position(2, 0), null);
        invalidMove4 = new Move(new Position(2, 1), new Position(3, 1), null);

        // exists a piece at the end position
        invalidMove5 = new Move(new Position(2, 1), new Position(5, 0), null);

        // not a piece at the start position
        invalidMove6 = new Move(new Position(0, 0), new Position(5, 0), null);

        assertEquals(CuT.validateMove(validMove1).getText(), validSimpleMoveMessage.getText());
        assertEquals(CuT.validateMove(validMove2).getText(), validSimpleMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove1).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove2).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove3).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove4).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove5).getText(), invalidMoveMessage.getText());
        assertEquals(CuT.validateMove(invalidMove6).getText(), invalidMoveMessage.getText());

        // tests KING-typed piece
        BoardView b = CuT.redPlayerBoard();
        b.getRow(2).getSpace(1).getPiece().setType(Piece.Type.KING);
        b.getRow(1).getSpace(0).setPiece(null);
        b.getRow(1).getSpace(2).setPiece(null);

        Move validMove3 = new Move(new Position(2, 1), new Position(1, 0), null);
        Move validMove4 = new Move(new Position(2, 1), new Position(1, 2), null);

        assertEquals(CuT.validateMove(validMove1).getText(), validSimpleMoveMessage.getText());
        assertEquals(CuT.validateMove(validMove2).getText(), validSimpleMoveMessage.getText());
        assertEquals(CuT.validateMove(validMove3).getText(), validSimpleMoveMessage.getText());
        assertEquals(CuT.validateMove(validMove4).getText(), validSimpleMoveMessage.getText());

        // can be improved with more tests
    }

    /**
     * Tests jump move check for single pieces {@link Game}
     */
    @Test
    public void testIsJumpMoveSingle() {
        BoardView b = CuT.redPlayerBoard();
        b.getRow(4).getSpace(3).setPiece(b.getRow(2).getSpace(5).getPiece());
        b.getRow(2).getSpace(5).setPiece(null);
        // type is null because validateMove() sets it
        Move validJump = new Move(new Position(5, 2), new Position(3, 4), null);

        assertEquals(CuT.validateMove(validJump).getText(), validJumpMoveMessage.getText());

        // multiple jumps
        b.getRow(2).getSpace(5).setPiece(b.getRow(1).getSpace(6).getPiece());
        b.getRow(1).getSpace(6).setPiece(null);
        // makes the intermediate jump
        Move tmp = new Move(new Position(5, 2), new Position(3, 4), null);
        CuT.validateMove(tmp);

        validJump = new Move(new Position(3, 4), new Position(1, 6), null);

        assertEquals(CuT.validateMove(validJump).getText(), validJumpMoveMessage.getText());
    }

    /**
     * Tests jump move check for king pieces {@link Game}
     */
    @Test
    public void testIsJumpMoveKing() {
        // setup
        BoardView b = CuT.redPlayerBoard();
        b.getRow(4).getSpace(3).setPiece(b.getRow(2).getSpace(5).getPiece());
        b.getRow(2).getSpace(5).setPiece(null);
        b.getRow(2).getSpace(5).setPiece(b.getRow(1).getSpace(6).getPiece());
        b.getRow(1).getSpace(6).setPiece(null);
        b.getRow(1).getSpace(6).setPiece(b.getRow(5).getSpace(2).getPiece());
        b.getRow(5).getSpace(2).setPiece(null);
        b.getRow(1).getSpace(6).getPiece().setType(Piece.Type.KING);
        Move tmp = new Move(new Position(1, 6), new Position(3, 4), null);
        CuT.validateMove(tmp);

        Move validJump = new Move(new Position(3, 4), new Position(5, 2), null);

        assertEquals(CuT.validateMove(validJump).getText(), validJumpMoveMessage.getText());
    }

    /**
     * Tests the method that checks for additional jump moves
     */
    @Test
    public void testAllPossibleJumpMoves() {
        // setup
        BoardView b = CuT.redPlayerBoard();
        b.getRow(4).getSpace(3).setPiece(b.getRow(2).getSpace(5).getPiece());
        b.getRow(2).getSpace(5).setPiece(null);

        Move invalidMove = new Move(new Position(5, 0), new Position(4, 1), null);
        assertEquals(CuT.validateMove(invalidMove).getText(), possibleJumpMoveMessage.getText());
    }

    /**
     * Tests the method that checks for additional jump moves from the latest move while submitting turn
     */
    @Test
    public void testSinglePossibleJumpMove() {
        // setup
        BoardView b = CuT.redPlayerBoard();
        b.getRow(4).getSpace(3).setPiece(b.getRow(2).getSpace(5).getPiece());
        b.getRow(2).getSpace(5).setPiece(null);
        b.getRow(2).getSpace(5).setPiece(b.getRow(1).getSpace(6).getPiece());
        b.getRow(1).getSpace(6).setPiece(null);

        Move tmp = new Move(new Position(5, 2), new Position(3, 4), null);
        CuT.validateMove(tmp);

        // false means makeMove() invoked singlePossibleJumpMove()
        assertFalse(CuT.makeMove());

        Move validJump = new Move(new Position(3, 4), new Position(1, 6), null);
        CuT.validateMove(validJump);

        // true means singlePossibleJumpMove() returned false
        assertTrue(CuT.makeMove());
    }

    /**
     * Tests the validate move method
     * (Doesn't actually test anything since other tests basically tested it)
     */
    @Test
    public void testValidateMove() {
        // no need since all other tests uses it and basically tested it
    }

    /**
     * Tests {@link Game#makeMove()}
     */
    @Test
    public void testMakeMove() {
        // setup for white player
        CuT.setPlayerInTurn(whitePlayer);
        BoardView b = CuT.redPlayerBoard();
        b.getRow(3).getSpace(4).setPiece(b.getRow(5).getSpace(2).getPiece());
        b.getRow(5).getSpace(2).setPiece(null);

        Move tmp = new Move(new Position(2, 5), new Position(4, 3), null);
        CuT.validateMove(tmp);

        assertTrue(CuT.makeMove());
        assertEquals(b.getNumRedPieces(), 11);

        // setup white piece going to bottom row, i.e. becoming king piece
        b.getRow(5).getSpace(2).setPiece(b.getRow(4).getSpace(3).getPiece());
        b.getRow(4).getSpace(3).setPiece(null);
        b.getRow(7).getSpace(0).setPiece(null);

        tmp = new Move(new Position(5, 2), new Position(7, 0), null);
        CuT.validateMove(tmp);

        assertTrue(CuT.makeMove());
        assertTrue(b.getRow(7).getSpace(0).getPiece().isKing());
    }

    /**
     * Tests the winning case of {@link Game#makeMove()} for the red player
     */
    @Test
    public void testMakeMoveWinRed() {
        // clear board
        BoardView b = CuT.redPlayerBoard();
        for (Row row : b) {
            for (Space space : row) {
                space.setPiece(null);
            }
        }
        // decrease num pieces
        for (int i = 0; i < 11; i++) {
            b.decreaseNumWhitePieces();
            b.decreaseNumRedPieces();
        }

        // setup last winning move for red
        Piece red = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        b.getRow(2).getSpace(5).setPiece(red);
        Piece white = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        b.getRow(1).getSpace(6).setPiece(white);

        Move winningMove = new Move(new Position(2, 5), new Position(0, 7), null);
        CuT.validateMove(winningMove);
        CuT.makeMove();

        assertTrue(CuT.isGameOver());
        assertEquals(CuT.getGameOverMessage(), redPlayer + "won! " + whitePlayer + "ran out of pieces.");
    }

    /**
     * Tests the winning case of {@link Game#makeMove()} for the white player
     */
    @Test
    public void testMakeMoveWinWhite() {
        // set white player's turn
        CuT.setPlayerInTurn(whitePlayer);
        // clear board
        BoardView b = CuT.redPlayerBoard();
        for (Row row : b) {
            for (Space space : row) {
                space.setPiece(null);
            }
        }
        // decrease num pieces
        for (int i = 0; i < 11; i++) {
            b.decreaseNumWhitePieces();
            b.decreaseNumRedPieces();
        }

        // setup last winning move for red
        Piece white = new Piece(Piece.Type.KING, Piece.Color.WHITE);
        b.getRow(7).getSpace(7).setPiece(white);
        Piece red = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        b.getRow(6).getSpace(6).setPiece(red);

        Move winningMove = new Move(new Position(7, 7), new Position(5, 5), null);
        CuT.validateMove(winningMove);
        CuT.makeMove();

        assertTrue(CuT.isGameOver());
        assertEquals(CuT.getGameOverMessage(), whitePlayer + "won! " + redPlayer + "ran out of pieces.");
    }

    /**
     * Tests {@link Game#backupMove()}
     */
    @Test
    public void testBackupMove() {
        Move tmp = new Move(new Position(5, 0), new Position(4, 1), null);

        // no move to backup
        assertFalse(CuT.backupMove());
        // executes the move
        CuT.validateMove(tmp);
        // backup successful
        assertTrue(CuT.backupMove());
        // no move to backup
        assertFalse(CuT.backupMove());
        // no move to make
        assertFalse(CuT.makeMove());
    }

    /**
     * Tests a certain case of multiple jump moves
     */
    @Test
    public void testThreeJumpMoves() {
        // clear board
        BoardView b = CuT.redPlayerBoard();
        for (Row row : b) {
            for (Space space : row) {
                space.setPiece(null);
            }
        }
        // decrease num pieces
//        for (int i = 0; i < 11; i++) {
//            b.decreaseNumWhitePieces();
//            b.decreaseNumRedPieces();
//        }

        // setup a 3-jump move for red
        Piece red = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        b.getRow(7).getSpace(0).setPiece(red);
        Piece white = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        b.getRow(6).getSpace(1).setPiece(white);
        Piece white2 = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        b.getRow(4).getSpace(3).setPiece(white2);
        Piece white3 = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        b.getRow(2).getSpace(5).setPiece(white3);

        Move move1 = new Move(new Position(7, 0), new Position(5, 2), null);
        CuT.validateMove(move1);
        Move move2 = new Move(new Position(5, 2), new Position(3, 4), null);
        CuT.validateMove(move2);
        Move move3 = new Move(new Position(3, 4), new Position(1, 6), null);
        CuT.validateMove(move3);
        CuT.makeMove();
        System.out.println(CuT.redPlayerBoard().getRow(1).getSpace(6).getPiece());
        assertEquals(CuT.redPlayerBoard().getRow(1).getSpace(6).getPiece(), red);

    }

    /**
     * Tests {@link Game#findRandomJumpMove()}
     */
    @Test
    public void testFindRandomJumpMove() {
        // setup
        BoardView b = CuT.redPlayerBoard();
        Piece white = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        b.getRow(4).getSpace(1).setPiece(white);

        Move expected = new Move(new Position(5, 2), new Position(3, 0), Move.MoveType.JUMP);
        assertEquals(expected.toString(), CuT.findRandomJumpMove().toString());
    }

    /**
     * Tests {@link Game#findRandomSimpleMove()}
     */
    @Test
    public void testFindRandomSimpleMove() {
        // clear board
        BoardView b = CuT.redPlayerBoard();
        for (Row row : b) {
            for (Space space : row) {
                space.setPiece(null);
            }
        }

        Piece red = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        b.getRow(7).getSpace(0).setPiece(red);
        Move expected = new Move(new Position(7, 0), new Position(6, 1), Move.MoveType.SIMPLE);
        assertEquals(expected.toString(), CuT.findRandomSimpleMove().toString());
    }
}
