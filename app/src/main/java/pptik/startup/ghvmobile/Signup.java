package pptik.startup.ghvmobile;


/**
 * Created by edo on 6/11/2016.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pptik.startup.ghvmobile.Connection.IConnectionResponseHandler;
import  pptik.startup.ghvmobile.Connection.RequestRest;

public class Signup extends AppCompatActivity {
    RequestParams params = new RequestParams();
    Context applicationContext;
    String regId = "";


    AsyncTask<Void, Void, String> createRegIdTask;
    private Button btnRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText retypePassword;
    private String deviceid;

    private ImageView profic;

    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";

    private static final int TAKE_PICTURE = 1;
    private static final int TAKE_KTP = 10;
    private Uri imageUri;

    final int CROP_PIC = 2;
    private Uri picUri;

    GoogleCloudMessaging gcmObj;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        applicationContext = getApplicationContext();
        inputEmail = (EditText) findViewById(R.id.signup_email_act);
        inputPassword = (EditText) findViewById(R.id.signup_password);
        retypePassword = (EditText) findViewById(R.id.signup_retype_password);
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        deviceid = tm.getDeviceId();
        // Get current date by calender
        btnRegister = (Button) findViewById(R.id.sign_up_button_act);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               attemptLogin();
            }
        });

        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (!TextUtils.isEmpty(registrationId)) {
            Intent i = new Intent(applicationContext, MainActivity.class);
            i.putExtra("regId", registrationId);
            startActivity(i);
            finish();
        }
    }

    private void attemptLogin() {

        // Reset errors.
        inputEmail.setError(null);
        inputPassword.setError(null);
        retypePassword.setError(null);

        // Store values at the time of the login attempt.
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String retypepassword = retypePassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            inputPassword.setError(getString(R.string.error_invalid_password));
            focusView = inputPassword;
            cancel = true;
        }
        if (!retypepassword.equals(password)) {
            retypePassword.setError(getString(R.string.error_invalid_password2));
            focusView =retypePassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
           inputEmail.setError(getString(R.string.error_field_required));
            focusView = inputEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
           inputEmail.setError(getString(R.string.error_invalid_email));
            focusView = inputEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // test connection
            //testCon();
            registerUser(email,password,"1234");
        }
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        if (password.length()<=4){
            return false;
        }
        return true;
    }

    private void storeRegIdinSharedPref(Context context, String regId,
                                        String emailID) {
        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putString(EMAIL_ID, emailID);
        editor.commit();
    }
    private void registerUser(String email, String password, String regId) {
        final ProgressDialog  dialog = ProgressDialog.show(Signup.this, null, "Memuat...", true);
        RequestRest req = new RequestRest(Signup.this, new IConnectionResponseHandler(){
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
                    boolean status=obj.getBoolean("status");
                    dialog.dismiss();
                    if (status){

                        Toast.makeText(Signup.this, "Berhasil membuat akun, Silahkan Login", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }else {
                        Toast.makeText(Signup.this, "Email Sudah Digunakan", Toast.LENGTH_LONG).show();
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


        req.registerUser(email, password, "12345");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
