package developers.apus.abecedario.clases;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import developers.apus.abecedario.constantes.TipoJuego;
import developers.apus.abecedario.excepciones.JuegoTerminadoException;

/**
 * Created by Miguel on 23/02/2016.
 */
public class Juego {

    private List<Letra> letras;

    private TipoJuego juego;

    private int puntos;

    private boolean inicio;

    private Letra actual;

    private List<Imagen> opciones;

    public Juego(JSONObject diccionario){
        letras = new ArrayList<>();
        Iterator<String> keys = diccionario.keys();
        while( keys.hasNext()){
            String key = keys.next();
            List<Imagen> imagenes = new ArrayList<>();
            try {
                JSONArray array = diccionario.getJSONArray(key);
                for (int i = 0; i < array.length(); i++){
                    Imagen imagen = new Imagen(array.getString(i), null);//TODO hacer set del sonido
                    imagenes.add(imagen);
                }

                Letra letra = new Letra(key, null, imagenes);//TODO hacer set del sonido
                letras.add(letra);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(letras, new Comparator<Letra>() {
            @Override
            public int compare(Letra a, Letra b) {
                return a.getId().compareTo(b.getId());
            }
        });
    }

    public Letra getLetraAleatoria() throws JuegoTerminadoException {
        List<Letra> copia =new ArrayList<>();
        Collections.copy(copia, letras);
        Letra letra;

        boolean encontrada = false;
        Random random = new Random();

        do {
            int id  = random.nextInt(letras.size());
            letra = letras.get(id);
            if(!letra.isMostrada()){
                encontrada = true;
            }
            else {
                copia.remove(id);
            }
        }
        while(!encontrada && !copia.isEmpty());
        if(encontrada) {
            actual = letra;
            return letra;
        }
        else
            throw new JuegoTerminadoException();

    }

    public Letra getSiguienteLetra() throws JuegoTerminadoException {
        Log.i("Juego", "inicio getSiguienteLetra");
        Letra letra = null;
        boolean encontrada = false;
        for (int i = 0; i < letras.size() && !encontrada; i++) {
            if(!letras.get(i).isMostrada()){
                letra = letras.get(i);
                encontrada = true;
            }
        }
        Log.i("Juego", "fin getSiguienteLetra");
        if(encontrada){
            actual = letra;
            return letra;
        }
        else{
            throw new JuegoTerminadoException();
        }
    }

    public List<Imagen> generarOpciones() {
        Log.i("Juego", "inicio generarOpciones");
        opciones = new ArrayList<>();

        opciones.add(actual.getImagenAleatoria());
        Random random = new Random();
        boolean completo = false;
        ArrayList<Integer> sacados = new ArrayList<>();
        while(!completo){
            int i = random.nextInt(letras.size());
            Letra act = letras.get(i);
            if( !act.getId().equals(actual.getId()) && !sacados.contains(i) ){
                opciones.add(act.getImagenAleatoria());
                sacados.add(i);
            }
            if(sacados.size()== 3){
                completo = true;
            }
        }
        Collections.shuffle(opciones);
        Log.i("Juego", "fin generarOpciones");
        return opciones;
    }

    public boolean verificarRespuesta(int imagen){

        boolean correcto = opciones.get(imagen).getNombre().startsWith(actual.getId());
        if(correcto){
            actual.mostrar();
        }
        return correcto;
    }

    public List<Letra> getLetras() {
        return letras;
    }

    public void reiniciar(){
        actual = null;
        opciones = null;
        for (Letra letra :
                letras) {
            letra.init();
        }
    }

    public Letra getActual() {
        return actual;
    }

    public List<Imagen> getOpciones() {
        return opciones;
    }
}
