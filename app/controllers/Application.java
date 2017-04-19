package controllers;

import models.Product;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;

public class Application extends Controller {
    static Form<Product> productForm = Form.form(Product.class);
    public static Result index() {
        return redirect(routes.Application.products());
    }

    public static Result products() {
        return ok(
                views.html.index.render(Product.all(), productForm)
        );
    }

    public static Result newProduct() {
        Form<Product> filledForm = productForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.index.render(Product.all(), filledForm)
            );
        } else {
            Product product = new Product(filledForm.get().getName());
            product.setDescription(filledForm.get().getDescription());
            product.setCost(filledForm.get().getCost());
            product.setAmount(filledForm.get().getAmount());
            product.save();
            return redirect(routes.Application.products());
        }
    }

    public static Result deleteProduct(UUID id) {
        Product.delete(id);
        return redirect(routes.Application.products());

    }
}






//
//    public static Result tasks() {
//        return ok(
//                views.html.index.render(Task.all(), taskForm)
//        );
//    }
//
//    public static Result newTask() {
//
//        Form<Task> filledForm = taskForm.bindFromRequest();
//        if(filledForm.hasErrors()) {
//            return badRequest(
//                    views.html.index.render(Task.all(), filledForm)
//            );
//        } else {
//            Task.create(filledForm.get());
//            return redirect(routes.Application.tasks());
//        }
//    }
//
//    public static Result deleteTask(Long id) {
//        Task.delete(id);
//        return redirect(routes.Application.tasks());
//    }

