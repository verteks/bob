package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupProduct extends Model {
    @Id
    private String name;
    private ArrayList<Product> list;

    public GroupProduct(String name){
        this.name = name;
        list = new ArrayList<Product>();
    }
    public static Model.Finder<String, GroupProduct> find = new Model.Finder<String,GroupProduct>(
            String.class, GroupProduct.class
    );
    public static GroupProduct find(String name) {
        return find.ref(name);
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void addProduct(Product product){
       list.add(product);
    }
    public void deleteProduct(Product product){
        list.remove(product);
    }
    public ArrayList<Product> allProduct(){
        return list;

    }
    public static List<GroupProduct> all() {
        return find.all();
    }

}
