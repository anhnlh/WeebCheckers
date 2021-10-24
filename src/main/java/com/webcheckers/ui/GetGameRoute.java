package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import static spark.Spark.halt;


/**
 * The {@code GET /game} route handler.
 *
 * @author Anh Nguyen
 */
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    // freemarker file
    public static final String VIEW_NAME = "game.ftl";

    // freemarker variables
    public static final String TITLE_ATTR = "title";
    public static final String BOARD_ATTR = "board";
    public static final String VIEW_MODE_ATTR = "viewMode";
    public static final String RED_PLAYER_ATTR = "redPlayer";
    public static final String WHITE_PLAYER_ATTR = "whitePlayer";
    public static final String ACTIVE_COLOR_ATTR = "activeColor";
    public static final String OPPONENT_ATTR = "opponent";
    public static final String GAME_ID_PARAM = "gameID";
    public static final String IS_GAME_OVER_ATTR = "isGameOver";
    public static final String GAME_OVER_MSG_ATTR = "gameOverMessage";
    public static final String MODE_OPTS_JSON_ATTR = "modeOptionsAsJSON";

    // message
    public static final Message OPPONENT_IN_GAME = Message.error("Opponent is in game. Try another player.");

    // parameter initializations
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    private HashMap<String, Game> gameMap;

    // enum for viewMode in game.ftl
    public enum mode {
        PLAY
    }

    // enum for activeColor in game.ftl
    public enum activeColor {
        RED, WHITE
    }

    /**
     * The constructor for the {@code GET /game} route handler.
     *
     * @param templateEngine The {@link TemplateEngine} used for rendering page HTML.
     */
    public GetGameRoute(HashMap<String, Game> gameMap, PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine is required");

        this.gameMap = gameMap;
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Renders the WebCheckers Game page.
     * Creates games on request of the red player
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) {

        LOG.finer("GetGameRoute is invoked.");

        // retrieve session
        final Session httpSession = request.session();
        Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER_ATTR);

        // start building the View-Model
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, "Welcome!");

        if (player != null) {
            String gameID = request.queryParams(GAME_ID_PARAM);
            vm.put(GetHomeRoute.CURRENT_USER_ATTR, player);

            // Creates a game if there is no game
            if (gameID == null) {
                if (!player.isPlaying()) {
                    Player opponent = playerLobby.getPlayer(request.queryParams(OPPONENT_ATTR));

                    if (!opponent.isPlaying()) {
                        // Creates a game with the opponent
                        Game game = new Game(player, opponent);
                        player.setPlaying(true);
                        opponent.setPlaying(true);
                        gameID = String.valueOf(game.getID());
                        gameMap.put(gameID, game);
                        response.redirect(WebServer.GAME_URL + "?gameID=" + gameID);
                    } else {
                        // opponent is in game, redirect to home page
                        httpSession.attribute(GetHomeRoute.ERROR_ATTR, OPPONENT_IN_GAME);
                        response.redirect(WebServer.HOME_URL);
                    }
                } else {
                    response.redirect(WebServer.HOME_URL);
                    halt();
                    return null;
                }
                halt();
                return null;
            } else {
                // Game exists, renders the /game page.
                Game game = gameMap.get(gameID);
                vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
                vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());

                if (game.isRedPlayer(player)) {
                    vm.put(BOARD_ATTR, game.redPlayerBoard());
                } else {
                    vm.put(BOARD_ATTR, game.whitePlayerBoard());
                }

                if (game.isRedPlayerTurn()) {
                    vm.put(ACTIVE_COLOR_ATTR, activeColor.RED);
                } else {
                    vm.put(ACTIVE_COLOR_ATTR, activeColor.WHITE);
                }

                vm.put(VIEW_MODE_ATTR, mode.PLAY);

//                final Map<String, Object> modeOptions = new HashMap<>(2);
//                modeOptions.put(IS_GAME_OVER_ATTR, true);
//                modeOptions.put(GAME_OVER_MSG_ATTR, "Example Game Over Message.");
//
//                Gson gson = new Gson();
//                vm.put(MODE_OPTS_JSON_ATTR, gson.toJson(modeOptions));
            }
        } else {
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
