package com.example.milena.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.screenButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openIpActivity();
            }
        });
    }

    public void openIpActivity(){
            Intent intent=new Intent(this, IpActivity.class);
            startActivity(intent);

        }
}
