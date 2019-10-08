package com.isunican.appocio.Vista;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.isunican.appocio.R;


/*
------------------------------------------------------------------
    Vista de información

    Presenta datos de información de la aplicación.
------------------------------------------------------------------
*/
public class VistaAcercaDe extends AppCompatActivity {

    TextView textView;

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
        setContentView(R.layout.activity_info);

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.otros);

        // captura el TextView
        // y muestra en él un texto de información predefinido
        textView = findViewById(R.id.textView);
        textView.setText(getResources().getString(R.string.infoTexto));
    }
}
