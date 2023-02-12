package com.example.gregor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FilterActivity extends AppCompatActivity {
    private GoiAdapter goiAdapter;
    private RecyclerView recyclerView;
    private List<GoiDetail> goiDetails;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private  Boolean isScrolling = false;
    int currentItems,totalItems,scrollItems;
    int offset = 20;
    private EditText searchText;
    private  ProgressBar progressBar2;
    private ImageButton searchButton;
    private String text = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        FilterActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        toolbar = findViewById(R.id.BarLayout);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Kisan Goi");
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        progressBar2 = findViewById(R.id.progressBar3);
        progressBar2.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerView);
        goiDetails = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        goiAdapter = new GoiAdapter(FilterActivity.this, goiDetails);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(goiAdapter);

        searchText = findViewById(R.id.searchByDistrict);
        searchButton = findViewById(R.id.searchButton);

        searchText.requestFocus();
        try{
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar2.setVisibility(View.VISIBLE);
                     text = String.valueOf(searchText.getText());
                     offset=20;
                  DownloadTask task = new DownloadTask();
                    task.execute("https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=xml&offset=0&limit=20");

                }
            });
            loadData();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection urlConnection = null;
            String result = "";
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }
                return  result;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    String records = "";
                    records = jsonObject.getString("records");
                    JSONArray arr = new JSONArray(records);
                    for(int i=0; i<arr.length();i++){
                        JSONObject part = arr.getJSONObject(i);
                        GoiDetail details = new GoiDetail();
                        details.setGroceryName(part.getString("commodity"));
                        details.setGroceryPlace(part.getString("district")+","+part.getString("state"));
                        details.setGroceryPrice(part.getString("model_Price"));
                        details.setGroceryTime(Long.valueOf(part.getString("timestamp")));

                        Log.i("WorkAPI",part.getString("district"));
                        goiDetails.add(details);
                    }
                    goiAdapter = new GoiAdapter(FilterActivity.this,goiDetails);
                    goiAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(goiAdapter);
                    if(totalItems == currentItems+scrollItems)
                    recyclerView.scrollToPosition(totalItems - currentItems+1);
                    progressBar.setVisibility(View.GONE);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(FilterActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
           progressBar2.setVisibility(View.GONE);
            if(goiDetails.isEmpty()){
                Toast.makeText(FilterActivity.this, "DATA NOT FETCHED.....ENTER FIRST LETTER CAPITAL ALWAYS", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void loadData(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollItems = linearLayoutManager.findFirstVisibleItemPosition();
                if(isScrolling && (currentItems+scrollItems)==totalItems){
                    isScrolling = false;
                    progressBar.setVisibility(View.VISIBLE);
                    fetchData();
                }

            }
        });
    }
    private void fetchData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    DownloadTask task = new DownloadTask();
                    task.execute("https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=xml&offset=0&limit=20");
                    offset+=20;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },5000);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.sortByPrice) {
            if (goiDetails.isEmpty()) {
                Toast.makeText(FilterActivity.this, "NO DATA FOR SORTING", Toast.LENGTH_SHORT).show();
            } else {
                Collections.sort(goiDetails, new Comparator<GoiDetail>() {
                    @Override
                    public int compare(GoiDetail g1, GoiDetail o2) {
                        return Integer.valueOf(g1.getGroceryPrice()).compareTo(Integer.valueOf(o2.getGroceryPrice()));
                    }
                });
                goiAdapter.notifyDataSetChanged();
            }
        }else if(item.getItemId() == R.id.sortByDate) {
            if (goiDetails.isEmpty()) {
                Toast.makeText(FilterActivity.this, "NO DATA AVAILABLE", Toast.LENGTH_SHORT).show();
            } else {
                Collections.sort(goiDetails, new Comparator<GoiDetail>() {
                    @Override
                    public int compare(GoiDetail g1, GoiDetail o2) {
                        return Integer.compare(Math.toIntExact(g1.getGroceryTime()), Math.toIntExact(o2.getGroceryTime()));
                    }
                });
                goiAdapter.notifyDataSetChanged();
            }
        }else if(item.getItemId() == android.R.id.home){
            finish();
        }

return true;

    }
}
