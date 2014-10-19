package com.aaron.zapatillas;

import android.graphics.Bitmap;

/**
 * Created by Aaron Perez on 17/10/2014.
 */
public class Zapatillas implements Comparable <Zapatillas> {
    private String modelo, caract, peso;
    private Bitmap marca;

    public Zapatillas() {
    }

    public Zapatillas(String modelo, String caract, String peso, Bitmap marca) {
        this.modelo = modelo;
        this.caract = caract;
        this.peso = peso;
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCaract() {
        return caract;
    }

    public void setCaract(String caract) {
        this.caract = caract;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Bitmap getMarca() {
        return marca;
    }

    public void setMarca(Bitmap marca) {
        this.marca = marca;
    }

    @Override
    public int compareTo(Zapatillas zapatillas) {
        return this.getModelo().toLowerCase().compareTo(zapatillas.getModelo().toLowerCase());
    }
}
