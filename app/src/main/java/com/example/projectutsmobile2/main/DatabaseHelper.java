package com.example.projectutsmobile2.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "cwt.db"; // CWT(Cross-World Trade)
    private static final int DATABASE_VERSION = 1;

    // Tabel User
    private static final String TABLE_USER = "User";
    private static final String USER_COLUMN_ID = "user_id";
    private static final String USER_COLUMN_USERNAME = "username";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_COLUMN_PASSWORD = "password";

    // Tabel Kategori
    private static final String TABLE_CATEGORY = "Category";
    private static final String CATEGORY_COLUMN_ID = "category_id";
    private static final String CATEGORY_COLUMN_IMAGEID = "category_imageid";
    private static final String CATEGORY_COLUMN_NAME = "category_name";

    // Tabel Item
    private static final String TABLE_ITEM = "Item";
    private static final String ITEM_COLUMN_ID = "item_id";
    private static final String ITEM_COLUMN_NAME = "item_name";
    private static final String ITEM_COLUMN_PRICE = "item_price";
    private static final String ITEM_COLUMN_STOCK = "item_stock";
    private static final String ITEM_COLUMN_CATEGORY_ID = "category_id"; // Foreign Key

    // Query Create Table User
    private static final String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_USER+"("
            +USER_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +USER_COLUMN_USERNAME+" TEXT,"
            +USER_COLUMN_EMAIL+" TEXT,"
            +USER_COLUMN_PASSWORD+" TEXT)";

    // Query Create Table Category
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "+TABLE_CATEGORY+"("
            +CATEGORY_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +CATEGORY_COLUMN_IMAGEID+" INTEGER,"
            +CATEGORY_COLUMN_NAME+" TEXT)";

    // Query Create Table Item
    private static final String CREATE_TABLE_ITEM = "CREATE TABLE " + TABLE_ITEM + "("
            +ITEM_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +ITEM_COLUMN_NAME+" TEXT,"
            +ITEM_COLUMN_PRICE+" TEXT,"
            +ITEM_COLUMN_STOCK+" TEXT,"
            +ITEM_COLUMN_CATEGORY_ID+" INTEGER,"
            +"FOREIGN KEY("+ITEM_COLUMN_CATEGORY_ID+") REFERENCES "+TABLE_CATEGORY+"("+CATEGORY_COLUMN_ID+"))";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ITEM);
        onCreate(db);
    }

    public void insertTableUser(String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("email", email);
        cv.put("username", username);
        cv.put("password", password);
        long result = db.insert(TABLE_USER, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    } // Insert Table User

    public void insertTableCategory(int image_id, String category_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("category_imageid", image_id);
        cv.put("category_name", category_name);
        long result = db.insert(TABLE_CATEGORY, null, cv);
    } // Insert Table Category

    public void insertTableItem(String item_name, String item_price, String item_stock, int category_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("item_name", item_name);
        cv.put("item_price", item_price);
        cv.put("item_stock", item_stock);
        cv.put("category_id", category_id);
        long result = db.insert(TABLE_ITEM, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Insert Item Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Insert Item Success", Toast.LENGTH_SHORT).show();
        }
    } // Insert Table Item

    public Cursor takeUserAccount() {
        String query = "SELECT "+USER_COLUMN_USERNAME+","+USER_COLUMN_EMAIL+","+USER_COLUMN_PASSWORD+" FROM "+TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    } // Take User Account

    public int checkItemRow() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ITEM, null);
        int count = 0;

        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    } // Check Item Row

    public Cursor getAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TABLE_ITEM + "." + ITEM_COLUMN_ID + ", "
                + TABLE_ITEM + "." + ITEM_COLUMN_NAME + ", "
                + TABLE_ITEM + "." + ITEM_COLUMN_PRICE + ", "
                + TABLE_ITEM + "." + ITEM_COLUMN_STOCK + ", "
                + TABLE_ITEM + "." + ITEM_COLUMN_CATEGORY_ID + ", "
                + TABLE_CATEGORY + "." + CATEGORY_COLUMN_IMAGEID + " AS ITEM_COLUMN_CATEGORY_IMAGEID, "
                + TABLE_CATEGORY + "." + CATEGORY_COLUMN_NAME + " AS ITEM_COLUMN_CATEGORY_NAME "
                + "FROM " + TABLE_ITEM
                + " INNER JOIN " + TABLE_CATEGORY
                + " ON " + TABLE_ITEM + "." + ITEM_COLUMN_CATEGORY_ID
                + " = " + TABLE_CATEGORY + "." + CATEGORY_COLUMN_ID;

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    } // Get All Items

    public Cursor getCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+CATEGORY_COLUMN_ID+","+CATEGORY_COLUMN_NAME+" FROM "+TABLE_CATEGORY;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }// Get Category

    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_ITEM, ITEM_COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }

    public boolean updateItem(int id, String name, String price, String stock, int category_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_COLUMN_NAME, name);
        values.put(ITEM_COLUMN_PRICE, price);
        values.put(ITEM_COLUMN_STOCK, stock);
        values.put(ITEM_COLUMN_CATEGORY_ID, category_id);
        int rowsUpdated = db.update(TABLE_ITEM, values, ITEM_COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated > 0;
    }

    public Cursor searchItem(String search) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_ITEM + "." + ITEM_COLUMN_ID + ", "
                + TABLE_ITEM + "." + ITEM_COLUMN_NAME + ", "
                + TABLE_ITEM + "." + ITEM_COLUMN_PRICE + ", "
                + TABLE_ITEM + "." + ITEM_COLUMN_STOCK + ", "
                + TABLE_ITEM + "." + ITEM_COLUMN_CATEGORY_ID + ", "
                + TABLE_CATEGORY + "." + CATEGORY_COLUMN_IMAGEID + " AS ITEM_COLUMN_CATEGORY_IMAGEID, "
                + TABLE_CATEGORY + "." + CATEGORY_COLUMN_NAME + " AS ITEM_COLUMN_CATEGORY_NAME "
                + "FROM " + TABLE_ITEM
                + " INNER JOIN " + TABLE_CATEGORY
                + " ON " + TABLE_ITEM + "." + ITEM_COLUMN_CATEGORY_ID
                + " = " + TABLE_CATEGORY + "." + CATEGORY_COLUMN_ID +
                " WHERE " + TABLE_ITEM + "." + ITEM_COLUMN_NAME + " LIKE '%" + search + "%' OR " +
                TABLE_CATEGORY + "." + CATEGORY_COLUMN_NAME + " LIKE '%" + search + "%'";

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean updateProfile(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(query);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
            return false;
        }
    }

}
