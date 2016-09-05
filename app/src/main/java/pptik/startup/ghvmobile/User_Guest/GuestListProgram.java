package pptik.startup.ghvmobile.User_Guest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by edo on 6/12/2016.
 */

import pptik.startup.ghvmobile.User_Admin.Admin;
import pptik.startup.ghvmobile.GetRole;
import pptik.startup.ghvmobile.R;
import pptik.startup.ghvmobile.User_Relawan.Relawan_Program;
import pptik.startup.ghvmobile.Support.CustomAdapter;
import pptik.startup.ghvmobile.Support.Program;
import pptik.startup.ghvmobile.Utilities.DrawerUtil;
import pptik.startup.ghvmobile.Setup.ApplicationConstants;

public class GuestListProgram extends AppCompatActivity {


    private ListView lv;
    private ArrayList<Program> listProgram;
    private ProgressDialog pDialog;
    private Context applicationContext;
    private Dialog addingClassDialog;
    private EditText inputCode;
    private CustomAdapter mAdapter;
    private Button bt;
    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";
    public static final String LEVEL_ID = "roleId";
    public static final String BSTS_ID = "userId";
    public static final String USER_ID="UsErId";
    SharedPreferences prefs;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_program_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Issue dan Program Terkini");
        applicationContext = getApplicationContext();
        prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        String theRole = prefs.getString(LEVEL_ID, "");
        int user_ID=prefs.getInt(USER_ID,0);
        lv=(ListView) findViewById(R.id.listView_program);
        listProgram = new ArrayList<Program>();
        new DrawerUtil(this, toolbar, 0).initDrawerGuest();
        CollectingMateri(this);

    }



    private void CollectingMateri(final GuestListProgram guestListProgram) {
        pDialog = ProgressDialog.show(GuestListProgram.this, null, "Memuat Data..");

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ApplicationConstants.API_GET_START_PROGRAM,
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response : ", response);

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
                                    listProgram.add(p);
                                  //  Log.i("idberita",abc.getString("id_program"));
                                }

                                mAdapter = new CustomAdapter(guestListProgram, listProgram);
                                lv.setAdapter(mAdapter);
                                pDialog.dismiss();
                            } else {
                                pDialog.dismiss();
                                String errorMsg = jObj.getString("Message");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Hide Progress Dialog
                        pDialog.hide();
                        if (pDialog != null) {
                            pDialog.dismiss();
                        }
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        pDialog.hide();
                        if (pDialog != null) {
                            pDialog.dismiss();
                        }
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Unexpected Error occcured! [Most common Error: Device might "
                                            + "not be connected to Internet or remote server is not up and running], check for other errors as well",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void onBackPressed() {
        checkRoleToBack();

    }

    private void checkRoleToBack() {
        Intent intent;
        GetRole g=new GetRole(this);
        String roleid=g.getrole();
        if (roleid.contains("2")){
            intent = new Intent(applicationContext, Relawan_Program.class);
            startActivity(intent);
            finish();
        }else if (roleid.contains("3")){
            intent = new Intent(applicationContext, GuestMenu.class);
            startActivity(intent);
            finish();
        }else if (roleid.contains("1")) {
            intent = new Intent(applicationContext, Admin.class);
            startActivity(intent);
            finish();
        }
    }
}
