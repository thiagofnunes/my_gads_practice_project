package com.example.my_aad_practice_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.my_aad_practice_project.interfaces.PostFormInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubmitActivity extends AppCompatActivity {


    public static final String BASE_URL = "https://docs.google.com/forms/d/e/";
    private EditText mFirstEt, mLastEt, mLinkEt, mEmailEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        mFirstEt = findViewById(R.id.firstNameEt);
        mLastEt = findViewById(R.id.lastNameEt);
        mLinkEt = findViewById(R.id.linkEt);
        mEmailEt = findViewById(R.id.emailEt);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.gads_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button submit = findViewById(R.id.submitBtn);
        submit.setOnClickListener(view -> {
            showConfirmationDialog();
        });
    }

    private void showConfirmationDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.are_you_sure_alert_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog confirmationAlertDialog = builder.create();
        confirmationAlertDialog.show();

        final ImageView closeBtn = (ImageView) dialogView.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(view -> {

            confirmationAlertDialog.dismiss();
        });

        final Button yesBtn = (Button) dialogView.findViewById(R.id.yesBtn);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        final PostFormInterface postFormInterface = retrofit.create(PostFormInterface.class);

        yesBtn.setOnClickListener(view -> {
            confirmationAlertDialog.dismiss();
            String email = mEmailEt.getText().toString();
            String firstName = mFirstEt.getText().toString();

            String lastName = mLastEt.getText().toString();
            String link = mLinkEt.getText().toString();
            Call<ResponseBody> sendFormCall = postFormInterface.postForm(email, firstName, lastName, link);
            sendFormCall.enqueue(callCallback);
        });

    }

    private void showPositiveDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.submission_successful_alert_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void showNegativeDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.submission_failed_alert_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private final Callback<ResponseBody> callCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            if (!response.isSuccessful()) {
                showNegativeDialog();
                return;
            }

            showPositiveDialog();

        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            showNegativeDialog();
            t.printStackTrace();
        }
    };

}