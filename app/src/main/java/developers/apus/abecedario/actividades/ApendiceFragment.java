package developers.apus.abecedario.actividades;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import developers.apus.abecedario.R;
import developers.apus.abecedario.adapters.ItemAdapterLetra;
import developers.apus.abecedario.adapters.ItemLetra;
import developers.apus.abecedario.clases.Imagen;
import developers.apus.abecedario.clases.Juego;
import developers.apus.abecedario.clases.Letra;
import developers.apus.abecedario.constantes.SonidosId;
import developers.apus.abecedario.interfaces.IAdapterComunication;

public class ApendiceFragment extends AppCompatActivity implements IAdapterComunication {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private static Juego juego;
    private MediaPlayer sonido;

    public static void setJuego(Juego juego) {
        if(ApendiceFragment.juego == null)
            ApendiceFragment.juego = juego;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apendice);

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recicladorApendice);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        try {
            List<Letra> letras = juego.getLetras();
            List<ItemLetra> itemLetras = new ArrayList<>();
            for(int i=0; i<letras.size(); i++)
            {
                Letra letra = letras.get(i);
                ItemLetra item = new ItemLetra(letra.getId());
                itemLetras.add(item);
                for (int j =0; j<letra.getImagenes().size(); j++ )
                {
                    Imagen image = letra.getImagenes().get(j);
                    item = new ItemLetra(image.getNombre());
                    itemLetras.add(item);
                }
            }
            adapter = new ItemAdapterLetra(ApendiceFragment.this, itemLetras );
            recycler.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   @Override
    public void callBack(String juego) {
       sonido = MediaPlayer.create(ApendiceFragment.this, SonidosId.getRawId(juego));
       sonido.start();
    }
}
