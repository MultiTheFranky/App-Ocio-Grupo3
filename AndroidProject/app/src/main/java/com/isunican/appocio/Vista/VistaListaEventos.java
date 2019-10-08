package com.isunican.appocio.Vista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.isunican.appocio.Modelo.Evento;
import com.isunican.appocio.Presentador.PresentadorEventos;
import com.isunican.appocio.R;

import java.util.ArrayList;
import java.util.List;


/*
------------------------------------------------------------------
    Vista principal

    Presenta los datos en formato lista.

------------------------------------------------------------------
*/
public class VistaListaEventos extends AppCompatActivity {

    PresentadorEventos presentadorEventos;

    // Vista de lista y adaptador para cargar datos en ella
    ListView listViewEventos;
    ArrayAdapter<Evento> adapter;

    // Dialogo para mostrar el progreso de obtencion de datos de las gasolineras
    ProgressBar progressBar;

    Toast toast;

    /**
     * onCreate
     *
     * Crea los elementos que conforman la actividad
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.presentadorEventos = new PresentadorEventos();

        // Barra de progreso
        // https://materialdoc.com/components/progress/
        progressBar = new ProgressBar(VistaListaEventos.this,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout layout = findViewById(R.id.activity_lista_eventos);
        layout.addView(progressBar,params);

        // Muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.otros);

        // Al terminar de inicializar todas las variables
        // se lanza una tarea para cargar los datos
        // Esto se ha de hacer en segundo plano definiendo una tarea asíncrona
        new CargaDatosEventosTask(this).execute();

    }


    /**
     * Menú action bar
     *
     * Redefine métodos para el uso de un menú de tipo action bar.
     *
     * onCreateOptionsMenu
     * Carga las opciones del menú a partir del fichero de recursos menu/menu.xml
     *
     * onOptionsItemSelected
     * Define las respuestas a las distintas opciones del menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.itemActualizar){
            new CargaDatosEventosTask(this).execute();
        }
        else if(item.getItemId()==R.id.itemInfo){
            Intent myIntent = new Intent(VistaListaEventos.this, VistaAcercaDe.class);
            VistaListaEventos.this.startActivity(myIntent);
            }
        return true;
    }


    /**
     * CargaDatosEventosTask
     *
     * Tarea asincrona para obtener los datos en segundo plano.
     *
     * Redefinimos varios métodos que se ejecutan en el siguiente orden:
     * onPreExecute: activamos el dialogo de progreso
     * doInBackground: solicitamos que el presenter cargue los datos
     * onPostExecute: desactiva el dialogo de progreso,
     *    muestra los elementos en formato lista (a partir de un adapter)
     *    y define la acción al realizar al seleccionar alguna de ellas
     *
     * http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-i-thread-y-asynctask/
     */
    private class CargaDatosEventosTask extends AsyncTask<Void, Void, Boolean> {

        Activity activity;

        /**
         * Constructor de la tarea asincrona
         * @param activity
         */
        public CargaDatosEventosTask(Activity activity) {
            this.activity = activity;
        }

        /**
         * onPreExecute
         *
         * Metodo ejecutado de forma previa a la ejecucion de la tarea definida en el metodo doInBackground()
         * Muestra un diálogo de progreso
         */
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar
        }

        /**
         * doInBackground
         *
         * Tarea ejecutada en segundo plano
         * Llama al presenter para que lance el método de carga de los datos
         * @param params
         * @return boolean
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            return presentadorEventos.cargaDatosEventos();
        }

        /**
         * onPostExecute
         *
         * Se ejecuta al finalizar doInBackground
         * Oculta el diálogo de progreso.
         * Muestra en una lista los datos cargados,
         * creando un adapter y pasándoselo a la lista.
         * Define el manejo de la selección de los elementos de la lista,
         * lanzando con una intent una actividad de detalle
         * a la que pasamos un objeto Evento
         *
         * @param res
         */
        @Override
        protected void onPostExecute(Boolean res) {

            // Si el progressDialog estaba activado, lo oculta
            progressBar.setVisibility(View.GONE);     // To Hide ProgressBar

            // Si se ha obtenido resultado en la tarea en segundo plano
            if (res) {
                // Definimos el array adapter
                adapter = new EventoArrayAdapter(activity, 0, (ArrayList<Evento>) presentadorEventos.getEventos());

                // Obtenemos la vista de la lista
                listViewEventos = findViewById(R.id.listViewEventos);

                // Cargamos los datos en la lista
                if (!presentadorEventos.getEventos().isEmpty()) {
                    // datos obtenidos con exito
                    listViewEventos.setAdapter(adapter);
                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_exito), Toast.LENGTH_LONG);
                } else {
                    // los datos estan siendo actualizados en el servidor, por lo que no son actualmente accesibles
                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_no_accesibles), Toast.LENGTH_LONG);
                }
            } else {
                // error en la obtencion de datos desde el servidor
                toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_no_obtenidos), Toast.LENGTH_LONG);
            }

            // Muestra el mensaje del resultado de la operación en un toast
            ((TextView) toast.getView().findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
            toast.show();

            /*
             * Define el manejo de los eventos de click sobre elementos de la lista
             * En este caso, al pulsar un elemento se lanzará una actividad con una vista de detalle
             * a la que le pasamos el objeto Evento sobre el que se pulsó, para que en el
             * destino tenga todos los datos que necesita para mostrar.
             * Para poder pasar un objeto Evento mediante una intent con putExtra / getExtra,
             * hemos tenido que hacer que el objeto Evento implemente la interfaz Parcelable
             */
            listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                    /* Obtengo el elemento directamente de su posicion,
                     * ya que es la misma que ocupa en la lista
                     * Alternativa 1: a partir de posicion obtener algun atributo int opcionSeleccionada = ((Evento) a.getItemAtPosition(position)).getIdentifier();
                     * Alternativa 2: a partir de la vista obtener algun atributo String opcionSeleccionada = ((TextView)v.findViewById(R.id.textViewIdentificador)).getText().toString();
                     */
                    Intent myIntent = new Intent(VistaListaEventos.this, VistaDetalle.class);
                    myIntent.putExtra(getResources().getString(R.string.pasoDatosEvento),
                            presentadorEventos.getEventos().get(position));
                    VistaListaEventos.this.startActivity(myIntent);

                }
            });
        }
    }


    /*
    ------------------------------------------------------------------
        EventoArrayAdapter

        Adaptador para inyectar los datos de los eventos
        en el listview del layout principal de la aplicacion
    ------------------------------------------------------------------
    */
    class EventoArrayAdapter extends ArrayAdapter<Evento> {

        private Context context;
        private List<Evento> listaEventos;

        // Constructor
        public EventoArrayAdapter(Context context, int resource, List<Evento> objects) {
            super(context, resource, objects);
            this.context = context;
            this.listaEventos = objects;
        }

        // Llamado al renderizar la lista
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Obtiene el elemento que se está mostrando
            Evento evento = listaEventos.get(position);

            // Indica el layout a usar en cada elemento de la lista
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_evento, null);

            // Asocia las variables de dicho layout
            ImageView categoria = view.findViewById(R.id.imageViewCategoria);
            TextView fecha = view.findViewById(R.id.textViewFecha);
            TextView nombre = view.findViewById(R.id.textViewNombre);

            // Y carga los datos del item
            fecha.setText(evento.getFecha());
            nombre.setText(evento.getNombre());

            // Para la categoría, toma el string y lo procesa
            // eliminando espacios, tildes y símbolos
            // de forma que el nombre resultante corresponda al de un recurso imagen.png
            String cadenaCategoria = evento.getCategoria().toLowerCase();
            cadenaCategoria = cadenaCategoria.replaceAll(" ","");
            cadenaCategoria = cadenaCategoria.replaceAll("á","a");
            cadenaCategoria = cadenaCategoria.replaceAll("é","e");
            cadenaCategoria = cadenaCategoria.replaceAll("í","i");
            cadenaCategoria = cadenaCategoria.replaceAll("ó","o");
            cadenaCategoria = cadenaCategoria.replaceAll("ú","u");
            cadenaCategoria = cadenaCategoria.replaceAll("/","");

            int imageID = context.getResources().getIdentifier(cadenaCategoria,
                    "drawable", context.getPackageName());
            if (imageID == 0) {
                imageID = context.getResources().getIdentifier(getResources().getString(R.string.categoriaOtros),
                        "drawable", context.getPackageName());
            }
            categoria.setImageResource(imageID);

            return view;
        }


    }

}