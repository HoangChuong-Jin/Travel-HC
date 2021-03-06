package com.example.travel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.List;
import java.util.Map;

public class QlLocationActivity extends AppCompatActivity {

    private RecyclerView rcvLocationql;
    private LocationAdapterAdmin mLocationAdapterAdmin;
    private List<LocationAdmin> listLocationAdmin = new ArrayList<>();

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_location);

        Toolbar toolbarR = findViewById(R.id.tb_admin);
        toolbarR.setTitle("Manage Location");
        setSupportActionBar(toolbarR);

        getListAllLocationAdmin();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getListAllLocationAdmin(){
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
                            int mien_id = item.getInt("mien_id");
                            String motaItem = item.getString("mota");
                            listLocationAdmin.add(new LocationAdmin(imgurl,ingten,imgid,motaItem,mien_id));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rcvLocationql = findViewById(R.id.rev_allLocation_ql);
                    mLocationAdapterAdmin = new LocationAdapterAdmin(QlLocationActivity.this);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(QlLocationActivity.this, 1);
                    rcvLocationql.setLayoutManager(gridLayoutManager);
                    mLocationAdapterAdmin.setData(listLocationAdmin);
                    rcvLocationql.setAdapter(mLocationAdapterAdmin);
                } else {
                    Toast.makeText(QlLocationActivity.this, "No data to view", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QlLocationActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(QlLocationActivity.this);
        requestQueue.add(stringRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searcha).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        menu.findItem(R.id.action_add).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(QlLocationActivity.this, AddLocationActivity.class);
                startActivity(intent);
                return false;
            }
        });

        menu.findItem(R.id.action_re).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mLocationAdapterAdmin.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mLocationAdapterAdmin.getFilter().filter(newText);
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