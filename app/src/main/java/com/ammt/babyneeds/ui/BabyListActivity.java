package com.ammt.babyneeds.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.ammt.babyneeds.R;
import com.ammt.babyneeds.adapters.BabyItemsAdapter;
import com.ammt.babyneeds.callbacks.BabyAdapterOnClickListener;
import com.ammt.babyneeds.model.Item;
import com.ammt.babyneeds.util.DatabaseHandler;
import com.ammt.babyneeds.util.PopUpDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class BabyListActivity extends AppCompatActivity implements BabyAdapterOnClickListener{
    @Inject
    PopUpDialog saveDialog, updateDialog;
    @Inject
    DatabaseHandler db;

    //Adapter
    List<Item> items;
    BabyItemsAdapter babyItemsAdapter;

    //Remove view
    View removeView;
    Button yesConfirmed;
    Button noConfirmed;
    AlertDialog confirmationDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_list);

        RecyclerView recyclerView = findViewById(R.id.baby_itemsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        removeView = LayoutInflater.from(BabyListActivity.this).inflate(R.layout.remove_confirm, null);
        yesConfirmed = removeView.findViewById(R.id.yes_remove_confirm);
        noConfirmed = removeView.findViewById(R.id.no_remove_confirm);
        confirmationDialog = new AlertDialog.Builder(BabyListActivity.this).setView(removeView).create();

        FloatingActionButton fab = findViewById(R.id.fab_list);
        fab.setOnClickListener(view -> saveDialog.getPopUpDialog(view1 -> saveDialog.itemDB(view1, new Item(), PopUpDialog.SAVE_ITEM_DIALOG)
        ));

        items = db.getAllBabyItems();

        babyItemsAdapter = new BabyItemsAdapter(BabyListActivity.this, items, this);
        recyclerView.setAdapter(babyItemsAdapter);
        babyItemsAdapter.notifyDataSetChanged();
    }

        @Override
        public void onClickUpdate(int position, View view) {
            updateDialog.getUpdateDialog(items.get(position), view1 -> {
                updateDialog.itemDB(view1, items.get(position), PopUpDialog.UPDATE_ITEM_DIALOG);
                babyItemsAdapter.notifyItemChanged(position, items.get(position));
            });
        }
        @Override
        public void onClickRemove(int position, View view) {
            confirmationDialog.show();

            yesConfirmed.setOnClickListener(view1 -> {
                db.removeBabyItem(items.get(position).getId());
                items.remove(position);
                confirmationDialog.dismiss();
                babyItemsAdapter.notifyItemRemoved(position);
            });

            noConfirmed.setOnClickListener(view12 -> confirmationDialog.dismiss());
        }
}