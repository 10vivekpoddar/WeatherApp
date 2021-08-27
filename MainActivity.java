package com.example.projectweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv,tv1;
    EditText et;
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tv);
        tv1=findViewById(R.id.tv1);
        et=findViewById(R.id.et);
        //  city=et.getText().toString();

    }

    public void get(View v){
        city=et.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city.toLowerCase()+"&appid=ddbf071c3cd3d6e5a1a9373851c1b8fe";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject array= (JSONObject) response.get("main");
                    //JSONArray array=  response.getJSONArray("main");

                    String s= array.getString("temp");
                    Double temp = Double.parseDouble(s)-273.15;
                    tv.setText("Temperature: "+temp.toString().substring(0,5)+" °C");

                    String humidity=array.getString("humidity");
                    tv1.setText("Humidity: "+humidity+" %");

                    //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    tv.setText(e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please try again",Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}
