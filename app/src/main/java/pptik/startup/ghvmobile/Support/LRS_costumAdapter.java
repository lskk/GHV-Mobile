package pptik.startup.ghvmobile.Support;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pptik.startup.ghvmobile.ApprovalRelawanDetail;
import pptik.startup.ghvmobile.DisapprovRelawan;
import pptik.startup.ghvmobile.R;

/**
 * Created by GIGABYTE on 17/06/2016.
 */
public class LRS_costumAdapter extends BaseAdapter {

    ArrayList<DataUser2> listDataUser2;
    Context context;
    private static LayoutInflater inflater=null;
    public LRS_costumAdapter(DisapprovRelawan ar, ArrayList<DataUser2> data) {
        listDataUser2 = new ArrayList<DataUser2>();
        listDataUser2 = data;
        context= ar;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listDataUser2.size();
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
            view = inflater.inflate(R.layout.lv_list_relawan, parent, false);
        }

        TextView nama = (TextView) view.findViewById(R.id.lv_relawn_name);
        nama.setText(listDataUser2.get(_position).getNamaLengkap());

        TextView email = ((TextView) view.findViewById(R.id.lv_relawan_email));
        email.setText(listDataUser2.get(_position).getEmail());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApprovalRelawanDetail.class);
                intent.putExtra("detail", listDataUser2.get(_position));
                intent.putExtra("status",3);
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
