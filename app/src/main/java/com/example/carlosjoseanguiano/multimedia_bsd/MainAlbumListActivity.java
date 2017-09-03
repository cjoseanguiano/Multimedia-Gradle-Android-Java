package com.example.carlosjoseanguiano.multimedia_bsd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


public class MainAlbumListActivity extends AppCompatActivity implements PhotoAlbumFragment.OnMediaSelectedPhotoAlbum, VideoAlbumFragment.OnMediaSelectedVideoAlbum {
    public static final String TAG = MainAlbumListActivity.class.getSimpleName();
    public static final String EXTRA_NAME = "sendUser";
    private static final String EXTRA_JID = "extra_jid";
    private static final String EXTRA_BUCKET = "extra_bucket";
    private static final String EXTRA_TYPE_ALBUM = "extra_type_album";
    private static final String BACK_PRESSED = "back_pressed";
    private String body;
    private boolean backPressed = false;
    private static final String EXTRA_BACK_SELECT = "extra_back_select";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_album_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    String tabFoto = "Picture";
    String tabVideo = "Video";

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (getIntent().hasExtra(BACK_PRESSED)) {
            backPressed = true;
        }
        adapter.addFragment(new PhotoAlbumFragment(backPressed), tabFoto);
        adapter.addFragment(new VideoAlbumFragment(backPressed), tabVideo);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMediaSelected(String message, String type, boolean backPressed) {
        Log.i(TAG, "onMediaSelected: ");
    }

}