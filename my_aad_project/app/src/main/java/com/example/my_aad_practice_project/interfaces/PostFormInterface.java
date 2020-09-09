package com.example.my_aad_practice_project.interfaces;

import com.example.my_aad_practice_project.model.Data;
import com.example.my_aad_practice_project.model.Form;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostFormInterface {

    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    Call<ResponseBody>  postForm(
            @Field("entry.1824927963") String email,
            @Field("entry.1877115667") String firstName,
            @Field("entry.2006916086") String lastName,
            @Field("entry.284483984") String githubLink
    );

}
