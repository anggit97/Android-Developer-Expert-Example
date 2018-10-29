package com.anggitprayogo.dicoding.cataloguemovie.feature.nowplaying;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.adapter.HomeAdapter;
import com.anggitprayogo.dicoding.cataloguemovie.model.HomeMovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class NowPlayingFragment extends Fragment implements NowPlayingView {


    private ArrayList<HomeMovieResponse.Results> results = new ArrayList<>();
    private HomeAdapter adapter;
    private NowPlayingPresenter presenter;
    private AlertDialog alertDialog;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    Unbinder unbinder;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMovie.setLayoutManager(linearLayoutManager);
        rvMovie.setHasFixedSize(true);
        adapter = new HomeAdapter(results, getActivity());
        rvMovie.setAdapter(adapter);

        alertDialog = new SpotsDialog.Builder().setContext(getActivity()).build();
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        presenter = new NowPlayingPresenter(this, serviceGenerator, getActivity());


        presenter.nowPlaying();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.nowPlaying();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        alertDialog.show();
    }

    @Override
    public void hideLoading() {
        alertDialog.dismiss();
    }

    @Override
    public void showMovieResponseError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovieResponseEmpty() {
        Toast.makeText(getActivity(), R.string.empty_response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovieResponseSuccess(HomeMovieResponse body) {
        results.clear();
        results.addAll(body.getResults());
        adapter.notifyDataSetChanged();
    }
}
