package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import static spark.Spark.halt;


/**
 * The {@code GET /game} route handler.
 *
 * @author Anh Nguyen
 */
public class GetGameRoute implements Route {
    // Values used in the view-model map for rendering the game view.
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    static final String VIEW_NAME = "game.ftl";
    static final String TITLE_ATTR = "title";
    static final String BOARD_ATTR = "board";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String MODE_OPTIONS_ATTR = "modeOptions";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String OPPONENT_ATTR = "opponent";
    static final String GAME_ID_PARAM = "gameID";

    private final PlayerLobby playerLobby;
    private HashMap<String, Game> gameMap;

    public enum mode {
        PLAY
    }

    public enum activeColor {
        RED, WHITE
    }


    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code GET /game} route handler.
     *
     * @param templateEngine The {@link TemplateEngine} used for rendering page HTML.
     */
    GetGameRoute(HashMap<String, Game> gameMap, PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine is required");

        this.gameMap = gameMap;
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Renders the WebCheckers Game page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER);

        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, "Welcome!");

        if (player != null) {
            String gameID = request.queryParams(GAME_ID_PARAM);
            vm.put(GetHomeRoute.CURRENT_USER, player);
            if (gameID == null) {
                if (!player.isPlaying()) {
                    Player opponent = playerLobby.getPlayer(request.queryParams(OPPONENT_ATTR));
                    if (!opponent.isPlaying()) {
                        Game game = new Game(player, opponent);
                        player.setPlaying(true);
                        opponent.setPlaying(true);
                        gameID = String.valueOf(game.getID());
                        gameMap.put(gameID, game);
                        response.redirect(WebServer.GAME_URL + "?gameID=" + gameID);
                    } else {
                        response.redirect(WebServer.HOME_URL);
                        halt();
                        return null;
                    }
                } else {
                    response.redirect(WebServer.HOME_URL);
                    halt();
                    return null;
                }
            } else {
                Game game = gameMap.get(gameID);
                vm.put(VIEW_MODE_ATTR, mode.PLAY);
                vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
                vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
                if (game.isRedPlayer(player)) {
                    System.out.println("Red Player:" + player);
                    vm.put(BOARD_ATTR, game.redPlayerBoard());
                } else {
                    System.out.println("White Player:" + player);
                    vm.put(BOARD_ATTR, game.whitePlayerBoard());
                }
                vm.put(ACTIVE_COLOR_ATTR, activeColor.RED);
            }
        } else {
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }



        LOG.finer("GetGameRoute is invoked.");
        //

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
