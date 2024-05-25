package com.example.remember;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImagenCorrecta extends AppCompatActivity {

    private TextView textViewPregunta, countdownTextView;
    private ImageView imagen1, imagen2;
    private Button buttonBack;

    private List<PreguntaYPareja> listaPreguntasYParejas;
    private int rondaActual;
    private Random random;

    private static final int NUMERO_RONDAS = 5;
    private static final int TIEMPO_CUENTA_REGRESIVA = 10; // 10 segundos

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
        listaPreguntasYParejas = new ArrayList<>();
        rondaActual = 1;
        random = new Random();

        // Agregar las parejas de imágenes y preguntas
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estas casas es la tuya?", new int[]{R.drawable.casa1_ic, R.drawable.casa2_ic}));
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estos perros es el tuyo?", new int[]{R.drawable.dog1_ic, R.drawable.dog2_ic}));
        //listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estos autos es el tuyo?", new int[]{R.drawable.auto1_ic, R.drawable.auto2_ic})); // Agrega los nombres de las imágenes correspondientes
        //listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estos gatos es el tuyo?", new int[]{R.drawable.cat1_ic, R.drawable.cat2_ic})); // Agrega los nombres de las imágenes correspondientes
        //listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estos árboles es el tuyo?", new int[]{R.drawable.tree1_ic, R.drawable.tree2_ic})); // Agrega los nombres de las imágenes correspondientes

        
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImagenCorrecta.this, Menu_paciente.class);
                startActivity(intent);
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
        // Obtener pareja de imágenes y pregunta para la ronda actual
        PreguntaYPareja preguntaYPareja = obtenerPreguntaYParejaAleatoria();

        // Actualizar contador de ronda
        textViewPregunta.setText(preguntaYPareja.getPregunta());

        // Mostrar imágenes aleatorias
        mostrarImagenes(preguntaYPareja);

        // Iniciar cuenta regresiva
        iniciarCuentaRegresiva();
    }

    private void mostrarImagenes(PreguntaYPareja preguntaYPareja) {
        // Mostrar imágenes
        imagen1.setImageResource(preguntaYPareja.getPareja()[0]);
        imagen2.setImageResource(preguntaYPareja.getPareja()[1]);
    }

    private PreguntaYPareja obtenerPreguntaYParejaAleatoria() {
        // Devolver una pareja de imágenes y pregunta aleatoria
        return listaPreguntasYParejas.get(random.nextInt(listaPreguntasYParejas.size()));
    }

    private void iniciarCuentaRegresiva() {
        // Mostrar cuenta regresiva
        countdownTextView.setVisibility(View.VISIBLE);

        // Empezar cuenta regresiva
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int tiempoRestante = TIEMPO_CUENTA_REGRESIVA;

            @Override
            public void run() {
                // Actualizar texto de la cuenta regresiva
                countdownTextView.setText(String.valueOf(tiempoRestante));

                // Verificar si el tiempo ha terminado
                if (tiempoRestante == 0) {
                    // Ocultar cuenta regresiva
                    countdownTextView.setVisibility(View.INVISIBLE);

                    // Mostrar mensaje de tiempo finalizado
                    mostrarMensajeTiempoFinalizado();
                } else {
                    // Reducir tiempo restante y continuar la cuenta regresiva
                    tiempoRestante--;

                    // Llamar recursivamente después de 1 segundo
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000); // Retraso inicial de 1 segundo
    }

    private void mostrarMensajeTiempoFinalizado() {
        // Crear diálogo de alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tiempo Finalizado")
                .setMessage("¿Quieres volver a jugar?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        empezarJuego();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // Volver al menú principal o realizar otra acción
                    }
                })
                .show();
    }

    // Clase auxiliar para almacenar pareja de imágenes y su pregunta correspondiente
    private static class PreguntaYPareja {
        private final String pregunta;
        private final int[] pareja;

        public PreguntaYPareja(String pregunta, int[] pareja) {
            this.pregunta = pregunta;
            this.pareja = pareja;
        }

        public String getPregunta() {
            return pregunta;
        }

        public int[] getPareja() {
            return pareja;
        }
    }
}
