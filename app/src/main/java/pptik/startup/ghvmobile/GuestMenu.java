package pptik.startup.ghvmobile;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pptik.startup.ghvmobile.Support.CustomAdapter;
import pptik.startup.ghvmobile.Support.ProfileRelawan;
import pptik.startup.ghvmobile.Support.Program;
import pptik.startup.ghvmobile.setup.ApplicationConstants;
import pptik.startup.ghvmobile.Utilities.DrawerUtil;
import pptik.startup.ghvmobile.Utilities.PictureFormatTransform;
/**
 * Created by GIGABYTE on 27/06/2016.
 */

public class GuestMenu extends AppCompatActivity {
    public int currentimageindex=0;
    //    Timer timer;
//    TimerTask task;
    ImageView slidingimage;

    private int[] IMAGE_IDS = {
            R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,
            R.drawable.banner4,R.drawable.banner5,R.drawable.banner6
    };

    private ListView lv;
    private ArrayList<Program> listProgram;
    private ProgressDialog pDialog;
    private Context applicationContext;
    private Dialog addingClassDialog;
    private EditText inputCode;
    private CustomAdapter mAdapter;
    private TextView bt,bt2;
    public int user_ID;
    SharedPreferences prefs;
    public boolean status;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        applicationContext = this.getApplicationContext();

        bt=(TextView) findViewById(R.id.btn_jadi_relawan) ;
        bt2=(TextView) findViewById(R.id.btn_news_program_issue) ;
        prefs = getSharedPreferences(ApplicationConstants.USER_PREFS_NAME,
                Context.MODE_PRIVATE);
        String registrationId = prefs.getString(ApplicationConstants.REG_ID, "");
        String theRole = prefs.getString(ApplicationConstants.LEVEL_ID, "");

       // checkstatusdaftar();
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Guest.class);
                startActivity(intent);

            }
        });
        final Handler mHandler = new Handler();

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {

                AnimateandSlideShow();

            }
        };
        int delay = 1000; // delay for 1 sec.

        int period = 15000; // repeat every 4 sec.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }

        }, delay, period);
        new DrawerUtil(this, toolbar, 0).initDrawerGuest();
    }

    private void AnimateandSlideShow() {


        slidingimage = (ImageView)findViewById(R.id.ImageView3_Left);
        slidingimage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);

        currentimageindex++;

        Animation rotateimage = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);


        slidingimage.startAnimation(rotateimage);



    }

    public void checkstatusdaftar(final Context __context){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(ApplicationConstants.API_GET_STATUS_DAFTAR+new GetRole(__context).getuserid(),
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response login : ", response);
                        Log.i("response ID : ", String.valueOf(new GetRole(__context).getuserid()));

                        try {
                            JSONObject jObj = new JSONObject(response);
                            status = jObj.getBoolean("status");
                            if (status) {
                                        final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(__context);
                                        alertDialog.setTitle("Info");
                                        alertDialog.setCancelable(false);
                                        alertDialog.setMessage("Maaf, Akun Anda sedang dalam verifikasi Admin." +
                                                "\nCobalah untuk memberikan informasi yang benar dan sesuai agar akun Anda dapat diverifikasi");
                                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog.create().dismiss();
                                            }
                                        });
                                        alertDialog.setNegativeButton("Edit Data Pendaftaran", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(__context, ProfileRelawan.class);
                                                __context.startActivity(intent);
                                                ((Activity)__context).finish();
                                            }
                                        });
                                        alertDialog.show();
                            } else {
                                        Intent intent = new Intent(__context, DaftarRelawan.class);
                                __context.startActivity(intent);
                                ((Activity)__context).finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Hide Progress Dialog

                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog

                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(__context,
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(__context,
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    __context,
                                    "Unexpected Error occcured! [Most common Error: Device might "
                                            + "not be connected to Internet or remote server is not up and running], check for other errors as well",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.logout:
                getSharedPreferences("UserDetails",
                        Context.MODE_PRIVATE).edit().clear().commit();
                intent = new Intent(applicationContext, Login.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
