package com.example.remember;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Resultados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        int numAciertos = getIntent().getIntExtra("numAciertos", 0);

        Intent intent = getIntent();

        TextView textViewAciertos = findViewById(R.id.num_aciertos);
        textViewAciertos.setText(String.valueOf(numAciertos));

        Button btn_again = findViewById(R.id.button_back);
        Button btnmenu = findViewById(R.id.button_salir);

        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Resultados.this, Preguntas.class);
                startActivity(intent);
            }
        });
        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Resultados.this, Menu_paciente.class);
                startActivity(intent);
            }
        });
    }
}
