package pptik.startup.ghvmobile;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by CLAY on 11/10/2016.
 */

public class GHVApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
       AppEventsLogger.activateApp(this);
    }
}
