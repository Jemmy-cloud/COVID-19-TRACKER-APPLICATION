package com.example.coronav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    TextView tvCases,tvRecovered,tvCritical,tvActive,tvTodaycases,tvTotaldeaths,tvTodaydeaths,tvAffectedCountries;
    ScrollView scrollView;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCases= findViewById (R.id.tvCases);
        tvRecovered = findViewById(R.id.tvrecovered);
        tvActive = findViewById(R.id.tvActive);
        tvCritical = findViewById(R.id.tvCritical);
        tvTodaycases = findViewById(R.id.tvTodayCases);
        tvTotaldeaths = findViewById(R.id.tvTotalDeaths);
        tvTodaydeaths = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);

        scrollView = findViewById(R.id.scrollstats);
        pieChart = findViewById( R.id. piechart);

        fetchData();




    }
    private void fetchData(){
        String url = "https://disease.sh/v3/covid-19/all";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject =new JSONObject(response.toString());
                            Log.d("Response",response.toString());

                            tvCases.setText(jsonObject.getString("cases"));
                            tvRecovered.setText(jsonObject.getString("recovered"));
                            tvCritical.setText(jsonObject.getString("critical"));
                            tvActive.setText(jsonObject.getString("active"));
                            tvTodaycases.setText(jsonObject.getString("todayCases"));
                           tvTotaldeaths.setText(jsonObject.getString("deaths"));
                            tvTodaydeaths.setText(jsonObject.getString("todayDeaths"));
                            tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));


                            pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#ffa726")));
                            pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66bb6a")));;
                            pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29b6f6")));
                           pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotaldeaths.getText().toString()), Color.parseColor("#Ef5350")));
                            pieChart.startAnimation();






                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        } );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    public void goTrackCountries(View view) {

        startActivity(new Intent(getApplicationContext(), AffectedCountries.class));
    }
}