package com.example.travel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAllActivity extends AppCompatActivity {

    private RecyclerView rcvLocation;
    private LocationAdapter mLocationAdapter;
    private List<Location> listLocation6 = new ArrayList<>();

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all);

        Toolbar toolbarR = findViewById(R.id.toolbarAllLocation);
        toolbarR.setTitle("All Location");
        setSupportActionBar(toolbarR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getListAllLocation();
    }

    private void getListAllLocation(){
        String urlAll = "https://travelhc.000webhostapp.com/location_all.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlAll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("failure")) {
                    JSONArray obj = null;
                    try {
                        obj = new JSONArray(response);
                        for(int i=0; i < obj.length(); i++){
                            JSONObject item = obj.getJSONObject(i);
                            String imgurl = item.getString("img");
                            String ingten = item.getString("tendiadiem");
                            int imgid = item.getInt("id");
                            String motaItem = item.getString("mota");
                            listLocation6.add(new Location(imgurl,ingten,imgid,motaItem));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rcvLocation = findViewById(R.id.rev_allLocation);
                    mLocationAdapter = new LocationAdapter(SearchAllActivity.this);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchAllActivity.this, 3);
                    rcvLocation.setLayoutManager(gridLayoutManager);
                    mLocationAdapter.setData(listLocation6);
                    rcvLocation.setAdapter(mLocationAdapter);
                } else {
                    Toast.makeText(SearchAllActivity.this, "No data to view", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchAllActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SearchAllActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regions,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mLocationAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mLocationAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}