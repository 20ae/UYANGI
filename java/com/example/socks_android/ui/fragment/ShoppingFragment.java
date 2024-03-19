package com.example.socks_android.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.socks_android.ColorActivity;
import com.example.socks_android.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingFragment extends Fragment {
    private View view;

    TextView category;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingFragment newInstance(String param1, String param2) {
        ShoppingFragment fragment = new ShoppingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping,container,false);


        category = (TextView) view.findViewById(R.id.category);

        //Create the necessary tabs needed
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.socksTab);
        tabLayout.addTab(tabLayout.newTab().setText("전체상품"));
        tabLayout.addTab(tabLayout.newTab().setText("BEST"));
        tabLayout.addTab(tabLayout.newTab().setText("신상품"));
        tabLayout.addTab(tabLayout.newTab().setText("남성양말"));
        tabLayout.addTab(tabLayout.newTab().setText("여성양말"));
        tabLayout.addTab(tabLayout.newTab().setText("아동양말"));
        //tabLayout.addTab(tabLayout.newTab().setText("test"));
        tabLayout.setTabTextColors(Color.parseColor("#403e3e"), Color.parseColor("#000000"));

        //initialise the view pager
        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewPager_socks);
        viewPager.setAdapter(new CustomAdapter(getChildFragmentManager(), tabLayout.getTabCount()));
        viewPager.setSaveEnabled(false);

        //Add listener to respond to touches and slides
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    category.setText("전체");
                }else if(tab.getPosition()==1){
                    category.setText("BEST");
                }
                else if(tab.getPosition()==2){
                    category.setText("신상품");
                }
                else if(tab.getPosition()==3){
                    category.setText("남성양말");
                }
                else if(tab.getPosition()==4){
                    category.setText("여성양말");
                }
                else if(tab.getPosition()==5){
                    category.setText("아동양말");
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
            super( fragmentManager);
            this.numberOfTabs = numberOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    AllProductsFragment all = new AllProductsFragment();
                    return all;
                case 1:
                    BestProductsFragment best = new BestProductsFragment();
                    return best;
                case 2:
                    NewProductsFragment newProductsFragment = new NewProductsFragment();
                    return newProductsFragment;
                case 3:
                    MensMainFragment mens = new MensMainFragment();
                    return mens;
                case 4:
                    WomensMainFragment womens = new WomensMainFragment();
                    return womens;
                case 5:
                    KidsMainFragment kids = new KidsMainFragment();
                    return kids;
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

