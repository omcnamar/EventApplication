package com.olegsagenadatrytwo.eventapplication.view.mainactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.olegsagenadatrytwo.eventapplication.R;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showError(String error) {
        
    }
}
