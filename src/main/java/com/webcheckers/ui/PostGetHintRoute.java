package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.model.Move;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.logging.Logger;

public class PostGetHintRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private final HashMap<String, Game> gameMap;
    private final Gson gson;

    public PostGetHintRoute(HashMap<String, Game> gameMap, Gson gson) {
        this.gameMap = gameMap;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostGetHintRoute has been invoked.");

        String gameID = request.queryParams(GetGameRoute.GAME_ID_PARAM);
        Game game = gameMap.get(gameID);

        Message message;
        // Find jump moves first
        Move move = game.findRandomJumpMove();
        if (move == null) {
            // If no jump moves, find simple moves
            move = game.findRandomSimpleMove();
        }
        if (move != null) {
            message = Message.info("[Hint] " + move);
        } else {
            message = Message.error("No move can be found.");
        }

        return gson.toJson(message);
    }
}
