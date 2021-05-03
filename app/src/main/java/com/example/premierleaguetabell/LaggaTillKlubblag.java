package com.example.premierleaguetabell;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//Här nedan lägger jag till länkar från youtube och föreläsningarna som hjälpte och inspererade mig
//KÄLLA: https://www.youtube.com/watch?v=aQAIMY-HzL8 , https://hv.instructure.com/courses/4287/pages/vg-forelasningar-man-26-apr?module_item_id=134666

public class LaggaTillKlubblag extends AppCompatActivity {
    EditText Klubbnamninmatning,Klubbstadinmatning,Klubbvinstinmatning,Klubboavgjordinmatning,Klubbforloradinmatning;
    Button LaggatillKlubblagknapp;

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laggatill_klubblag);

        //Tillbakaknappen i actionbaren med sin titel
        getSupportActionBar().setTitle("Gå tillbaka till poänglistan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Här nedan ser vi knappen och textrutor som används i denna aktivitet och som tilldelas från xml filen från layoutmappen
        LaggatillKlubblagknapp = (Button) findViewById(R.id.LaggatillKlubblagknapp);

        Klubbnamninmatning = (EditText) findViewById(R.id.Klubbnamninmatning);
        Klubbstadinmatning = (EditText) findViewById(R.id.Klubbstadinmatning);
        Klubbvinstinmatning = (EditText) findViewById(R.id.Klubbvinstinmatning);
        Klubboavgjordinmatning = (EditText) findViewById(R.id.Klubboavgjordinmatning);
        Klubbforloradinmatning = (EditText) findViewById(R.id.Klubbforloradinmatning);

         openHelper = new MySQLiteHelper(this);


         // Här under ligger funktionen för knappen "Lägg Till" som användaren klickar på i denna aktivitet och som användare matar man in sina inmatningar för ett nytt klubblag
        LaggatillKlubblagknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Om  det är så att det är tomt i de  två första inmatningsrutorna , ber programmet användaren att mata in något i fälten innan man kan gå vidare med att lägga till ett klubblag
                if(TextUtils.isEmpty(Klubbnamninmatning.getText().toString()) || TextUtils.isEmpty(Klubbstadinmatning.getText().toString()))
                {
                    Toast.makeText(LaggaTillKlubblag.this, "Alla fält måste vara ifyllda...", Toast.LENGTH_SHORT).show();
                }

                else
                    {
                        String klubbnamn = Klubbnamninmatning.getText().toString();
                        String klubbstad = Klubbstadinmatning.getText().toString();

                        // Om  det är så att användaren matat in bokstäver/text och inte siffror kommer det ett toastmeddelande som ber användaren att fylla i endast siffror, annars läggs klubblaget till i databasen som sedan kommer med i listan
                        try {
                            int Vinstmatch = Integer.parseInt(Klubbvinstinmatning.getText().toString());
                            int Oavgjordmatch = Integer.parseInt(Klubboavgjordinmatning.getText().toString());
                            int Forlustmatch = Integer.parseInt(Klubbforloradinmatning.getText().toString());

                            // Metoden för utträckningen av totalpoäng anropas
                            int totalpoang = raknautTotalpoang(Vinstmatch,Oavgjordmatch);

                            // Här öppnas och skapas det i databasen och de nya inmatningarna skickas in till databasen
                            db = openHelper.getWritableDatabase();
                            lagginKlubblagsData(klubbnamn,klubbstad,Vinstmatch,Oavgjordmatch,Forlustmatch,totalpoang);

                            Toast.makeText(LaggaTillKlubblag.this, "Klubblag är tillagd", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LaggaTillKlubblag.this, MainActivity.class);
                            startActivity(intent);
                        }

                         catch (Exception e)
                        {
                            Toast.makeText(LaggaTillKlubblag.this, " Fyll i alla fält och Endast siffror gäller för vinst-, oavgjord- och förlustinmatning ", Toast.LENGTH_LONG).show();
                        }
                    }
            }
        });
    }

    // Här är metoden för totalpoäng som anropas ovan
    public int raknautTotalpoang(int Vinstmatch, int Oavgjordmatch )
    {
     int  Totalpoang =  Vinstmatch * 3 + Oavgjordmatch * 1;
     return Totalpoang;
    }

    // Detta är insert metoden som lägger till klubblaget in till databasen med hjälp av "db.insert"
    // Skapar ContentValues som skrivs som kolumnerna/ values jag har i databasen
    public void lagginKlubblagsData(String klubbnamn, String klubbstad, int Vinstmatch , int Oavgjordmatch, int Forlustmatch, int totalpoang)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.KLUBBNAMN,klubbnamn);
        contentValues.put(MySQLiteHelper.KLUBBSTAD,klubbstad);
        contentValues.put(MySQLiteHelper.KLUBBVINST,Vinstmatch);
        contentValues.put(MySQLiteHelper.KLUBBOAVGJORD,Oavgjordmatch);
        contentValues.put(MySQLiteHelper.KLUBBFORLUST,Forlustmatch);
        contentValues.put(MySQLiteHelper.TOTALPOANG,totalpoang);
        long id = db.insert(MySQLiteHelper.TABLE_NAME,null,contentValues);
    }

}