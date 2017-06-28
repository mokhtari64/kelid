package ir.mehdi.kelid.model;

import java.util.Vector;

/**
 * Created by admin on 28/06/2017.
 */

public class PropertyCategory {
   public int id;
   public String name;
   public Vector<PropertyDetail> properties=new Vector<>();

    public PropertyCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
