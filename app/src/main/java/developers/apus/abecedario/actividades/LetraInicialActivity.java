package developers.apus.abecedario.actividades;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import developers.apus.abecedario.R;
import developers.apus.abecedario.clases.Imagen;
import developers.apus.abecedario.clases.Juego;
import developers.apus.abecedario.clases.Letra;
import developers.apus.abecedario.excepciones.JuegoTerminadoException;

public class LetraInicialActivity extends AppCompatActivity implements View.OnClickListener {

    private static Juego juego;
    private Letra actual;
    private List<Imagen> opciones;
    private MediaPlayer correcto, incorrecto;


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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        incorrecto = MediaPlayer.create( this, R.raw.incorrecto );
        correcto = MediaPlayer.create( this, R.raw.correcto );

        actualizarLayout();

    }

    private void actualizarLayout() {
        try {
            actual = juego.getSiguienteLetra();
            ImageView letra = (ImageView)findViewById(R.id.letra);
            Resources resources = getResources();
            final int resourceId = resources.getIdentifier(actual.getId(), "drawable", getPackageName());
            letra.setImageResource(resourceId);

            opciones = juego.getOpciones(actual);

            ImageView opcion1 = (ImageView)findViewById(R.id.opcion1);
            final int opcion1Id = resources.getIdentifier(opciones.get(0).getNombre(), "drawable", getPackageName());
            opcion1.setImageResource(opcion1Id);

            ImageView opcion2 = (ImageView)findViewById(R.id.opcion2);
            final int opcion2Id = resources.getIdentifier(opciones.get(1).getNombre(), "drawable", getPackageName());
            opcion2.setImageResource(opcion2Id);

            ImageView opcion3 = (ImageView)findViewById(R.id.opcion3);
            final int opcion3Id = resources.getIdentifier(opciones.get(2).getNombre(), "drawable", getPackageName());
            opcion3.setImageResource(opcion3Id);

            ImageView opcion4 = (ImageView)findViewById(R.id.opcion4);
            final int opcion4Id = resources.getIdentifier(opciones.get(3).getNombre(), "drawable", getPackageName());
            opcion4.setImageResource(opcion4Id);


        } catch (JuegoTerminadoException e) {
            Toast.makeText(getApplicationContext(), "Has terminado el juego", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        boolean correcto = false;
        switch (v.getId()){
            case R.id.opcion1:
                correcto = juego.verificarRespuesta(actual, opciones.get(0));
                break;
            case R.id.opcion2:
                correcto = juego.verificarRespuesta(actual, opciones.get(1));
                break;
            case R.id.opcion3:
                correcto = juego.verificarRespuesta(actual, opciones.get(2));
                break;
            case R.id.opcion4:
                correcto = juego.verificarRespuesta(actual, opciones.get(3));
                break;
        }

        if(correcto){

            new Thread(new Runnable() {
                public void run() {
                    try {
                        LetraInicialActivity.this.correcto.start();
                    }
                    catch (IllegalStateException e){

                    }
                }
            }).start();

            actualizarLayout();
        }
        else{
            Vibrator vi = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 200 milliseconds
            vi.vibrate(200);

            try {
                this.incorrecto.start();
            }
            catch (IllegalStateException e){

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        juego.reiniciar();
    }
}
