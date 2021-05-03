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
//KÄLLA: https://www.youtube.com/watch?v=pFktQj69SbU ,https://hv.instructure.com/courses/4287/pages/vg-forelasningar-man-26-apr?module_item_id=134666

public class UppdateraKlubblag extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    EditText UppdateraKlubbnamn, UppdateraKlubbstad, UppdateraKlubbvinst, UppdateraKlubboavgjord, UppdateraKlubbForlorade,UppdateraId;
    Button UppdateraKlubblagknapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uppdatera_klubblag);

        //Tillbakaknappen i actionbaren med sin titel
        getSupportActionBar().setTitle("Gå tillbaka till poänglistan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Här nedan ser vi knappen och textrutor som används i denna aktivitet och som tilldelas från xml filen från layoutmappen
        UppdateraKlubblagknapp = (Button) findViewById(R.id.UppdateraKlubblagknapp);

        UppdateraId = (EditText) findViewById(R.id.UppdateraId);
        UppdateraKlubbnamn = (EditText) findViewById(R.id.UppdateraKlubbnamn);
        UppdateraKlubbstad = (EditText) findViewById(R.id.UppdateraKlubbstad);
        UppdateraKlubbvinst = (EditText) findViewById(R.id.UppdateraKlubbvinst);
        UppdateraKlubboavgjord = (EditText) findViewById(R.id.UppdateraKlubboavgjord);
        UppdateraKlubbForlorade = (EditText) findViewById(R.id.UppdateraKlubbForlorade);

        openHelper = new MySQLiteHelper(this);

        // Här under ligger funktionen för knappen "Uppdatera" som användaren klickar på i denna aktivitet, som användare matar man in sina inmatningar för ett att uppdatera ett visst klubblag beroende på vilket klubbid som man skriver in som inmatning
        UppdateraKlubblagknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Om  det är så att det är tomt i de  två första inmatningsrutorna  ber programmet användaren att mata in något i fälten innan man kan gå vidare med att Uppdatera ett klubblag
                if(TextUtils.isEmpty(UppdateraKlubbnamn.getText().toString()) || TextUtils.isEmpty(UppdateraKlubbstad.getText().toString()))
                {
                    Toast.makeText(UppdateraKlubblag.this, "Alla fält måste vara ifyllda...", Toast.LENGTH_SHORT).show();
                }


                else
                    {
                        String klubbnamn = UppdateraKlubbnamn.getText().toString();
                        String klubbstad = UppdateraKlubbstad.getText().toString();

                        // Om  det är så att användaren matat in bokstäver/text och inte siffror kommer det ett toastmeddelande som ber användaren att fylla i endast siffror, annars uppdateras inte klubblaget och man stannar kvar i aktiviteten tills man matat in rätt inmatningsdatatyp + alla fält ska vara ifyllda
                        try {
                            int Vinstmatch = Integer.parseInt(UppdateraKlubbvinst.getText().toString());
                            int Oavgjordmatch = Integer.parseInt(UppdateraKlubboavgjord.getText().toString());
                            int Forlustmatch = Integer.parseInt(UppdateraKlubbForlorade.getText().toString());

                            // Metoden för utträckningen av totalpoäng anropas
                            int totalpoang = raknautTotalpoang(Vinstmatch, Oavgjordmatch);

                            // Här öppnas och uppdateras det i databasen och de uppdaterade inmatningarna skickas in till databasen
                            db = openHelper.getWritableDatabase();
                            UppdateraKlubblagsData(klubbnamn, klubbstad, Vinstmatch, Oavgjordmatch, Forlustmatch, totalpoang);

                            Toast.makeText(UppdateraKlubblag.this, "Klubblaget är uppdaterat", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(UppdateraKlubblag.this, MainActivity.class);
                            startActivity(intent);
                        }

                       catch (NumberFormatException e)
                        {
                            Toast.makeText(UppdateraKlubblag.this, " Fyll i alla fält och Endast siffror gäller för vinst-, oavgjord- och förlustinmatning ", Toast.LENGTH_LONG).show();
                        }
                    }
            }
        });

    }

    // Här är metoden för totalpoäng som anropas ovan
    public int raknautTotalpoang(int Vinstmatch, int Oavgjordmatch) {
        int Totalpoang = Vinstmatch * 3 + Oavgjordmatch * 1;
        return Totalpoang;
    }

    // Detta är update metoden som uppdaterar  klubblaget in till databasen med hjälp av "db.update"
    // Här uppdaterar jag med hjälp av det id som blivit tilldelat till klubblaget när det skapades
    public boolean UppdateraKlubblagsData(String klubbnamn, String klubbstad, int Vinstmatch, int Oavgjordmatch, int Forlustmatch, int totalpoang)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.KLUBBNAMN,klubbnamn);
        contentValues.put(MySQLiteHelper.KLUBBSTAD,klubbstad);
        contentValues.put(MySQLiteHelper.KLUBBVINST,Vinstmatch);
        contentValues.put(MySQLiteHelper.KLUBBOAVGJORD,Oavgjordmatch);
        contentValues.put(MySQLiteHelper.KLUBBFORLUST,Forlustmatch);
        contentValues.put(MySQLiteHelper.TOTALPOANG,totalpoang);
        String id = UppdateraId.getText().toString();

        return db.update(MySQLiteHelper.TABLE_NAME,contentValues,MySQLiteHelper.ID + "=?", new String[] {id}) > 0 ;
    }
}