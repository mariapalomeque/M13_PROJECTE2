package com.example.remember;

public class ObjetoPaciente {
    private String titulo;
    private int imagen;

    public ObjetoPaciente(String titulo, int imagenUrl) {
        this.titulo = titulo;
        this.imagen = imagenUrl;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagenUrl) {
        this.imagen = imagenUrl;
    }
}

