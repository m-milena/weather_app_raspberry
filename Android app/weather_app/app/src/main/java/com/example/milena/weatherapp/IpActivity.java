package com.example.milena.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class IpActivity extends AppCompatActivity {

    private EditText ip_adress;
    public TextView ipConnection;
    private Button connect_button;
    public Button exit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);


        ip_adress = (EditText) findViewById(R.id.ipText);
        connect_button = (Button) findViewById(R.id.connectButton);
        ipConnection = (TextView) findViewById(R.id.testConnection);

        connect_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //10.0.2.2
                String ip_string = ip_adress.getText().toString();


                if (ip_string.matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}))))))")) {
                    openWeatherActivity(ip_string);
                } else {
                    Toast.makeText(IpActivity.this, "Wrong IP Adress format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.only_dot_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                finishAffinity();
                return true;
            case R.id.info:
                openAboutApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openAboutApp() {
        AboutApp aboutApp = new AboutApp();
        aboutApp.show(getSupportFragmentManager(), "aboutApp");
    }

    public void openWeatherActivity(String ip_string) {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("ip_string", ip_string);
        startActivityForResult(intent, 1);
    }




}






