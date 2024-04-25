package com.example.remember;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class AdaptadorPaciente extends RecyclerView.Adapter<AdaptadorPaciente.ObjetoPacienteViewHolder> {

    private List<ObjetoPaciente> listaObjetosPaciente;

    public AdaptadorPaciente(List<ObjetoPaciente> listaObjetosPaciente) {
        this.listaObjetosPaciente = listaObjetosPaciente;
    }

    public static class ObjetoPacienteViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ObjetoPacienteViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imagenPaciente);
            textView = view.findViewById(R.id.tituloPaciente);
        }
    }

    @Override
    public ObjetoPacienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objeto_paciente, parent, false);
        return new ObjetoPacienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ObjetoPacienteViewHolder holder, int position) {
        ObjetoPaciente objetoPaciente = listaObjetosPaciente.get(position);
        holder.textView.setText(objetoPaciente.getTitulo());

        Glide.with(holder.imageView.getContext())
                .load(objetoPaciente.getImagen())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listaObjetosPaciente.size();
    }
}
