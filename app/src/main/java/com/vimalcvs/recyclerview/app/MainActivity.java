package com.vimalcvs.recyclerview.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vimalcvs.recyclerview.R;
import com.vimalcvs.recyclerview.data.HomeSharedPreference;
import com.vimalcvs.recyclerview.models.HomeApiResponse;
import com.vimalcvs.recyclerview.models.HomeResult;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements HomeOnRecordEventListener {

    private RecyclerView recyclerView;
    private HomeSharedPreference homeSharedPreference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        homeSharedPreference = new HomeSharedPreference();
        if (homeSharedPreference.hasResponse(this)) {
            HomeApiResponse homeApiResponse = homeSharedPreference.getResponse(this);
            if (homeApiResponse != null) {
                generateDataList(homeApiResponse);
            }
        } else {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();

            /*Create handle for the RetrofitInstance interface*/
            HomeGetDataService service = HomeRetrofitClientInstance.getRetrofitInstance().create(HomeGetDataService.class);
            Call<HomeApiResponse> call = service.getResponse(10);
            call.enqueue(new Callback<HomeApiResponse>() {
                @Override
                public void onResponse(@NotNull Call<HomeApiResponse> call, @NotNull Response<HomeApiResponse> apiResponse) {
                    progressDialog.dismiss();
                    if (apiResponse.body() != null) {
                        generateDataList(apiResponse.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HomeApiResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void generateDataList(HomeApiResponse homeApiResponse) {
        homeSharedPreference.saveResponse(this, homeApiResponse);
        // set adapter
        HomeAdapter adapter = new HomeAdapter(this, homeApiResponse.getHomeResults(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void accept(HomeResult homeResult) {
        homeSharedPreference.accept(this, homeResult);
    }

    @Override
    public void decline(HomeResult homeResult) {
        homeSharedPreference.decline(this, homeResult);
    }
}
