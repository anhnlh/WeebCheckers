package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private final HashMap<String, Game> gameMap;
    private final Gson gson;

    public PostSubmitTurnRoute(HashMap<String, Game> gameMap, Gson gson) {
        this.gameMap = gameMap;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostSubmitTurnRoute has been invoked.");

        String gameID = request.queryParams(GetGameRoute.GAME_ID_PARAM);
        Game game = gameMap.get(gameID);

        // switch turns
        if (game.isRedPlayerTurn()) {
            game.setPlayerInTurn(game.getWhitePlayer());
        } else {
            game.setPlayerInTurn(game.getRedPlayer());
        }

        // returns false when there is still a jump move possible
        if (game.makeMove()) {
            // TODO: out of moves case
            return gson.toJson(Message.info("Turn submitted."));
        } else {
            return gson.toJson(Message.error("Possible jump move detected. You must play all jump moves."));
        }
    }
}
