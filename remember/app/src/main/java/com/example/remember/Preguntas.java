package com.example.remember;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Preguntas extends AppCompatActivity {

    private int preguntaActual = 0;
    private TextView textViewContador;
    private Button buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4, btnSalir;
    private Button correctButton;
    private List<Pregunta> listaPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        if (savedInstanceState != null) {
            preguntaActual = savedInstanceState.getInt("preguntaActual", 0);
            listaPreguntas = savedInstanceState.getParcelableArrayList("listaPreguntas");
        } else {
            listaPreguntas = crearListaDePreguntas();
            Collections.shuffle(listaPreguntas);
        }

        ImageView ayuda = findViewById(R.id.ayuda);
        ayuda.setOnClickListener(view -> {
            Intent intent = new Intent(Preguntas.this, info_pregunta.class);
            startActivity(intent);
        });

        textViewContador = findViewById(R.id.contador);
        buttonAnswer1 = findViewById(R.id.button_answer1);
        buttonAnswer2 = findViewById(R.id.button_answer2);
        buttonAnswer3 = findViewById(R.id.button_answer3);
        buttonAnswer4 = findViewById(R.id.button_answer4);
        btnSalir = findViewById(R.id.button_back);

        setQuestionAndAnswers();

        buttonAnswer1.setOnClickListener(this::onAnswerClick);
        buttonAnswer2.setOnClickListener(this::onAnswerClick);
        buttonAnswer3.setOnClickListener(this::onAnswerClick);
        buttonAnswer4.setOnClickListener(this::onAnswerClick);

        btnSalir.setOnClickListener(view -> {
            if (btnSalir.getText().toString().equals("Salir")) {
                Intent intent = new Intent(Preguntas.this, Menu_paciente.class);
                startActivity(intent);
            } else {
                preguntaActual++;
                if (preguntaActual < listaPreguntas.size()) {
                    resetButtons();
                    setQuestionAndAnswers();
                    btnSalir.setText("Salir");
                } else {
                    Intent intent = new Intent(Preguntas.this, Resultados.class);
                    intent.putExtra("numAciertos", numAciertos);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("preguntaActual", preguntaActual);
        outState.putParcelableArrayList("listaPreguntas", new ArrayList<>(listaPreguntas));
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        preguntaActual = savedInstanceState.getInt("preguntaActual", 0);
        listaPreguntas = savedInstanceState.getParcelableArrayList("listaPreguntas");
        resetButtons();
        setQuestionAndAnswers();
    }

    private void setQuestionAndAnswers() {
        Pregunta pregunta = listaPreguntas.get(preguntaActual);
        TextView textViewQuestion = findViewById(R.id.text_question);
        textViewQuestion.setText(pregunta.getPregunta());
        List<String> respuestas = pregunta.getRespuestas();
        buttonAnswer1.setText(respuestas.get(0));
        buttonAnswer2.setText(respuestas.get(1));
        buttonAnswer3.setText(respuestas.get(2));
        buttonAnswer4.setText(respuestas.get(3));
        correctButton = getButtonForCorrectAnswer(pregunta.getRespuestaCorrecta(), respuestas);
        updateCounter();
    }

    private Button getButtonForCorrectAnswer(String respuestaCorrecta, List<String> respuestas) {
        if (respuestaCorrecta.equals(respuestas.get(0))) {
            return buttonAnswer1;
        } else if (respuestaCorrecta.equals(respuestas.get(1))) {
            return buttonAnswer2;
        } else if (respuestaCorrecta.equals(respuestas.get(2))) {
            return buttonAnswer3;
        } else {
            return buttonAnswer4;
        }
    }
    private int numAciertos = 0;

    private void onAnswerClick(View view) {
        Button clickedButton = (Button) view;
        if (clickedButton == correctButton) {
            clickedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            Toast.makeText(this, "¡Respuesta correcta!", Toast.LENGTH_SHORT).show();
            numAciertos++; // Incrementar contador de aciertos
        } else {
            clickedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            correctButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            Toast.makeText(this, "¡Respuesta incorrecta!", Toast.LENGTH_SHORT).show();
        }
        disableButtons();
        btnSalir.setText("Continuar");
    }


    private void disableButtons() {
        buttonAnswer1.setEnabled(false);
        buttonAnswer2.setEnabled(false);
        buttonAnswer3.setEnabled(false);
        buttonAnswer4.setEnabled(false);
    }

    private void resetButtons() {
        buttonAnswer1.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        buttonAnswer2.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        buttonAnswer3.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        buttonAnswer4.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        buttonAnswer1.setBackgroundColor(getResources().getColor(R.color.background));
        buttonAnswer2.setBackgroundColor(getResources().getColor(R.color.background));
        buttonAnswer3.setBackgroundColor(getResources().getColor(R.color.background));
        buttonAnswer4.setBackgroundColor(getResources().getColor(R.color.background));

        buttonAnswer1.setEnabled(true);
        buttonAnswer2.setEnabled(true);
        buttonAnswer3.setEnabled(true);
        buttonAnswer4.setEnabled(true);
    }



    private void updateCounter() {
        textViewContador.setText(String.format("%d/5", preguntaActual + 1));
    }

    private List<Pregunta> crearListaDePreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new Pregunta("¿Cuál es la capital de Francia?", "París", "Londres", "Roma", "Madrid"));
        preguntas.add(new Pregunta("¿Cuál es el río más largo del mundo?", "Amazonas", "Nilo", "Misisipi", "Yangtsé"));
        preguntas.add(new Pregunta("¿En qué año terminó la Segunda Guerra Mundial?", "1945", "1969", "1989", "1939"));
        preguntas.add(new Pregunta("¿Cuánto es 3+3?", "6", "5", "2", "9"));
        preguntas.add(new Pregunta("¿Qué famoso científico formuló la teoría de la relatividad?", "B) Albert Einstein", "Isaac Newton", "Nikola Tesla", "Stephen Hawking"));
        preguntas.add(new Pregunta("¿Quién es el tenista con más títulos ganados en Roland Garros?", "Rafa Nadal", "Novak Djokovic", "Roger Federer", "Serena Williams"));
        preguntas.add(new Pregunta("¿Cuál es el océano más grande?", "Pacífico", "Atlántico", "Índico", "Ártico"));
        preguntas.add(new Pregunta("¿Cuál es el planeta más cercano al sol?", "Mercurio", "Venus", "Tierra", "Marte"));
        preguntas.add(new Pregunta("¿Cuál es la montaña más alta del mundo?", "Everest", "K2", "Makalu", "Kangchenjunga"));
        preguntas.add(new Pregunta("¿Cuál es el país más grande del mundo por territorio?", "Rusia", "Canadá", "Estados Unidos", "China"));
        Collections.shuffle(preguntas);
        return preguntas.subList(0, 5);
    }

    public static class Pregunta implements Parcelable {
        private final String pregunta;
        private final List<String> respuestas;
        private final String respuestaCorrecta;


        public Pregunta(String pregunta, String respuestaCorrecta, String... respuestasIncorrectas) {
            this.pregunta = pregunta;
            this.respuestaCorrecta = respuestaCorrecta;
            this.respuestas = new ArrayList<>();
            this.respuestas.add(respuestaCorrecta);
            Collections.addAll(this.respuestas, respuestasIncorrectas);
            Collections.shuffle(this.respuestas);
        }

        protected Pregunta(Parcel in) {
            pregunta = in.readString();
            respuestas = in.createStringArrayList();
            respuestaCorrecta = in.readString();
        }

        public static final Creator<Pregunta> CREATOR = new Creator<Pregunta>() {
            @Override
            public Pregunta createFromParcel(Parcel in) {
                return new Pregunta(in);
            }

            @Override
            public Pregunta[] newArray(int size) {
                return new Pregunta[size];
            }
        };

        public String getPregunta() {
            return pregunta;
        }

        public List<String> getRespuestas() {
            return respuestas;
        }

        public String getRespuestaCorrecta() {
            return respuestaCorrecta;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(pregunta);
            dest.writeStringList(respuestas);
            dest.writeString(respuestaCorrecta);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
