package pptik.startup.ghvmobile;

/**
 * Created by edo on 6/11/2016.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import  pptik.startup.ghvmobile.setup.ApplicationConstants;

public class Login extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 0;

    RequestParams params = new RequestParams();

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ProgressDialog prgDialog;
    private RadioGroup role;
    private RadioButton inputRole;

    private String deviceid;

    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";
    public static final String LEVEL_ID = "roleId";
    public static final String BSTS_ID = "userId";
    public static final String USER_ID="UsErId";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    GoogleCloudMessaging gcmObj;
    SharedPreferences prefs;

    String regId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       TextView fab = (TextView) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);
            }
        });

        String registrationId = prefs.getString(REG_ID, "");
        String theRole = prefs.getString(LEVEL_ID, "");
        int user_ID=prefs.getInt(USER_ID,0);
        if (!TextUtils.isEmpty(registrationId)) {
            if(theRole.contains("3")){
                Intent i = new Intent(Login.this,GuestMenu.class);
                i.putExtra("regId", registrationId);
                startActivity(i);
                finish();
            }
            else if(theRole.contains("2")){
                Intent i = new Intent(Login.this, RelawanMenu.class);
                i.putExtra("regId", registrationId);
                startActivity(i);
                finish();
            }else if(theRole.contains("1")){
                Intent i = new Intent(Login.this, Admin.class);
                i.putExtra("regId", registrationId);
                startActivity(i);
                finish();
            }

        }

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });



        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

      /*  TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        deviceid = tm.getDeviceId();
*/
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               // attemptLogin();
                if(mEmailView.getText().toString().equals("") || mPasswordView.getText().toString().equals("")){
                    Snackbar.make(view, "Kolom email dan password tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                }else {
                    if (checkPlayServices())
                        registerInBackground();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    //----------- regsiter gcm
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                prgDialog = ProgressDialog.show(Login.this, null, "Memuat...", true);
            }


            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(Login.this);
                    }
                    regId = gcmObj
                            .register(ApplicationConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    //    storeRegIdinSharedPref(applicationContext, regId, email);
                    prgDialog.dismiss();
                    //    registerUser(name, email, gender, birth, phone, password, role, ojegNumber, idktp, regId);
                   Log.i("GCM id",regId);
                    attemptLogin();
                    //    Toast.makeText(applicationContext, "Registered with GCM Server successfully.\n\n" + msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time." + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(
                        Login.this,
                        "Maaf, perangkat Anda tidak mendukung Play sERVICE",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {
            Log.i("Gplay sERVICE", "Support Playservice");
        }
        return true;
    }
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // test connection
            //testCon();
            String testregid="test";
            doLogin(email, password, regId);
        }
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void doLogin(final String email, final String password, final  String regid){
        prgDialog.show();
        params.put("email", email);
        params.put("password", password);
        params.put("gcm_id", regid);

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ApplicationConstants.API_LOGIN, params,
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
                                JSONObject user = jObj.getJSONObject("user");
                                String Email = user.getString("email");
                                String gcmid = user.getString("gcm_id");
                                int id_user = user.getInt("id_user");
                                String Level = user.getString("level");
                                if(Level.equals("")){
                                    Level = "3";
                                }
                                //store to sharedpreference
                                storeRegIdinSharedPref(getApplicationContext(), gcmid, Email, Level,id_user );
                                String theRole = Level;
                                Log.i("Login role : ", Level);
                                Log.i("Login role : ", String.valueOf(id_user));

                                if(theRole.contains("3")){
                                    Intent i = new Intent(getApplicationContext(),GuestMenu.class);
                                    i.putExtra("regId", gcmid);
                                    startActivity(i);
                                    finish();
                                    Log.i("Ke halaman user : ", Level);
                                }
                                else if(theRole.contains("2")){
                                    Intent i = new Intent(getApplicationContext(), RelawanMenu.class);
                                    i.putExtra("regId", gcmid);
                                    startActivity(i);
                                    finish();
                                    Log.i("Ke halaman admin : ", Level);
                                }
                                else if(theRole.contains("1")){
                                    Intent i = new Intent(getApplicationContext(), Admin.class);
                                    i.putExtra("regId", gcmid);
                                    startActivity(i);
                                    finish();
                                    Log.i("Ke halaman admin : ", Level);
                                }

                            } else {

                                String errorMsg = jObj.getString("msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
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
    private void storeRegIdinSharedPref(Context context, String regId,
                                        String emailID, String role, int id) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putString(EMAIL_ID, emailID);
        editor.putString(LEVEL_ID, role);
        editor.putInt(USER_ID, id);
        Log.i("dude",String.valueOf(id));
        editor.commit();
    }
}