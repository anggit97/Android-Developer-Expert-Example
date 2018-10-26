package com.anggitprayogo.dicoding.myflexiblefragment.fragment;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anggitprayogo.dicoding.myflexiblefragment.R;

public class CategoryFragment extends Fragment implements View.OnClickListener{

    private Context mContext;
    private Button btnGoToLifeStyle;
    private Fragment fragment;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        btnGoToLifeStyle = view.findViewById(R.id.btn_detail_category);

        btnGoToLifeStyle.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_detail_category:
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTranscation = mFragmentManager.beginTransaction();
                DetailCategoryFragment detailCategoryFragment = new DetailCategoryFragment();

                Bundle bundle  = new Bundle();
                bundle.putString(DetailCategoryFragment.EXTRA_NAME, "Lifestyle");
                String desc = "Deskripsi lifestyle misalnya";
                detailCategoryFragment.setArguments(bundle);
                detailCategoryFragment.setDescription(desc);

                mFragmentTranscation.add(R.id.frame_cotainer, detailCategoryFragment, getClass().getCanonicalName());
                mFragmentTranscation.addToBackStack(null);
                mFragmentTranscation.commit();

                break;
        }
    }
}
