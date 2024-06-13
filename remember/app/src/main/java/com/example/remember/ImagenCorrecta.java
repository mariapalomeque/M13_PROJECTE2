package com.example.remember;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ImagenCorrecta extends AppCompatActivity {

    private TextView textViewPregunta, countdownTextView, rondasTextView;
    private ImageView imagen1, imagen2;
    private Button buttonBack;

    private List<PreguntaYPareja> listaPreguntasYParejas;
    private List<PreguntaYPareja> preguntasNoUsadas;
    private int rondaActual;
    private int correctas;
    private Random random;

    private static final int NUMERO_RONDAS = 5;
    private static final int TIEMPO_CUENTA_REGRESIVA = 10; // 10 segundos

    private Handler cuentaRegresivaHandler;
    private Runnable cuentaRegresivaRunnable;
    private boolean rondaTerminada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagencorrecta);

        textViewPregunta = findViewById(R.id.textViewPregunta);
        countdownTextView = findViewById(R.id.countdownTextView);
        rondasTextView = findViewById(R.id.rondas);
        imagen1 = findViewById(R.id.imagen1);
        imagen2 = findViewById(R.id.imagen2);
        buttonBack = findViewById(R.id.button_back);

        listaPreguntasYParejas = new ArrayList<>();
        rondaActual = 1;
        correctas = 0;
        random = new Random();
        rondaTerminada = false;

        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cual és el rey de España?", new int[]{R.drawable.reyespana_ic, R.drawable.pedrosanchez_ic}));
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estos perros és un Pastor Aleman?", new int[]{R.drawable.dog2_ic, R.drawable.dog1_ic}));
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cual és la torre Eiffel?", new int[]{R.drawable.torre_eiffel_ic, R.drawable.torre_pisa_ic}));
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Que ciudad és Nueva York?", new int[]{R.drawable.nyskyline_ic, R.drawable.bcnskyline_ic}));
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Quien és el rey del POP?", new int[]{R.drawable.mj_ic, R.drawable.elvis_ic}));

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rondaTerminada) {
                    rondaActual++;
                    if (rondaActual <= NUMERO_RONDAS) {
                        mostrarRonda();
                    } else {
                        mostrarResultadoFinal();
                    }
                } else {
                    Intent intent = new Intent(ImagenCorrecta.this, Menu_paciente.class);
                    startActivity(intent);
                }
            }
        });

        empezarJuego();
    }

    private void empezarJuego() {
        rondaActual = 1;
        correctas = 0; // Reiniciar contador de correctas
        preguntasNoUsadas = new ArrayList<>(listaPreguntasYParejas);
        Collections.shuffle(preguntasNoUsadas); // Barajar las preguntas para asegurar aleatoriedad
        mostrarRonda();
    }

    private void mostrarRonda() {
        rondaTerminada = false;
        buttonBack.setText("SALIR");
        actualizarTextoRonda();

        PreguntaYPareja preguntaYPareja = preguntasNoUsadas.remove(0); // Obtener la primera pregunta no usada
        textViewPregunta.setText(preguntaYPareja.getPregunta());
        imagen1.clearColorFilter();
        imagen2.clearColorFilter();
        imagen1.setEnabled(true);
        imagen2.setEnabled(true);
        mostrarImagenes(preguntaYPareja);
        iniciarCuentaRegresiva();
    }

    private void mostrarImagenes(final PreguntaYPareja preguntaYPareja) {
        List<Integer> imagenes = new ArrayList<>();
        imagenes.add(preguntaYPareja.getPareja()[0]); // Imagen correcta
        imagenes.add(preguntaYPareja.getPareja()[1]); // Imagen incorrecta

        Collections.shuffle(imagenes);

        imagen1.setImageResource(imagenes.get(0));
        imagen2.setImageResource(imagenes.get(1));

        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta(imagenes.get(0), preguntaYPareja.getPareja()[0], imagen1);
            }
        });

        imagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta(imagenes.get(1), preguntaYPareja.getPareja()[0], imagen2);
            }
        });
    }

    private void verificarRespuesta(int imagenSeleccionada, int imagenCorrecta, ImageView imagenSeleccionadaView) {
        cuentaRegresivaHandler.removeCallbacks(cuentaRegresivaRunnable);
        int alphaValue = 128; // Valor de transparencia (0-255), donde 255 es completamente opaco y 0 es completamente transparente
        if (imagenSeleccionada == imagenCorrecta) {
            correctas++;
            int greenColorWithTransparency = android.graphics.Color.argb(alphaValue, 0, 255, 0);
            imagenSeleccionadaView.setColorFilter(greenColorWithTransparency, PorterDuff.Mode.SRC_ATOP);
        } else {
            int redColorWithTransparency = android.graphics.Color.argb(alphaValue, 255, 0, 0);
            imagenSeleccionadaView.setColorFilter(redColorWithTransparency, PorterDuff.Mode.SRC_ATOP);
        }

        imagen1.setEnabled(false);
        imagen2.setEnabled(false);

        if (imagenSeleccionadaView == imagen1) {
            imagen2.setEnabled(false);
        } else {
            imagen1.setEnabled(false);
        }

        buttonBack.setText("CONTINUAR");
        rondaTerminada = true;
    }

    private void iniciarCuentaRegresiva() {
        if (cuentaRegresivaHandler != null && cuentaRegresivaRunnable != null) {
            cuentaRegresivaHandler.removeCallbacks(cuentaRegresivaRunnable);
        }

        cuentaRegresivaHandler = new Handler();
        cuentaRegresivaRunnable = new Runnable() {
            int tiempoRestante = TIEMPO_CUENTA_REGRESIVA;

            @Override
            public void run() {
                countdownTextView.setText(String.valueOf(tiempoRestante));

                if (!rondaTerminada) {
                    if (tiempoRestante == 0) {
                        mostrarMensajeTiempoFinalizado();
                    } else {
                        tiempoRestante--;
                        cuentaRegresivaHandler.postDelayed(this, 1000);
                    }
                }
            }
        };

        cuentaRegresivaHandler.postDelayed(cuentaRegresivaRunnable, 1000);
    }

    private void actualizarTextoRonda() {
        String textoRonda = rondaActual + "/" + NUMERO_RONDAS;
        rondasTextView.setText(textoRonda);
    }

    private void mostrarMensajeTiempoFinalizado() {
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
                        Intent intent = new Intent(ImagenCorrecta.this, Menu_paciente.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    private void mostrarResultadoFinal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Juego Terminado")
                .setMessage("Has completado el juego con " + correctas + " de 5 respuestas correctas.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(ImagenCorrecta.this, Menu_paciente.class);

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