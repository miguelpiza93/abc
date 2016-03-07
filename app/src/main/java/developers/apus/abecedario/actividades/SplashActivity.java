package developers.apus.abecedario.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


import developers.apus.abecedario.R;
import developers.apus.abecedario.utilidades.Splash;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        int tiempo = 1000; // en milisegundos
        Splash.mostrarSplash(tiempo, this, MainActivity.class);
    }
}
