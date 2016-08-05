package pptik.startup.ghvmobile;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GIGABYTE on 20/06/2016.
 */
public class GetRole{
    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";
    public static final String LEVEL_ID = "roleId";
    public static final String BSTS_ID = "userId";
    public static final String USER_ID="UsErId";
    SharedPreferences prefs;
    public String roleid;
    private  Context context;

    public  GetRole(Context _context){
        context = _context;
    }

    public String getrole(){

        prefs = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        roleid=prefs.getString(LEVEL_ID, "");

        return roleid;
    }
}
