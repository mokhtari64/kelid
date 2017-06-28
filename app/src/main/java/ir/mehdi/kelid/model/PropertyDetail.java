package ir.mehdi.kelid.model;

/**
 * Created by Mahdi on 06/27/2016.
 */
public class PropertyDetail {
    public int id , tag ;
    public String name;
    public boolean selected;
    public PropertyCategory category;

    @Override
    public boolean equals(Object obj) {
        return id==((PropertyDetail)obj).id;
    }
}
