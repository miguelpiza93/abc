package developers.apus.abecedario.actividades;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import developers.apus.abecedario.R;

public class DeletreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletrea);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_li);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletreaActivity.this.finish();
            }
        });

        LinearLayout layoutLetras = (LinearLayout)findViewById(R.id.matriz_letras);
        inicializarLayoutLetras(layoutLetras);

    }

    private void inicializarLayoutLetras(LinearLayout layoutLetras) {

        final String[] letras = {"letras_01", "letras_02", "letras_03", "letras_04", "letras_05", "letras_06",
                "letras_07", "letras_08", "letras_09", "letras_10", "letras_11", "letras_12", "letras_13",
                "letras_14", "letras_15", "letras_16", "letras_17", "letras_18", "letras_19", "letras_20",
                "letras_21", "letras_22", "letras_23", "letras_24", "letras_25", "letras_26", "letras_27"};

        int indice = 0;
        int numeroFilas =  getResources().getInteger(R.integer.numero_filas_juego_deletrea);
        int numeroColumnas =  getResources().getInteger(R.integer.numero_columnas_juego_deletrea);
        float alto = getResources().getDimension(R.dimen.alto_matriz);

        Resources resources = getResources();
        String pckageName  =getPackageName();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) alto, 1.0f);

        for (int i = 0; i <numeroFilas; i++) {
            LinearLayout layoutAux = new LinearLayout(getApplicationContext());
            layoutAux.setOrientation(LinearLayout.HORIZONTAL);
            layoutAux.setWeightSum(numeroColumnas);
            for (int j = 0; j < numeroColumnas; j++) {
                ImageView letraAct = new ImageView(getApplicationContext());
                String nombre = "";
                if(indice < letras.length){
                    nombre = letras[indice];
                    final int finalIndice = indice;
                    letraAct.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), letras[finalIndice], Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    nombre = "cuadro_blanco";
                }
                int resourceId = resources.getIdentifier(nombre, "drawable", pckageName);
                letraAct.setImageResource(resourceId);
                letraAct.setLayoutParams(params);
                layoutAux.addView(letraAct);
                indice++;
            }
            layoutAux.setLayoutParams(params);
            layoutLetras.addView(layoutAux);
        }
    }
}
