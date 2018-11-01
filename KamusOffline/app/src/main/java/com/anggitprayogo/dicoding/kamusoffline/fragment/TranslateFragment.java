package com.anggitprayogo.dicoding.kamusoffline.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.anggitprayogo.dicoding.kamusoffline.R;
import com.anggitprayogo.dicoding.kamusoffline.adapter.TransalateAdapter;
import com.anggitprayogo.dicoding.kamusoffline.db.KamusHelper;
import com.anggitprayogo.dicoding.kamusoffline.entity.Kamus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TranslateFragment extends Fragment {

    public static final String EXTRA_TYPE = "extra_type";

    private ArrayList<Kamus> kamuses = new ArrayList<>();
    private TransalateAdapter adapter;
    private KamusHelper kamusHelper;
    private int type;

    @BindView(R.id.et_query)
    EditText etQuery;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.rv_results)
    RecyclerView rvResults;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Unbinder unbinder;

    public TranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(EXTRA_TYPE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_translate, container, false);

        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvResults.setLayoutManager(linearLayoutManager);
        rvResults.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new TransalateAdapter(kamuses, getActivity());
        rvResults.setAdapter(adapter);

        kamusHelper = new KamusHelper(getActivity());
        kamusHelper.open();

        etQuery.addTextChangedListener(new TextWatcher() {

            Handler handler = new Handler();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                final String query = s.toString();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!query.isEmpty()){
                            new SearchData().execute(query.toString());
                        }
                    }
                }, 500);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etQuery.setText("");
            }
        });

        return view;
    }

    class SearchData extends AsyncTask<String, Void, ArrayList<Kamus>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            rvResults.setVisibility(View.GONE);
            kamuses.clear();
        }

        @Override
        protected ArrayList<Kamus> doInBackground(String... strings) {
            kamuses = kamusHelper.getKosakataByWord(strings[0], type);
            return kamuses;
        }

        @Override
        protected void onPostExecute(ArrayList<Kamus> aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            rvResults.setVisibility(View.VISIBLE);
            adapter = new TransalateAdapter(aVoid, getActivity());
            rvResults.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
