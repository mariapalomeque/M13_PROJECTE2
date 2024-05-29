package com.example.remember;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IniciarSesion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        Button btiniciarsession = findViewById(R.id.btn_iniciar_sesion);

        btiniciarsession.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesion.this, presentacion.class);
                startActivity(intent);
            }
        });

    }
}
