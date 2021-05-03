package com.example.premierleaguetabell;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
//Här nedan lägger jag till länkar från youtube och föreläsningarna som hjälpte och inspererade mig
//KÄLLA: https://www.youtube.com/watch?v=aQAIMY-HzL8 , https://hv.instructure.com/courses/4287/pages/vg-forelasningar-man-26-apr?module_item_id=134666

// Här ligger Startsidan som man ser när applikationen körs igång, där en lista med klubblag som visas i en rangordning med hjälp av Totalpoäng
// Det finns även en knapp som tar användaren vidare till nästa aktivitet "LäggTillKlubblag"

public class MainActivity extends AppCompatActivity {

    MySQLiteHelper db;

    Button Laggatillnyttlag;
    ListView Klubblistan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Premier League Poänglista");

        // Här nedan ser vi knappen och textrutan som används i denna aktivitet och som tilldelas från xml filen från layoutmappen
        Klubblistan = (ListView) findViewById(R.id.Klubblistan);
        Laggatillnyttlag = (Button) findViewById(R.id.Laggatillnyttlag);

        db = new MySQLiteHelper(this);

        // Här skapas en arraylista som jag kallade "klubblista"
        ArrayList<String> klubblista = new ArrayList<>();

        // Kopplingen till funktionen "visaData" som finns i klassen "MySQLHelper" (som förklaras lite mer om vad funktionen gör i MySQLHelper), detta är funktionen som visar upp alla klubblagar som finns i databasen i listan
        Cursor cursor = db.visaData();

        // Här kollar jag om databasen är tom, och det kommer upp en Toast som berättar för användaren att databasen är tom om det är så att det inte finns något inmatat i databasen och det inte finns något att visa upp i listan
        if(cursor.getCount() == 0)
        {
            Toast.makeText(MainActivity.this, "Databasen är tom, det finns inget att visa i listan", Toast.LENGTH_SHORT).show();
        }

        // Om det är så att det finns data i databasen kommer det att visas upp på följande sätt som man ser nedan, det skapas även en adapter som kopplas ihop med min lista som använder sig utav android studions egna "simple_list_item_1" och visar upp min lista på följande sätt
        else
            {
                while (cursor.moveToNext())
                {
                    klubblista.add ( "Klubbid: "+ cursor.getString(0) +   "\nKlubblagsnamn: " + cursor.getString(1) + " \nKlubblagstad: " + cursor.getString(2)  + "\nPL poäng: " + cursor.getString(6));
                    ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, klubblista);
                    Klubblistan.setAdapter(listAdapter);

                    Intent intent = new Intent(MainActivity.this,UppdateraKlubblag.class);
                }
            }

        // Här ser vi koden för när användaren klickar på listan för att sedan uppdatera ett visst klubblag, då man som användare skickas vidare till nästa aktivitet "UppdateraKlubblag"
        Klubblistan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, UppdateraKlubblag.class);
                startActivity(intent);
            }
        });

        // Här ser vi koden för när användaren klickar på "Lägg till ett nytt klubblag" knappen för att sedan Lägga till ett nytt klubblag till databasen som sedan visas upp i listan, då man som användare skickas vidare till nästa aktivitet "LäggTillKlubblag"
        Laggatillnyttlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LaggaTillKlubblag.class);
                startActivity(intent);
            }
        });
    }
}

