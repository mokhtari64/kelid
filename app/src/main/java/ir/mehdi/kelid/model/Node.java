package ir.mehdi.kelid.model;

import java.util.Vector;

/**
 * Created by iman on 6/14/2016.
 */
public class Node {
    public String name;
    public int id;
    public int parent_id;
    public String feature;
    public Node parent;
    public Vector<Node> childs=new Vector();
    public Vector<PropertyDetail> pDetail = new Vector<>();
    public String path="";

    @Override
    public String toString() {
        return name;
    }
}