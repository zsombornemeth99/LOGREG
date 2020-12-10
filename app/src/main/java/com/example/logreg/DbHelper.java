package com.example.logreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DB_Version = 1;
    public static final String DB_Name = "felhasznalo.db";

    public static final String FELHASZNALO_TABLE = "palinka";

    public static final String COL_ID = "id";
    public static final String COL_EMAIL = "email";
    public static final String COL_FELHNEV = "felhnev";
    public static final String COL_JELSZO = "jelszo";
    public static final String COL_TELJESNEV = "teljesnev";

    public DbHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + FELHASZNALO_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " VARCHAR(255) NOT NULL UNIQUE," +
                COL_FELHNEV + " VARCHAR(255) NOT NULL UNIQUE," +
                COL_JELSZO + " VARCHAR(255) NOT NULL, " +
                COL_TELJESNEV + " INTEGER NOT NULL" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + FELHASZNALO_TABLE;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean adatRogzites(String email, String felhnev, String jelszo, String teljnev)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_FELHNEV, felhnev);
        values.put(COL_JELSZO, jelszo);
        values.put(COL_TELJESNEV, teljnev);

        long result = db.insert(FELHASZNALO_TABLE, null, values);

        return result != 1;
    }

    public Cursor bejelentkezes(String felhnev, String jelszo) {
        SQLiteDatabase db = this.getReadableDatabase();
        return  db.rawQuery("SELECT felhnev, jelszo FROM " + FELHASZNALO_TABLE + " WHERE felhnev = '" + felhnev + "' and jelszo = '"
                + jelszo + "'" ,null );
    }
}
