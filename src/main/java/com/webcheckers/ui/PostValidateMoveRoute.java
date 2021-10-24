package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.model.Move;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private static final String ACTION_DATA_PARAM = "actionData";

    private final HashMap<String, Game> gameMap;
    private final Gson gson;

    public PostValidateMoveRoute(HashMap<String, Game> gameMap, Gson gson) {
        this.gameMap = gameMap;
        this.gson = gson;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetGameRoute is invoked.");

        String param = request.queryParams(ACTION_DATA_PARAM);
        String gameID = request.queryParams(GetGameRoute.GAME_ID_PARAM);
        Game game = gameMap.get(gameID);
        Move move = gson.fromJson(param, Move.class);
        Message message = game.validateMove(move);

        return gson.toJson(message);
    }
}
