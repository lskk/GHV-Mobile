package pptik.startup.ghvmobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pptik.startup.ghvmobile.Setup.ApplicationConstants;
import pptik.startup.ghvmobile.Support.Program;

/**
 * Created by GIGABYTE on 05/08/2016.
 */
public class MainMenu extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener
         {
             private ArrayList<Program> listProgram;
             private SliderLayout mDemoSlider;
             private Context context;
             private Toolbar toolbar;
             private LinearLayout L_volunteer,L_program,L_map;

           protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main_menu_activity);
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                context = this;

                bindingXml();
                getDataNews();
                }


            private void bindingXml(){
                mDemoSlider = (SliderLayout)findViewById(R.id.slider);
                listProgram = new ArrayList<Program>();
                L_volunteer=(LinearLayout)findViewById(R.id.layout_1);
                L_program=(LinearLayout)findViewById(R.id.layout_2);
                L_map=(LinearLayout)findViewById(R.id.layout_3);

            }

             private void  sliderInit(){

                 for (int i=0;i<listProgram.size();i++){
                     TextSliderView textSliderView = new TextSliderView(this);
                     // initialize a SliderLayout
                     textSliderView
                             .description(listProgram.get(i).getNamaProgram().toUpperCase())
                             .image(listProgram.get(i).getPathfoto())
                             .setScaleType(BaseSliderView.ScaleType.Fit)
                             .setOnSliderClickListener(this);

                     //add your extra information
                     textSliderView.bundle(new Bundle());
                     textSliderView.getBundle()
                             .putInt("position",i);

                     mDemoSlider.addSlider(textSliderView);

                 }


                 mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                 mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                 mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                 mDemoSlider.setDuration(4000);
                 mDemoSlider.addOnPageChangeListener(this);

             }
             private void getDataNews(){

                 // Make RESTful webservice call using AsyncHttpClient object
                 AsyncHttpClient client = new AsyncHttpClient();
                 client.get(ApplicationConstants.API_GET_URGENT_NEWS,
                         new AsyncHttpResponseHandler() {
                             // When the response returned by REST has Http
                             // response code '200'
                             @Override
                             public void onSuccess(String response) {
                                 Log.i("response data : ", response);

                                 try {
                                     JSONObject jObj = new JSONObject(response);
                                     boolean status = jObj.getBoolean("status");
                                     if (status) {
                                         JSONArray berita= jObj.getJSONArray("berita");

                                         for (int i=0;i<berita.length();i++){
                                             JSONObject abc=berita.getJSONObject(i);
                                             Program p = new Program();
                                             p.setIdProgram(abc.getInt("id_program"));
                                             p.setNamaProgram(abc.getString("nama_program"));
                                             p.setMulai(abc.getString("mulai"));
                                             p.setAkhir(abc.getString("akhir"));
                                             p.setStatus(abc.getInt(("status")));
                                             p.setSupervisor(abc.getString("supervisor"));
                                             p.setDeskripsi(abc.getString("deskripsi"));
                                             p.setLokasiProgram(abc.getString("lokasi_program"));
                                             p.setLatitude(abc.getString("latitude"));
                                             p.setLongitude(abc.getString("longitude"));
                                             p.setKeterangan(abc.getString("keterangan"));
                                             p.setPathfoto(abc.getString("main_image"));
                                             listProgram.add(p);
                                         }
                                        sliderInit();

                                     } else {


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

                             }
                         });

             }
             @Override
             protected void onStop() {
                 // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
                 mDemoSlider.stopAutoCycle();
                 super.onStop();
             }

             @Override
             public void onSliderClick(BaseSliderView slider) {
                 Intent intent = new Intent(context, Detailmateri.class);
                 intent.putExtra("program", listProgram.get(slider.getBundle().getInt("position")));
                 context.startActivity(intent);
             }

             @Override
             public boolean onCreateOptionsMenu(Menu menu) {
                 // Inflate the menu; this adds items to the action bar if it is present.
                 getMenuInflater().inflate(R.menu.menu_main, menu);
                 return true;
             }

             @Override
             public boolean onOptionsItemSelected(MenuItem item) {
                 // Handle action bar item clicks here. The action bar will
                 // automatically handle clicks on the Home/Up button, so long
                 // as you specify a parent activity in AndroidManifest.xml.
                 int id = item.getItemId();
                 Intent intent;
                 //noinspection SimplifiableIfStatement
                 if (id == R.id.logout) {
                     //--- logout
                     context.getSharedPreferences("UserDetails",
                             Context.MODE_PRIVATE).edit().clear().commit();
                     intent = new Intent(context, Login.class);
                     context.startActivity(intent);
                     finish();
                     return true;
                 }

                 return super.onOptionsItemSelected(item);
             }

             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

             @Override
             public void onPageSelected(int position) {
                 Log.d("Slider Demo", "Page Changed: " + position);
             }

             @Override
             public void onPageScrollStateChanged(int state) {}

}
