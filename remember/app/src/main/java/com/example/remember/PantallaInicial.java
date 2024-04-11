package com.example.remember;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicial);

        Button btPaciente = findViewById(R.id.boton_paciente);
        Button btDoctor = findViewById(R.id.boton_doctor);
        btPaciente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantallaInicial.this, CrearCuenta.class);
                startActivity(intent);
            }
        });

        btDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PantallaInicial.this, CrearCuenta.class);
                startActivity(i);
            }
        });

    }
}