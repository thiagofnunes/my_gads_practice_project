package com.example.my_aad_practice_project.interfaces;

import com.example.my_aad_practice_project.model.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SkillIqInterface {
    @GET("skilliq")
    Call<List<Data>> getData();

}
