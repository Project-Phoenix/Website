package util;

import play.mvc.Controller;

public class Http extends Controller {
    
    public static String GET(String tag) {
        try {
            return request().queryString().get(tag)[0];
        } catch (Exception e) {
            return null;
        }
    }

}
