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

        LinearLayout but_memory = findViewById(R.id.but_memory);
        LinearLayout but_preguntados = findViewById(R.id.but_preguntados);
        LinearLayout but_simon = findViewById(R.id.but_simon);
        LinearLayout but_imagencorrecta = findViewById(R.id.imagencorrecta);



        but_memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_paciente.this, info_memory.class);
                startActivity(intent);
            }
        });

        but_preguntados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_paciente.this, info_pregunta.class);
                startActivity(i);
            }
        });

        but_simon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_paciente.this, Simon.class);
                startActivity(i);
            }
        });

        but_imagencorrecta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_paciente.this, ImagenCorrecta.class);
                startActivity(i);
            }
        });
    }
}