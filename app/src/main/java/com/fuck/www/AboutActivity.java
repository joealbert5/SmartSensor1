package com.fuck.www;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.db.chart.model.LineSet;

/**
 * Created by joeal_000 on 5/6/2017.
 */

public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_main);
    }

    public void onClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}