package com.isunican.appocio.Vista;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.isunican.appocio.Modelo.Evento;
import com.isunican.appocio.R;


/*
------------------------------------------------------------------
    Vista de detalle

    Presenta datos de detalle de un Evento concreto.
    El evento a mostrar se le pasa directamente a la actividad
    en la llamada por intent (usando putExtra / getExtra)
    Para ello Evento implementa la interfaz Parcelable
------------------------------------------------------------------
*/
public class VistaDetalle extends AppCompatActivity {

    TextView textView;
    Evento e;

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
        setContentView(R.layout.activity_detail);

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.otros);

        // captura el TextView
        // obtiene el objeto Evento a mostrar
        // y lo introduce en el TextView convertido a cadena de texto
        textView = findViewById(R.id.textView);
        e = getIntent().getExtras().getParcelable(getResources().getString(R.string.pasoDatosEvento));
        textView.setText(e.toString());

    }
}