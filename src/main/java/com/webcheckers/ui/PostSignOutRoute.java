package com.webcheckers.ui;

import java.util.*;
import spark.*;

import com.webcheckers.app.PlayerLobby;


/**
 * The constructor for the {@code POST /signout} route handler.
 *
 * @param templateEngine
 *    template engine to use for rendering HTML page
 *
 * @throws NullPointerException
 *    when the {@code playerLobby} or {@code templateEngine} parameter is null
 */
public class PostSignOutRoute implements Route{

    static final String SESSION_ATTR = "id";
    
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    public PostSignOutRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // start session and get current user's name
        final Session httpSession = request.session();
        String name = httpSession.attribute(PostSignInRoute.SESSION_ATTR);

        // remove player from lobby and change session player to null
        playerLobby.removePlayer(name);
        httpSession.attribute(GetHomeRoute.CURRENT_USER, null);

        // variables for home.ftl set
        vm.put(GetHomeRoute.TITLE, "Welcome!");
        vm.put(GetHomeRoute.ACTIVE_PLAYER_COUNT, playerLobby.size());
        
        // render
        return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
    }

}
