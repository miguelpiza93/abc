package developers.apus.abecedario.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import developers.apus.abecedario.PruebaActivity;
import developers.apus.abecedario.R;
import developers.apus.abecedario.adapters.ItemAdapter;
import developers.apus.abecedario.adapters.Juegos;
import developers.apus.abecedario.clases.Imagen;
import developers.apus.abecedario.clases.Juego;
import developers.apus.abecedario.clases.Letra;
import developers.apus.abecedario.constantes.TipoJuego;
import developers.apus.abecedario.interfaces.IAdapterComunication;
import developers.apus.abecedario.utilidades.Json;
import developers.apus.abecedario.utilidades.Util;

/**
 * Created by Miguel on 24/02/2016.
 */
public class CatalogoJuegosFragment extends Fragment implements IAdapterComunication {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Juego juego;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.catalogo_fragment, container, false);

        JSONObject diccionario = null;
        try {
            String rutaDiccionario = getResources().getString(R.string.diccionario);
            diccionario = new JSONObject(Util.leerArchivo(rutaDiccionario, rootView.getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Obtener el Recycler
        recycler = (RecyclerView) rootView.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        String json = Util.leerArchivo(getResources().getString(R.string.juegos), rootView.getContext());

        try {
            Juegos juegos = Json.json2Object(json, Juegos.class);
            adapter = new ItemAdapter(CatalogoJuegosFragment.this, juegos.getJuegos());
            recycler.setAdapter(adapter);

            juego = new Juego(diccionario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void callBack(String juego) {
        Intent intent = null;
        this.juego.reiniciar();

        if(juego.toUpperCase().equals(TipoJuego.LETRA_INICIAL)){
            LetraInicialActivity.setJuego(this.juego);
            intent = new Intent(getActivity(), LetraInicialActivity.class);
        }
        else if(juego.toUpperCase().equals(TipoJuego.DELETREA)){
            DeletreaActivity.setJuego(this.juego);
            intent = new Intent(getActivity(), DeletreaActivity.class);
        }
        else if(juego.toUpperCase().equals(TipoJuego.APENDICE)){
            ApendiceFragment.setJuego(this.juego);
            intent = new Intent(getActivity(), ApendiceFragment.class);
        }
        else if(juego.toUpperCase().equals(TipoJuego.Item_de_prueba)){
            ArrayList<Imagen> imagenes = new ArrayList<>();

            List<Letra> letras = this.juego.getLetras();
            for (Letra l: letras ) {
                imagenes.addAll(l.getImagenes());
            }
            PruebaActivity.opciones = imagenes;
            intent = new Intent(getActivity(), PruebaActivity.class);
        }
        if( intent != null)
            startActivity(intent);
    }
}