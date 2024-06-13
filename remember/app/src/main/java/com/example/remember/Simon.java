package com.example.remember;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simon extends AppCompatActivity {

    private TextView contadorTextView;
    private Button botonRojo, botonVerde, botonAzul, botonAmarillo;
    private Button buttonBack;
    private TextView cuentaRegresivaTextView;

    private List<Integer> secuencia;
    private int rondaActual;
    private int indiceSecuencia;
    private boolean esperandoUsuarioInput;

    private static final int TIEMPO_ENTRE_COLORES_MS = 1000; // Medio segundo
    private static final int NUMERO_COLORES_INICIAL = 1;
    private static final int NUMERO_COLORES_MAXIMO = 5;

    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon);

        contadorTextView = findViewById(R.id.contador);
        botonRojo = findViewById(R.id.boton_rojo);
        botonVerde = findViewById(R.id.boton_verde);
        botonAzul = findViewById(R.id.boton_azul);
        botonAmarillo = findViewById(R.id.boton_amarillo);
        buttonBack = findViewById(R.id.button_back);
        cuentaRegresivaTextView = findViewById(R.id.countdownTextView);

        secuencia = new ArrayList<>();
        rondaActual = 1;
        esperandoUsuarioInput = false;

        random = new Random();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Simon.this, Menu_paciente.class);
                startActivity(intent);
                finish(); // Opcionalmente, puedes llamar a finish() si quieres que la actividad actual se cierre.
            }
        });

        empezarJuego();
    }

    private void empezarJuego() {
        secuencia.clear();
        rondaActual = 1;
        for (int i = 0; i < NUMERO_COLORES_INICIAL; i++) {
            agregarColorAleatorio();
        }
        mostrarCuentaRegresiva();
    }

    private void agregarColorAleatorio() {
        secuencia.add(random.nextInt(4)); // 0: rojo, 1: verde, 2: azul, 3: amarillo
    }

    private void mostrarCuentaRegresiva() {
        final Handler handler = new Handler();
        cuentaRegresivaTextView.setVisibility(View.VISIBLE);

        final int[] cuenta = {3}; // Inicializar la cuenta regresiva en 3
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (cuenta[0] > 0) {
                    cuentaRegresivaTextView.setText(String.valueOf(cuenta[0]));
                    cuenta[0]--;
                    handler.postDelayed(this, 1000); // Retraso de 1 segundo entre cada número
                } else {
                    cuentaRegresivaTextView.setText("¡VAMOS!");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cuentaRegresivaTextView.setVisibility(View.INVISIBLE);
                            mostrarSecuencia();
                        }
                    }, 1000); // Esperar 1 segundo antes de ocultar el texto "¡VAMOS!" y mostrar la secuencia
                }
            }
        };
        handler.post(runnable);
    }

    private void mostrarSecuencia() {
        contadorTextView.setText(rondaActual + "/" + NUMERO_COLORES_MAXIMO); // Actualizar contador de rondas
        indiceSecuencia = 0;
        esperandoUsuarioInput = false;

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (indiceSecuencia < secuencia.size()) {
                    resaltarColor(secuencia.get(indiceSecuencia));
                    indiceSecuencia++;
                    handler.postDelayed(this, TIEMPO_ENTRE_COLORES_MS);
                } else {
                    esperandoUsuarioInput = true;
                    indiceSecuencia = 0; // Resetear el índice para la entrada del usuario
                }
            }
        };
        handler.postDelayed(runnable, TIEMPO_ENTRE_COLORES_MS);
    }

    private void resaltarColor(int color) {
        final Button boton;
        switch (color) {
            case 0:
                boton = botonRojo;
                break;
            case 1:
                boton = botonVerde;
                break;
            case 2:
                boton = botonAzul;
                break;
            case 3:
                boton = botonAmarillo;
                break;
            default:
                boton = null;
                break;
        }

        if (boton != null) {
            ViewCompat.setAlpha(boton, 0.5f); // Cambiar la opacidad para simular iluminación
            new Handler().postDelayed(() -> ViewCompat.setAlpha(boton, 1.0f), TIEMPO_ENTRE_COLORES_MS);
        }
    }

    public void botonPresionado(View view) {
        if (!esperandoUsuarioInput) return;

        int botonPresionado = -1;
        String botonId = getResources().getResourceEntryName(view.getId());

        switch (botonId) {
            case "boton_rojo":
                botonPresionado = 0;
                break;
            case "boton_verde":
                botonPresionado = 1;
                break;
            case "boton_azul":
                botonPresionado = 2;
                break;
            case "boton_amarillo":
                botonPresionado = 3;
                break;
        }

        if (indiceSecuencia < secuencia.size()) {
            if (botonPresionado == secuencia.get(indiceSecuencia)) {
                resaltarColor(botonPresionado); // Resaltar el botón presionado por el usuario
                indiceSecuencia++;
                if (indiceSecuencia == secuencia.size()) {
                    if (rondaActual == NUMERO_COLORES_MAXIMO) {
                        // El usuario ganó
                        // Aquí puedes escribir el código para manejar la victoria
                    } else {
                        // Siguiente ronda
                        rondaActual++;
                        agregarColorAleatorio();
                        mostrarCuentaRegresiva(); // Agregar cuenta regresiva antes de la siguiente ronda
                    }
                }
            } else {
                showGameOverDialog();
            }
        }
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Secuencia Incorrecta!");
        builder.setMessage("¡Has perdido! ¿Quieres intentarlo de nuevo?");
        builder.setPositiveButton("No", (dialog, which) -> {
            Intent intent = new Intent(Simon.this, Menu_paciente.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("Sí", (dialog, which) -> {
            recreate();
        });
        builder.setCancelable(false); // Evitar que el diálogo se cierre al hacer clic fuera de él
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
