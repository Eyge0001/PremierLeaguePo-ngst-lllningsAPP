package com.example.premierleaguetabell;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//KÄLLA: https://www.youtube.com/watch?v=Q0gRqbtFLcw


// Här är min splashscreen med min icon som visas vid appstart i någon sekund innan man kommer in i appen
public class PremierLeagueSplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this,MainActivity.class));
    }
}
