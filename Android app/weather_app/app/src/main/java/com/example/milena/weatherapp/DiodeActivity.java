package com.example.milena.weatherapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;


public class DiodeActivity extends AppCompatActivity {

    int defaultColorR=163;
    int defaultColorG=67;
    int defaultColorB=15;
    public String stringR, stringB, stringG;
    int diodecolor=Color.rgb(defaultColorR,defaultColorG,defaultColorB);
    public Button colorchange_button;
    public TextView color_view;
    String ip_string;

    public TextView ip_adress_view;
    public Button display_button;
    public EditText input_text;
    public String text_string;
    String url_send;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diode);
        Intent intent=getIntent();
        ip_string = intent.getStringExtra("ip_string");
        colorchange_button = (Button) findViewById(R.id.rgb_button);
        color_view = (TextView) findViewById(R.id.rgb_view);
        ip_adress_view= (TextView) findViewById(R.id.ip_adress2_view);
        display_button=(Button) findViewById(R.id.display_button);
        input_text=(EditText) findViewById(R.id.diodetext_input);
        url_send="http://"+ip_string+"/Serwer/api/v1/diody.php";
        ip_adress_view.setText(ip_string);
        colorchange_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final ColorPicker cp = new ColorPicker(DiodeActivity.this, defaultColorR, defaultColorG, defaultColorB);
                cp.show();
                cp.enableAutoClose();

                cp.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(@ColorInt int color) {
                        Log.d("Red", Integer.toString(Color.red(color)));
                        Log.d("Green", Integer.toString(Color.green(color)));
                        Log.d("Blue", Integer.toString(Color.blue(color)));
                        defaultColorB=Color.blue(color);
                        defaultColorG=Color.green(color);
                        defaultColorR=Color.red(color);
                        color_view.setText("R: "+Color.red(color) + " G: "+Color.green(color) + " B: " +Color.blue(color));
                        colorchange_button.setBackgroundColor(color);
                    }
                });

            }
        });


        display_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stringR=Integer.toString(defaultColorR);
                stringG=Integer.toString(defaultColorG);
                stringB=Integer.toString(defaultColorB);
                text_string=input_text.getText().toString();
                data = "?text="+text_string+"&R="+stringR+"&G="+stringG+"&B="+stringB;
                send_data();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.only_dot_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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

    public void onBackPressed() {
        Intent data = new Intent();
        // add data to Intent
        data.putExtra("ip_string", ip_string);
        setResult(Activity.RESULT_OK, data);
        super.onBackPressed();
    }

    public void openAboutApp(){
        AboutApp aboutApp =new AboutApp();
        aboutApp.show(getSupportFragmentManager(), "aboutApp");
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return DownloadURL(urls[0]);
            } catch (IOException e) {
                return "URL is not good";
            }
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }



    public void send_data() {
        String stringURL = url_send+data;

        ConnectivityManager conMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringURL);
            Toast.makeText(DiodeActivity.this, "Data send", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(DiodeActivity.this, "Can't connect", Toast.LENGTH_LONG).show();

    }


    private String DownloadURL(String myurl) throws IOException {

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-length", "0");
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);

            conn.connect();
            int response = conn.getResponseCode();
            if (response == conn.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();


                return sb.toString();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
