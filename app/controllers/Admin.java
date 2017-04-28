package controllers;

import controllers.*;
import models.GroupProduct;
import models.Product;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;


public class Admin extends Controller {
    static Form<Product> productForm = Form.form(Product.class);
    public static Result index() {
        return redirect(routes.Admin.dashboars());
    }

    public static Result dashboars() {
        return ok(
                views.html.admin.index.render()
        );
    }

    public static Result newProduct() {
        return ok(
                views.html.admin.newProduct.render(productForm)
        );
    }
    public static Result saveProduct() {
        Form<Product> filledForm = productForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.admin.newProduct.render(filledForm)
            );
        } else {
            Product product = new Product(filledForm.get().getName());
            product.setDescription(filledForm.get().getDescription());
            product.setCost(filledForm.get().getCost());
            product.setAmount(filledForm.get().getAmount());
            product.save();
            return redirect(routes.Admin.newProduct());
        }
    }

    public static Result products() {
        return ok(
                views.html.admin.products.render(Product.all())
        );
    }



}

