package com.webcheckers.ui;

import java.util.*;
import java.util.logging.*;

import spark.*;

/**
 * The {@code GET /help} route handler.
 *
 * @author Sierra Tran
 */
public class GetHelpRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHelpRoute.class.getName());

    // freemarker file
    public static final String VIEW_NAME = "help.ftl";

    // freemarker variables
    public static final String TITLE_ATTR = "title";

    // parameter initializations
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code GET /help} route handler.
     *
     * @param templateEngine The {@link TemplateEngine} used for rendering page HTML.
     */
    public GetHelpRoute(TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine is required");

        this.templateEngine = templateEngine;

        LOG.config("GetHelpRoute is initialized.");
    }

    /**
     * Renders the WebCheckers Help page.
     * 
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) {
        
        LOG.finer("GetHelpRoute is invoked.");

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // display help title
        vm.put(TITLE_ATTR, "Help");
        
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
