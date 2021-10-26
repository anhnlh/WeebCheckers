package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.HashMap;
import java.util.logging.Logger;

public class PostResignGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private final HashMap<String, Game> gameMap;
    private final Gson gson;

    public PostResignGameRoute(HashMap<String, Game> gameMap, Gson gson) {
        this.gameMap = gameMap;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostResignGameRoute has been invoked");

        final Session httpSession = request.session();
        Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER_ATTR);

        String gameID = request.queryParams(GetGameRoute.GAME_ID_PARAM);
        Game game = gameMap.get(gameID);

        game.setGameOver();
        game.setGameOverMessage(player + " has resigned.");

        // document says there can be an error, but I don't see how that can happen
        return gson.toJson(Message.info("Resignation successful"));
    }
}
