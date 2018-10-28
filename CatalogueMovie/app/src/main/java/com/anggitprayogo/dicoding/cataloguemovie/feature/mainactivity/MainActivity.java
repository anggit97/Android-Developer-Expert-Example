package com.anggitprayogo.dicoding.cataloguemovie.feature.mainactivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.adapter.MainAdapter;
import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;
import com.anggitprayogo.dicoding.cataloguemovie.util.HideKeyboard;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import okhttp3.internal.Util;

public class MainActivity extends AppCompatActivity implements MainView{

    private final String TAG = getClass().getSimpleName();

    private ArrayList<MovieResponse.Results> results = new ArrayList<>();
    private MainPresenter presenter;
    private MainAdapter adapter;
    private AlertDialog spotsDialogBuilder;

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ServiceGenerator serviceGenerator = new ServiceGenerator();

        presenter = new MainPresenter(this,serviceGenerator, this);
        spotsDialogBuilder = new SpotsDialog.Builder().setContext(this).build();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMovie.setLayoutManager(linearLayoutManager);
        rvMovie.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new MainAdapter(results, this);
        rvMovie.setAdapter(adapter);


        presenter.getMovie("a");
    }

    @Override
    public void tampilkanMovie() {

    }

    @Override
    public void showLoading() {
        spotsDialogBuilder.show();
    }

    @Override
    public void hideLoading() {
        spotsDialogBuilder.dismiss();
    }

    @Override
    public void showMovieResponseError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovieResponseEmpty() {
        Toast.makeText(this, "Kosong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovieResponseSuccess(MovieResponse body) {
        results.clear();
        results.addAll(body.getResults());
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btn_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_search:
                if (etSearch.getText().toString().isEmpty()){
                    Toast.makeText(this, "kata kunci pencarian tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                presenter.getMovie(etSearch.getText().toString());
                HideKeyboard.hideKeyboard(this);
                break;
        }
    }
}
