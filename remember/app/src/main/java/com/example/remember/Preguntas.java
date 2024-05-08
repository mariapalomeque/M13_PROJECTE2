package com.example.remember;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Preguntas extends AppCompatActivity {

    private int preguntaActual = 0;
    private TextView textViewContador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        ImageView ayuda=findViewById(R.id.ayuda);

        ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Preguntas.this, info_pregunta.class);
                startActivity(intent);
            }
        });

        // Asignar vistas
        ImageView imageView = findViewById(R.id.image_question);
        TextView textViewQuestion = findViewById(R.id.text_question);
        textViewContador = findViewById(R.id.contador);
        Button buttonAnswer1 = findViewById(R.id.button_answer1);
        Button buttonAnswer2 = findViewById(R.id.button_answer2);
        Button buttonAnswer3 = findViewById(R.id.button_answer3);
        Button buttonAnswer4 = findViewById(R.id.button_answer4);

        Button btnSalir = findViewById(R.id.button_back);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Preguntas.this, Menu_paciente.class);
                startActivity(intent);
            }
        });

        // Configurar pregunta y respuestas
        setQuestionAndAnswers(textViewQuestion, buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4);

        // Asignar listeners a los botones de respuesta
        buttonAnswer1.setOnClickListener(v -> checkAnswer(true));
        buttonAnswer2.setOnClickListener(v -> checkAnswer(false));
        buttonAnswer3.setOnClickListener(v -> checkAnswer(false));
        buttonAnswer4.setOnClickListener(v -> checkAnswer(false));
    }

    private void setQuestionAndAnswers(TextView textViewQuestion, Button... buttons) {
        switch (preguntaActual) {
            case 0:
                textViewQuestion.setText("¿Cuál es la capital de Francia?");
                buttons[0].setText("París"); // Respuesta correcta
                buttons[1].setText("Londres");
                buttons[2].setText("Roma");
                buttons[3].setText("Madrid");
                break;
            case 1:
                textViewQuestion.setText("¿Cuál es el río más largo del mundo?");
                buttons[0].setText("Amazonas"); // Respuesta correcta
                buttons[1].setText("Nilo");
                buttons[2].setText("Misisipi");
                buttons[3].setText("Yangtsé");
                break;
            case 2:
                textViewQuestion.setText("¿Como se llama tu hermano?");
                buttons[0].setText("Juan"); // Respuesta correcta
                buttons[1].setText("Antoniete");
                buttons[2].setText("Joselin");
                buttons[3].setText("Felipe");
                break;
            case 3:
                textViewQuestion.setText("¿Cuanto es 3+3?");
                buttons[0].setText("5"); // Respuesta correcta
                buttons[1].setText("2");
                buttons[2].setText("6");
                buttons[3].setText("9");
                break;
            case 4:
                textViewQuestion.setText("¿Donde naciste?");
                buttons[0].setText("Sitges"); // Respuesta correcta
                buttons[1].setText("Teruel");
                buttons[2].setText("Sudanell");
                buttons[3].setText("Albatarrec");
                break;
            default:
                // Si el contador alcanza 5, se mostrará un mensaje de éxito
                Toast.makeText(this, "¡Felicidades! Has respondido todas las preguntas correctamente.", Toast.LENGTH_SHORT).show();
                break;
        }
        updateCounter();
    }

    private void checkAnswer(boolean correct) {
        if (correct) {
            // Respuesta correcta
            Toast.makeText(this, "¡Respuesta correcta!", Toast.LENGTH_SHORT).show();
            preguntaActual++; // Incrementar el número de pregunta actual
            if (preguntaActual < 5) {
                // Mostrar la siguiente pregunta y respuestas
                setQuestionAndAnswers((TextView) findViewById(R.id.text_question),
                        (Button) findViewById(R.id.button_answer1),
                        (Button) findViewById(R.id.button_answer2),
                        (Button) findViewById(R.id.button_answer3),
                        (Button) findViewById(R.id.button_answer4));
            }
        } else {
            // Respuesta incorrecta
            Toast.makeText(this, "¡Respuesta incorrecta!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCounter() {
        // Actualizar el texto del contador
        textViewContador.setText(String.format("%d/5", preguntaActual + 1));
    }
}
