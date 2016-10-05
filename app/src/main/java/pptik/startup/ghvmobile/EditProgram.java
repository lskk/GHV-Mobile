package pptik.startup.ghvmobile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import pptik.startup.ghvmobile.Setup.ApplicationConstants;
import pptik.startup.ghvmobile.Support.PhotoManager;
import pptik.startup.ghvmobile.User_Admin.ApprovalProgramDetail;

public class EditProgram extends AppCompatActivity {

    private ImageView mainimage;
    EditText nama,lokasi,mulai,akhir,supervisor,deskripsi,keterangan;
    Button submit;
    private int id_program;
    private ProgressDialog pDialog;

    //upload image variable
    private TextView upload_image;
    protected final int CAMERA_REQUEST = 100;
    protected final int SELECT_PICTURE = 200;
    private PhotoManager photoManager;
    private String rootPhotoDirectory = "", finalPhotoPath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_program_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bindingXml();
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            id_program=extras.getInt("id_program");
            ambildataprogram();
        }


    }

    private void bindingXml(){
    mainimage=(ImageView)findViewById(R.id.picture_path_program);
        nama=(EditText)findViewById(R.id.submit_program_nama);
        lokasi=(EditText)findViewById(R.id.submit_program_lokasi);
        mulai=(EditText)findViewById(R.id.submit_program_mulai);
        akhir=(EditText)findViewById(R.id.submit_program_akhir);
        supervisor=(EditText)findViewById(R.id.submit_program_supervisor);
        deskripsi=(EditText)findViewById(R.id.submit_program_deskripsi);
        keterangan=(EditText)findViewById(R.id.submit_program_keterangan);
        submit=(Button)findViewById(R.id.submit_program_btnsubmit);


        upload_image=(TextView)findViewById(R.id.upload_photo_program);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(EditProgram.this).create();
                dialog.setMessage("Please Choose Picture Method :");
                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, SELECT_PICTURE);
                    }
                });

                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Take Photo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                });
                dialog.show();
            }
        });
        photoManager  = new PhotoManager();
        rootPhotoDirectory = photoManager.getNewPathAppsDirectory();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CAMERA_REQUEST && resultCode==RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
            String fileDest = sd.format(new Date())+".png";
            File photoName = new File(fileDest);
            String fullPhotoPath = rootPhotoDirectory+File.separator+photoName.toString();
            //Bitmap bmp = photo.createScaledBitmap(photo, 400,400, true);
            //Bitmap bmp = ImageScaleUtil.createScaledBitmap(photo, 400, 400, ImageScaleUtil.ScalingLogic.FIT);
            //bmp.recycle();
            //takePictures.setImageBitmap(photo);
            //picture_path.setText(fullPhotoPath);
            finalPhotoPath = fullPhotoPath;
            Bitmap resizedimage=Bitmap.createScaledBitmap(photo,(int)(photo.getWidth()*0.5), (int)(photo.getHeight()*0.5), true);
            mainimage.setImageBitmap(resizedimage);
            savebitmap(photo, fullPhotoPath);
        }else if(requestCode == SELECT_PICTURE && resultCode==RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            String path = getPath(selectedImage);
            //takePictures.setImageBitmap(BitmapFactory.decodeFile(path));
            //File source = new File(path);
            //SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
            //File destination = new File(rootPhotoDirectory+File.separator+sd.format(new Date())+".png");
            finalPhotoPath = path;
            Bitmap decode= BitmapFactory.decodeFile(path);
            Bitmap resizedimage=Bitmap.createScaledBitmap(decode,(int)(decode.getWidth()*0.5), (int)(decode.getHeight()*0.5), true);
            mainimage.setImageBitmap(resizedimage);
            //picture_path.setText(destination.getAbsolutePath());
            //if(photoManager.copyPhotoFromGallery(source, destination)){
            //    Log.d("Succes","Success Copy File");
            //}else{
            //    Log.d("Failed", "Failde Copy File");
            //}
        }
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String picturepath = cursor.getString(column_index);
        cursor.close();
        return picturepath;
    }

    private File savebitmap(Bitmap bmp, String path) {
        try {
            File photo = new File(path);
            FileOutputStream fos = new FileOutputStream(photo);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Log.d("Success", "File Success Created");
            Toast.makeText(EditProgram.this, path, Toast.LENGTH_LONG).show();
            Log.d("Path", path);
            return photo;
        }catch (Exception e) {
            Log.d("Failed","File Failed Created : "+e.getMessage());
            return null;
        }
    }
    private void ambildataprogram(){
        pDialog = ProgressDialog.show(EditProgram.this, "Memuat Program", "Harap Tunggu");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ApplicationConstants.API_GET_PROGRAM_BY_ID+id_program,
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean status = jObj.getBoolean("status");
                            if (status) {
                                JSONArray berita= jObj.getJSONArray("berita");

                                for (int i=0;i<berita.length();i++){
                                    JSONObject abc=berita.getJSONObject(i);
                                    nama.setText(abc.getString("nama_program"));
                                    mulai.setText(abc.getString("mulai"));
                                    akhir.setText(abc.getString("akhir"));
                                    supervisor.setText(abc.getString("supervisor"));
                                    lokasi.setText(abc.getString("lokasi_program"));
                                    keterangan.setText(abc.getString("keterangan"));
                                    deskripsi.setText(abc.getString("deskripsi"));
                                    if (!abc.getString("main_image").isEmpty() ||abc.getString("main_image")!=null){
                                        Picasso.with(EditProgram.this)
                                                .load(abc.getString("main_image"))
                                                .fit()
                                                .into(mainimage);
                                    }

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

}
