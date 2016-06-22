package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.Grocery;

/**
 * Created by Sukriti on 6/17/16.
 */

public class DBAdapter {

    private static final String DATABASE_NAME = "groceries";
    private static final String DATABASE_TABLE = "groceries_table";
    private static final int DATABASE_VERSION = 1;
    // Column Names!
    private static final String KEY_ID = "_id"; // Primary Key - Unique For Each Object!
    private static final String NAME = "name";
    private static final String CHECKED = "checked";


    // Heirarchy - DatabseName -> we have multiple Tables -> Within each table we have Multiple Columns!

    private static final String CREATE_DATABASE = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT NOT NULL," + CHECKED + " INTEGER NOT NULL DEFAULT 0" +  ")";

    // CREATE TABLE TABLE_NAME()
    // Within the parameters we provide the columsn and their properties!

    final Context context;
    DatabaseHelper DBHelper; // THis class helpes in creating the database!
    SQLiteDatabase db;

    public DBAdapter(Context c) {
        this.context = c;
        DBHelper = new DatabaseHelper(context);
    }

    // This Class Allows us to create Database, To Upgrade Database!
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            // Now create the database!
            try {
                db.execSQL(CREATE_DATABASE); // Creates Table and Columns in the Database!
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                // We will have tell the device to delete the older version and use the latest version!
                db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);// DELETES The Table if present.
                onCreate(db);
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
    } // End of DBHelper Class

    // Sqlite has C.R.U.D functions
    // Create Retrieve Update Delete
    // First have to open the database, Then Perform These Functions and then close the database.

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }


    // 1. Create Data

    public long createGorceryItem(Grocery c) {
        // We will have one row of data when we call this method.
        // long is the unique id for each row which is automatially created.
        // We use ContentValues to insert data into the row!

        ContentValues cV = new ContentValues();
        cV.put(NAME, c.getItem());
        cV.put(CHECKED, c.getChecked());
        return db.insert(DATABASE_TABLE, null, cV);

    }

    // 2. Delete Data

    public boolean deleteGroceries(long rowId) { // Row Id is the unique id for each row!
        // 2nd Parameter is we will have to use a query to search for that particular row!
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    // Retrieve All Data
    // Cursor helps us to navigate Colums and Rows!

    public Cursor getAllGorceries() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ID, NAME, CHECKED}, null, null, null, null, null);
    }


    // Retrieve Individual Data or Single Ro

    public Cursor getIndividualGroceryItem(long rowId) throws SQLException {

        Cursor c = db.query(true, DATABASE_TABLE, new String[]{KEY_ID, NAME, CHECKED}, KEY_ID + "=" + rowId, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    public Cursor getSearchedName(String name) throws SQLException {

        Cursor c = db.query(true, DATABASE_TABLE, new String[]{KEY_ID, NAME, CHECKED}, NAME + "=" + name, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    // Update Data in a Row!

    public void updateGroceryItem(long rowId, Grocery grocery) {

        ContentValues cV = new ContentValues();
        cV.put(NAME, grocery.getItem());
        cV.put(CHECKED, grocery.getChecked());
        db.update(DATABASE_TABLE, cV, KEY_ID + "=" + rowId, null);

    }





}



