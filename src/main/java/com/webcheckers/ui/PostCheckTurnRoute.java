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

/**
 * The UI Controller to process the POST request made to the
 * /checkTurn route.
 */
public class PostCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private final HashMap<String, Game> gameMap;
    private final Gson gson;

    public PostCheckTurnRoute(HashMap<String, Game> gameMap, Gson gson) {
        this.gameMap = gameMap;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostCheckTurnRoute has been invoked.");

        final Session httpSession = request.session();
        Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER_ATTR);

        String gameID = request.queryParams(GetGameRoute.GAME_ID_PARAM);
        Game game = gameMap.get(gameID);

        Message message;
        if ((game.isRedPlayer(player) && game.isRedPlayerTurn()) ||
            (!game.isRedPlayer(player) && !game.isRedPlayerTurn())) {
            message = Message.info("true");
        } else {
            message = Message.info("false");
        }

        return gson.toJson(message);
    }
}
