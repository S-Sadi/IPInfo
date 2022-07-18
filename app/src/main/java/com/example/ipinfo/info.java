package com.example.ipinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



public class info extends AppCompatActivity {
    private String ip;
    private TextView ip_details_textView, ip_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ip_textview = findViewById(R.id.ip_textview);
        ip_details_textView = findViewById(R.id.ip_details_textview);

        Intent intent = getIntent();
        ip = intent.getStringExtra("fatch_ip");
        ip_textview.setText(ip);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://ip-api.com/json/"+ip+"?fields=33210175";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            String ip_details_text = "Continent:    "+data.getString("continent")+"\n"
                                    +"Continent Code: "+data.getString("countryCode")+"\n"
                                    +"Country: "+data.getString("country")+"\n"
                                    +"Country Code: "+data.getString("countryCode")+"\n"
                                    +"Region: "+data.getString("region")+"\n"
                                    +"Region Name: "+data.getString("regionName")+"\n"
                                    +"City: "+data.getString("city")+"\n"
                                    +"District: "+data.getString("district")+"\n"
                                    +"Zip: "+data.getString("zip")+"\n"
                                    +"Timezone: "+data.getString("timezone")+"\n"
                                    +"Currency: "+data.getString("currency")+"\n"
                                    +"ISP: "+data.getString("isp")+"\n"
                                    +"ORG: "+data.getString("org")+"\n"
                                    +"AS: "+data.getString("as")+"\n"
                                    +"AS Name: "+data.getString("asname")+"\n"
                                    +"Reverse: "+data.getString("reverse")+"\n"
                                    +"Proxy: "+data.getString("proxy")+"\n"
                                    +"Hosting: "+data.getString("hosting")+"\n"
                                    ;
                            ip_details_textView.setText(ip_details_text);
                        } catch (JSONException e) {
                            new AlertDialog.Builder(info.this)
                                    .setTitle("IP error")
                                    .setMessage("Please enter valid ip address")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            info.super.onBackPressed();
                                        }
                                    })
                                    .show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ip_details_textView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}