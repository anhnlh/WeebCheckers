package com.webcheckers.ui;

import java.util.*;
import java.util.logging.*;

import spark.*;

/**
 * The UI Controller to GET the Help page.
 *
 * @author Sierra Tran and Rhamsez Thevenin
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
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetHelpRoute(TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine is required");

        this.templateEngine = templateEngine;

        LOG.config("GetHelpRoute is initialized.");
    }

    /**
     * Render the WebCheckers Help page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Help page
     */
    @Override
    public Object handle(Request request, Response response) {
        
        LOG.finer("GetHelpRoute is invoked.");

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // display help title for help menu
        vm.put(TITLE_ATTR, "Help");

        // render the View
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
