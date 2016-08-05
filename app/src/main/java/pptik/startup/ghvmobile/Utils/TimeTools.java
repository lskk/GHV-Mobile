package pptik.startup.ghvmobile.Utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTools {

    public static String getCurrentTime(){
        return  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    }

}
