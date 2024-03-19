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


public class UseFragment extends Fragment {
    private View view;

    TextView category;
    TextView main;

    public UseFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping, container, false);

        category = (TextView) view.findViewById(R.id.category);
        main = (TextView) view.findViewById(R.id.main);
        main.setText("양말 용도");
        category.setText("캐주얼");

        //Create the necessary tabs needed
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.socksTab);
        tabLayout.addTab(tabLayout.newTab().setText("캐주얼"));
        tabLayout.addTab(tabLayout.newTab().setText("정장"));
        tabLayout.addTab(tabLayout.newTab().setText("스포츠"));
        tabLayout.addTab(tabLayout.newTab().setText("수면양말"));
        //tabLayout.addTab(tabLayout.newTab().setText("test"));
        tabLayout.setTabTextColors(Color.parseColor("#403e3e"), Color.parseColor("#000000"));

        //initialise the view pager
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager_socks);
        viewPager.setAdapter(new UseFragment.CustomAdapter(getChildFragmentManager(), tabLayout.getTabCount()));

        //Add listener to respond to touches and slides
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    category.setText("캐주얼");
                } else if (tab.getPosition() == 1) {
                    category.setText("정장");
                } else if (tab.getPosition() == 2) {
                    category.setText("스포츠");
                } else if (tab.getPosition() == 3) {
                    category.setText("수면양말");
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
                    UseCasualFragment useCasualFragment = new UseCasualFragment();
                    return useCasualFragment;
                case 1:
                    UseSuitFragment useSuitFragment = new UseSuitFragment();
                    return useSuitFragment;
                case 2:
                    UseSportsFragment useSportsFragment = new UseSportsFragment();
                    return useSportsFragment;
                case 3:
                    UseSleepFragment useSleepFragment = new UseSleepFragment();
                    return useSleepFragment;
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
