package com.example.premierleaguetabell;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Här nedan lägger jag till länkar från youtube och föreläsningarna som hjälpte och inspererade mig
//KÄLLA: https://www.youtube.com/watch?v=aQAIMY-HzL8 , https://hv.instructure.com/courses/4287/pages/vg-forelasningar-man-26-apr?module_item_id=134666

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "KlubblagDB";
    public static final String TABLE_NAME = "KLUBBLAG";
    public static final String ID = "KlubbId";
    public static final String KLUBBNAMN = "Klubbnamn";
    public static final String KLUBBSTAD = "Klubbstad";
    public static final String KLUBBVINST = "Klubbvinst";
    public static final String KLUBBOAVGJORD = "Klubboavgjord";
    public static final String KLUBBFORLUST = "Klubbförlust";
    public static final String TOTALPOANG = "Totalpoaäng";

    Context context;

    // Här talar jag om vad min databas ska heta, i detta fall kommer databasen få namnet  "KlubblagDB"
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    // Här skapas en ny tabell "KLUBBLAG" i databasen med sju kolumner med sina datatyper, där klubbid är primär nyckel som sedan används vid uppdatering av klubblag, resten av kolumnerna tar emot datatyper som man ser nedan
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + "(KlubbId INTEGER PRIMARY KEY AUTOINCREMENT,Klubbnamn TEXT, Klubbstad TEXT, Klubbvinst TEXT,Klubboavgjord TEXT,Klubbförlust TEXT, Totalpoaäng TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Denna funktion hämtar alla värden/ Klubblag som finns i databasen, jag gör en SQL query/ fråga för att hämta alla värden från tabellen, sen så "sorterar" eller "organiserar" jag värdena baserat på totalpoäng som de har i en ordning från störst till minst i fallande ordning
    public Cursor visaData ()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT * FROM " + TABLE_NAME + " ORDER BY Totalpoaäng DESC";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
}
