package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.model.Move;
import com.webcheckers.util.Message;
import spark.*;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * The UI Controller to POST /validateMove
 * @author Anh Nguyen
 */
public class PostValidateMoveRoute implements Route {

    //Loggers for postValidateMoveRoute
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    //action data parameter for postValidateMoveRoute
    private static final String actionDataParam = "actionData";
    //map to store the game and the move
    private final HashMap<String, Game> gameMap;
    //gson for postValidateMoveRoute
    private final Gson gson;

    /**
     * Constructor for the PostValidateMoveRoute
     * @param gameMap the map to store the game and the move
     */
    public PostValidateMoveRoute(HashMap<String, Game> gameMap, Gson gson) {
        this.gameMap = gameMap;
        this.gson = gson;
    }
    
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");

        String param = request.queryParams(actionDataParam);
        String gameID = request.queryParams(GetGameRoute.GAME_ID_PARAM);
        Game game = gameMap.get(gameID);
        Move move = gson.fromJson(param, Move.class);
        Message message = game.validateMove(move);

        return gson.toJson(message);
    }
}
