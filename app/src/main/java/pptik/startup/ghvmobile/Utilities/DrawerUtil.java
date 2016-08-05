package pptik.startup.ghvmobile.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


import pptik.startup.ghvmobile.Admin;
import pptik.startup.ghvmobile.Detailmateri;


/**
 * Created by Hynra on 6/8/16.
 */
public class DrawerUtil {

    private Context context;
    private Toolbar toolbar;
    private int identifier;
    private com.mikepenz.materialdrawer.Drawer result;
    private SecondaryDrawerItem bantuanItem;

    public DrawerUtil(Context _context, Toolbar _toolbar, int _identifier){
        context = _context;
        toolbar = _toolbar;
        identifier = _identifier;
    }

    public void initDrawer() {

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity((Activity) context)
             //   .withHeaderBackground(context.getResources().getDrawable(R.drawable.bitmap_cover_drawer))
                .build();

        PrimaryDrawerItem profile;
        PrimaryDrawerItem permintaanTPI;
        PrimaryDrawerItem tambahLaporan;
        PrimaryDrawerItem riwayatLaporan;
        PrimaryDrawerItem panicButton;
        PrimaryDrawerItem logout;


        result = new DrawerBuilder()
                .withActivity((Activity) context)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        profile = new PrimaryDrawerItem().withName("Profil").withIcon(GoogleMaterial.Icon.gmd_face).withIdentifier(1),
                        permintaanTPI = new PrimaryDrawerItem().withName("Permintaan TPI").withIcon(GoogleMaterial.Icon.gmd_timeline).withIdentifier(2),
                        tambahLaporan = new PrimaryDrawerItem().withName("Tambah Laporan").withIcon(GoogleMaterial.Icon.gmd_add).withIdentifier(3),
                        riwayatLaporan = new PrimaryDrawerItem().withName("Riwayat Laporan").withIcon(GoogleMaterial.Icon.gmd_history).withIdentifier(4),
                        panicButton = new PrimaryDrawerItem().withName("Tombol Panik").withIcon(GoogleMaterial.Icon.gmd_filter_tilt_shift).withIdentifier(5),
                        logout = new PrimaryDrawerItem().withName("Keluar").withIcon(GoogleMaterial.Icon.gmd_exit_to_app).withIdentifier(6)

                )
                .withSelectedItem(identifier)
                .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;

                            if (drawerItem.getIdentifier() == 1) {
                                    //--- Profil
                                    intent = new Intent(context, Admin.class);
                                    context.startActivity(intent);
                                    ((Activity)context).finish();
                                    result.closeDrawer();
                            }else if(drawerItem.getIdentifier() == 2){
                                    //--- PERMINTAAN TPI
                                    result.closeDrawer();
                                }
                            }else if(drawerItem.getIdentifier() == 3){
                                    //--- TAMBAH LAPORAN
                                    result.closeDrawer();
                            }else if(drawerItem.getIdentifier() == 4){
                                    //--- RIWAYAT LAPORAN
                                    result.closeDrawer();

                            }else if(drawerItem.getIdentifier() == 5){
                                    result.closeDrawer();

                            }else if(drawerItem.getIdentifier() == 6){
                                    //--- LOGOUT
                                    result.closeDrawer();
                        }
                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //result.setSelection(0);
        result.closeDrawer();
    }
}