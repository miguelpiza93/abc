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
        for (Field f : raws) {
            try {
                ids.put(f.getName(),f.getInt(null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getRawId(String nombre){
        // "_" (Ñ) is not a valid resource name, so its files are named letra_enie.
        Integer id = ids.get("_".equals(nombre) ? "letra_enie" : nombre);
        // 0 when there is no recording for this word yet.
        return id != null ? id : 0;
    }
}
