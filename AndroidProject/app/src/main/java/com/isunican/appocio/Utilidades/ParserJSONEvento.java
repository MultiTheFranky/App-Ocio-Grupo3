package com.isunican.appocio.Utilidades;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.isunican.appocio.Modelo.Evento;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/*
------------------------------------------------------------------
    Clase con los metodos necesarios para
    parsear un stream de datos en formato JSON
    en este caso, un stream con eventos de ocio
------------------------------------------------------------------
*/

public class ParserJSONEvento {

    /**
     * parseaArrayEvento
     *
     * Se le pasa un stream de datos en formato JSON que contiene eventos de ocio.
     * Crea un JsonReader para el stream de datos y llama a un método auxiliar que lo analiza
     * y extrae una lista de objetos Evento que es la que se devuelve
     *
     * @param in Inputsream Stream de datos JSON
     * @return List<Evento> Lista de objetos evento con los datos obtenidas tras parsear el JSON
     * @throws IOException
     */
    public static List<Evento> parseaArrayEventos (InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readArrayEventos(reader);
        } finally {
            reader.close();
        }
    }

    /**
     * readArrayEventos
     *
     * Se le pasa un objeto JsonReader con el stream de datos JSON a analizar.
     * Crea una lista de eventos.
     * Va leyendo elementos hasta encontrar la cabecera "summary"
     * ya que de ella cuelga el array de eventos.
     * Mientras haya elementos los analiza con un método auxiliar
     * que procesa los datos de un evento concreto
     * y devuelve un objeto Evento que añadimos a la lista de eventos.
     * Finalmente se devuelve la lista de eventos.
     *
     * @param in JsonReader Stream de datos JSON apuntando al comienzo del stream
     * @return List Lista de objetos Evento con los datos obtenidas tras parsear el JSON
     * @throws IOException
     */
    public static List readArrayEventos (JsonReader reader) throws IOException {
        List<Evento> listaEventos = new ArrayList<>();

        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            Log.d("ENTRA", "Nombre del elemento: "+name);

            if(name.equals("resources")){
                reader.beginArray();
                while (reader.hasNext()){
                    listaEventos.add(readEvento(reader));
                }
                reader.endArray();
            }else{
                reader.skipValue();
            }


        }
        reader.endObject();
        return listaEventos;
    }

    /**
     * readEvento
     *
     * Se le pasa un objeto JsonReader con el stream de datos JSON a analizar
     * que está apuntando a un evento concreto.
     * Va procesando este stream buscando las etiquetas de cada elemento
     * que se desea extraer, como "nombre", "descripcion" o "fecha"
     * y almacena la cadena de su valor en un atributo del tipo adecuado,
     * parseándolo a entero, doble, etc. si es necesario.
     * Una vez extraidos todos los atributos, crea un objeto Evento con ellos
     * y lo devuelve.
     *
     * @param in JsonReader stream de datos JSON
     * @return Evento Objetos Evento con los datos obtenidas tras parsear el JSON
     * @throws IOException
     */
    public static Evento readEvento (JsonReader reader) throws IOException {

        Evento evento = new Evento();
        reader.beginObject();

        while(reader.hasNext()){
            String name = reader.nextName();

//TODO: if peek y jsontoken, pasar a switch
            if (name.equals("dc:name") && reader.peek() != JsonToken.NULL) {
                evento.setNombre(reader.nextString());

            }else if (name.equals("ayto:alt-name") && reader.peek() != JsonToken.NULL) {
                evento.setNombreAlternativo(reader.nextString());

            }else if (name.equals("ayto:categoria") && reader.peek() != JsonToken.NULL) {
                evento.setCategoria(reader.nextString());

            }else if (name.equals("ayto:lon") && reader.peek() != JsonToken.NULL) {
                evento.setLongitud(Double.parseDouble(reader.nextString()));

            }else if (name.equals("ayto:link") && reader.peek() != JsonToken.NULL) {
                evento.setEnlace(reader.nextString());

            }else if (name.equals("dc:description") && reader.peek() != JsonToken.NULL) {
                evento.setDescripcion(reader.nextString());

            }else if (name.equals("dc:identifier") && reader.peek() != JsonToken.NULL) {
                evento.setIdentificador(Integer.parseInt(reader.nextString()));

            }else if (name.equals("ayto:imagen") && reader.peek() != JsonToken.NULL) {
                evento.setImagen(reader.nextString());

            }else if (name.equals("ayto:datetime") && reader.peek() != JsonToken.NULL) {
                evento.setFecha(reader.nextString());

            }else if (name.equals("ayto:alt-link") && reader.peek() != JsonToken.NULL) {
                evento.setEnlaceAlternativo(reader.nextString());

            }else if (name.equals("ayto:lat") && reader.peek() != JsonToken.NULL) {
                evento.setLatitud(Double.parseDouble(reader.nextString()));

            }else if (name.equals("ayto:alt-description") && reader.peek() != JsonToken.NULL) {
                evento.setDescripcionAlternativa(reader.nextString());

            }else{
                reader.skipValue();
            }

        }
        reader.endObject();

        return evento;
    }
}