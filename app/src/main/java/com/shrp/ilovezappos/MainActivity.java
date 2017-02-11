package com.shrp.ilovezappos;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pojo.Product;
import pojo.Results;
import retrofit.ZapposService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Constants;

public class MainActivity extends AppCompatActivity implements Callback<Results>,View.OnClickListener {

    EditText etSearchBox;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etSearchBox = (EditText) findViewById(R.id.etSearchBox);
        etSearchBox.setOnClickListener(MainActivity.this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Call<Results> call, Response<Results> response) {
        if(response.isSuccessful()) {
            Results results = response.body();
            Product product = results.results.get(0);
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            intent.putExtra("product", product);
            ActivityOptions options = ActivityOptions.makeScaleUpAnimation(fab, 0, 0, fab.getWidth(), fab.getHeight());
            startActivity(intent, options.toBundle());
        } else {
            Toast.makeText(MainActivity.this, "Call failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Results> call, Throwable t) {
        Log.d("[RESPONSE]", "response failed - onFailure");
        Toast.makeText(MainActivity.this, "Unable to load results", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                String term = etSearchBox.getText().toString();
                Gson gson = new GsonBuilder().setLenient().create();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build();
                ZapposService service = retrofit.create(ZapposService.class);
                Call<Results> callResults = service.loadResults(term, Constants.API_KEY);

                callResults.enqueue(MainActivity.this);
                break;

            case R.id.etSearchBox:
                fab.setVisibility(View.VISIBLE);
                break;
        }
    }
}
