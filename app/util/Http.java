package util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import play.mvc.Controller;

public class Http extends Controller {
    
    public static String GET(String tag) {
        try {
            return request().queryString().get(tag)[0];
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String urlEncode(String str) {
        try { 
            return URLEncoder.encode(str, "UTF-8"); 
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    
    public static String urlDecode(String str) {
        try { 
            return URLDecoder.decode(str, "UTF-8"); 
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
