package com.webcheckers.ui;
import com.webcheckers.app.PlayerLobby;
import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static spark.Spark.halt;

public class PostSignInRoute implements Route {
  
    private final String SIGN_IN = "signIn";
    private final String TAKEN = "nameTaken";
    private final String TITLE = "title";
    private final String NAME = "userID";

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
        final String name = request.queryParams(SIGN_IN);
        if(playerLobby.addPlayer(NAME)) { 
            vm.put(TAKEN, false);
            vm.put("title", "Signed in!");
            // retrieve the HTTP session
            final Session httpSession = request.session();
            // Store unique attribute for player
            httpSession.attribute(SESSION_ATTR, name);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        } else {
            vm.put(TAKEN, true);
            vm.put(TITLE, "Sign in!");
            return templateEngine.render(new ModelAndView(vm, GetSignInRoute.VIEW_NAME));
        }

    }

}