package developers.apus.abecedario.actividades;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import developers.apus.abecedario.R;
import developers.apus.abecedario.clases.Imagen;
import developers.apus.abecedario.clases.Juego;
import developers.apus.abecedario.constantes.ImagenesId;
import developers.apus.abecedario.excepciones.JuegoTerminadoException;

public class LetraInicialActivity extends AppCompatActivity implements View.OnClickListener {
    private static Juego juego;
    private MediaPlayer correcto, incorrecto, celebracion;

    public static void setJuego(Juego juego) {
        if(LetraInicialActivity.juego == null)
            LetraInicialActivity.juego = juego;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letra_inicial);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_li);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        catch (NullPointerException e){}

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LetraInicialActivity.this.finish();
            }
        });

        findViewById(R.id.opcion1).setOnClickListener(this);
        findViewById(R.id.opcion2).setOnClickListener(this);
        findViewById(R.id.opcion3).setOnClickListener(this);
        findViewById(R.id.opcion4).setOnClickListener(this);

        actualizarLayout();
    }

    @Override protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            public void run() {
                incorrecto = MediaPlayer.create( LetraInicialActivity.this, R.raw.incorrecto );
                correcto = MediaPlayer.create( LetraInicialActivity.this, R.raw.correcto );
            }
        }).start();
    }

    @Override protected void onPause() {
        correcto = null;
        incorrecto = null;
        super.onPause();
    }

    private void actualizarLayout() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (juego.getActual() == null) {
                    try {
                        juego.getSiguienteLetra();
                        juego.generarOpciones();
                    } catch (JuegoTerminadoException e) {
                    }
                }

                ImageView letra = (ImageView) findViewById(R.id.letra);
                letra.setImageResource(ImagenesId.getDrawableId(juego.getActual().getId()));

                List<Imagen> opciones = juego.getOpciones();

                ImageView opcion1 = (ImageView) findViewById(R.id.opcion1);
                opcion1.setImageResource(ImagenesId.getDrawableId(opciones.get(0).getNombre()));

                ImageView opcion2 = (ImageView) findViewById(R.id.opcion2);
                opcion2.setImageResource(ImagenesId.getDrawableId(opciones.get(1).getNombre()));

                ImageView opcion3 = (ImageView) findViewById(R.id.opcion3);
                opcion3.setImageResource(ImagenesId.getDrawableId(opciones.get(2).getNombre()));

                ImageView opcion4 = (ImageView) findViewById(R.id.opcion4);
                opcion4.setImageResource(ImagenesId.getDrawableId(opciones.get(3).getNombre()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        boolean correcto = false;
        switch (v.getId()){
            case R.id.opcion1:
                correcto = juego.verificarRespuesta(0);
                break;
            case R.id.opcion2:
                correcto = juego.verificarRespuesta(1);
                break;
            case R.id.opcion3:
                correcto = juego.verificarRespuesta(2);
                break;
            case R.id.opcion4:
                correcto = juego.verificarRespuesta(3);
                break;
        }

        if(correcto){
            try {
                juego.getSiguienteLetra();
                juego.generarOpciones();
                actualizarLayout();
                LetraInicialActivity.this.correcto.start();
            } catch (JuegoTerminadoException e) {
                Toast.makeText(this, "Has terminado el juego", Toast.LENGTH_SHORT)
                        .show();
                try {
                    celebracion = MediaPlayer.create(LetraInicialActivity.this, R.raw.celebracion);
                    celebracion.start();
                } catch (IllegalStateException e2) { }
                LetraInicialActivity.this.finish();
            }
        }
        else{
            Vibrator vi = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 200 milliseconds
            vi.vibrate(200);

            try {
                this.incorrecto.start();
            }
            catch (IllegalStateException e){ }
        }
    }
}
