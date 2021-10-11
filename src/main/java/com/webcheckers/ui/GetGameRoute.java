package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import spark.*;

import static spark.Spark.halt;


/**
 * The {@code GET /game} route handler.
 */
public class GetGameRoute implements Route {
    // Values used in the view-model map for rendering the game view.
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    static final String VIEW_NAME = "game.ftl";
    static final String TITLE = "title";
    static final String BOARD = "board";
    static final String CURRENT_USER = "currentUser";
    static final String VIEW_MODE = "viewMode";
    static final String MODE_OPTIONS = "modeOptions";
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String OPPONENT = "opponent";

    private PlayerLobby playerLobby;

    private enum MODE {
        PLAY
    }

    private enum ACTIVE_COLOR {
        RED
    }


    private final TemplateEngine templateEngine;

    /**
    * The constructor for the {@code GET /game} route handler.
    *
    * @param templateEngine
    *    The {@link TemplateEngine} used for rendering page HTML.
    */
    GetGameRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine is required");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Renders the WebCheckers Game page.
     * 
     * @param request
     *  the HTTP request
     * 
     * @param response
     *  the HTTP response
     * 
     * @return 
     *  the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) {
//        LOG.finer("GetGameRoute is invoked.");
//        final Session httpSession = request.session();
//        Map<String,Object> vm = new HashMap<>();
//        final Map<String, Object> modeOptions = new HashMap<>(2);
//        vm.put(TITLE_ATTR, DESCRIPTION);
//        final Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER);
//
//        // if game object is not null
//            // build the View-Model
//            final Map<String, Object> vm = new HashMap<>();
//            vm.put(GetHomeRoute.TITLE, TITLE);
//            // render the Game view
//            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
//        // else
//            // response.redirect(WebServer.HOME_URL);
//            // halt();
//            // return null;
        final Session httpSession = request.session();

        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE, "Welcome!");

        vm.put(GetHomeRoute.CURRENT_USER, httpSession.attribute(GetHomeRoute.CURRENT_USER));

//        httpSession.attribute(GetHomeRoute.PLAYER_LOBBY_KEY, playerLobby);
//        vm.put(GetHomeRoute.PLAYER_LOBBY_KEY, httpSession.attribute(GetHomeRoute.PLAYER_LOBBY_KEY));

        if(httpSession.attribute(BOARD)==null) {
            httpSession.attribute(BOARD, new BoardView());
        }
        vm.put(BOARD, httpSession.attribute(BOARD));

        httpSession.attribute(VIEW_MODE, MODE.PLAY);
        vm.put(VIEW_MODE, httpSession.attribute(VIEW_MODE));

        httpSession.attribute(RED_PLAYER, httpSession.attribute(GetHomeRoute.CURRENT_USER));
        vm.put(RED_PLAYER, httpSession.attribute(RED_PLAYER));

        Player opponent = playerLobby.getPlayer(request.queryParams(OPPONENT));
        httpSession.attribute(WHITE_PLAYER, opponent);
        vm.put(WHITE_PLAYER, httpSession.attribute(WHITE_PLAYER));

        httpSession.attribute(ACTIVE_COLOR_ATTR, ACTIVE_COLOR.RED);
        vm.put(ACTIVE_COLOR_ATTR, httpSession.attribute(ACTIVE_COLOR_ATTR));

        LOG.finer("GetGameRoute is invoked.");
        //

        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}
