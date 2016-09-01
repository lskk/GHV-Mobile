package pptik.startup.ghvmobile.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONObject;

import java.io.InputStream;

import pptik.startup.ghvmobile.R;
import pptik.startup.ghvmobile.setup.ApplicationConstants;

/**
 * Created by hynra on 8/18/16.
 */
public class MarkerUserFragment extends Fragment implements View.OnClickListener {
    private JSONObject data;
    private TextView nama,hp;
    private Button BTN_detail;
    private ImageView thumbnail;
    private String pathfoto=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences prefs;
        View view = inflater.inflate(R.layout.fragments_user_detail, container, false);
        nama=(TextView)view.findViewById(R.id.fragment_user_name);
        hp=(TextView)view.findViewById(R.id.fragment_user_hp);
        thumbnail=(ImageView) view.findViewById(R.id.fragment_user_thumb);
        BTN_detail=(Button)view.findViewById(R.id.fragment_user_BTN_detail);
        nama.setText(data.optString("nama_lengkap"));
        hp.setText(data.optString("hp"));
        pathfoto=data.optString("path_foto");
        if (pathfoto !=null || !pathfoto.isEmpty()){
            new DownloadImageTask(thumbnail)
                    .execute(pathfoto);
        }
         prefs = getActivity().getApplicationContext().getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String role=prefs.getString(ApplicationConstants.LEVEL_ID,"0");
        if (Integer.parseInt(role) !=1){
            BTN_detail.setVisibility(View.INVISIBLE);
        }
        return view;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            thumbnail.setImageBitmap(result);

        }
    }
    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONObject getData() {
        return data;
    }
    @Override
    public void onClick(View v) {
       /* Intent intent = new Intent(getActivity().getApplicationContext(), CctvPlayerActivity.class);
        intent.putExtra("urlStr", data.optString("Video"));
        startActivity(intent);*/
    }
}
