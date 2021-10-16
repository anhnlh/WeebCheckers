package com.webcheckers.ui;

import java.util.*;
import spark.*;

import static spark.Spark.halt;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.util.Message;

public class PostSignInRoute implements Route {

    //freemarker variables
    private final String TITLE = "title";
    private final String ERROR = "error";
    private final String USER_ID = "userID";

    //error message
    private static final Message INVALID_NAME = Message.error("Invalid Request. Please try again.");

    // session id
    static final String SESSION_ATTR = "id";

    //parameter initializations
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code POST /signin} route handler.
     *
     * @param templateEngine
     *    template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the {@code playerLobby} or {@code templateEngine} parameter is null
     */
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    //
    // TemplateViewRoute method
    //

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // retrieve the HTTP session
        final Session httpSession = request.session();

        // Store unique attribute for player
        String name = request.queryParams(USER_ID);
        name = name.trim();
        name = name.replaceAll(" ", "_");

        httpSession.attribute(SESSION_ATTR, name);


        if(playerLobby.addPlayer(name)) { 
            // setting home.ftl variables & getting session player
            // Stores current user into session
            httpSession.attribute(GetHomeRoute.CURRENT_USER, playerLobby.getPlayer(name));
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        } else {
            // give an error if invalid name is given
            vm.put(TITLE, "Sign in error.");
            vm.put(ERROR, INVALID_NAME);
            return templateEngine.render(new ModelAndView(vm, GetSignInRoute.VIEW_NAME));
        }

    }

}