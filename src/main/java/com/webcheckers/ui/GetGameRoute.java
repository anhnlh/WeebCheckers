package com.webcheckers.ui;

import java.util.Objects;

import spark.*;

/**
 * The {@code GET /game} route handler.
 */
public class GetGameRoute implements Route {
    // Values used in the view-model map for rendering the game view.
    static final String TITLE = "title";
    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;

    /**
    * The constructor for the {@code GET /game} route handler.
    *
    * @param templateEngine
    *    The {@link TemplateEngine} used for rendering page HTML.
    */
    GetGameRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine is required");

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
     *  the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        return null;
    }
}
