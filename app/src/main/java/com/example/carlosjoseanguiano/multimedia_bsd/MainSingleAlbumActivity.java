package com.example.carlosjoseanguiano.multimedia_bsd;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainSingleAlbumActivity extends AppCompatActivity implements TypeAlbumFragment.OnMediaSingleAlbumListener {

    private static final String TAG = MainSingleAlbumActivity.class.getSimpleName();
    private ArrayList<String> path_On = new ArrayList<>();
    private String valueTypeBucket;
    private String valueTypeAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_single_album);
        Log.i(TAG, "onCreate: ");

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

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TypeAlbumFragment(valueTypeBucket, valueTypeAlbum, path_On), null);
        viewPager.setAdapter(adapter);
    }

    private void checkValueofAlbum(String typeFile, String typeOfSelect) {
        if (typeFile != null) {
            valueTypeAlbum = typeFile;
        } else {
            valueTypeAlbum = typeOfSelect;
        }
    }

    private void checkValueofBucket(String typeBucket, String bucketPhotoViewer) {
        if (typeBucket != null) {
            valueTypeBucket = typeBucket;
        } else {
            valueTypeBucket = bucketPhotoViewer;
        }
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
    public void onMediaSelected(ArrayList<String> mediaList, String typeAlbum, String typeFile, boolean v) {
        Log.i(TAG, "onMediaSelected: ");
    }
}
