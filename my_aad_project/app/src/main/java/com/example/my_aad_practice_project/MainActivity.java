package com.example.my_aad_practice_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.my_aad_practice_project.adapters.SectionsPagerAdapter;
import com.example.my_aad_practice_project.interfaces.LearningLeadersInterface;
import com.example.my_aad_practice_project.interfaces.SkillIqInterface;
import com.example.my_aad_practice_project.model.Data;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    public static final String HTTPS_GADSAPI_BASE_URL = "https://gadsapi.herokuapp.com/api/";
    private ProgressBar mProgressBar;
    private Retrofit mRetrofit;
    private List<Data> mSkillIqList;
    private List<Data> mLearningLeadersList;

    private static boolean isSkillListLoaded;
    private static boolean isLearningLeadersListLoaded;
    private static final Object SEMAPHORE = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progessBar);
        isSkillListLoaded = false;
        isLearningLeadersListLoaded = false;

        mRetrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(HTTPS_GADSAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadLists();

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(v -> {
            openSubmitActivity();
        });
    }

    private void openSubmitActivity() {
        Intent intent = new Intent(MainActivity.this, SubmitActivity.class);
        startActivity(intent);
    }

    private void loadLists() {
        mProgressBar.setVisibility(View.VISIBLE);
        getSkillIqList();
        getLearningLeadersList();
    }

    private void getSkillIqList() {

        SkillIqInterface skillIqInterface = mRetrofit.create(SkillIqInterface.class);
        Call<List<Data>> skillIqCall = skillIqInterface.getData();

        skillIqCall.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                if (!response.isSuccessful()) {
                    showErrorSnackbar();
                    return;
                }

                mSkillIqList = response.body();
                synchronized (SEMAPHORE) {
                    isSkillListLoaded = true;
                }
                checkListsLoading();
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                showErrorSnackbar();
                t.getStackTrace();
            }
        });
    }

    private void getLearningLeadersList() {
        LearningLeadersInterface badgeHoursInterface = mRetrofit.create(LearningLeadersInterface.class);
        Call<List<Data>> call2 = badgeHoursInterface.getData();

        call2.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (!response.isSuccessful()) {
                    showErrorSnackbar();
                    return;
                }

                mLearningLeadersList = response.body();
                synchronized (SEMAPHORE) {
                    isLearningLeadersListLoaded = true;
                }
                checkListsLoading();

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                showErrorSnackbar();
                t.getStackTrace();

            }
        });
    }

    private void checkListsLoading() {
        synchronized (SEMAPHORE) {
            if (isLearningLeadersListLoaded && isSkillListLoaded) {
                createAdapterAndViewPager();
            }
        }
    }

    private void createAdapterAndViewPager() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(MainActivity.this, getSupportFragmentManager());
        sectionsPagerAdapter.setSkillIQlist(mSkillIqList);
        sectionsPagerAdapter.setLearningLeaderslist(mLearningLeadersList);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        mProgressBar.setVisibility(View.GONE);
    }

    private void showErrorSnackbar() {
        mProgressBar.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content).getRootView(), getString(R.string.error_loading_data), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), view -> {

                    loadLists();
                });

        snackbar.show();
    }
}
