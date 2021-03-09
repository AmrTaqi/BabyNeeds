package com.ammt.babyneeds.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ammt.babyneeds.model.Item;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class DatabaseHandler extends SQLiteOpenHelper {

    @Inject
    public DatabaseHandler(@Nullable @ApplicationContext Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BABY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_ITEM_NAME + " TEXT,"
                + Constants.KEY_ITEM_QUANTITY + " INTEGER,"
                + Constants.KEY_ITEM_COLOR + " TEXT,"
                + Constants.KEY_ITEM_SIZE + " INTEGER,"
                + Constants.KEY_DATE_ADDED + " LONG);";

        db.execSQL(CREATE_BABY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    //CRUD operations

    public void addBabyItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = createItemsContentValues(item);
        values.put(Constants.KEY_DATE_ADDED, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);
        db.close();
    }
    public Item getBabyitem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.KEY_ITEM_NAME, Constants.KEY_ITEM_QUANTITY,
        Constants.KEY_ITEM_COLOR, Constants.KEY_ITEM_SIZE, Constants.KEY_DATE_ADDED}, Constants.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) cursor.moveToFirst();

        assert cursor != null;
        Item item = setBabyItemsFromDB(cursor);
        db.close();
        return item;
    }

    public ArrayList<Item> getAllBabyItems() {
        ArrayList<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.KEY_ITEM_NAME, Constants.KEY_ITEM_QUANTITY,
                        Constants.KEY_ITEM_COLOR, Constants.KEY_ITEM_SIZE, Constants.KEY_DATE_ADDED}, null, null,
                null, null, Constants.KEY_DATE_ADDED+" DESC");
        if (cursor.moveToFirst()) {
            do items.add(setBabyItemsFromDB(cursor));
            while (cursor.moveToNext());
        }
        db.close();
        return items;
    }
    public void updateBabyItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(Constants.TABLE_NAME, createItemsContentValues(item), Constants.KEY_ID + "=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public void removeBabyItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public int getBabyItemsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s", Constants.TABLE_NAME), null);
        int itemsCount = cursor.getCount();
        db.close();
        return itemsCount;
    }

    private ContentValues createItemsContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_ITEM_NAME, item.getItemName());
        values.put(Constants.KEY_ITEM_QUANTITY, item.getItemQty());
        values.put(Constants.KEY_ITEM_COLOR, item.getItemColor());
        values.put(Constants.KEY_ITEM_SIZE, item.getSize());
        return values;
    }

    private Item setBabyItemsFromDB(Cursor cursor) {
        Item item = new Item();
        item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)));
        item.setItemQty(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_QUANTITY)));
        item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_COLOR)));
        item.setSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));
        String addedDate = DateFormat.getDateInstance().format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_ADDED))).getTime());
        item.setDateAdded(addedDate);
        return item;
    }
}
