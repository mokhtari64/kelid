package ir.mehdi.kelid.model;

/**
 * Created by iman on 6/14/2016.
 */
public class Region implements Comparable<Region>{
    public int cityid;
    public String name;
    public int id;

    @Override
    public String toString() {
        return name;
    }


    @Override
    public int compareTo(Region another) {
        return name.compareTo(another.name);
    }
}
