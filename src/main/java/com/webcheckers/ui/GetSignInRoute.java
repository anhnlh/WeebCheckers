package com.webcheckers.ui;

import java.util.*;
import java.util.logging.*;

import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Sign In page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    // freemarker file
    public static final String VIEW_NAME = "signin.ftl";

    // freemarker variables
    public static final String TITLE_ATTR = "title";
    private static final String MESSAGE_ATTR = "message";

    // message
    private static final Message SIGN_IN_MSG = Message.info("Sign-in into the world of Web Checkers!");

    // parameter initializations
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignInRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine is required");
        
        this.templateEngine = templateEngine;

        LOG.config("GetSignInRoute is initialized.");
    }

    /**
     * Render the WebCheckers Sign in page.
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

        LOG.finer("GetSignInRoute is invoked.");

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // display welcome title for sign in
        vm.put(TITLE_ATTR, "Welcome!");

        // display a user message in the Sign In page
        vm.put(MESSAGE_ATTR, SIGN_IN_MSG);
        
        // render the View
        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
    }
}
