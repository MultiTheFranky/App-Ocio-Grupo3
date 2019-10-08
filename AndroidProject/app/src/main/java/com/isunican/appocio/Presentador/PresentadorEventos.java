package com.isunican.appocio.Presentador;

import android.util.Log;

import com.isunican.appocio.Modelo.*;
import com.isunican.appocio.Utilidades.ParserJSONEvento;
import com.isunican.appocio.Utilidades.RemoteFetch;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

/*
------------------------------------------------------------------
    Clase presenter con la logica
    Mantiene un objeto ModelEventos que es el que mantendrá
    los datos cargados en nuestra aplicación
    Incluye métodos para gestionar la lista y cargar datos en ella.
------------------------------------------------------------------
*/
public class PresentadorEventos {

    private List<Evento> eventos;

    //URLs para obtener los datos
    public static final String URL_OCIO="http://datos.santander.es/api/rest/datasets/agenda_cultural.json";
    public static final String URL_OCIO_INTRANET="https://personales.unican.es/blancobc/recursos/agenda_cultural.json";

    /**
     * Constructor, getters y setters
     */
    public PresentadorEventos(){
        this.eventos = new ArrayList<>();
    }

    public List<Evento> getEventos(){ return eventos; }
    public void setEventos(List<Evento> l){ this.eventos = l; }



    /**
     * cargaDatosEventos
     *
     * Carga los datos de los eventos en la lista de la clase.
     * Para ello llama a métodos de carga de datos internos de la clase ModelEventos.
     * En este caso realiza una carga de datos remotos dada una URL
     *
     * @return boolean Devuelve true si se han podido cargar los datos
     */
    public boolean cargaDatosEventos() {
        return cargaDatosRemotos(URL_OCIO_INTRANET);
    }

    /**
     * cargaDatosDummy
     *
     * Carga en la eventos de eventos varios definidos a "mano"
     * para hacer pruebas de funcionamiento
     *
     * @param
     * @return boolean
     */
    private boolean cargaDatosDummy(){
        this.eventos.add(new Evento(1, "Feria vehiculo de ocasion", "nombre alt", "compras", "descripcion", "descripcionAlternativa", "3-6/11/2019", 0.0, 0.0, "enlace", "enlaceAlternativo", "imagen"));
        this.eventos.add(new Evento(2, "Concierto Bustamante", "nombre alt", "musica", "descripcion", "descripcionAlternativa", "30/11/2019", 0.0, 0.0, "enlace", "enlaceAlternativo", "imagen"));
        return true;
    }

    /**
     * cargaDatosLocales
     *
     * A partir de la dirección de un fichero JSON pasado como parámetro:
     * Parsea la información para obtener una eventos de eventos.
     * Finalmente, dicha eventos queda almacenada en la clase.
     *
     * @param String Nombre del fichero
     * @return boolean Devuelve true si se han podido cargar los datos
     */
    private boolean cargaDatosLocales(String fichero){
        return(fichero != null);
    }


    /**
     * cargaDatosRemotos
     *
     * A partir de la dirección pasada como parámetro:
     * Utiliza RemoteFetch para cargar el fichero JSON ubicado en dicha URL
     * en un stream de datos.
     * Luego utiliza ParserJSONEvento para parsear dicho stream
     * y extraer una eventos de objetos.
     * Finalmente, dicha eventos queda almacenada en la clase.
     *
     * @param String Dirección URL del JSON con los datos
     * @return boolean Devuelve true si se han podido cargar los datos
     */
    private boolean cargaDatosRemotos(String direccion){
        try {
            BufferedInputStream buffer = RemoteFetch.cargaBufferDesdeURL(direccion);
            eventos = ParserJSONEvento.parseaArrayEventos(buffer);
            Log.d("ENTRA", "Obten eventos:" + eventos.size());
            return true;
        } catch (Exception e) {
            Log.e("ERROR", "Error en la obtención de eventos: " + e.getMessage());
            return false;
        }
    }

}
