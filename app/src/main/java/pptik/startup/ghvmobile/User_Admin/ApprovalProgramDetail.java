package pptik.startup.ghvmobile.User_Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pptik.startup.ghvmobile.R;
import pptik.startup.ghvmobile.Support.DataProgram;
import pptik.startup.ghvmobile.Support.DataProgram2;
import pptik.startup.ghvmobile.Setup.ApplicationConstants;

/**
 * Created by GIGABYTE on 20/06/2016.
 */
public class ApprovalProgramDetail  extends AppCompatActivity {
    private DataProgram dp;
    private DataProgram2 dp2;
    private int id_program,status_program;
    private TextView nama,awal,akhir,status,supervisor,lokasi,keterangan,deskripsi;

    private Button btnApprove,btnDelete;

    SharedPreferences prefs;
    private ProgressDialog pDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_approval_program_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Program");
        prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        nama = (TextView) findViewById(R.id.detail_program_nama);
        awal = (TextView) findViewById(R.id.detail_program_awal);
        akhir=(TextView) findViewById(R.id.detail_program_akhir);
        supervisor=(TextView) findViewById(R.id.detail_program_supervisor);
        lokasi=(TextView) findViewById(R.id.detail_program_lokasi);
        keterangan=(TextView) findViewById(R.id.detail_program_keterangan);
        deskripsi=(TextView) findViewById(R.id.detail_program_deskripsi);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            status_program=extras.getInt("status");

        }

        btnApprove = (Button) findViewById(R.id.btn_approval_program);
         if (status_program==1){
            dp= (DataProgram) extras.getSerializable("detail");
            id_program=dp.getIdProgram();
            btnApprove.setText("Disapprove");
            btnApprove.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    approveprogram(2);
                }
            });
        }else {
            dp= (DataProgram) extras.getSerializable("detail");
            id_program=dp.getIdProgram();
            btnApprove.setText("Approve");
            btnApprove.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    approveprogram(1);
                }
            });
        }
        btnDelete=(Button)findViewById(R.id.btn_delete_program);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    deleteprogram();
            }
        });
        ambildataprogram();
    }
    private void deleteprogram(){
        pDialog = ProgressDialog.show(ApprovalProgramDetail.this, "Menghapus Program", "Harap Tunggu");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ApplicationConstants.API_DELETE_BERITA+id_program,
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response login : ", response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean status = jObj.getBoolean("status");
                            if (status) {
                                pDialog.dismiss();
                                finish();
                            } else {
                                pDialog.dismiss();
                                String errorMsg = jObj.getString("msg");
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
    private void approveprogram(int no){
        pDialog = ProgressDialog.show(ApprovalProgramDetail.this, "Mengirim Data", "Harap Tunggu");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ApplicationConstants.API_APPROVAL_BERITA+id_program+"/"+no,
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response login : ", response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean status = jObj.getBoolean("status");
                            if (status) {
                                pDialog.dismiss();
                                finish();
                            } else {
                                pDialog.dismiss();
                                String errorMsg = jObj.getString("msg");
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

    private void ambildataprogram(){
        pDialog = ProgressDialog.show(ApprovalProgramDetail.this, "Memuat Program", "Harap Tunggu");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ApplicationConstants.API_GET_PROGRAM_BY_ID+id_program,
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response login : ", response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean status = jObj.getBoolean("status");
                            if (status) {
                                JSONArray berita= jObj.getJSONArray("berita");

                                for (int i=0;i<berita.length();i++){
                                    JSONObject abc=berita.getJSONObject(i);
                                    nama.setText(abc.getString("nama_program"));
                                    awal.setText(abc.getString("mulai"));
                                    akhir.setText(abc.getString("akhir"));
                                    supervisor.setText(abc.getString("supervisor"));
                                    lokasi.setText(abc.getString("lokasi_program"));
                                    keterangan.setText(abc.getString("keterangan"));
                                    deskripsi.setText(abc.getString("deskripsi"));

                                }


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
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case android.R.id.home:
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void  onDestroy(){
        super.onDestroy();
    }
}
