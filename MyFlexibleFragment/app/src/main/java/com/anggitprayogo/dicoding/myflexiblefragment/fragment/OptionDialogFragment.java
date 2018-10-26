package com.anggitprayogo.dicoding.myflexiblefragment.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anggitprayogo.dicoding.myflexiblefragment.R;

public class OptionDialogFragment extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private Button btnClose, btnChoose;
    private RadioGroup rgOptions;
    private int checkId;

    private OnOptionDialogClickListener onOptionDialogClickListener;

    interface OnOptionDialogClickListener{
        void onOptionChoose(String value);
    }

    public OnOptionDialogClickListener getOnOptionDialogClickListener() {
        return onOptionDialogClickListener;
    }

    public void setOnOptionDialogClickListener(OnOptionDialogClickListener onOptionDialogClickListener) {
        this.onOptionDialogClickListener = onOptionDialogClickListener;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public OptionDialogFragment() {
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
        View view = inflater.inflate(R.layout.fragment_option_dialog, container, false);

        btnClose = view.findViewById(R.id.btn_close);
        btnChoose = view.findViewById(R.id.btn_choose);
        rgOptions = view.findViewById(R.id.rg_options);

        btnChoose.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        Toast.makeText(mContext, ""+getCheckId(), Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

        String value = "Gak dipilih";

        switch (v.getId()){
            case R.id.btn_choose:
                if (rgOptions.getCheckedRadioButtonId() != 0){
                    switch (rgOptions.getCheckedRadioButtonId()){
                        case R.id.rb_saf:
                            value = "Sir Alex Ferguson";
                            break;
                        case R.id.rb_mou:
                            value = "Mourinho";
                            break;
                        case R.id.rb_moyes:
                            value = "David Moyes";
                            break;
                        case R.id.rb_lvg:
                            value = "Luous van Gaal";
                            break;
                    }

                    getOnOptionDialogClickListener().onOptionChoose(value);
                    getDialog().cancel();
                }
                break;
            case R.id.btn_close:
                getDialog().cancel();
                break;
        }
    }
}
