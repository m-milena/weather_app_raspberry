package com.example.milena.weatherapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

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

public class WeatherActivity extends AppCompatActivity {
   public String url;
   public TextView jsonView;
   String ip_adress;

    private int mInterval = 1000; // 1 sec
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (savedInstanceState != null) {
            String saved_ip = savedInstanceState.getString("ip_string");
            ip_adress=saved_ip;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        String ip_string = intent.getStringExtra("ip_string");
        TextView textView_ip =findViewById(R.id.ipView);
        textView_ip.setText(ip_string);
        ip_adress=ip_string;
        url = "http://"+ip_string+"/Serwer/api/v1/czujniki.php";
        jsonView=(TextView)findViewById(R.id.testConnection) ;

        jsonView.setMovementMethod(new ScrollingMovementMethod());
        mHandler = new Handler();
        startRepeatingTask();
        new WeatherActivity.WebServiceHandler()
                .execute(url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                new WeatherActivity.WebServiceHandler()
                        .execute(url);
           } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();

    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.leds:
                openDiodeActivity();
                return true;
            case R.id.refresh:
                new WeatherActivity.WebServiceHandler()
                        .execute(url);
                return true;
            case R.id.exit:
                finishAffinity();
                return true;

            case R.id.info:
                openAboutApp(ip_adress);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("ip_string", ip_adress);
        super.onSaveInstanceState(savedInstanceState);
    }


    public void openAboutApp(String ip_adress){
        AboutApp aboutApp =new AboutApp();
        aboutApp.show(getSupportFragmentManager(), "aboutApp");
    }

    public void openDiodeActivity() {
        Intent intent = new Intent(this, DiodeActivity.class);
        intent.putExtra("ip_string", ip_adress);
        startActivityForResult(intent, 1);
    }

    private class WebServiceHandler extends AsyncTask<String, Void, String> {

        // okienko dialogowe, które każe użytkownikowi czekać
        private ProgressDialog dialog = new ProgressDialog(WeatherActivity.this);

        // metoda wykonywana jest zaraz przed główną operacją (doInBackground())
        // mamy w niej dostęp do elementów UI
       // @Override
       // protected void onPreExecute() {
            // wyświetlamy okienko dialogowe każące czekać
          //  dialog.setMessage("Czekaj...");
           // dialog.show();
      // }

        // główna operacja, która wykona się w osobnym wątku
        // nie ma w niej dostępu do elementów UI
        @Override
        protected String doInBackground(String... urls) {

            try {
                // zakładamy, że jest tylko jeden URL
                URL url = new URL(urls[0]);
                URLConnection connection = url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(7000);

                // pobranie danych do InputStream
                InputStream in = new BufferedInputStream(
                        connection.getInputStream());

                // konwersja InputStream na String
                // wynik będzie przekazany do metody onPostExecute()
                return streamToString(in);

            } catch (Exception e) {
                // obsłuż wyjątek
                Log.d(MainActivity.class.getSimpleName(), e.toString());
                return null;
            }

        } // metoda wykonuje się po zakończeniu metody głównej,
        // której wynik będzie przekazany;
        // w tej metodzie mamy dostęp do UI
        @Override
        protected void onPostExecute(String result) {

            float r, p, y;
            String rString, pString, yString;
            String tempString, pressString, humString;
            // chowamy okno dialogowe
            dialog.dismiss();

            try {
                // reprezentacja obiektu JSON w Javie
                  JSONObject json = new JSONObject(result);

                // pobranie pól obiektu JSON i wyświetlenie ich na ekranie
                rString=(json.optJSONObject("Zyroskop").optJSONObject("wartosc").opt("r")+"").substring(0,5);
                pString=(json.optJSONObject("Zyroskop").optJSONObject("wartosc").opt("p")+"").substring(0,5);
                yString=(json.optJSONObject("Zyroskop").optJSONObject("wartosc").opt("y")+"").substring(0,5);

                tempString=(json.optJSONObject("Temperatura").opt("wartosc")+"").substring(0,5);
                pressString=(json.optJSONObject("Cisnienie").opt("wartosc")+"").substring(0,7);
                humString=(json.optJSONObject("Wilgotnosc").opt("wartosc")+"").substring(0,5);
                r=Float.parseFloat(rString);
                p=Float.parseFloat(pString);
                y=Float.parseFloat(yString);

                changeTextColor(r,p,y);

                ((TextView) findViewById(R.id.zyroskopView)).setText("Gyroscope r: "+rString
                        + ", p: "+ pString +", y: "
                        + yString);
                ((TextView) findViewById(R.id.testConnection)).setText(result);
                  ((TextView) findViewById(R.id.tempView)).setText(" "
                          + tempString+" "+ json.optJSONObject("Temperatura").opt("jednostka"));
                ((TextView) findViewById(R.id.pressureView)).setText(" "
                        + pressString +" "+ json.optJSONObject("Cisnienie").opt("jednostka"));
                ((TextView) findViewById(R.id.humidityView)).setText(" "
                        + humString +" "+ json.optJSONObject("Wilgotnosc").opt("jednostka"));



            } catch (Exception e) {
                // obsłuż wyjątek
                Log.d(MainActivity.class.getSimpleName(), e.toString());
            }
        }
    }

    // konwersja z InputStream do String
    public static String streamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try {

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            reader.close();

        } catch (IOException e) {
            // obsłuż wyjątek
            Log.d(MainActivity.class.getSimpleName(), e.toString());
        }
        return stringBuilder.toString();
    }

// funkcja zmiany koloru tekstu w zaleznosci od wartosci
    public void changeTextColor(float r, float p, float y){

        if((r>80 && r<260)||  (p>80 && p<260)){
            ((TextView) findViewById(R.id.zyroskopView)).setTextColor(this.getResources().getColor(R.color.red));
        }
        else{
            ((TextView) findViewById(R.id.zyroskopView)).setTextColor(this.getResources().getColor(R.color.almost_white));
        }
    }

}
