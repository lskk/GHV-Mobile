package pptik.startup.ghvmobile.Support;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import pptik.startup.ghvmobile.Admin;
import pptik.startup.ghvmobile.Connection.IConnectionResponseHandler;
import pptik.startup.ghvmobile.Connection.RequestRest;
import pptik.startup.ghvmobile.GetRole;
import pptik.startup.ghvmobile.GuestMenu;
import pptik.startup.ghvmobile.R;
import pptik.startup.ghvmobile.RelawanMenu;
import pptik.startup.ghvmobile.setup.ApplicationConstants;

/**
 * Created by GIGABYTE on 15/06/2016.
 */
public class ProfileRelawan extends AppCompatActivity {
    private Button btnDaftar;
    private EditText namalengkap;
    private EditText namapanggilan;
    private RadioGroup gender;
    private RadioButton inputgender;
    private RadioGroup goldarah;
    private RadioButton inputgoldarah;
    private EditText tempatlahir;
    private EditText tanggallahir;
    private int year;
    private int month;
    private int day;
    private EditText agama;
    private RadioGroup statuskawin;
    private RadioButton inputstatuskawin;
    private EditText jumlahanak;
    private RadioGroup jidentitas;
    private RadioButton inputjidentitas;
    private EditText noidentitas;
    private RadioGroup kewarganegaraan;
    private RadioButton inputkewarganegaraan;
    private EditText alamat;
    private EditText kota,provinsi,kodepos,telprumah,hp,aktivitas,namakerabat,hpkerabat;
    private RadioGroup pendidikanterakhir;
    private RadioButton inputpendidikanterakhir;
    private EditText minat,keahlian,pengalaman,motivasi;
    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";
    public static final String BSTS_ID  = "bStsID";
    public static final String USER_ID    = "UsErId";
    public static final String LEVEL_ID = "roleId";
    SharedPreferences prefs;
    private ProgressDialog pDialog;
    private String roleid;
    private Context applicationContext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relawan_profile_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        applicationContext = getApplicationContext();
        prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        final String emailID = prefs.getString(EMAIL_ID, "");
        String id_user=prefs.getString(BSTS_ID,"");
        int user_ID=prefs.getInt(USER_ID,0);
        Log.i("userid",String.valueOf(user_ID));
        GetRole g=new GetRole(this);
        roleid=g.getrole();
        namalengkap=(EditText)findViewById(R.id.profile_relawan_name);
        namapanggilan=(EditText)findViewById(R.id.profile_relawan_nick);
        gender = (RadioGroup) findViewById(R.id.profile_relawan_jk);
        inputgender = (RadioButton)findViewById(R.id.pjk_Laki);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pjk_Laki:
                        // do operations specific to this selection
                        inputgender = (RadioButton)findViewById(R.id.pjk_Laki);
                        break;
                    case R.id.pjk_perempuan:
                        inputgender = (RadioButton)findViewById(R.id.pjk_perempuan);
                        break;
                }
            }
        });
        goldarah = (RadioGroup) findViewById(R.id.profile_relawan_gol_darah);
        inputgoldarah = (RadioButton)findViewById(R.id.gol_o);
        goldarah.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.gol_o:
                        // do operations specific to this selection
                        inputgoldarah = (RadioButton)findViewById(R.id.gol_o);
                        break;
                    case R.id.gol_a:
                        inputgoldarah = (RadioButton)findViewById(R.id.gol_a);
                        break;
                    case R.id.gol_b:
                        inputgoldarah = (RadioButton)findViewById(R.id.gol_b);
                        break;
                    case R.id.gol_ab:
                        inputgoldarah = (RadioButton)findViewById(R.id.gol_ab);
                        break;
                }
            }
        });
        tempatlahir=(EditText)findViewById(R.id.profile_relawan_tlahir);
        tanggallahir=(EditText)findViewById(R.id.profile_relawan_tgllahir);
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        tanggallahir.setFocusable(false);
        tanggallahir.setFocusableInTouchMode(false);
        tanggallahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // On button click show datepicker dialog
                DateDialog();
            }
        });
        agama=(EditText)findViewById(R.id.profile_relawan_agama);

        statuskawin = (RadioGroup) findViewById(R.id.profile_relawan_status);
        inputstatuskawin = (RadioButton)findViewById(R.id.s_menikah);
        statuskawin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.s_menikah:
                        // do operations specific to this selection
                        inputstatuskawin = (RadioButton)findViewById(R.id.s_menikah);
                        break;
                    case R.id.s_b_menikah:
                        inputstatuskawin = (RadioButton)findViewById(R.id.s_b_menikah);
                        break;

                }
            }
        });

        jumlahanak=(EditText)findViewById(R.id.profile_relawan_jumlahanak);

        jidentitas = (RadioGroup) findViewById(R.id.profile_relawan_jidentitas);
        inputjidentitas = (RadioButton)findViewById(R.id.ji_ktp);
        jidentitas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.ji_ktp:
                        // do operations specific to this selection
                        inputjidentitas = (RadioButton)findViewById(R.id.ji_ktp);
                        break;
                    case R.id.ji_sim:
                        inputjidentitas = (RadioButton)findViewById(R.id.ji_sim);
                        break;

                }
            }
        });
        noidentitas=(EditText)findViewById(R.id.profile_relawan_no_identitas);
        kewarganegaraan = (RadioGroup) findViewById(R.id.profile_relawan_kewarganegaraan);
        inputkewarganegaraan = (RadioButton)findViewById(R.id.k_wni);
        kewarganegaraan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.k_wni:
                        // do operations specific to this selection
                        inputkewarganegaraan = (RadioButton)findViewById(R.id.k_wni);
                        break;
                    case R.id.k_wna:
                        inputkewarganegaraan = (RadioButton)findViewById(R.id.k_wna);
                        break;

                }
            }
        });

        alamat=(EditText)findViewById(R.id.profile_relawan_alamat);
        kota=(EditText)findViewById(R.id.profile_relawan_kota);
        provinsi=(EditText)findViewById(R.id.profile_relawan_prov);
        kodepos=(EditText)findViewById(R.id.profile_relawan_kodepos);
        telprumah=(EditText)findViewById(R.id.profile_relawan_tlpn);
        hp=(EditText)findViewById(R.id.profile_relawan_hp);
        aktivitas=(EditText)findViewById(R.id.profile_relawan_aktivitas);
        namakerabat=(EditText)findViewById(R.id.profile_relawan_nama_kerabat);
        hpkerabat=(EditText)findViewById(R.id.profile_relawan_hp_kerabat);

        pendidikanterakhir = (RadioGroup) findViewById(R.id.profile_relawan_pendidikan_terakhir);
        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_sd);
        pendidikanterakhir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pt_sd:
                        // do operations specific to this selection
                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_sd);
                        break;
                    case R.id.pt_smp:
                        // do operations specific to this selection
                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_smp);
                        break;
                    case R.id.pt_sma:
                        // do operations specific to this selection
                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_sma);
                        break;
                    case R.id.pt_s1:
                        // do operations specific to this selection
                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_s1);
                        break;
                    case R.id.pt_s2:
                        // do operations specific to this selection
                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_s2);
                        break;
                    case R.id.pt_s3:
                        // do operations specific to this selection
                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_s3);
                        break;


                }
            }
        });
        minat=(EditText)findViewById(R.id.profile_relawan_minat);
        keahlian=(EditText)findViewById(R.id.profile_relawan_keahlian);
        pengalaman=(EditText)findViewById(R.id.profile_relawan_pengalaman);
        motivasi=(EditText)findViewById(R.id.profile_relawan_motivasi);

        btnDaftar = (Button) findViewById(R.id.profile_relawan_btnRegister);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String bd = year + "-" + month + "-" + day;
                if (!namalengkap.getText().toString().isEmpty()&&
                        !namapanggilan.getText().toString().isEmpty()&&
                        !tempatlahir.getText().toString().isEmpty()&&
                        !bd.isEmpty()&&
                        !agama.getText().toString().isEmpty()&&
                        !jumlahanak.getText().toString().isEmpty()&&
                        !noidentitas.getText().toString().isEmpty()&&
                        !alamat.getText().toString().isEmpty()&&
                        !kota.getText().toString().isEmpty()&&
                        !provinsi.getText().toString().isEmpty()&&
                        !kodepos.getText().toString().isEmpty()&&
                        !telprumah.getText().toString().isEmpty()&&
                        !hp.getText().toString().isEmpty()&&
                        !aktivitas.getText().toString().isEmpty()&&
                        !namakerabat.getText().toString().isEmpty()&&
                        !hpkerabat.getText().toString().isEmpty()&&
                        !minat.getText().toString().isEmpty()&&
                        !keahlian.getText().toString().isEmpty()&&
                        !pengalaman.getText().toString().isEmpty()&&
                        !motivasi.getText().toString().isEmpty()){
                    registerUser( emailID,   namalengkap.getText().toString(),  namapanggilan.getText().toString(),
                            inputgender.getTag().toString(),  inputgoldarah.getTag().toString(),  tempatlahir.getText().toString(),
                            bd,  agama.getText().toString(),  inputstatuskawin.getTag().toString(),
                            jumlahanak.getText().toString(),  inputjidentitas.getTag().toString(),  noidentitas.getText().toString(),
                            inputkewarganegaraan.getTag().toString(),  alamat.getText().toString(),  kota.getText().toString(), provinsi.getText().toString(),
                            kodepos.getText().toString(),
                            telprumah.getText().toString(),  hp.getText().toString(),  aktivitas.getText().toString(),
                            namakerabat.getText().toString(),
                            hpkerabat.getText().toString(),  inputpendidikanterakhir.getTag().toString(),  minat.getText().toString(),
                            keahlian.getText().toString(),  pengalaman.getText().toString(),  motivasi.getText().toString());


                }   else {
                    Toast.makeText(getApplicationContext(),
                            "Silahkan Lengkapi data anda", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        getprofileuser(user_ID);

    }
    public void DateDialog(){

        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int _year, int _monthOfYear, int _dayOfMonth)
            {
                year = _year;
                month = _monthOfYear;
                day = _dayOfMonth;
                tanggallahir.setText(_year+"-"+_monthOfYear+"-"+_dayOfMonth);
            }};

        DatePickerDialog dpDialog=new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();

    }
    private void getprofileuser(int id_user){
        pDialog = ProgressDialog.show(ProfileRelawan.this, "Memuat Profile", "Harap Tunggu");

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();

        Log.i("iduser",ApplicationConstants.API_GET_RELAWAN_BY_ID+id_user);

        client.get(ApplicationConstants.API_GET_RELAWAN_BY_ID+id_user,
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
                                JSONArray berita= jObj.getJSONArray("user");

                                for (int i=0;i<berita.length();i++){
                                    JSONObject abc=berita.getJSONObject(i);
                                    namalengkap.setText(abc.getString("nama_lengkap"));
                                    namapanggilan.setText(abc.getString("nama_panggilan"));
                                    if (abc.getInt("jk")==1){
                                        inputgender = (RadioButton)findViewById(R.id.pjk_Laki);
                                        inputgender.setChecked(true);
                                    }else {
                                        inputgender = (RadioButton)findViewById(R.id.pjk_perempuan);
                                        inputgender.setChecked(true);
                                    }

                                    if (abc.getInt("gol_darah")==1){
                                        inputgoldarah = (RadioButton)findViewById(R.id.gol_o);
                                        inputgoldarah.setChecked(true);
                                    }else if (abc.getInt("gol_darah")==2){
                                        inputgoldarah = (RadioButton)findViewById(R.id.gol_a);
                                        inputgoldarah.setChecked(true);
                                    }else if (abc.getInt("gol_darah")==3){
                                        inputgoldarah = (RadioButton)findViewById(R.id.gol_b);
                                        inputgoldarah.setChecked(true);
                                    }else {
                                        inputgoldarah = (RadioButton)findViewById(R.id.gol_ab);
                                        inputgoldarah.setChecked(true);
                                    }
                                    tempatlahir.setText(abc.getString("tempat_lahir"));

                                    tanggallahir.setText(abc.getString("tgl_lahir"));
                                    String temptanggallahir=abc.getString("tgl_lahir");
                                    String[] data=temptanggallahir.split("-");
                                    for (i=0;i<data.length;i++){
                                        if (i==0){
                                            try{
                                            year = Integer.parseInt(data[i]);
                                        } catch (NumberFormatException e) {
                                            year = 2016;
                                        }

                                        }else if (i==1){
                                            month=Integer.parseInt(data[i]);
                                        }else if (i==2){
                                            day=Integer.parseInt(data[i]);
                                        }
                                    }
                                    agama.setText(abc.getString("agama"));

                                    if (abc.getInt("status_perkawinan")==1){
                                        inputstatuskawin=(RadioButton)findViewById(R.id.s_menikah);
                                        inputstatuskawin.setChecked(true);
                                    }else {
                                        inputstatuskawin=(RadioButton)findViewById(R.id.s_b_menikah);
                                        inputstatuskawin.setChecked(true);
                                    }
                                    jumlahanak.setText(abc.getString("jumlah_anak"));
                                    if (abc.getInt("jenis_identitas")==1){
                                        inputjidentitas=(RadioButton)findViewById(R.id.ji_ktp);
                                        inputjidentitas.setChecked(true);
                                    }else {
                                        inputjidentitas=(RadioButton)findViewById(R.id.ji_sim);
                                        inputjidentitas.setChecked(true);
                                    }
                                    noidentitas.setText(abc.getString("no_identitas"));
                                    if (abc.getInt("kewarganegaraan")==1){
                                        inputkewarganegaraan=(RadioButton)findViewById(R.id.k_wni);
                                        inputkewarganegaraan.setChecked(true);
                                    }else {
                                        inputkewarganegaraan=(RadioButton)findViewById(R.id.k_wna);
                                        inputkewarganegaraan.setChecked(true);
                                    }
                                    alamat.setText(abc.getString("alamat"));
                                    kota.setText(abc.getString("kota"));
                                    provinsi.setText(abc.getString("provinsi"));
                                    kodepos.setText(abc.getString("kode_pos"));
                                    telprumah.setText(abc.getString("telp_rumah"));
                                    hp.setText(abc.getString("hp"));
                                    aktivitas.setText(abc.getString("pekerjaan"));
                                    namakerabat.setText(abc.getString("nama_kerabat"));
                                    hpkerabat.setText(abc.getString("hp_kerabat"));
                                    if (abc.getInt("pendidikan_terakhir")==1){
                                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_sd);
                                        inputpendidikanterakhir.setChecked(true);
                                    }else if (abc.getInt("pendidikan_terakhir")==2){
                                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_smp);
                                        inputpendidikanterakhir.setChecked(true);
                                    }else if (abc.getInt("pendidikan_terakhir")==3){
                                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_sma);
                                        inputpendidikanterakhir.setChecked(true);
                                    }else if (abc.getInt("pendidikan_terakhir")==4){
                                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_s1);
                                        inputpendidikanterakhir.setChecked(true);
                                    }else if (abc.getInt("pendidikan_terakhir")==5){
                                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_s2);
                                        inputpendidikanterakhir.setChecked(true);
                                    }else {
                                        inputpendidikanterakhir = (RadioButton)findViewById(R.id.pt_s3);
                                        inputpendidikanterakhir.setChecked(true);
                                    }
                                    minat.setText(abc.getString("minat"));
                                    keahlian.setText(abc.getString("keahlian"));
                                    pengalaman.setText(abc.getString("pengalaman_organisasi"));
                                    motivasi.setText(abc.getString("motivasi"));
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
    private void registerUser(String email,  String nama_lengkap, String nama_panggilan,
                              String jk, String gol_darah, String tempat_lahir,
                              String tgl_lahir, String agama, String status_perkawinan,
                              String jumlah_anak, String jenis_identitas, String no_identitas,
                              String kewarganegaraan, String alamat, String kota,String provinsi, String kode_pos,
                              String telp_rumah, String hp, String pekerjaan, String nama_kerabat,
                              String hp_kerabat, String pendidikan_terakhir, String minat,
                              String keahlian, String pengalaman_organisasi, String motivasi) {
        final ProgressDialog dialog = ProgressDialog.show(ProfileRelawan.this, "Connecting", "Register", true);
        RequestRest req = new RequestRest(ProfileRelawan.this, new IConnectionResponseHandler(){
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
                    Toast.makeText(ProfileRelawan.this, "Data Berhasil DI UPDATE", Toast.LENGTH_LONG).show();
                    Intent intent;

                    if (roleid.contains("2")){
                        intent = new Intent(applicationContext, RelawanMenu.class);
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


        req.registerRelawan( email,   nama_lengkap,  nama_panggilan,
                jk,  gol_darah,  tempat_lahir,
                tgl_lahir,  agama,  status_perkawinan,
                jumlah_anak,  jenis_identitas,  no_identitas,
                kewarganegaraan,  alamat,  kota, provinsi,  kode_pos,
                telp_rumah,  hp,  pekerjaan,  nama_kerabat,
                hp_kerabat,  pendidikan_terakhir,  minat,
                keahlian,  pengalaman_organisasi,  motivasi);
    }
    @Override
    public void onBackPressed() {
        checkRoleForBack();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case android.R.id.home:
                checkRoleForBack();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkRoleForBack() {
        Intent intent;

        if (roleid.contains("2")){
            intent = new Intent(applicationContext, RelawanMenu.class);
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