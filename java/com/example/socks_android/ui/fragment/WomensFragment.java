package com.example.socks_android.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.socks_android.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class WomensFragment extends Fragment {


    public WomensFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab, container, false);

        //Create the necessary tabs needed
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.socksTab);
        tabLayout.addTab(tabLayout.newTab().setText("전체"));
        tabLayout.addTab(tabLayout.newTab().setText("여성중목양말"));
        tabLayout.addTab(tabLayout.newTab().setText("여성발목양말"));
        tabLayout.addTab(tabLayout.newTab().setText("여성덧신"));
        tabLayout.addTab(tabLayout.newTab().setText("여성스포츠,두툼양말"));
        tabLayout.addTab(tabLayout.newTab().setText("여성프리미엄양말"));
        tabLayout.addTab(tabLayout.newTab().setText("여성니삭스"));
        tabLayout.setTabTextColors(Color.parseColor("#403e3e"), Color.parseColor("#000000"));

        //initialise the view pager
        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewPager_socks);
        viewPager.setAdapter(new CustomAdapter(getChildFragmentManager(), tabLayout.getTabCount()));

        //Add listener to respond to touches and slides
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
            super( fragmentManager);
            this.numberOfTabs = numberOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    WomensMainFragment main = new WomensMainFragment();
                    return main;
                case 1:
                    WomensMiddleFragment middle = new WomensMiddleFragment();
                    return middle;
                case 2:
                    WomensAnkleFragment ankle = new WomensAnkleFragment();
                    return ankle;
                case 3:
                    WomensFakeFragment fake = new WomensFakeFragment();
                    return fake;
                case 4:
                    WomensSportsFragment sports = new WomensSportsFragment();
                    return sports;
                case 5:
                    WomensPremiumFragment premium = new WomensPremiumFragment();
                    return premium;
                case 6:
                    WomensKneeFragment knee = new WomensKneeFragment();
                    return knee;
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
