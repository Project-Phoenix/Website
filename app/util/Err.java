package util;

import play.mvc.Controller;
import play.mvc.Result;
import views.html._Error;

public class Err extends Controller{
    public static Result displayError(int errorCode, String errorText) {
        return ok(_Error.render(errorCode,errorText));
    }
    public static Result displayError(int errorCode) {
        return ok(_Error.render(errorCode,"An error occured! :'("));
    }
}
