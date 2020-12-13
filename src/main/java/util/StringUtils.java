package util;

import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isUndefinedOrNull(String param) {
        param = param.trim();
        if("undefined".equals(param)) return true;
        if("null".equals(param)) return true;
        return false;
    }

    /**
     * truncat URL by slash order number from tail to head.
     * orderNum start from 1
     * @param param
     * @param orderNum
     * @return
     */
    public static String truncatURLBySlash(String param, int orderNum) {
        if(param == null) return null;
        if(param.length() <= 0) return "";
        int idx = param.length() - 1;
        while(orderNum > 0 && idx >= 0) {
            if(param.charAt(idx) == '/') {
                orderNum --;
            }
            idx--;
        }
        return param.substring(0, idx + 1);
    }
}
