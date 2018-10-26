package com.anggitprayogo.dicoding.myflexiblefragment.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anggitprayogo.dicoding.myflexiblefragment.R;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private final String TAG = getClass().getSimpleName();

    private Context mContext;

    private TextView tvMesssage;
    private Button btnGoToCategory;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvMesssage = view.findViewById(R.id.tv_message);
        btnGoToCategory = view.findViewById(R.id.btn_category);

        btnGoToCategory.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    private void showFragment(Fragment fragment) {
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.add(R.id.frame_cotainer, fragment, TAG);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_category:
                showFragment(new CategoryFragment());
                break;
        }
    }
}
