package com.example.remember;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class memory_mid extends AppCompatActivity {
    private long timeElapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_mid);

        Button btnmid = findViewById(R.id.btn_memory_mid);
        timeElapsed = getIntent().getLongExtra("timeElapsed", 0);
        btnmid.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(memory_mid.this, Memory_large.class);
                intent.putExtra("timeElapsed", timeElapsed);
                startActivity(intent);
                finish();
            }
        });


    }
}