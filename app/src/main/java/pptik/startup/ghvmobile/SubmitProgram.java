package pptik.startup.ghvmobile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import pptik.startup.ghvmobile.Connection.IConnectionResponseHandler;
import pptik.startup.ghvmobile.Connection.RequestRest;

/**
 * Created by GIGABYTE on 14/06/2016.
 */
public class SubmitProgram extends AppCompatActivity {
    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";
    public static final String BSTS_ID  = "bStsID";
    public static final String LEVEL_ID = "roleId";
    public static final String USER_ID="UsErId";
    SharedPreferences prefs;
    Context applicationContext;

    private EditText namaprogram;
    private EditText lokasiprogram;
    private EditText mulai;
    private int mulaiyear;
    private int mulaimonth;
    private int mulaiday;
    private EditText akhir;
    private int akhiryear;
    private int akhirmonth;
    private int akhirday;
    private EditText supervisor;
    private EditText deskripsi;
    private  EditText keterangan;
    private Button btnSubmit;
    private String RoleID;
    private  int id_user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_issue_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        applicationContext = getApplicationContext();

        prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        RoleID = prefs.getString(LEVEL_ID, "");
        String registrationId = prefs.getString(REG_ID, "");
        final String emailID = prefs.getString(EMAIL_ID, "");
        id_user=prefs.getInt(USER_ID,0);
        namaprogram=(EditText)findViewById(R.id.submit_program_nama);
        lokasiprogram=(EditText)findViewById(R.id.submit_program_lokasi);
        mulai=(EditText)findViewById(R.id.submit_program_mulai);
        final Calendar c = Calendar.getInstance();
        mulaiyear  = c.get(Calendar.YEAR);
        mulaimonth = c.get(Calendar.MONTH);
        mulaiday   = c.get(Calendar.DAY_OF_MONTH);

        mulai.setFocusable(false);
        mulai.setFocusableInTouchMode(false);
        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // On button click show datepicker dialog
                DateMulai();
            }
        });

        akhir=(EditText)findViewById(R.id.submit_program_akhir);
        akhiryear  = c.get(Calendar.YEAR);
        akhirmonth = c.get(Calendar.MONTH);
        akhirday   = c.get(Calendar.DAY_OF_MONTH);

        akhir.setFocusable(false);
        akhir.setFocusableInTouchMode(false);
        akhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // On button click show datepicker dialog
                DateAkhir();
            }
        });
        supervisor=(EditText)findViewById(R.id.submit_program_supervisor);
        deskripsi=(EditText)findViewById(R.id.submit_program_deskripsi);
        keterangan=(EditText)findViewById(R.id.submit_program_keterangan);

        btnSubmit=(Button) findViewById(R.id.submit_program_btnsubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String tanggalmulai = mulaiyear + "-" + mulaimonth + "-" + mulaiday;
                String tanggalakhir =akhiryear + "-" + akhirmonth + "-" + akhirday;
                if (!namaprogram.getText().toString().isEmpty()&&
                        !lokasiprogram.getText().toString().isEmpty()&&
                        !supervisor.getText().toString().isEmpty()&&
                        !deskripsi.getText().toString().isEmpty()){

                    submitProgram(String.valueOf(id_user),
                            namaprogram.getText().toString(),
                            lokasiprogram.getText().toString(),
                            tanggalmulai,tanggalakhir,
                            supervisor.getText().toString(),
                            deskripsi.getText().toString(),
                            keterangan.getText().toString());

                }   else {
                    Toast.makeText(getApplicationContext(),
                            "Silahkan Lengkapi data", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
    private void submitProgram(String id_user,String nama_program
            ,String lokasi_program,String mulai,String akhir
            ,String supervisor,String deskripsi,String keterangan) {
        final ProgressDialog dialog = ProgressDialog.show(SubmitProgram.this, "Connecting", "Send Data", true);
        RequestRest req = new RequestRest(SubmitProgram.this, new IConnectionResponseHandler(){
            @Override
            public void OnSuccessArray(JSONArray result){
                Log.i("result", result.toString());
                dialog.dismiss();
            }

            @Override
            public void onSuccessJSONObject(String result){
                try {
                    JSONObject obj = new JSONObject(result);
                    Log.i("Test", result);
                    dialog.dismiss();
                    Toast.makeText(SubmitProgram.this, "Program Akan Di tampilkan setelah di verivikasi admin", Toast.LENGTH_LONG).show();

                    if (RoleID.contains("2")){

                        Intent i = new Intent(SubmitProgram.this,Relawan.class);
                        startActivity(i);
                        finish();
                    }else if(RoleID.contains("1")){

                        Intent i = new Intent(SubmitProgram.this,Admin.class);
                        startActivity(i);
                        finish();
                    }

                } catch (JSONException e){

                }


            }

            @Override
            public void onFailure(String e){
                Log.i("Test", e);
                dialog.dismiss();
            }

            @Override
            public void onSuccessJSONArray(String result){
                Log.i("Test", result);
                dialog.dismiss();
            }
        });


        req.submitProgram( id_user, nama_program
                , lokasi_program, mulai, akhir
                , supervisor, deskripsi, keterangan);
    }
        public void DateMulai(){

            DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int _year, int _monthOfYear, int _dayOfMonth)
                {
                    mulaiyear = _year;
                    mulaimonth = _monthOfYear;
                    mulaiday = _dayOfMonth;
                    mulai.setText(_year+"-"+_monthOfYear+"-"+_dayOfMonth);
                }};

            DatePickerDialog dpDialog=new DatePickerDialog(this, listener, mulaiyear, mulaimonth, mulaiday);
            dpDialog.show();

        }
        public void DateAkhir (){

            DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int _year, int _monthOfYear, int _dayOfMonth)
                {
                    akhiryear = _year;
                    akhirmonth = _monthOfYear;
                    akhirday = _dayOfMonth;
                    akhir.setText(_year+"-"+_monthOfYear+"-"+_dayOfMonth);
                }};

            DatePickerDialog dpDialog=new DatePickerDialog(this, listener, akhiryear, akhirmonth, akhirday);
            dpDialog.show();

        }

    @Override
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
            case android.R.id.home:
                checkRoleToBack();
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
        checkRoleToBack();
    }

    private void checkRoleToBack() {
        Intent intent;
        GetRole g=new GetRole(this);
        String roleid=g.getrole();
        if (roleid.contains("2")){
            intent = new Intent(applicationContext, Relawan.class);
            startActivity(intent);
            finish();
        }else if (roleid.contains("3")){
            intent = new Intent(applicationContext, Guest.class);
            startActivity(intent);
            finish();
        }else if (roleid.contains("1")) {
            intent = new Intent(applicationContext, Admin.class);
            startActivity(intent);
            finish();
        }
    }
}
