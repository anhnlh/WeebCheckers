package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import spark.*;

import com.webcheckers.app.PlayerLobby;

/**
 * The {@code POST /signout} route handler.
 *
 * @author Rhamsez Thevenin
 */
public class PostSignOutRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    // parameter initializations
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code POST /signout} route handler.
     *
     * @param templateEngine
     *    template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the {@code playerLobby} or {@code templateEngine} parameter is null
     */
    public PostSignOutRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Handles the sign out request.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign In page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignOutRoute is invoked.");

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // start session and get current user's name
        final Session httpSession = request.session();
        String name = httpSession.attribute(PostSignInRoute.SESSION_ATTR);

        // remove player from lobby and change session player to null
        playerLobby.removePlayer(name);
        httpSession.attribute(GetHomeRoute.CURRENT_USER_ATTR, null);

        // variables for home.ftl set
        vm.put(GetHomeRoute.TITLE_ATTR, "Welcome!");
        vm.put(GetHomeRoute.ACTIVE_PLAYER_COUNT_ATTR, playerLobby.activePlayersMessage());
        
        // render
        return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
    }

}
