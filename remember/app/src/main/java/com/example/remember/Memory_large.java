package com.example.remember;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memory_large extends AppCompatActivity {

    private static final String TAG = "Memory";
    private List<ImageButton> buttons;
    private List<MemoryCard> cards;
    private Integer indexOfSingleSelectedCard;
    private TextView countdownTextView;

    private CountDownTimer countDownTimer;
    private long timeRemaining; // Tiempo restante en milisegundos
    private long previousTimeElapsed; // Tiempo anterior pasado desde Memory normal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_large);

        ImageView ayuda = findViewById(R.id.ayuda);
        ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Memory_large.this, info_memory.class);
                startActivity(intent);
            }
        });

        countdownTextView = findViewById(R.id.coutdown);
        startCountdown();

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.hamstermemory);
        images.add(R.drawable.applememory);
        images.add(R.drawable.relojmemory);
        images.add(R.drawable.montserrat_memory);
        images.add(R.drawable.ball);
        images.add(R.drawable.telephone);
        images.add(R.drawable.ferrero);
        images.add(R.drawable.thunder);

        List<Integer> pairedImages = new ArrayList<>(images);
        images.addAll(pairedImages);

        // Randomize the order of images
        Collections.shuffle(images);

        buttons = new ArrayList<>();
        buttons.add(findViewById(R.id.imageButton1));
        buttons.add(findViewById(R.id.imageButton2));
        buttons.add(findViewById(R.id.imageButton3));
        buttons.add(findViewById(R.id.imageButton4));
        buttons.add(findViewById(R.id.imageButton5));
        buttons.add(findViewById(R.id.imageButton6));
        buttons.add(findViewById(R.id.imageButton7));
        buttons.add(findViewById(R.id.imageButton8));
        buttons.add(findViewById(R.id.imageButton9));
        buttons.add(findViewById(R.id.imageButton10));
        buttons.add(findViewById(R.id.imageButton11));
        buttons.add(findViewById(R.id.imageButton12));
        buttons.add(findViewById(R.id.imageButton13));
        buttons.add(findViewById(R.id.imageButton14));
        buttons.add(findViewById(R.id.imageButton15));
        buttons.add(findViewById(R.id.imageButton16));

        cards = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i++) {
            cards.add(new MemoryCard(images.get(i)));
        }

        for (int i = 0; i < buttons.size(); i++) {
            final int index = i;
            buttons.get(i).setOnClickListener(v -> {
                Log.i(TAG, "click");
                updateModels(index);
                updateViews();
            });
        }

        // Recibir el tiempo pasado desde Memory_mid
        previousTimeElapsed = getIntent().getLongExtra("timeElapsed", 0);
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished; // Guardar el tiempo restante
                countdownTextView.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdownTextView.setText("0");
                showGameOverDialog();
            }
        }.start();
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Tiempo agotado!");
        builder.setMessage("¡Has perdido! ¿Quieres intentarlo de nuevo?");
        builder.setPositiveButton("No", (dialog, which) -> {
            Intent intent = new Intent(Memory_large.this, Menu_paciente.class);
            startActivity(intent);
        });
        builder.setNegativeButton("Sí", (dialog, which) -> {
            recreate();
        });
        builder.setCancelable(false); // Evitar que el diálogo se cierre al hacer clic fuera de él
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateViews() {
        for (int i = 0; i < cards.size(); i++) {
            ImageButton button = buttons.get(i);
            MemoryCard card = cards.get(i);
            if (card.isMatched()) {
                button.setAlpha(0.1f);
            }
            button.setImageResource(card.isFaceUp() ? card.getIdentifier() : R.drawable.fondocarta_memory);
        }
    }

    private void updateModels(int position) {
        MemoryCard card = cards.get(position);
        // Error checking:
        if (card.isFaceUp()) {
            Toast.makeText(this, "No es correcta", Toast.LENGTH_SHORT).show();
            return;
        }

        if (indexOfSingleSelectedCard == null) {
            restoreCards();
            indexOfSingleSelectedCard = position;
        } else {
            checkForMatch(indexOfSingleSelectedCard, position);
            indexOfSingleSelectedCard = null;
        }
        card.setFaceUp(!card.isFaceUp());
    }

    private void restoreCards() {
        for (MemoryCard card : cards) {
            if (!card.isMatched()) {
                card.setFaceUp(false);
            }
        }
    }

    private void checkForMatch(int position1, int position2) {
        if (cards.get(position1).getIdentifier() == cards.get(position2).getIdentifier()) {
            Toast.makeText(this, "Pareja", Toast.LENGTH_SHORT).show();
            cards.get(position1).setMatched(true);
            cards.get(position2).setMatched(true);

            boolean allMatched = true;
            for (MemoryCard card : cards) {
                if (!card.isMatched()) {
                    allMatched = false;
                    break;
                }
            }

            if (allMatched) {
                // Detener el temporizador
                countDownTimer.cancel();
                // Mostrar el diálogo de finalización
                showCompletionDialog();
            }
        }
    }

    private void showCompletionDialog() {
        // Calcular el tiempo tardado en segundos en esta fase
        long timeElapsedInSeconds = (120000 - timeRemaining) / 1000;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡MUY BIEN!");
        builder.setMessage("Juego en modo difícil completado en " + timeElapsedInSeconds + " segundos.\n" +
                "Tiempo total en la fase fácil: " + previousTimeElapsed + " segundos.\n" +
                "¿Quieres jugar otra vez?");
        builder.setPositiveButton("Sí", (dialog, which) -> {
            // Reiniciar la actividad actual para jugar de nuevo
            Intent intent = new Intent(Memory_large.this, Memory.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            // Volver al menú principal
            Intent intent = new Intent(Memory_large.this, Menu_paciente.class);
            startActivity(intent);
            finish();
        });
        builder.setCancelable(false); // Evitar que el diálogo se cierre al hacer clic fuera de él
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
