package com.zinedroid.syromalabar.smartcatechism.activity;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseActivity;
import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.BottomNavigationViewHelper;
import com.zinedroid.syromalabar.smartcatechism.common.Models.MenuContent;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.fragments.ChapterDetailFragment;
import com.zinedroid.syromalabar.smartcatechism.fragments.ClassSelectionFragment;
import com.zinedroid.syromalabar.smartcatechism.fragments.DailySaintsFragment;
import com.zinedroid.syromalabar.smartcatechism.fragments.HomeFragment;
import com.zinedroid.syromalabar.smartcatechism.fragments.LanguageSelectionFragment;
import com.zinedroid.syromalabar.smartcatechism.fragments.MenuDetailFragment;
import com.zinedroid.syromalabar.smartcatechism.fragments.PocBibleFragment;
import com.zinedroid.syromalabar.smartcatechism.fragments.YouCatFragment;
import com.zinedroid.syromalabar.smartcatechism.webservice.WebserviceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.isServiceCalled;
import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.mPlayer;


public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, Utility.HomeTitle, WebserviceCall.WebServiceCall,Utility.menuIconChange {

    Toolbar toolbar;
    public static BaseFragment fragment;
    FragmentTransaction ft;
    FragmentManager fm;
    Menu optionMenu;
    ArrayList<MenuContent> mCategoryList;
    NavigationView navigationView;
    Dialog dialog;
    DrawerLayout drawer;
    BottomNavigationView bottomNavigationView;
    ImageView mMenuIcon;
    TextView mTitleTextView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpDrawer();
        if (isNetworkAvailable()) {
                if (!isServiceCalled) {
                    new WebserviceCall(this, AppConstants.Methods.getResourceCategoryList).execute(new String[]{""});
                }
            } else {
            if (fragment == null) {
                onFrangmentChange(new LanguageSelectionFragment(), true, false);


                /*if (!getSharedPreference(AppConstants.APIKeys.CLASS_ID).equalsIgnoreCase("DEFAULT")) {
                    onFrangmentChange(new HomeFragment(), true, true);
                }
                else {
                    onFrangmentChange(new LanguageSelectionFragment(), true, false);
                }*/

            } else {
                onFrangmentChange(new LanguageSelectionFragment(), true, false);
            }
            }

           // getSupportActionBar().setTitle("Select Language");



    }

    private void onFrangmentChange(BaseFragment fragment, boolean replace, boolean addBackstack) {
        this.fragment = fragment;

        fm = getFragmentManager();

        ft = fm.beginTransaction();
        if (replace) {
            ft.replace(R.id.fragment, fragment);
        } else {
            ft.add(R.id.fragment, fragment);
        }
        if (addBackstack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }


    public void setUpDrawer() {
        mMenuIcon = (ImageView) findViewById(R.id.menu_icon);
        mTitleTextView = (TextView) findViewById(R.id.mTitleTextView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setVisibility(View.GONE);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            BaseFragment fragment;
            switch (item.getItemId()) {
                case R.id.select_language:
                    //    toolbar.setTitle("Shop");
                    //    CURRENT_TAG = TAG_HOME;

                    ChangeFragment(new LanguageSelectionFragment(), true, true);
                    bottomNavigationView.setVisibility(View.GONE);
                    break;
                case R.id.select_class:

                    ChangeFragment(new ClassSelectionFragment(), true, true);
                    break;

            }
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();

            }
            return true;
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragment.getClass().getSimpleName().equalsIgnoreCase("LanguageSelectionFragment")) {
                new AlertDialog.Builder(this)
                        .setTitle("Close App?")
                        .setMessage("Do you really want to close this app?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        finish();
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
//                super.onBackPressed();
            } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DailySaintsFragment")) {
                /*fragment = new HomeFragment();
                onFrangmentChange(fragment, true, true);*/
                fm.popBackStack();
            }

            else if (fragment.getClass().getSimpleName().equalsIgnoreCase("SaintDetailFragment")) {
//                fragment = new DailySaintsFragment();
//                onFrangmentChange(fragment, true, false);
                fm.popBackStack();
            }
            else if (fragment.getClass().getSimpleName().equalsIgnoreCase("GalleryFragment")) {
//                fragment = new DailySaintsFragment();
//                onFrangmentChange(fragment, true, false);
                fm.popBackStack();
            }
            else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MenuDetailFragment")) {
                fragment = new HomeFragment();
                onFrangmentChange(fragment, true, false);
            } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("ChapterDetailFragment")) {
                try {
                    if (mPlayer != null) {
                        if (mPlayer.isPlaying()) {
                            Log.d("clicked", "clicked");
                            mPlayer.stop();
                            mPlayer.release();  //
                            mPlayer = null;
                        }
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }

                fragment = new HomeFragment();
                onFrangmentChange(fragment, true, false);
            }

            else if (fragment.getClass().getSimpleName().equalsIgnoreCase("PocBibleFragment")) {
                if(!getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID).equalsIgnoreCase("DEFAULT")){
                    fm.popBackStack();
                }
                else {
                    onFrangmentChange(new LanguageSelectionFragment(), true, false);
                }
            }
            else if (fragment.getClass().getSimpleName().equalsIgnoreCase("YouCatFragment")) {
                if(!getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID).equalsIgnoreCase("DEFAULT")){
                    fm.popBackStack();
                }
                else {
                    onFrangmentChange(new LanguageSelectionFragment(), true, false);
                }
            }

            else if (fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment")) {
                /*fragment = new ClassSelectionFragment();
                onFrangmentChange(fragment, true, false);*/
                new AlertDialog.Builder(this)
                        .setTitle("Close App?")
                        .setMessage("Do you really want to close this app?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {


                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        finish();
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();

            } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("ClassSelectionFragment")) {
                fragment = new LanguageSelectionFragment();
                bottomNavigationView.setVisibility(View.GONE);
                onFrangmentChange(fragment, true, false);
            } else {
                fm.popBackStack();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        optionMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else
        if (id == R.id.lang_settings) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("lang", "clear");
            startActivity(intent);
        } else if (id == R.id.class_settings) {
            if (isNetworkAvailable()) {
                fragment = new ClassSelectionFragment();
                onFrangmentChange(fragment, true, true);
            } else {
                Toast.makeText(this, "Please connect to the network and try again!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.font_change) {
            dialog = new Dialog(HomeActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.font_change_dialog);
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=com.zinedroid.mobile.smsmartcatechism&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.daily_saints) {
            fragment = new DailySaintsFragment();
            onFrangmentChange(fragment, true, true);

        } else if (id == R.id.home) {
            fragment = new HomeFragment();
            onFrangmentChange(fragment, true, false);

        } else if (id == R.id.poc_bible) {
            fragment = new PocBibleFragment();
            onFrangmentChange(fragment, true, true);

        /*    PackageManager manager = getPackageManager();
            Intent intent = manager.getLaunchIntentForPackage("org.jesusyouth.poc.activity");

            try {
                startActivity(intent);
//                startActivityForResult(intent, 101);
            } catch (Exception e) {
                e.printStackTrace();
                Uri uri = Uri.parse("market://search?q=pname:" + "org.jesusyouth.poc.activity");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent1);
                } catch (ActivityNotFoundException e1) {
                    //creates a small window to notify there is no app available
                }

            }*/
        } else if (id == R.id.docat) {

            fragment = new YouCatFragment();
            onFrangmentChange(fragment, true, true);



           /* PackageManager manager = getPackageManager();
            Intent intent = manager.getLaunchIntentForPackage("com.docatapp");

            try {
                startActivity(intent);
//                startActivityForResult(intent, 101);
            } catch (Exception e) {
                e.printStackTrace();
                Uri uri = Uri.parse("market://search?q=pname:" + "com.docatapp");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent1);
                } catch (ActivityNotFoundException e1) {
                    //creates a small window to notify there is no app available
                }

            }*/
        } else {
            workCategory(item);
        }

        return super.onOptionsItemSelected(item);
    }


    public void onCancel(View view) {
        dialog.dismiss();
    }

    public void onTextLarge(View view) {

        if (fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment")) {
            setSharedPreference("FONT", "LARGE");
            fragment = new HomeFragment();
            dialog.dismiss();
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DailySaintsFragment")) {
            setSharedPreference("FONT", "LARGE");
            fragment = new DailySaintsFragment();
            dialog.dismiss();
        } else {
            setSharedPreference("FONT", "LARGE");
            fragment = new ChapterDetailFragment();
            onFrangmentChange(fragment, true, true);
            dialog.dismiss();
        }


    }

    public void onTextSmall(View view) {

        if (fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment")) {
            setSharedPreference("FONT", "SMALL");
            fragment = new HomeFragment();
            dialog.dismiss();
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DailySaintsFragment")) {
            setSharedPreference("FONT", "SMALL");
            fragment = new DailySaintsFragment();
            dialog.dismiss();
        } else {
            setSharedPreference("FONT", "SMALL");
            fragment = new ChapterDetailFragment();
            onFrangmentChange(fragment, true, true);
            dialog.dismiss();

        }

    }

    public void onTextMedium(View view) {

        if (fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment")) {
            setSharedPreference("FONT", "MEDIUM");
            fragment = new HomeFragment();
            dialog.dismiss();
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DailySaintsFragment")) {
            setSharedPreference("FONT", "MEDIUM");
            fragment = new DailySaintsFragment();
            dialog.dismiss();
        } else {
            setSharedPreference("FONT", "MEDIUM");
            fragment = new ChapterDetailFragment();
            onFrangmentChange(fragment, true, true);
            dialog.dismiss();
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else
        if (id == R.id.lang_settings) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("lang", "clear");
            startActivity(intent);
        } else if (id == R.id.class_settings) {
            if (isNetworkAvailable()) {
                fragment = new ClassSelectionFragment();
                onFrangmentChange(fragment, true, false);
            } else {
                Toast.makeText(this, "Please connect to the network and try again!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.font_change) {
            dialog = new Dialog(HomeActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.font_change_dialog);
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);
        } else if (id == R.id.home) {
            fragment = new HomeFragment();
            onFrangmentChange(fragment, true, false);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=com.zinedroid.mobile.smsmartcatechism&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.daily_saints) {
            fragment = new DailySaintsFragment();
            onFrangmentChange(fragment, true, true);
        } else if (id == R.id.poc_bible) {
            fragment = new PocBibleFragment();
            onFrangmentChange(fragment, true, false);
           /* PackageManager manager = getPackageManager();
            Intent intent = manager.getLaunchIntentForPackage("org.jesusyouth.poc.activity");

            try {
                startActivity(intent);
//                startActivityForResult(intent, 101);
            } catch (Exception e) {
                e.printStackTrace();
                Uri uri = Uri.parse("market://search?q=pname:" + "org.jesusyouth.poc.activity");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent1);
                } catch (ActivityNotFoundException e1) {
                    //creates a small window to notify there is no app available
                }

            }*/
        } else if (id == R.id.docat) {

            fragment = new YouCatFragment();
            onFrangmentChange(fragment, true, true);



           /* PackageManager manager = getPackageManager();
            Intent intent = manager.getLaunchIntentForPackage("com.docatapp");

            try {
                startActivity(intent);
//                startActivityForResult(intent, 101);
            } catch (Exception e) {
                e.printStackTrace();
                Uri uri = Uri.parse("market://search?q=pname:" + "com.docatapp");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent1);
                } catch (ActivityNotFoundException e1) {
                    //creates a small window to notify there is no app available
                }

            }*/
        } else {
            workCategory(item);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void workCategory(MenuItem item) {


        if (isNetworkAvailable()) {
            for (int j = 0; j < mCategoryList.size(); j++) {
                if (mCategoryList.get(j).getContent().equalsIgnoreCase(String.valueOf(item.getTitle()))) {
                    MenuContent mContents = new MenuContent();
                    mContents = mCategoryList.get(j);
                    AppConstants.mMenuContent = mContents;
                    onFrangmentChange(new MenuDetailFragment(), true, true);
                    Log.i("Selected Menu++", mContents.getContent());
                }
            }
        }
    }


    @Override
    public void onTitleChange(String Title) {
       // getSupportActionBar().setTitle(Title);
        mTitleTextView.setText(Title);
        if (fragment.getClass().getSimpleName().equalsIgnoreCase("ClassSelectionFragment")) {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem select_class = menu.findItem(R.id.select_class);
            select_class.setVisible(false);

            bottomNavigationView.setVisibility(View.VISIBLE);
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment")) {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem select_class = menu.findItem(R.id.select_class);
            select_class.setVisible(true);
            MenuItem select_language = menu.findItem(R.id.select_language);
            select_language.setVisible(true);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("PocBibleFragment")) {
            bottomNavigationView.setVisibility(View.GONE);
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("YouCatFragment")) {
            bottomNavigationView.setVisibility(View.GONE);
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DailySaintsFragment")) {
           /* Menu menu = bottomNavigationView.getMenu();
            MenuItem select_class = menu.findItem(R.id.select_class);
            select_class.setVisible(false);
            MenuItem select_language = menu.findItem(R.id.select_language);
            select_language.setVisible(false);*/
            bottomNavigationView.setVisibility(View.GONE);
        }

    }

    @Override
    public void ChangeFragment(BaseFragment fragment, boolean replace, boolean addBackstack) {
        onFrangmentChange(fragment, replace, addBackstack);
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        isServiceCalled = false;

        if (fragment == null) {
            onFrangmentChange(new LanguageSelectionFragment(), true, false);
          /*  if (!getSharedPreference(AppConstants.APIKeys.CLASS_ID).equalsIgnoreCase("DEFAULT")) {
                onFrangmentChange(new HomeFragment(), true, true);
            }
            else {
                onFrangmentChange(new LanguageSelectionFragment(), true, false);
            }
*/
        } else {
            onFrangmentChange(new LanguageSelectionFragment(), true, false);
        }
        switch (method) {
            case AppConstants.Methods.getResourceCategoryList:
                try {
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.ResponseCode.CATEGORY_LIST_SUCCESS)) {
                        JSONArray mCategoryArray = mJsonObject.getJSONArray(AppConstants.APIKeys.RESOURCE_LIST);
                        SubMenu extas = optionMenu.addSubMenu(0, 3, 8, "Extras");
                        SubMenu extasNavMenu = navigationView.getMenu().addSubMenu(0, 3, 8, "Extras");
                        mCategoryList = new ArrayList<>();
                        for (int i = 0; i < mCategoryArray.length(); i++) {
                            MenuContent mContents = new MenuContent();
                            JSONObject mCategoryItem = mCategoryArray.getJSONObject(i);
                            mContents.setContentId(mCategoryItem.getString(AppConstants.APIKeys.ID));
                            mContents.setContent(mCategoryItem.getString(AppConstants.APIKeys.CATEGORY));
                            extas.add(mCategoryItem.getString(AppConstants.APIKeys.CATEGORY));
                            extasNavMenu.add(mCategoryItem.getString(AppConstants.APIKeys.CATEGORY));
                            mCategoryList.add(mContents);
                        }
                        getMenuInflater().inflate(R.menu.activity_home_drawer, optionMenu);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void iconchange(BaseFragment fragment1) {
        if (fragment1.getClass().getSimpleName().equalsIgnoreCase("HomeFragment")) {
            Log.d("homefragment", "homefragment");
            // fragment=new HomeFragment(HomeActivity.this,"home");
            mMenuIcon.setImageResource(R.drawable.ic_menu_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    drawer.openDrawer(Gravity.START);
                }
            });

        }
        else if (fragment1.getClass().getSimpleName().equalsIgnoreCase("ChapterDetailFragment")) {
            mMenuIcon.setImageResource(R.drawable.ic_arrow_back_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFrangmentChange(new HomeFragment(), true, true);
                }
            });
        }
        else if (fragment1.getClass().getSimpleName().equalsIgnoreCase("SaintDetailFragment")) {

            mMenuIcon.setImageResource(R.drawable.ic_arrow_back_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onFrangmentChange(new DailySaintsFragment(), true, true);
                    fm.popBackStack();
                  //  onFrangmentChange(new HomeFragment(), true, false);
                }
            });
        }
        else if (fragment1.getClass().getSimpleName().equalsIgnoreCase("DailySaintsFragment")) {

            mMenuIcon.setImageResource(R.drawable.ic_arrow_back_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((!getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID).equalsIgnoreCase("DEFAULT"))&&(!getSharedPreference(AppConstants.APIKeys.CLASS_ID).equalsIgnoreCase("DEFAULT"))){
                        onFrangmentChange(new HomeFragment(), true, false);
                    }
                    else {
                        onFrangmentChange(new LanguageSelectionFragment(), true, true);
                    }


                }
            });
        }
        else  if (fragment1.getClass().getSimpleName().equalsIgnoreCase("LanguageSelectionFragment")) {
            mMenuIcon.setImageResource(R.drawable.ic_menu_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    drawer.openDrawer(Gravity.START);
                }
            });

        }
        else  if (fragment1.getClass().getSimpleName().equalsIgnoreCase("ClassSelectionFragment")) {
            mMenuIcon.setImageResource(R.drawable.ic_menu_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    drawer.openDrawer(Gravity.START);
                }
            });

        }
        else if (fragment1.getClass().getSimpleName().equalsIgnoreCase("MenuDetailFragment")) {

            mMenuIcon.setImageResource(R.drawable.ic_arrow_back_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // onFrangmentChange(new HomeFragment(), true, true);
                    fm.popBackStack();
                }
            });
        }
        else if (fragment1.getClass().getSimpleName().equalsIgnoreCase("GalleryFragment")) {

            mMenuIcon.setImageResource(R.drawable.ic_arrow_back_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // onFrangmentChange(new HomeFragment(), true, true);
                    fm.popBackStack();
                }
            });
        }
        else if (fragment1.getClass().getSimpleName().equalsIgnoreCase("PocBibleFragment")) {

            mMenuIcon.setImageResource(R.drawable.ic_arrow_back_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onFrangmentChange(new DailySaintsFragment(), true, true);

                    //  onFrangmentChange(new HomeFragment(), true, false);

                    if(!getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID).equalsIgnoreCase("DEFAULT")){
                        fm.popBackStack();
                    }
                    else {
                        onFrangmentChange(new LanguageSelectionFragment(), true, false);
                    }


                }
            });
        }
        else if (fragment1.getClass().getSimpleName().equalsIgnoreCase("YouCatFragment")) {

            mMenuIcon.setImageResource(R.drawable.ic_arrow_back_white_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onFrangmentChange(new DailySaintsFragment(), true, true);

                    //  onFrangmentChange(new HomeFragment(), true, false);

                    if(!getSharedPreference(AppConstants.APIKeys.LANGUAGE_ID).equalsIgnoreCase("DEFAULT")){
                        fm.popBackStack();
                    }
                    else {
                        onFrangmentChange(new LanguageSelectionFragment(), true, false);
                    }


                }
            });
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

}