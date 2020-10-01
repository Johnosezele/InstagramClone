package com.johnosezele1.instagramclone.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.johnosezele1.instagramclone.R;
import com.johnosezele1.instagramclone.Utils.BottomNavigationViewHelper;
import com.johnosezele1.instagramclone.Utils.GridImageAdapter;
import com.johnosezele1.instagramclone.Utils.UniversalImageLoader;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;

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

        tempGridSetup();
    }
    private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("https://i.redd.it/bb4jxttxhpo51.jpg");
        imgURLs.add("https://i.redd.it/vs7siosr4po51.jpg");
        imgURLs.add("https://i.redd.it/eopeuhaitpo51.jpg");
        imgURLs.add("https://i.redd.it/f42nsc3fqjo51.jpg");
        imgURLs.add("https://i.redd.it/p3swaitszho51.jpg");
        imgURLs.add("https://i.redd.it/32yeysp65po51.jpg");
        imgURLs.add("https://i.redd.it/7bll9wuikoo51.jpg");
        imgURLs.add("https://i.redd.it/a92onj12pio51.jpg");
        imgURLs.add("https://i.redd.it/0erdcegw6oo51.jpg");
        imgURLs.add("https://i.redd.it/uafceuxi4po51.jpg");
        imgURLs.add("https://i.redd.it/ju5sab7yono51.jpg");
        imgURLs.add("https://i.redd.it/ff7p724o3lo51.jpg");

        setupImageGrid(imgURLs);
    }

    private void setupImageGrid(ArrayList<String> imgURLs){
        GridView gridView = findViewById(R.id.gridView);

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs);
        gridView.setAdapter(adapter);
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