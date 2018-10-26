package com.anggitprayogo.dicoding.myflexiblefragment.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anggitprayogo.dicoding.myflexiblefragment.ProfileActivity;
import com.anggitprayogo.dicoding.myflexiblefragment.R;

public class DetailCategoryFragment extends Fragment implements View.OnClickListener{

    public static final String EXTRA_NAME = "extra_name";
    private String description;

    private Context mContext;
    private TextView tvCategoryName, tvCategoryDescription;
    private Button btnProfile, btnShowDialog;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DetailCategoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_detail_category, container, false);

        tvCategoryName = view.findViewById(R.id.tv_category_name);
        tvCategoryDescription = view.findViewById(R.id.tv_category_description);
        btnProfile = view.findViewById(R.id.btn_profile);
        btnShowDialog = view.findViewById(R.id.btn_show_dialog);

        btnProfile.setOnClickListener(this);
        btnShowDialog.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String categoryName = getArguments().getString(EXTRA_NAME);
        tvCategoryName.setText(categoryName);
        tvCategoryDescription.setText(getDescription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_category:
                break;
            case R.id.tv_category_description:
                break;
            case R.id.btn_profile:
                Intent toProfileActivity = new Intent(getActivity(), ProfileActivity.class);
                startActivity(toProfileActivity);
                break;
            case R.id.btn_show_dialog:

                final int id = 10;

                OptionDialogFragment mOptionDialogFragment = new OptionDialogFragment();
                mOptionDialogFragment.setOnOptionDialogClickListener(new OptionDialogFragment.OnOptionDialogClickListener() {
                    @Override
                    public void onOptionChoose(String value) {
                        Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
                    }
                });

                mOptionDialogFragment.setCheckId(id);

                FragmentManager mFragmentManager = getChildFragmentManager();
                mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment.class.getSimpleName());
                break;
        }
    }
}
