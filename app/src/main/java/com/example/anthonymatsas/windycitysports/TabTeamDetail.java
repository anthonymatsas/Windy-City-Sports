package com.example.anthonymatsas.windycitysports;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.UUID;

public class TabTeamDetail extends AppCompatActivity {
    //Class level variables
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private UUID id;
    private Team team;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_team_detail);
        id = (UUID)getIntent().getSerializableExtra("id");
        team = TeamList.get(getApplicationContext()).getTeam(id);
        setTitle(team.getName());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        //Adapeter for each section. Return correct fragment for each tab
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            id = (UUID)getIntent().getSerializableExtra("id");
            switch (position) {
                case 0:
                    return HistoryFragment.newInstance(id);
                case 1:
                    return FeedFragment.newInstance(id);
                case 2:
                    return VideoFragment.newInstance(id);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total tabbed pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //Set titles of each tab
            switch (position) {
                case 0:
                    return "History";
                case 1:
                    return "Feed";
                case 2:
                    return "Videos";
            }
            return null;
        }
    }
}
