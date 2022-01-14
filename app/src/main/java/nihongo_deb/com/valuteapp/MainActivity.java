package nihongo_deb.com.valuteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String JSON_URL = "https://www.cbr-xml-daily.ru/daily_json.js";

    ListView valuteItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        valuteItems = findViewById(R.id.lv_valutes);

        loadJSONFromURL(JSON_URL);
    }

    private void loadJSONFromURL(String url){
        final ProgressBar progressBar = findViewById(R.id.pb_loading);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try{
                            JSONObject obj = new JSONObject(response);
                            ArrayList<JSONObject> listItems = getArrayListFromJSONObject(obj);
                            ListAdapter adapter = new ListViewAdapter(getApplicationContext(), R.layout.valute_item, R.id.tv_name, listItems);
                            valuteItems.setAdapter(adapter);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private static ArrayList<JSONObject> getArrayListFromJSONObject(JSONObject jsonObject){
        ArrayList<JSONObject> joList = new ArrayList<>();
        try{
            JSONObject valute = jsonObject.getJSONObject("Valute");
            Iterator<String> keys = valute.keys();
            while (keys.hasNext()){
                String key = keys.next();
                joList.add(valute.getJSONObject(key));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return joList;
    }

}