package com.example.searchjobs.retrofit;

import com.example.searchjobs.clases.Jobs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JobsInterface {
    @GET("positions.json?")
    Call<List<Jobs>> searchJobs(@Query("description") String description);
}
