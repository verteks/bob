package controllers;

import models.Product;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;

import views.html.*;

public class Application extends Controller {
    static Form<Product> ProductForm = Form.form(Product.class);
    public static Result index() {
        return redirect(routes.Application.products());
    }

    public static Result products() {
        return ok(
                views.html.index.render(Product.all(), ProductForm)
        );
    }

    public static Result newProduct() {
//        Form<Notebook> filledForm = notebookForm.bindFromRequest();
//        if(filledForm.hasErrors()) {
//            return badRequest(
//                    views.html.index.render(Notebook.all(), filledForm)
//            );
//        } else {
//            Notebook.create(filledForm.get());
//            return redirect(routes.Application.notebook());
//        }
        return TODO;
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

