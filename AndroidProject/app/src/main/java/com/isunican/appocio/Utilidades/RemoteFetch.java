package com.isunican.appocio.Utilidades;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/*
------------------------------------------------------------------
    Clase que realiza la descarga de datos remotos
    Mantiene un stream con los datos en crudo
    e incluye un método para cargarlos desde una dirección URL
------------------------------------------------------------------
*/

public class RemoteFetch {

    /**
     * cargaBufferDesdeURL
     *
     * Toma la dirección pasada como parámetro y retorna los datos accesibles en esa dirección
     *
     * @param in String Dirección web con los datos a descargar
     * @return void
     * @throws IOException
     */
    public static BufferedInputStream cargaBufferDesdeURL(final String direccion) throws IOException {
        URL url = new URL(direccion);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.addRequestProperty("Accept", "application/json");
        return new BufferedInputStream(urlConnection.getInputStream());
    }

}
