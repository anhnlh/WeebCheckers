package com.webcheckers.ui;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.util.Message;

import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static spark.Spark.halt;

public class PostSignInRoute implements Route {

  
    // private final String SIGN_IN = "signIn";
    private final String TAKEN = "nameTaken";
    private final String TITLE = "title";
    private final String USER_ID = "userID";
    private static final Message INVALID_NAME = Message.error("Invalid Request. Please try again.");

    static final String SESSION_ATTR = "id";

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code POST /guess} route handler.
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
        //
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
        final String name = request.queryParams(USER_ID);

        // retrieve the HTTP session
        final Session httpSession = request.session();
        // Store unique attribute for player
        httpSession.attribute(SESSION_ATTR, name);

        vm.put(TITLE, "Try Again.");

        if(playerLobby.addPlayer(name)) { 
            vm.put(TAKEN, false);
            
            // Stores current user into session
            httpSession.attribute("currentUser", playerLobby.getPlayer(name));

            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        } else {
            vm.put(TAKEN, true);
            vm.put("error", INVALID_NAME);
            return templateEngine.render(new ModelAndView(vm, GetSignInRoute.VIEW_NAME));
        }

    }

}