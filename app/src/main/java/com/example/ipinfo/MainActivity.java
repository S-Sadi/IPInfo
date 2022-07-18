package com.example.ipinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ImageView logo;
    private EditText ip_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo_img);
        ip_text = findViewById(R.id.ip_editText);
        btn = findViewById(R.id.fetch_btn);

        Ip_Graber ip_grab = new Ip_Graber();
        Thread thread = new Thread(ip_grab);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ip = ip_grab.getIp();
        ip_text.setText(ip);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ip_text.getText().toString().isEmpty()){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error")
                            .setMessage("Please enter a IP address")
                            .setPositiveButton("OK", null)
                            .show();
                }else{
                    Intent intent = new Intent(MainActivity.this, info.class);
                    intent.putExtra("fatch_ip", ip_text.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}

class Ip_Graber implements Runnable{
    private String ip;
    @Override
    public void run() {
        URL whatIsMyIP = null;
        try {
            whatIsMyIP = new URL("https://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            assert whatIsMyIP != null;
            in = new BufferedReader(new InputStreamReader(whatIsMyIP.openStream()));
            ip = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getIp(){
        return ip;
    }
}