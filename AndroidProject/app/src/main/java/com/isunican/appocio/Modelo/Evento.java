package com.isunican.appocio.Modelo;

import android.os.Parcel;
import android.os.Parcelable;


/*
------------------------------------------------------------------
    Clase que almacena la informacion de un evento de ocio
    Implementa la interfaz Parceable, que permite que luego podamos
    pasar objetos de este tipo entre activities a traves de una llamada intent
------------------------------------------------------------------
*/

public class Evento implements Parcelable {
    private int identificador;
    private String nombre;
    private String nombreAlternativo;
    private String categoria;
    private String descripcion;
    private String descripcionAlternativa;
    private String fecha;
    private double longitud;
    private double latitud;
    private String enlace;
    private String enlaceAlternativo;
    private String imagen;

    /**
     * Constructor, getters y setters
     */
    public Evento(int identificador, String nombre, String nombreAlternativo, String categoria, String descripcion, String descripcionAlternativa, String fecha, double longitud, double latitud, String enlace, String enlaceAlternativo, String imagen){

        this.identificador = identificador;
        this.nombre = nombre;
        this.nombreAlternativo = nombreAlternativo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.descripcionAlternativa = descripcionAlternativa;
        this.fecha = fecha;
        this.longitud = longitud;
        this.latitud = latitud;
        this.enlace = enlace;
        this.enlaceAlternativo = enlaceAlternativo;
        this.imagen = imagen;
    }

    public Evento(){

        this.identificador = 0;
        this.nombre = "";
        this.nombreAlternativo = "";
        this.categoria = "";
        this.descripcion = "";
        this.descripcionAlternativa = "";
        this.fecha = "";
        this.longitud = 0.0;
        this.latitud = 0.0;
        this.enlace = "";
        this.enlaceAlternativo = "";
        this.imagen = "";
    }

    public int getIdentificador() { return identificador; }
    public void setIdentificador(int identificador) { this.identificador = identificador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombreAlternativo() { return nombreAlternativo; }
    public void setNombreAlternativo(String nombreAlternativo) { this.nombreAlternativo = nombreAlternativo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDescripcionAlternativa() { return descripcionAlternativa; }
    public void setDescripcionAlternativa(String descripcionAlternativa) { this.descripcionAlternativa = descripcionAlternativa; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public String getEnlace() { return enlace; }
    public void setEnlace(String enlace) { this.enlace = enlace; }

    public String getEnlaceAlternativo() { return enlaceAlternativo; }
    public void setEnlaceAlternativo(String enlaceAlternativo) { this.enlaceAlternativo = enlaceAlternativo; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }



    /**
     * toString
     *
     * Redefine el método toString para obtener los datos
     * de una Evento en formato texto
     *
     * @param
     * @return String
     */
    @Override
    public String toString(){
        String textoEvento = "";
        textoEvento +=
                "Id: " + getIdentificador() + "\n"+
                "Nombre: " + getNombre() + "\n" +
                "Nombre alternativo: " + getNombreAlternativo() + "\n" +
                "Categoría: " + getCategoria() + "\n" +
                "Fecha: " + getFecha() + "\n" +
                "Localización: " + getLongitud() + " " + getLatitud() + "\n" +
                "Enlace: " + getEnlace() + "\n" +
                "Enlace alternativo: " + getEnlaceAlternativo() + "\n" +
                "Imagen: " + getImagen() + "\n" +
                "Descripción: " + getDescripcion() + "\n" +
                "Descripción alternativa: " + getDescripcionAlternativa() + "\n\n";

        return textoEvento;
    }


    /**
     * interfaz Parcelable
     *
     * Métodos necesarios para implementar la interfaz Parcelable
     * que nos permitirá pasar objetos del tipo Evento
     * directamente entre actividades utilizando intents
     * Se enviarían utilizando putExtra
     * myIntent.putExtra("id", objeto evento);
     * y recibiéndolos con
     * Evento g = getIntent().getExtras().getParcelable("id")
     */
    protected Evento(Parcel in) {
        identificador = in.readInt();
        nombre = in.readString();
        nombreAlternativo = in.readString();
        categoria = in.readString();
        descripcion = in.readString();
        descripcionAlternativa = in.readString();
        fecha = in.readString();
        longitud = in.readDouble();
        latitud = in.readDouble();
        enlace = in.readString();
        enlaceAlternativo = in.readString();
        imagen = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(identificador);
        dest.writeString(nombre);
        dest.writeString(nombreAlternativo);
        dest.writeString(categoria);
        dest.writeString(descripcion);
        dest.writeString(descripcionAlternativa);
        dest.writeString(fecha);
        dest.writeDouble(longitud);
        dest.writeDouble(latitud);
        dest.writeString(enlace);
        dest.writeString(enlaceAlternativo);
        dest.writeString(imagen);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Evento> CREATOR = new Parcelable.Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };
}