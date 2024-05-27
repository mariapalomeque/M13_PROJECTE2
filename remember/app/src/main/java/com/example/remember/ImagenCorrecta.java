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
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ImagenCorrecta extends AppCompatActivity {

    private TextView textViewPregunta, countdownTextView;
    private ImageView imagen1, imagen2, imageResultado;
    private Button buttonBack;

    private List<PreguntaYPareja> listaPreguntasYParejas;
    private int rondaActual;
    private int correctas;
    private Random random;

    private static final int NUMERO_RONDAS = 5;
    private static final int TIEMPO_CUENTA_REGRESIVA = 10; // 10 segundos

    private Handler cuentaRegresivaHandler;
    private Runnable cuentaRegresivaRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagencorrecta);

        // Inicializar vistas
        textViewPregunta = findViewById(R.id.textViewPregunta);
        countdownTextView = findViewById(R.id.countdownTextView);
        imagen1 = findViewById(R.id.imagen1);
        imagen2 = findViewById(R.id.imagen2);
        imageResultado = findViewById(R.id.imageResultado);  // Nueva vista para mostrar el resultado
        buttonBack = findViewById(R.id.button_back);

        // Inicializar variables
        listaPreguntasYParejas = new ArrayList<>();
        rondaActual = 1;
        correctas = 0;
        random = new Random();

        // Agregar las parejas de imágenes y preguntas
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estas casas es la tuya?", new int[]{R.drawable.casa1_ic, R.drawable.casa2_ic}));
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estos perros es el tuyo?", new int[]{R.drawable.dog1_ic, R.drawable.dog2_ic}));
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estos coches es el tuyo?", new int[]{R.drawable.car1_ic, R.drawable.car2_ic}));
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál de estos niños es tu nieto Martín?", new int[]{R.drawable.kid1_ic, R.drawable.kid2_ic}));
        listaPreguntasYParejas.add(new PreguntaYPareja("¿Cuál es tu deporte favorito?", new int[]{R.drawable.sport1_ic, R.drawable.sport2_ic}));

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
        correctas = 0; // Reiniciar contador de correctas

        // Mostrar primera ronda
        mostrarRonda();
    }

    private PreguntaYPareja obtenerPreguntaYParejaAleatoria() {
        // Devolver una pregunta y pareja de imágenes aleatoria
        return listaPreguntasYParejas.get(random.nextInt(listaPreguntasYParejas.size()));
    }

    private void mostrarRonda() {
        // Obtener pareja de imágenes y pregunta para la ronda actual
        PreguntaYPareja preguntaYPareja = obtenerPreguntaYParejaAleatoria();

        // Actualizar texto de la pregunta
        textViewPregunta.setText(preguntaYPareja.getPregunta());

        // Mostrar imágenes aleatorias
        mostrarImagenes(preguntaYPareja);

        // Iniciar cuenta regresiva
        iniciarCuentaRegresiva();
    }

    private void mostrarImagenes(final PreguntaYPareja preguntaYPareja) {
        // Crear una lista para las imágenes
        List<Integer> imagenes = new ArrayList<>();
        imagenes.add(preguntaYPareja.getPareja()[0]); // Imagen correcta
        imagenes.add(preguntaYPareja.getPareja()[1]); // Imagen incorrecta

        // Barajar la lista para aleatorizar las posiciones
        Collections.shuffle(imagenes);

        // Mostrar imágenes en las posiciones aleatorias
        imagen1.setImageResource(imagenes.get(0));
        imagen2.setImageResource(imagenes.get(1));

        // Configurar eventos de clic para las imágenes
        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta(imagenes.get(0), preguntaYPareja.getPareja()[0]);
            }
        });

        imagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta(imagenes.get(1), preguntaYPareja.getPareja()[0]);
            }
        });
    }

    private void verificarRespuesta(int imagenSeleccionada, int imagenCorrecta) {
        // Verificar si la imagen seleccionada es la correcta
        if (imagenSeleccionada == imagenCorrecta) {
            correctas++;
        } else {
            mostrarImagenResultado(R.drawable.cross_ic);  // Mostrar cruz roja
        }

        // Pasar a la siguiente ronda después de mostrar la imagen de resultado
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rondaActual < NUMERO_RONDAS) {
                    rondaActual++;
                    mostrarRonda();
                } else {
                    mostrarResultadoFinal();
                }
            }
        }, 1000); // Mostrar la imagen durante 1 segundo
    }

    private void mostrarImagenResultado(int resourceId) {
        // Mostrar imagen de resultado
        imageResultado.setImageResource(resourceId);
        imageResultado.setVisibility(View.VISIBLE);

        // Ocultar imagen después de 1 segundo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageResultado.setVisibility(View.GONE);
            }
        }, 1000);
    }

    private void iniciarCuentaRegresiva() {
        // Cancelar cualquier cuenta regresiva en curso
        if (cuentaRegresivaHandler != null && cuentaRegresivaRunnable != null) {
            cuentaRegresivaHandler.removeCallbacks(cuentaRegresivaRunnable);
        }

        // Crear un nuevo Handler y Runnable para la cuenta regresiva
        cuentaRegresivaHandler = new Handler();
        cuentaRegresivaRunnable = new Runnable() {
            int tiempoRestante = TIEMPO_CUENTA_REGRESIVA;

            @Override
            public void run() {
                // Actualizar texto de la cuenta regresiva
                countdownTextView.setText(String.valueOf(tiempoRestante));

                // Verificar si el tiempo ha terminado
                if (tiempoRestante == 0)
                    // Mostrar mensaje de tiempo finalizado
                    mostrarMensajeTiempoFinalizado();
                else {
                    // Reducir tiempo restante y continuar la cuenta regresiva
                    tiempoRestante--;

                    // Llamar recursivamente después de 1 segundo
                    cuentaRegresivaHandler.postDelayed(this, 1000);
                }
            }
        };

        // Iniciar la cuenta regresiva
        cuentaRegresivaHandler.postDelayed(cuentaRegresivaRunnable, 1000);
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

    private void mostrarResultadoFinal() {
        // Crear diálogo de alerta para mostrar el resultado final
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Juego Terminado")
                .setMessage("Has completado el juego con " + correctas + " respuestas correctas.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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
