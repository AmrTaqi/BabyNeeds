package com.ammt.babyneeds.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ammt.babyneeds.ui.BabyListActivity;
import com.ammt.babyneeds.R;
import com.ammt.babyneeds.model.Item;
import com.google.android.material.snackbar.Snackbar;
import javax.inject.Inject;

public class PopUpDialog {
    @Inject
    DatabaseHandler db;
    private AlertDialog dialog;
    private Activity activity;
    private Button setDataButton;
    private EditText itemName, itemQty, itemColor, itemSize;
    public static final int SAVE_ITEM_DIALOG = 1;
    public static final int UPDATE_ITEM_DIALOG = 2;


    @Inject
    public PopUpDialog(Activity activity) {
        this.activity = activity;
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_dialog, null);
        this.itemName = view.findViewById(R.id.item_name_pop);
        this.itemQty = view.findViewById(R.id.item_quantity_pop);
        this.itemColor = view.findViewById(R.id.item_color_pop);
        this.itemSize = view.findViewById(R.id.item_size_pop);
        this.setDataButton = view.findViewById(R.id.save_btn_pop);
        this.dialog = new AlertDialog.Builder(activity).setView(view).create();
    }

    public void getPopUpDialog(View.OnClickListener v) {
        setDataButton.setOnClickListener(v);
        dialog.show();
    }

    public void getUpdateDialog(Item item, View.OnClickListener v) {
        setDataButton.setText(R.string.update_button_text_pop);
        itemName.setText(item.getItemName());
        itemQty.setText(String.valueOf(item.getItemQty()));
        itemColor.setText(item.getItemColor());
        itemSize.setText(String.valueOf(item.getSize()));
        setDataButton.setOnClickListener(v);
        dialog.show();
    }

    public void itemDB(View view, Item item, int dialogType) {
        if (!itemName.getText().toString().trim().isEmpty()
                && !itemQty.getText().toString().trim().isEmpty()
                && !itemColor.getText().toString().trim().isEmpty()
                && !itemSize.getText().toString().trim().isEmpty()) {
            if (dialogType == SAVE_ITEM_DIALOG) {
                db.addBabyItem(setItemData(item));
                Snackbar.make(view, "Item Saved", Snackbar.LENGTH_SHORT).show();
            } else if (dialogType == UPDATE_ITEM_DIALOG){
                db.updateBabyItem(setItemData(item));
                Snackbar.make(view, "Item Updated", Snackbar.LENGTH_SHORT).show();
            }
            new Handler().postDelayed(() -> {
                dialog.dismiss();
                if (dialogType == SAVE_ITEM_DIALOG) {
                    activity.startActivity(new Intent(activity, BabyListActivity.class));
                    activity.finish();
                }
            }, 1200);
        } else {
            Snackbar.make(view, "Empty Fields Not Allowed", Snackbar.LENGTH_SHORT).show();

        }
    }

    private Item setItemData(Item item) {
        item.setItemName(itemName.getText().toString().trim());
        item.setItemQty(Integer.parseInt(itemQty.getText().toString().trim()));
        item.setItemColor(itemColor.getText().toString().trim());
        item.setSize(Integer.parseInt(itemSize.getText().toString().trim()));
        return item;
    }


}
