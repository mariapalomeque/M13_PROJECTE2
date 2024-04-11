package com.example.remember;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class Menu_paciente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_paciente);

        LinearLayout linearLayout = findViewById(R.id.but_memory);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí pones el código que quieres ejecutar cuando se haga clic en el layout
                Intent intent = new Intent(Menu_paciente.this, info_memory.class);
                startActivity(intent);
            }
        });

    }
}