package pptik.startup.ghvmobile.User_Admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pptik.startup.ghvmobile.Login;
import pptik.startup.ghvmobile.R;
import pptik.startup.ghvmobile.Support.DataUser;
import pptik.startup.ghvmobile.Support.DataUser2;
import pptik.startup.ghvmobile.Support.LRB_costumAdapter;
import pptik.startup.ghvmobile.Support.LRS_costumAdapter;
import pptik.startup.ghvmobile.setup.ApplicationConstants;

/**
 * Created by GIGABYTE on 15/06/2016.
 */
public class ApprovalRelawan extends AppCompatActivity {
    private ListView lv,lv2;
    private ArrayList<DataUser> listDataUser;
    private ArrayList<DataUser2> listDataUser2;

    private ProgressDialog pDialog,pDialog2;
    private Context applicationContext;
    private Dialog addingClassDialog;
    private EditText inputCode;
    private LRB_costumAdapter mAdapter;
    private LRS_costumAdapter mAdapter2;
    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";
    public static final String LEVEL_ID = "roleId";
    public static final String BSTS_ID = "userId";
    public static final String USER_ID="UsErId";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approval_relawan_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Request");
        applicationContext = getApplicationContext();
        lv=(ListView) findViewById(R.id.lv_belum_diapprove);
        lv2=(ListView) findViewById(R.id.lv_sudah_diapprove);
        listDataUser = new ArrayList<DataUser>();
        listDataUser2 = new ArrayList<DataUser2>();

    //    CollectingMateri(this);

    }


    private void CollectingMateri(final ApprovalRelawan ar) {
        pDialog = ProgressDialog.show(ApprovalRelawan.this, null, "Memuat Data..");

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ApplicationConstants.API_GET_LIST_BELUM_DI_APPROVE,
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response login : ", response);
                        listDataUser.clear();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean status = jObj.getBoolean("status");
                            if (status) {
                                JSONArray listuser= jObj.getJSONArray("listuser");

                                for (int i=0;i<listuser.length();i++){
                                    JSONObject abc=listuser.getJSONObject(i);
                                    DataUser d = new DataUser();
                                    d.setIdUser(abc.getInt("id_user"));
                                    d.setEmail(abc.getString("email"));
                                    d.setNamaLengkap(abc.getString("nama_lengkap"));
                                    listDataUser.add(d);
                                    //  Log.i("idberita",abc.getString("id_program"));
                                }


                                mAdapter = new LRB_costumAdapter(ar, listDataUser);
                                lv.setAdapter(null);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_approval_relawan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case android.R.id.home:
                checkRoleToBackup();
                return true;
            case R.id.logout:
                getSharedPreferences("UserDetails",
                        Context.MODE_PRIVATE).edit().clear().commit();
                intent = new Intent(applicationContext, Login.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.dissaprovelist:
                intent = new Intent(applicationContext, DisapprovRelawan.class);
                startActivity(intent);
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
        checkRoleToBackup();

    }

    private void checkRoleToBackup() {
     /*   Intent intent;
        GetRole g=new GetRole(this);
        String roleid=g.getrole();
        if (roleid.contains("2")){
            intent = new Intent(applicationContext, Relawan.class);
            startActivity(intent);
            finish();
        }else if (roleid.contains("3")){
            intent = new Intent(applicationContext, GuestListProgram.class);
            startActivity(intent);
            finish();
        }else if (roleid.contains("1")) {
            intent = new Intent(applicationContext, Admin.class);
            startActivity(intent);
            finish();
        } */
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
        CollectingMateri(this);
    }
}
