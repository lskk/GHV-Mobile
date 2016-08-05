package pptik.startup.ghvmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import pptik.startup.ghvmobile.Support.ProfileRelawan;

/**
 * Created by edo on 6/29/2016.
 */
public class RelawanMenu extends AppCompatActivity {
    public int currentimageindex=0;
    //    Timer timer;
//    TimerTask task;
    ImageView slidingimage;

    private int[] IMAGE_IDS = {
            R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,
            R.drawable.banner4,R.drawable.banner5,R.drawable.banner6
    };

    private ListView lv;
    private Context applicationContext;
    private TextView bt2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relawan_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        applicationContext = this.getApplicationContext();
        bt2=(TextView) findViewById(R.id.btn_news_program_issue) ;

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Relawan.class);
                startActivity(intent);
                finish();
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
    }

    private void AnimateandSlideShow() {


        slidingimage = (ImageView)findViewById(R.id.ImageView3_Left);
        slidingimage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);

        currentimageindex++;

        Animation rotateimage = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);


        slidingimage.startAnimation(rotateimage);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_relawan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_profile:
                intent = new Intent(applicationContext, ProfileRelawan.class);
                startActivity(intent);
                return true;
            case R.id.menu_submited_issue:
                intent = new Intent(applicationContext, SubmitedProgram.class);
                startActivity(intent);
                return true;
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

    }

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
