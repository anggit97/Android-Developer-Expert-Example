package com.anggitprayogo.dicoding.mypreloaddata.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anggitprayogo.dicoding.mypreloaddata.R;
import com.anggitprayogo.dicoding.mypreloaddata.adapter.MahasiswaAdapter;
import com.anggitprayogo.dicoding.mypreloaddata.db.MahasiswaHelper;
import com.anggitprayogo.dicoding.mypreloaddata.entity.Mahasiswa;

import java.util.ArrayList;

public class MahasiswaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MahasiswaAdapter adapter;
    private MahasiswaHelper mahasiswaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mahasiswaHelper = new MahasiswaHelper(this);
        adapter = new MahasiswaAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        mahasiswaHelper.open();

        //Fetch all data
        ArrayList<Mahasiswa> mahasiswas = new ArrayList<>();
        mahasiswas.addAll(mahasiswaHelper.getAllData());
        mahasiswaHelper.close();
        adapter.addItem(mahasiswas);

    }
}
