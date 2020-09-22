package com.johnosezele.instagramclone.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.johnosezele.instagramclone.R;
import com.johnosezele.instagramclone.Utils.BottomNavigationViewHelper;
import com.johnosezele.instagramclone.Utils.GridImageAdapter;
import com.johnosezele.instagramclone.Utils.UniversalImageLoader;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;

    private Context mContext = ProfileActivity.this;

    private ProgressBar mProgressBar;
    private ImageView profilePhoto;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");

        setUpBottomNavigationView();
        setupToolbar();
        setupActivityWidgets();
        setProfileImage();
    }
    private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("https://unsplash.com/photos/6VPEOdpFNAs");
        imgURLs.add("https://en.wikipedia.org/wiki/File:Male_impala_profile.jpg");
        imgURLs.add("https://en.wikipedia.org/wiki/File:NafSadh_Profile.jpg");
        imgURLs.add("https://commons.wikimedia.org/wiki/File:G-dragon_profile.jpg");
        imgURLs.add("https://unsplash.com/photos/MRb1aJIwCJs");
        imgURLs.add("https://unsplash.com/photos/oC8D35Omyxw");
        imgURLs.add("https://unsplash.com/photos/7PcN1KEjRZc");
        imgURLs.add("https://unsplash.com/photos/QZge5rhkgSs");
        imgURLs.add("https://unsplash.com/photos/qwyytY_Iguk");
        imgURLs.add("https://unsplash.com/photos/EwKXn5CapA4");
        imgURLs.add("https://unsplash.com/photos/dfo06_DqxpA");
        imgURLs.add("https://unsplash.com/photos/vngzm4P2BTs");

        setupImageGrid(imgURLs);
    }

    private void setupImageGrid(ArrayList<String> imgURLs){
        GridView gridView = findViewById(R.id.gridView);

        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs);
    }
    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile photo.");
        String imgURL = "https://static.vecteezy.com/system/resources/previews/000/071/469/original/android-logo-vector.jpg";
        UniversalImageLoader.setImage(imgURL, profilePhoto, mProgressBar, "");
    }
    private void setupActivityWidgets(){
        mProgressBar = findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);
        profilePhoto = findViewById(R.id.profile_photo);
    }

    /**
     * Responsible for setting up the profile toolbar
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);

        ImageView profileMenu = findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings.");
                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * BottomNavigationView setup
     */
    private void setUpBottomNavigationView() {
        Log.d(TAG, "setUpBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}