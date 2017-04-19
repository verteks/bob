package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.awt.*;
import java.util.List;
import java.util.UUID;
@Entity
public class  Product extends Model{
    @Id
    private UUID id;
    @Required
    private String name;
    private String description;
    private double cost;
    private double amount;
    private Image image;

    public static Finder<UUID, Product> find = new Finder<UUID, Product>(
            UUID.class, Product.class
    );
    public Product(String name){
        this.name = name;
        this.save();
    }
    public static List<Product> all() {
        return find.all();
    }
    public static void delete(UUID id){
        find.ref(id).delete();
    }

    public double getAmount() {
        return amount;
    }

    public double getCost() {
        return cost;
    }

    public Image getImage() {
        return image;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
