package pptik.startup.ghvmobile.Support;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import pptik.startup.ghvmobile.ApprovalRelawan;
import pptik.startup.ghvmobile.Detailmateri;
import pptik.startup.ghvmobile.Guest;
import pptik.startup.ghvmobile.R;
import pptik.startup.ghvmobile.Relawan;

/**
 * Created by fiyyanp on 4/25/2016.
 */
public class CustomAdapter extends BaseAdapter{
    ArrayList<Program> listProgram;
    ArrayList<DataUser> listDataUser;
    Context context;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Guest guest, ArrayList<Program> data) {
        listProgram = new ArrayList<Program>();
        listProgram = data;
        context= guest;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public CustomAdapter(Relawan guest, ArrayList<Program> data) {
        listProgram = new ArrayList<Program>();
        listProgram = data;
        context= guest;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public CustomAdapter(ApprovalRelawan ar, ArrayList<DataUser> data) {
        listDataUser = new ArrayList<DataUser>();
        listDataUser = data;
        context= ar;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return listProgram.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int _position, View convertView, ViewGroup parent) {
        View view = convertView;
        // There are three cases here
        if (view == null) {
            // 1) The view has not yet been created - we need to initialize the YouTubeThumbnailView.
            view = inflater.inflate(R.layout.lv_list_item, parent, false);
        }
        TextView nama = (TextView) view.findViewById(R.id.lv_program_name);
        nama.setText(listProgram.get(_position).getNamaProgram());

        TextView tanggal = ((TextView) view.findViewById(R.id.lv_program_start));
        tanggal.setText("Tanggal : "+listProgram.get(_position).getMulai());

        TextView supervisor = ((TextView) view.findViewById(R.id.lv_program_supervisor));
        supervisor.setText("Supervisor : "+listProgram.get(_position).getSupervisor());

        TextView verified = ((TextView) view.findViewById(R.id.lv_program_verified));
        //// TODO: 7/12/16 : remove verified textview, ngabisin space list item 
        verified.setVisibility(View.GONE);
        if (listProgram.get(_position).getStatus()==1){
            tanggal.setText(tanggal.getText().toString()+" | Status : Verified");
        }else {
            tanggal.setText(tanggal.getText().toString()+" | Status : Unverified");
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detailmateri.class);
                intent.putExtra("program", listProgram.get(_position));
                context.startActivity(intent);
               /* Log.i("test :", listProgram.get(_position).getNamaProgram());
                Log.i("test :", listProgram.get(_position).getMulai());
                Log.i("test :", listProgram.get(_position).getAkhir());
                Log.i("test :", listProgram.get(_position).getSupervisor());
                Log.i("test :", listProgram.get(_position).getDeskripsi());
                Log.i("test :", listProgram.get(_position).getLokasiProgram());
                Log.i("test :", listProgram.get(_position).getLatitude());
                Log.i("test :", listProgram.get(_position).getLongitude());
                Log.i("test :", listProgram.get(_position).getKeterangan());*/
            }
        });

        return view;
    }

}
