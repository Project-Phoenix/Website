package util;

import play.mvc.Controller;
import play.mvc.Result;
import bootstrap.html.error;

public class Err extends Controller{
    public static Result displayError(int errorCode, String errorText) {
        return ok(error.render(errorCode,errorText));
    }
    public static Result displayError(int errorCode) {
        return ok(error.render(errorCode,"An error occured! :'("));
    }
}
