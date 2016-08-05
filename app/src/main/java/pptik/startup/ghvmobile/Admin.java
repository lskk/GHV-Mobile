package pptik.startup.ghvmobile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import pptik.startup.ghvmobile.Support.CustomAdapter;
import pptik.startup.ghvmobile.Support.Program;

/**
 * Created by GIGABYTE on 15/06/2016.
 */
public class Admin extends AppCompatActivity {
    private ListView lv;
    private ArrayList<Program> listProgram;
    private ProgressDialog pDialog;
    private Context applicationContext;
    private Dialog addingClassDialog;
    private EditText inputCode;
    private CustomAdapter mAdapter;
    private Button btaprovalrelawan,btnaprovalprogram,btnsubmitprogram;

    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_page_activity);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    applicationContext = getApplicationContext();
        btaprovalrelawan=(Button)findViewById(R.id.admin_btnapprovalrelawan) ;
        btnaprovalprogram=(Button)findViewById(R.id.admin_btnapprovalprogram);
        btaprovalrelawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ApprovalRelawan.class);
                startActivity(intent);
              //  finish();
            }
        });
        btnaprovalprogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ApprovalProgram.class);
                startActivity(intent);
             //   finish();
            }
        });
        btnsubmitprogram=(Button)findViewById(R.id.admin_btnsubmitprogram);
        btnsubmitprogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubmitProgram.class);
                startActivity(intent);
             //   finish();
            }
        });
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
      finish();

    }

}
