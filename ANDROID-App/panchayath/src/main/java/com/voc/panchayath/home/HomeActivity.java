package com.voc.panchayath.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.voc.panchayath.R;
import com.voc.panchayath.base.BaseActivity;
import com.voc.panchayath.model.Panchayath;
import com.voc.panchayath.util.MySharedPreferences;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private HomeActivity ctxt = HomeActivity.this;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private TextView txtName, txtEmail;
    private Toolbar toolbar;

    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_COMPLAINTS = "complaints";
    private static final String TAG_ABOUT_PANCHAYATH = "about_panchayath";
    private static final String TAG_ABOUT_APP = "about_app";
    private static final String TAG_LOGOUT = "logout";
    public static String CURRENT_TAG = TAG_ABOUT_PANCHAYATH;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private Runnable mPendingRunnable = null;
    private static String[] activityTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        activityTitles = getResources().getStringArray(R.array.activity_titles);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.header_user_name);
        txtEmail = navHeader.findViewById(R.id.header_user_email);

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_COMPLAINTS;
            loadHomeFragment();
        }

        setUpProfile();
    }

    private void setUpProfile() {
        Panchayath panchayath = MySharedPreferences.getPanchayathData(ctxt);
        if (panchayath != null) {
            txtName.setText(panchayath.getGramaName());
            txtEmail.setText(panchayath.getEmail());
        }
    }


    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        mPendingRunnable = () -> {
            // update the main content by replacing fragments
            Fragment fragment = getHomeFragment();
            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.activity_container, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.postDelayed(mPendingRunnable, 1000);
        }

        //Closing drawer on item click
        drawer.closeDrawers();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 1:
                return new ComplaintsFragment();
            case 2:
                return new AboutAppFragment();
            case 3:
                logout();
                return null;
            case 0:
            default:
                return new AboutMyPanchayathFragment();
        }
    }

    private void setToolbarTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(activityTitles[navItemIndex]);
        }
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        // This method will trigger on item Click of navigation menu
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    //Check to see which item was being clicked and perform appropriate action
                    switch (menuItem.getItemId()) {
                        //Replacing the main content with ContentFragment Which is our Inbox View;

                        case R.id.nav_about_my_panchayath:
                            navItemIndex = 0;
                            CURRENT_TAG = TAG_ABOUT_PANCHAYATH;
                            break;
                        case R.id.nav_complaints:
                            navItemIndex = 1;
                            CURRENT_TAG = TAG_COMPLAINTS;
                            break;
                        case R.id.nav_about_app:
                            navItemIndex = 2;
                            CURRENT_TAG = TAG_ABOUT_APP;
                            break;
                        case R.id.nav_logout:
                            navItemIndex = 3;
                            CURRENT_TAG = TAG_LOGOUT;
                            break;
                        default:
                            navItemIndex = 0;
                    }

                    //Checking if the item is in checked state or not, if not make it in checked state
                    menuItem.setChecked(!menuItem.isChecked());
                    menuItem.setChecked(true);

                    loadHomeFragment();

                    return true;
                });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                ctxt, drawer, toolbar,
                R.string.open, R.string.close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHandler != null) {
            mHandler.removeCallbacks(mPendingRunnable);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_ABOUT_PANCHAYATH;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }
}
