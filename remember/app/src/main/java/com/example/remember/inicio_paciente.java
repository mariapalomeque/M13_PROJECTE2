package com.example.remember;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class inicio_paciente extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_paciente);

        TextView btIniciarSesion = findViewById(R.id.cuenta_registrada);
        Button btCrearCuenta = findViewById(R.id.crear_cuenta);
        btIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(inicio_paciente.this, iniciar_sesion_paciente.class);
                startActivity(i);
            }
        });

        btCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(inicio_paciente.this, RegistroPaciente.class);
                startActivity(i);
            }
        });
    }
}
