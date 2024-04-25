package com.example.remember;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Pacientes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdaptadorPaciente adaptadorPaciente;
    private List<ObjetoPaciente> listaObjetoPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pacientes);

        recyclerView = findViewById(R.id.recyclerViewPacientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaObjetoPaciente = new ArrayList<>();
        listaObjetoPaciente.add(new ObjetoPaciente("Felipe Gonzalez", R.drawable.paciente1));
        listaObjetoPaciente.add(new ObjetoPaciente("Juan Huerta", R.drawable.paciente2));
        listaObjetoPaciente.add(new ObjetoPaciente("Amparo  Martinez", R.drawable.paciente3));
        listaObjetoPaciente.add(new ObjetoPaciente("Lucas Perez", R.drawable.paciente4));
        listaObjetoPaciente.add(new ObjetoPaciente("Soldedad  Ramirez", R.drawable.paciente5));
        listaObjetoPaciente.add(new ObjetoPaciente("Adela Sanchez", R.drawable.paciente6));

        adaptadorPaciente = new AdaptadorPaciente(listaObjetoPaciente);
        recyclerView.setAdapter(adaptadorPaciente);

    }


}
