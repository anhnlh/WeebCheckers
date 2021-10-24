package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private static final String ACTION_DATA_PARAM = "actionData";

    private final HashMap<String, Game> gameMap;
    private final TemplateEngine templateEngine;
    private final Gson gson;

    public PostValidateMoveRoute(HashMap<String, Game> gameMap, TemplateEngine templateEngine, Gson gson) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.gameMap = gameMap;
        this.templateEngine = templateEngine;
        this.gson = gson;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetGameRoute is invoked.");

        String param = request.queryParams(ACTION_DATA_PARAM);
        String gameID = request.queryParams(GetGameRoute.GAME_ID_PARAM);
        Game game = gameMap.get(gameID); // used to check move
        Move move = gson.fromJson(param, Move.class); // passed into check move method
        Message message = Message.error("invalid move"); // example usage

        return new Gson().toJson(message);
    }
}
