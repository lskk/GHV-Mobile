package pptik.startup.ghvmobile.Connection;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import pptik.startup.ghvmobile.Utils.TimeTools;
import pptik.startup.ghvmobile.setup.ApplicationConstants;

public class RequestRest extends ConnectionHandler {

    final int DEFAULT_TIMEOUT = 400 * 1000;
    private  String TAG_MAPVIEW = "Get Mapview ";
    private  String TAG_TESTCON = "Connection Test";
    private  String TAG_SIGNUP = "Signup";
    private  String TAG_DAFTAR_RELAWAN = "Daftar_relawan";
    protected static AsyncHttpClient mClient = new AsyncHttpClient();

    public RequestRest(Context context, IConnectionResponseHandler handler) {
        this.mContext = context;
        this.responseHandler = handler;
    }

    @Override
    public String getAbsoluteUrl(String relativeUrl) {
        return ApplicationConstants.HTTP_URL + relativeUrl;
    }


    public void testConnection(){
        RequestParams params = new RequestParams();
        mClient.addHeader("x-ami-dt", TimeTools.getCurrentTime());
        mClient.addHeader("x-ami-cc", "MOBILE");
        System.setProperty("http.keepAlive", "false");
        get("network.json", params, new JsonHttpResponseHandler() {

         //   ProgressDialog dialog;

            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG_TESTCON, "Sending request");
             //   dialog = ProgressDialog.show(mContext, "Connecting", "Check Connection", true);

            }

            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);
                Log.i(TAG_TESTCON, "Success");
                responseHandler.onSuccessJSONObject(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Log.e(TAG_TESTCON, "Failed");
                responseHandler.onFailure(responseBody);//e.getMessage());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i(TAG_TESTCON, "Disconnected");
             //   dialog.dismiss();
            }

        }, mClient);
    }


    public void registerUser( String email,  String password, String regId){
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        params.put("gcm_id", regId);


        Log.i("email", email);
        Log.i("password", password);
        Log.i("gcm_id", regId);



        post("useract/store", params, new JsonHttpResponseHandler() {

            //   ProgressDialog dialog;

            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG_SIGNUP, "Sending request");
                //   dialog = ProgressDialog.show(mContext, "Connecting", "Check Connection", true);
            }

            @Override
            public void onSuccess(JSONObject status) {
                super.onSuccess(status);
                Log.i(TAG_SIGNUP, "Success");
                responseHandler.onSuccessJSONObject(status.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Log.e(TAG_SIGNUP, "Failed");
                responseHandler.onFailure(e.toString());//e.getMessage());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i(TAG_SIGNUP, "Disconnected");
                //   dialog.dismiss();
            }

        }, mClient);
    }

    public void registerRelawan( String email,  String nama_lengkap, String nama_panggilan,
                                 String jk, String gol_darah, String tempat_lahir,
                                 String tgl_lahir, String agama, String status_perkawinan,
                                 String jumlah_anak, String jenis_identitas, String no_identitas,
                                 String kewarganegaraan, String alamat, String kota,String provinsi, String kode_pos,
                                 String telp_rumah, String hp, String pekerjaan, String nama_kerabat,
                                 String hp_kerabat, String pendidikan_terakhir, String minat,
                                 String keahlian, String pengalaman_organisasi, String motivasi,String pathfoto,String latitude,String longitude){
        RequestParams params = new RequestParams();
        params.put("nama_lengkap",nama_lengkap);
        params.put("nama_panggilan",nama_panggilan);
        params.put("jk",jk);
        params.put("gol_darah",gol_darah);
        params.put("tempat_lahir",tempat_lahir);
        params.put("tgl_lahir",tgl_lahir);
        params.put("agama",agama);
        params.put("status_perkawinan",status_perkawinan);
        params.put("jumlah_anak",jumlah_anak);
        params.put("jenis_identitas",jenis_identitas);
        params.put("no_identitas",no_identitas);
        params.put("kewarganegaraan",kewarganegaraan);
        params.put("hp",hp);
        params.put("alamat",alamat);
        params.put("kota",kota);
        params.put("provinsi",provinsi);
        params.put("kode_pos",kode_pos);
        params.put("telp_rumah",telp_rumah);
        params.put("pekerjaan",pekerjaan);
        params.put("nama_kerabat",nama_kerabat);
        params.put("hp_kerabat",hp_kerabat);
        params.put("pendidikan_terakhir",pendidikan_terakhir);
        params.put("minat",minat);
        params.put("keahlian",keahlian);
        params.put("pengalaman_organisasi",pengalaman_organisasi);
        params.put("motivasi",motivasi);
        params.put("latitude",latitude);
        params.put("longitude",longitude);
        Log.i("path foto request rest",pathfoto);
        if (pathfoto.isEmpty() || pathfoto==null){
            params.put("img_profil_relawan", false);
        }else {
            File photo = new File(pathfoto);
            try {
                params.put("img_profil_relawan", photo);
            } catch (FileNotFoundException e) {

            }
        }
        Log.i("param",params.toString());
        post("useract/storerelawan/"+email, params, new JsonHttpResponseHandler() {

            //   ProgressDialog dialog;

            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG_DAFTAR_RELAWAN, "Sending request");
                //   dialog = ProgressDialog.show(mContext, "Connecting", "Check Connection", true);
            }

            @Override
            public void onSuccess(JSONObject status) {
                super.onSuccess(status);
                Log.i(TAG_DAFTAR_RELAWAN, "Success");
                responseHandler.onSuccessJSONObject(status.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Log.e(TAG_DAFTAR_RELAWAN, "Failed");
                responseHandler.onFailure(e.toString());//e.getMessage());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i(TAG_DAFTAR_RELAWAN, "Disconnected");
                //   dialog.dismiss();
            }

        }, mClient);
    }
    public void submitProgram(String id_user,String nama_program
            ,String lokasi_program,String mulai,String akhir
            ,String supervisor,String deskripsi,String keterangan,String latitude,String longitude){
        RequestParams params = new RequestParams();
        params.put("id_user",id_user);
        params.put("nama_program",nama_program);
        params.put("lokasi_program",lokasi_program);
        params.put("mulai",mulai);
        params.put("akhir",akhir);
        params.put("supervisor",supervisor);
        params.put("deskripsi",deskripsi);
        params.put("keterangan",keterangan);
        params.put("latitude",latitude);
        params.put("longitude",longitude);

        post("beritaact/store", params, new JsonHttpResponseHandler() {

            //   ProgressDialog dialog;

            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG_DAFTAR_RELAWAN, "Sending request");
                //   dialog = ProgressDialog.show(mContext, "Connecting", "Check Connection", true);
            }

            @Override
            public void onSuccess(JSONObject status) {
                super.onSuccess(status);
                Log.i(TAG_DAFTAR_RELAWAN, "Success");
                responseHandler.onSuccessJSONObject(status.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Log.e(TAG_DAFTAR_RELAWAN, "Failed");
                responseHandler.onFailure(e.toString());//e.getMessage());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i(TAG_DAFTAR_RELAWAN, "Disconnected");
                //   dialog.dismiss();
            }

        }, mClient);
    }
}
