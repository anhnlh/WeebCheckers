package com.webcheckers.ui;

import java.util.*;
import java.util.logging.*;

import spark.*;

import com.webcheckers.util.Message;

public class GetLeaderBoardRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetLeaderBoardRoute.class.getName());

    // freemarker file
    public static final String VIEW_NAME = "leaderboard.ftl";

    // freemarker variables
    public static final String TITLE_ATTR = "title";
    private static final String MESSAGE_ATTR = "message";

    // message
    private static final Message LB_MSG = Message.info("Web Checker's Leader Board");

    // parameter initializations
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetLeaderBoardRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine is required");
        
        this.templateEngine = templateEngine;

        LOG.config("GetLeaderBoardRoute is initialized.");
    }

    /**
     * Render the WebCheckers Leader Board page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the LeaderBoard page
     */
    @Override
    public Object handle(Request request, Response response) {

        LOG.finer("GetLeaderBoardRoute is invoked.");

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // display welcome title for sign in
        vm.put(TITLE_ATTR, "Welcome!");

        // display a user message in the Sign In page
        vm.put(MESSAGE_ATTR, LB_MSG);
        
        // render the View
        return templateEngine.render(new ModelAndView(vm , "leaderboard.ftl"));
    }
}
