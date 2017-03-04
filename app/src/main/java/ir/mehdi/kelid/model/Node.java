package ir.mehdi.kelid.model;

import java.util.Vector;


public class Node {
    public int level;
    public String feature;
    public String name;
    public int id;
    public Vector<PropertyDetail> pDetail = new Vector<>();
    public int parent_id;
    public Node parent;
    public Vector<Node> childs=new Vector<>();
    public String path="";


}
