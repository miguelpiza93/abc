package developers.apus.abecedario.actividades;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;


import developers.apus.abecedario.R;
import developers.apus.abecedario.constantes.ImagenesId;
import developers.apus.abecedario.utilidades.Splash;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            public void run() {
                ImagenesId.init();
            }
        }).start();

        int tiempo = 1000; // en milisegundos
        Splash.mostrarSplash(tiempo, this, MainActivity.class);


    }
}
