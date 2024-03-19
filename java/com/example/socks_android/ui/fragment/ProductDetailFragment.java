package com.example.socks_android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.socks_android.ColorActivity;
import com.example.socks_android.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {
    private View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailFragment newInstance(String param1, String param2) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DetailExplainFragment DEF = new DetailExplainFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.product_content, DEF).commit();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_product_detail,container,false);

        //컬러 버튼 클릭
        Button color_button = (Button) view.findViewById(R.id.color_button);
        color_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ColorActivity.class);
                startActivity(intent);
            }
        });

        //상품설명 버튼 클릭
        Button productDetailButton = (Button) view.findViewById(R.id.product_detail);
        productDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailExplainFragment DEF = new DetailExplainFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.detailView, DEF).commit();
            }
        });

        //리뷰 버튼 클릭
        Button reviewButton = (Button) view.findViewById(R.id.review);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewFragment RF = new ReviewFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.detailView, RF).commit();
            }
        });

        return view;

    }
}