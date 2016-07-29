package com.q4tech.directsell.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.q4tech.directsell.R;

/**
 * Created by alex.perez on 28/07/2016.
 */
public class DescriptionActivity extends CustomActivity {

    private EditText description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_done_white_24dp);

        description = (EditText) findViewById(R.id.description);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                String desc = description.getText().toString();
                Intent result = new Intent();
                result.putExtra(AddProductActivity.DESCRIPTION, desc);
                setResult(RESULT_OK, result);
                finish();
        }
        return true;
    }
}
