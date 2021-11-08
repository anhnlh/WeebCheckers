package com.webcheckers.ui;

import freemarker.log.Logger;
import spark.*;

public class GetHelpRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    // freemarker file
    public static final String VIEW_NAME = "help.ftl";

    // freemarker variables


    public GetHelpRoute() {

    }

    @Override
    public Object handle(Request request, Response response) {
        return null;
    }
}
