package com.example.autosisc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class AutoDBHelper extends SQLiteOpenHelper {
    public static final int DATA_BASE_VERSION=2;
    public static final String DATA_BASE_NAME="AutosISC.db";
    public static final String SQL_CREATE_TABLE_AUTOMOVIL=
        "CREATE TABLE" + AutosISCContrac.Automobil.TABLE_NAME + " ( "+
                AutosISCContrac.Automobil._ID+" INTEGER, "+
                AutosISCContrac.Automobil.COLUMN_NAME_FOLIO+" TEXT PRIMARY KEY, "+
                AutosISCContrac.Automobil.COLUMN_NAME_MARCA+" TEXT, "+
                AutosISCContrac.Automobil.COLUMN_NAME_MODELO+" TEXT, "+
                AutosISCContrac.Automobil.COLUMN_NAME_AÑO+" TEXT, "+
                AutosISCContrac.Automobil.COLUMN_NAME_COLOR+" TEXT, "+
                AutosISCContrac.Automobil.COLUMN_NAME_SERIE+" TEXT, "+
                AutosISCContrac.Automobil.COLUMN_NAME_PRECIO+" REAL )";

    public static final  String SQL_DROP_TABLE_AUTOMOVIL="DROP TABLE IF EXITS"
            +AutosISCContrac.Automobil.TABLE_NAME;


    public AutoDBHelper(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_AUTOMOVIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_AUTOMOVIL);
        onCreate(sqLiteDatabase);
    }
}
