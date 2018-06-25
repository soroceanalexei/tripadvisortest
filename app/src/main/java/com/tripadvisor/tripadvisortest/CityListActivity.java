package com.tripadvisor.tripadvisortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import java.util.List;

import static com.tripadvisor.tripadvisortest.CityDetailActivity.CITY;

public class CityListActivity extends FragmentActivity implements FileReaderFragment.FileReaderListener,
        CityAdapter.OnItemClickListener {

    public static final String FILE_READER_FRAGMENT_TAG = "FILE_READER_FRAGMENT_TAG";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter cityAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private FileReaderFragment fileReaderFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        if (savedInstanceState == null) {
            fileReaderFragment = new FileReaderFragment();
            fileReaderFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, fileReaderFragment, FILE_READER_FRAGMENT_TAG).commit();
        } else {
            fileReaderFragment = (FileReaderFragment) getSupportFragmentManager().findFragmentByTag(FILE_READER_FRAGMENT_TAG);
        }

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    void startDetailsActivity(City city) {
        Intent intent = new Intent(this, CityDetailActivity.class);
        intent.putExtra(CITY, city);
        startActivity(intent);
    }

    @Override
    public void onRequestStarted() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestFinished(List<City> cities) {
        progressBar.setVisibility(View.GONE);
        cityAdapter = new CityAdapter(cities, this);
        recyclerView.setAdapter(cityAdapter);
    }

    @Override
    public void onItemClick(City item) {
        startDetailsActivity(item);
    }
}