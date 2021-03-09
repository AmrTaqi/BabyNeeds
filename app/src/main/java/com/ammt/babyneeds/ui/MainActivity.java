package com.ammt.babyneeds.ui;

import android.content.Intent;
import android.os.Bundle;

import com.ammt.babyneeds.R;
import com.ammt.babyneeds.model.Item;
import com.ammt.babyneeds.util.DatabaseHandler;
import com.ammt.babyneeds.util.PopUpDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    @Inject PopUpDialog popUpDialog;
    @Inject DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (db.getBabyItemsCount() > 0) {
            startActivity(new Intent(MainActivity.this, BabyListActivity.class));
            finish();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> popUpDialog.getPopUpDialog(view1 -> popUpDialog.itemDB(view1, new Item(), PopUpDialog.SAVE_ITEM_DIALOG)));
    }
    }