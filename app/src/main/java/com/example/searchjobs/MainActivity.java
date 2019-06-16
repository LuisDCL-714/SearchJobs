package com.example.searchjobs;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.searchjobs.Adapter.JobsAdapters;
import com.example.searchjobs.clases.Jobs;
import com.example.searchjobs.retrofit.JobsInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText inputSearch;
    private Button btnSearch;
    private Retrofit retrofit;
    private JobsInterface jobsInterface;
    private Call<List<Jobs>> jobsCall;
    private String job;
    private RecyclerView recyclerView;
    private JobsAdapters jobsAdapters;
    private List<Jobs> jobsList;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputSearch = findViewById(R.id.inputSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerViewJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(MainActivity.this);
                progress.setMessage("Cargando...");
                progress.setCancelable(false);
                progress.show();
                job = inputSearch.getText().toString();
                if(job.equals("")){
                    Toast.makeText(MainActivity.this, "¡Por favor inserte un trabajo!", Toast.LENGTH_LONG).show();
                    progress.hide();
                }else{
                    retrofit = new Retrofit.Builder().baseUrl("https://jobs.github.com/").addConverterFactory(GsonConverterFactory.create()).build();
                    jobsInterface = retrofit.create(JobsInterface.class);
                    jobsCall = jobsInterface.searchJobs(job);
                    jobsCall.enqueue(new Callback<List<Jobs>>() {
                        @Override
                        public void onResponse(Call<List<Jobs>> call, Response<List<Jobs>> response) {
                            if(response.isSuccessful()){
                                jobsList = response.body();
                                jobsAdapters = new JobsAdapters(jobsList);
                                recyclerView.setAdapter(jobsAdapters);
                                progress.hide();
                            }else{
                                Toast.makeText(MainActivity.this, "¡No existe ese trabajo en nuestra base de datos!", Toast.LENGTH_SHORT).show();
                                progress.hide();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Jobs>> call, Throwable t) {
                            Log.e("ERROR", "Causa: "+t.getCause()+", Mensaje: "+t.getMessage());
                            Toast.makeText(MainActivity.this, "¡Se presento un error!", Toast.LENGTH_SHORT).show();
                            progress.hide();
                        }
                    });
                }
            }
        });
    }
}
