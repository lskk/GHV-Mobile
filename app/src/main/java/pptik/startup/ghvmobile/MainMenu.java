package pptik.startup.ghvmobile;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.HashMap;

import pptik.startup.ghvmobile.Utilities.DrawerUtil;
import pptik.startup.ghvmobile.Utilities.PictureFormatTransform;

/**
 * Created by GIGABYTE on 05/08/2016.
 */
public class MainMenu extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener
         {
             private SliderLayout mDemoSlider;

             private Context context;
             private Toolbar toolbar;

           protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main_menu_activity);
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                context = this;

                bindingXml();
               sliderInit();
                }


            private void bindingXml(){
                mDemoSlider = (SliderLayout)findViewById(R.id.slider);

            }

             private void  sliderInit(){
                 HashMap<String,String> url_maps = new HashMap<String, String>();
                 url_maps.put("Hannibal", "http://gallery.yopriceville.com/var/albums/Free-Clipart-Pictures/Ribbons-and-Banners-PNG/Orange_Transparent_Banner_PNG_Clipart.png?m=1399672800");
                 url_maps.put("Big Bang Theory", "http://gallery.yopriceville.com/var/albums/Free-Clipart-Pictures/Ribbons-and-Banners-PNG/Orange_Transparent_Banner_PNG_Clipart.png?m=1399672800");
                 url_maps.put("House of Cards", "http://gallery.yopriceville.com/var/albums/Free-Clipart-Pictures/Ribbons-and-Banners-PNG/Orange_Transparent_Banner_PNG_Clipart.png?m=1399672800");
                 url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");


                 for(String name : url_maps.keySet()){
                     TextSliderView textSliderView = new TextSliderView(this);
                     // initialize a SliderLayout
                     textSliderView
                             .description(name)
                             .image(url_maps.get(name))
                             .setScaleType(BaseSliderView.ScaleType.Fit)
                             .setOnSliderClickListener(this);

                     //add your extra information
                     textSliderView.bundle(new Bundle());
                     textSliderView.getBundle()
                             .putString("extra",name);

                     mDemoSlider.addSlider(textSliderView);
                 }
                 mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                 mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                 mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                 mDemoSlider.setDuration(4000);
                 mDemoSlider.addOnPageChangeListener(this);

             }
             @Override
             protected void onStop() {
                 // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
                 mDemoSlider.stopAutoCycle();
                 super.onStop();
             }

             @Override
             public void onSliderClick(BaseSliderView slider) {
                 Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
             }

             @Override
             public boolean onCreateOptionsMenu(Menu menu) {
                 MenuInflater menuInflater = getMenuInflater();
                // menuInflater.inflate(R.menu.main,menu);
                 return super.onCreateOptionsMenu(menu);
             }

             @Override
             public boolean onOptionsItemSelected(MenuItem item) {
                 switch (item.getItemId()){

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
