package developers.apus.abecedario.constantes;

import java.lang.reflect.Field;
import java.util.TreeMap;

import developers.apus.abecedario.R;

/**
 * Created by Miguel on 09/05/2016.
 */
public class SonidosId {
    private static TreeMap<String, Integer> ids;

    public static void init(){
        ids = new TreeMap<>();
        Field[] raws = R.raw.class.getFields();
        R.raw rawResources = new R.raw();
        for (Field f : raws) {
            try {
                ids.put(f.getName(),f.getInt(rawResources));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getRawId(String nombre){
        return ids.get(nombre);
    }
}
