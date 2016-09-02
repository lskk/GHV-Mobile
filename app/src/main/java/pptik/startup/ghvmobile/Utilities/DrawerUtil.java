package pptik.startup.ghvmobile.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


import pptik.startup.ghvmobile.SubmitedProgram;
import pptik.startup.ghvmobile.User_Admin.Admin;
import pptik.startup.ghvmobile.User_Guest.GuestListProgram;
import pptik.startup.ghvmobile.User_Guest.GuestMenu;
import pptik.startup.ghvmobile.Login;
import pptik.startup.ghvmobile.User_Relawan.ProfileRelawan;
import pptik.startup.ghvmobile.User_Relawan.Relawan_Program;


/**
 * Created by Hynra on 6/8/16.
 */
public class DrawerUtil {

    private Context context;
    private Toolbar toolbar;
    private int identifier;
    private com.mikepenz.materialdrawer.Drawer result;
    private SecondaryDrawerItem bantuanItem;
    private int roleid;
    SharedPreferences prefs;
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
    public void initDrawerGuest() {



        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity((Activity) context)
                //   .withHeaderBackground(context.getResources().getDrawable(R.drawable.bitmap_cover_drawer))
                .build();

        PrimaryDrawerItem registerRelawan;
        PrimaryDrawerItem listProgramIssue;
        PrimaryDrawerItem tentangKami;
        PrimaryDrawerItem logout;


        result = new DrawerBuilder()
                .withActivity((Activity) context)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        registerRelawan = new PrimaryDrawerItem().withName("Register Volunteer").withIcon(GoogleMaterial.Icon.gmd_pan_tool).withIdentifier(1),
                        listProgramIssue = new PrimaryDrawerItem().withName("Daftar Program dan Issue").withIcon(GoogleMaterial.Icon.gmd_view_headline).withIdentifier(2),
                        tentangKami = new PrimaryDrawerItem().withName("About Us").withIcon(GoogleMaterial.Icon.gmd_info).withIdentifier(3),
                        logout = new PrimaryDrawerItem().withName("Log Out").withIcon(GoogleMaterial.Icon.gmd_exit_to_app).withIdentifier(4)

                )
                .withSelectedItem(identifier)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;

                            if (drawerItem.getIdentifier() == 1) {
                                //--- daftar relawan
                                GuestMenu gm=new GuestMenu();
                                gm.checkstatusdaftar(context);
                                result.closeDrawer();
                            }else if(drawerItem.getIdentifier() == 2){
                                //--- Daftar Issue/berita
                                intent = new Intent(context, GuestListProgram.class);
                                context.startActivity(intent);
                                result.closeDrawer();
                                ((Activity)context).finish();
                            }else if(drawerItem.getIdentifier() == 3){

                                result.closeDrawer();
                            }else if(drawerItem.getIdentifier() == 4){
                                //--- logout
                                context.getSharedPreferences("UserDetails",
                                        Context.MODE_PRIVATE).edit().clear().commit();
                                intent = new Intent(context, Login.class);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                                result.closeDrawer();

                            }else if(drawerItem.getIdentifier() == 5){
                                result.closeDrawer();

                            }else if(drawerItem.getIdentifier() == 6){
                                //--- LOGOUT
                                result.closeDrawer();
                            }
                        }

                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //result.setSelection(0);
        result.closeDrawer();
    }
    public void initDrawerRelawan() {



        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity((Activity) context)
                //   .withHeaderBackground(context.getResources().getDrawable(R.drawable.bitmap_cover_drawer))
                .build();

        PrimaryDrawerItem profileRelawan;
        PrimaryDrawerItem listProgramIssue;
        PrimaryDrawerItem submittedProgram;
        PrimaryDrawerItem tentangKami;
        PrimaryDrawerItem logout;


        result = new DrawerBuilder()
                .withActivity((Activity) context)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        profileRelawan = new PrimaryDrawerItem().withName("Profile").withIcon(GoogleMaterial.Icon.gmd_face).withIdentifier(1),
                        listProgramIssue = new PrimaryDrawerItem().withName("Daftar Program dan Issue").withIcon(GoogleMaterial.Icon.gmd_view_headline).withIdentifier(2),
                        submittedProgram = new PrimaryDrawerItem().withName("Submitted Program").withIcon(GoogleMaterial.Icon.gmd_view_headline).withIdentifier(3),
                        tentangKami = new PrimaryDrawerItem().withName("About Us").withIcon(GoogleMaterial.Icon.gmd_info).withIdentifier(4),
                        logout = new PrimaryDrawerItem().withName("Log Out").withIcon(GoogleMaterial.Icon.gmd_exit_to_app).withIdentifier(5)

                )
                .withSelectedItem(identifier)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;

                            if (drawerItem.getIdentifier() == 1) {
                                //--- daftar relawan
                                intent = new Intent(context, ProfileRelawan.class);
                                context.startActivity(intent);
                                result.closeDrawer();
                                ((Activity)context).finish();
                            }else if(drawerItem.getIdentifier() == 2){
                                //--- Daftar Issue/berita
                                intent = new Intent(context, Relawan_Program.class);
                                context.startActivity(intent);
                                result.closeDrawer();
                                ((Activity)context).finish();
                            }else if(drawerItem.getIdentifier() == 3){

                                intent = new Intent(context, SubmitedProgram.class);
                                context.startActivity(intent);
                                result.closeDrawer();
                                ((Activity)context).finish();
                            }else if(drawerItem.getIdentifier() == 4){

                                intent = new Intent(context, SubmitedProgram.class);
                                context.startActivity(intent);
                                result.closeDrawer();
                            }else if(drawerItem.getIdentifier() == 5){
                                //--- logout
                                context.getSharedPreferences("UserDetails",
                                        Context.MODE_PRIVATE).edit().clear().commit();
                                intent = new Intent(context, Login.class);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                                result.closeDrawer();

                            }else if(drawerItem.getIdentifier() == 5){
                                result.closeDrawer();

                            }else if(drawerItem.getIdentifier() == 6){
                                //--- LOGOUT
                                result.closeDrawer();
                            }
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