package com.example.socks_android.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.socks_android.R;
import com.google.android.material.tabs.TabLayout;


public class LengthFragment extends Fragment {
    private View view;

    TextView category;
    TextView main;

    public LengthFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping, container, false);

        category = (TextView) view.findViewById(R.id.category);
        main = (TextView) view.findViewById(R.id.main);
        main.setText("양말 길이");
        category.setText("중목 양말");

        //Create the necessary tabs needed
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.socksTab);
        tabLayout.addTab(tabLayout.newTab().setText("중목 양말"));
        tabLayout.addTab(tabLayout.newTab().setText("페이크삭스"));
        tabLayout.addTab(tabLayout.newTab().setText("발목 양말"));
        tabLayout.addTab(tabLayout.newTab().setText("장목 양말"));
        tabLayout.addTab(tabLayout.newTab().setText("니삭스"));
        //tabLayout.addTab(tabLayout.newTab().setText("test"));
        tabLayout.setTabTextColors(Color.parseColor("#403e3e"), Color.parseColor("#000000"));

        //initialise the view pager
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager_socks);
        viewPager.setAdapter(new LengthFragment.CustomAdapter(getChildFragmentManager(), tabLayout.getTabCount()));

        //Add listener to respond to touches and slides
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    category.setText("중목 양말");
                } else if (tab.getPosition() == 1) {
                    category.setText("페이크삭스");
                } else if (tab.getPosition() == 2) {
                    category.setText("발목 양말");
                } else if (tab.getPosition() == 3) {
                    category.setText("장목 양말");
                } else if (tab.getPosition() == 4) {
                    category.setText("니삭스");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    private class CustomAdapter extends FragmentStatePagerAdapter {
        int numberOfTabs;

        private CustomAdapter(FragmentManager fragmentManager, int numberOfTabs) {
            super(fragmentManager);
            this.numberOfTabs = numberOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    MiddleFragment middleFragment = new MiddleFragment();
                    return middleFragment;
                case 1:
                    FakeFragment fakeFragment = new FakeFragment();
                    return fakeFragment;
                case 2:
                    AnkleFragment ankleFragment = new AnkleFragment();
                    return ankleFragment;
                case 3:
                    CrewFragment crewFragment = new CrewFragment();
                    return crewFragment;
                case 4:
                    KneeFragment kneeFragment = new KneeFragment();
                    return kneeFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return numberOfTabs;
        }
    }
}