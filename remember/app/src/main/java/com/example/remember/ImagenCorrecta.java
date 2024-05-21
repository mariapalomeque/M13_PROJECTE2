package com.example.remember;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remember.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImagenCorrecta extends AppCompatActivity {

    private TextView textViewPregunta, countdownTextView;
    private ImageView imagen1, imagen2;
    private Button buttonBack;

    private List<Integer> imagenes;
    private int rondaActual;
    private Random random;

    private static final int NUMERO_RONDAS = 5;
    private static final int TIEMPO_CUENTA_REGRESIVA = 10000; // 10 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagencorrecta);

        // Inicializar vistas
        textViewPregunta = findViewById(R.id.textViewPregunta);
        countdownTextView = findViewById(R.id.countdownTextView);
        imagen1 = findViewById(R.id.imagen1);
        imagen2 = findViewById(R.id.imagen2);
        buttonBack = findViewById(R.id.button_back);

        // Inicializar variables
        imagenes = new ArrayList<>();
        rondaActual = 1;
        random = new Random();

        // Configurar evento de clic para el botón de salir
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Iniciar juego
        empezarJuego();
    }

    private void empezarJuego() {
        // Restablecer ronda actual
        rondaActual = 1;

        // Mostrar primera ronda
        mostrarRonda();
    }

    private void mostrarRonda() {
        // Actualizar contador de ronda
        textViewPregunta.setText(obtenerPregunta(rondaActual));

        // Mostrar imágenes aleatorias
        mostrarImagenes();

        // Iniciar cuenta regresiva
        iniciarCuentaRegresiva();
    }

    private void mostrarImagenes() {
        // Obtener dos imágenes aleatorias
        int imagen1Resource = obtenerImagenAleatoria();
        int imagen2Resource = obtenerImagenAleatoria();

        // Mostrar imágenes
        imagen1.setImageResource(imagen1Resource);
        imagen2.setImageResource(imagen2Resource);
    }

    private int obtenerImagenAleatoria() {
        // Devolver una imagen aleatoria (simulado)
        return imagenes.get(random.nextInt(imagenes.size()));
    }

    private String obtenerPregunta(int ronda) {
        // Devolver la pregunta según la ronda (simulado)
        switch (ronda) {
            case 1:
                return "¿Cuál de estas casas es la tuya?";
            case 2:
                return "¿Cuál de estos autos es el tuyo?";
            // Agregar más preguntas aquí según las rondas necesarias
            default:
                return "¿Cuál es tu opción favorita?";
        }
    }

    private void iniciarCuentaRegresiva() {
        // Mostrar cuenta regresiva
        countdownTextView.setVisibility(View.VISIBLE);

        // Empezar cuenta regresiva con un retardo de 1 segundo
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int tiempoRestante = 10; // Iniciar desde 10 segundos

            @Override
            public void run() {
                // Actualizar texto de la cuenta regresiva
                countdownTextView.setText(String.valueOf(tiempoRestante));

                // Verificar si el tiempo ha terminado
                if (tiempoRestante == 0) {
                    // Ocultar cuenta regresiva
                    countdownTextView.setVisibility(View.INVISIBLE);

                    // Pasar a la siguiente ronda (si es la última, el jugador ganó)
                    if (rondaActual < NUMERO_RONDAS) {
                        rondaActual++;
                        mostrarRonda();
                    } else {
                        // El jugador ganó (código a ejecutar cuando gane)
                    }
                } else {
                    // Reducir tiempo restante y continuar la cuenta regresiva
                    tiempoRestante--;
                    // Reducir tiempo restante y continuar la cuenta regresiva
                    tiempoRestante--;

                    // Llamar recursivamente después de 1 segundo
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000); // Retraso inicial de 1 segundo
    }
}