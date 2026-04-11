package com.example.ex11042;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class HelperDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EXPENSES = "Expenses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_AMOUNT = "Amount";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_RECURRING = "Recurring";

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strCreate = "CREATE TABLE " + TABLE_EXPENSES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_AMOUNT + " REAL, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_RECURRING + " INTEGER)";
        Log.d("SQL_QUERY", strCreate);
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    public void addExpense(String desc, double amount, String category, String date, int recurring) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, desc);
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_RECURRING, recurring);
        db.insert(TABLE_EXPENSES, null, values);
        db.close();
    }

    public ArrayList<Expense> getAllExpenses() {
        ArrayList<Expense> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EXPENSES + " ORDER BY " + COLUMN_DATE + " DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String desc = cursor.getString(1);
                double amount = cursor.getDouble(2);
                String category = cursor.getString(3);
                String date = cursor.getString(4);
                boolean recurring = cursor.getInt(5) == 1;
                list.add(new Expense(id, desc, amount, category, date, recurring));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Expense> getFilteredExpenses(String minPrice, String maxPrice, String categoryFilter, String sortOrder) {
        ArrayList<Expense> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        double min = minPrice.isEmpty() ? 0 : Double.parseDouble(minPrice);
        double max = maxPrice.isEmpty() ? 9999999 : Double.parseDouble(maxPrice);

        String selection = COLUMN_AMOUNT + " >= ? AND " + COLUMN_AMOUNT + " <= ?";
        ArrayList<String> argsList = new ArrayList<>();
        argsList.add(String.valueOf(min));
        argsList.add(String.valueOf(max));

        if (!categoryFilter.equals("All")) {
            selection += " AND " + COLUMN_CATEGORY + " = ?";
            argsList.add(categoryFilter);
        }

        String[] selectionArgs = argsList.toArray(new String[0]);
        String orderBy = COLUMN_DATE + " DESC";

        if(sortOrder.equals("Amount (Highest)")) orderBy = COLUMN_AMOUNT + " DESC";
        else if(sortOrder.equals("Amount (Lowest)")) orderBy = COLUMN_AMOUNT + " ASC";
        else if(sortOrder.equals("Date (Oldest First)")) orderBy = COLUMN_DATE + " ASC";

        Cursor cursor = db.query(TABLE_EXPENSES, null, selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Expense(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2),
                        cursor.getString(3), cursor.getString(4), cursor.getInt(5) == 1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public double getTotalAmount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_EXPENSES, null);
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return total;
    }
}