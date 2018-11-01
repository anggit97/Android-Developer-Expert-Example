package com.anggitprayogo.dicoding.kamusoffline.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.anggitprayogo.dicoding.kamusoffline.R;
import com.anggitprayogo.dicoding.kamusoffline.entity.Kamus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TranslateDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL = "extra_detail";

    @BindView(R.id.tv_kosakata)
    TextView tvKosakata;
    @BindView(R.id.tv_keterangan)
    TextView tvKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_detail);
        ButterKnife.bind(this);

        Kamus bundle = getIntent().getParcelableExtra(EXTRA_DETAIL);

        if (bundle != null){
            tvKosakata.setText(bundle.getKosakata());
            tvKeterangan.setText(bundle.getDeskripsi());
        }
    }
}
