package developers.apus.abecedario.constantes;

import java.lang.reflect.Field;
import java.util.TreeMap;

import developers.apus.abecedario.R;

/**
 * Created by Miguel on 08/03/2016.
 */
public class ImagenesId {

    private static TreeMap<String, Integer> ids;

    public static void init(){
        ids = new TreeMap<>();
        Field[] drawables = R.drawable.class.getFields();
        for (Field f : drawables) {
            try {
                if(!f.getName().equals("splash"))
                {
                    ids.put(f.getName(),f.getInt(null));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getDrawableId(String nombre){
        // "_" (Ñ) is not a valid resource name, so its files are named letra_enie.
        return ids.get("_".equals(nombre) ? "letra_enie" : nombre);
    }

}
