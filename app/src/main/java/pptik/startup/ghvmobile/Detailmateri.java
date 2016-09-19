package pptik.startup.ghvmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import pptik.startup.ghvmobile.User_Admin.Admin;
import pptik.startup.ghvmobile.User_Guest.GuestListProgram;
import pptik.startup.ghvmobile.Support.Program;
import pptik.startup.ghvmobile.User_Relawan.Relawan_Program;

/**
 * Created by edo on 6/12/2016.
 */
public class Detailmateri  extends AppCompatActivity {
    private Program p;
    private TextView nama,awal,akhir,status,supervisor,lokasi,keterangan,deskripsi;
    private Context applicationContext;
    private String roleid;
    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";
    public static final String BSTS_ID  = "bStsID";
    public static final String USER_ID    = "UsErId";
    public static final String LEVEL_ID = "roleId";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_program_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        applicationContext = getApplicationContext();
        prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        roleid = prefs.getString(LEVEL_ID, "");

        nama = (TextView) findViewById(R.id.detail_program_nama);
        awal = (TextView) findViewById(R.id.detail_program_awal);
        akhir=(TextView) findViewById(R.id.detail_program_akhir);
        status=(TextView) findViewById(R.id.detail_program_status);
        supervisor=(TextView) findViewById(R.id.detail_program_supervisor);
        lokasi=(TextView) findViewById(R.id.detail_program_lokasi);
        keterangan=(TextView) findViewById(R.id.detail_program_keterangan);
        deskripsi=(TextView) findViewById(R.id.detail_program_deskripsi);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            p= (Program) extras.getSerializable("program");
            nama.setText(p.getNamaProgram());
            awal.setText(p.getMulai());
            akhir.setText(p.getAkhir());
            if (p.getStatus()==1){
                status.setText("Verified");
            }else {
                status.setText("Unverified");
            }
            supervisor.setText(p.getSupervisor());
            lokasi.setText(p.getLokasiProgram());
            keterangan.setText(p.getKeterangan());
            deskripsi.setText(p.getDeskripsi());
            getSupportActionBar().setTitle(p.getNamaProgram());
        }
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
