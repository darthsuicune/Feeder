package com.suicune.feeder.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.suicune.feeder.R;

public class AddEntryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        if (savedInstanceState == null) {
            AddEntryFragment fragment = (AddEntryFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.add_entry_fragment);
        }
    }
}
