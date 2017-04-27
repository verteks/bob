package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Product;
import play.Routes;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import play.mvc.Security;
import util.Secured;

import java.util.List;
import java.util.UUID;

import static play.libs.Json.toJson;
import static play.mvc.Controller.request;
import static play.mvc.Controller.response;
import static play.mvc.Results.*;

/**
 * Created by vshir on 20.04.2017.
 */
@Security.Authenticated(Secured.class)
public class CMS {

        static Form<Product> productForm = Form.form(Product.class);

        public static Result index() {
            return redirect(controllers.routes.CMS.products());
        }
        public static Result products() {
            return ok(
                    views.html.content.render(productForm)
            );
        }
        private static Result errorJsonResult(String errorMessage) {
            return badRequest(errorJson(errorMessage));
        }

        private static JsonNode errorJson(String errorMessage) {
            return Json.newObject().put("error", errorMessage);
        }

        public static Result productsJson() {
            List<Product> all = Product.all();
            return ok(toJson(all));
        }
        @BodyParser.Of(BodyParser.Json.class)
        public static Result deleteProductJson() {
            play.Logger.info("deleteProductJson()");
            JsonNode json = request().body().asJson();
            if (json == null) {
              return errorJsonResult("Json expected");
            } else {
                UUID id = null;
                try {
                    if (json.findPath("id").asText() != "null") {
                        id = UUID.fromString(json.findPath("id").asText());
                    }
                } catch (IllegalArgumentException nfe) {
                    return errorJsonResult("wrong type id");
                } finally {
//                    if (id == null) {
//                        play.Logger.info(json.findPath("id").asText().toString());
//                        return errorJsonResult("id must be specified");
//                    }
                }
                assert(id!=null);

                Product product = Product.find(id);
                play.Logger.debug(product.toString());
                if (product == null) {
                    return notFound(errorJson("product is not found"));
                }
                JsonNode result = Json.toJson(product);
                product.delete(id);
                return ok(result);
            }
        }
        @BodyParser.Of(BodyParser.Json.class)
        public static Result saveProductJson() {
            play.Logger.info("saveProductJson");
            JsonNode json = request().body().asJson();
            if (json == null) {
                return errorJsonResult("JSON expected");
            } else {
                UUID id = null; //todo
                try {
                    if (json.findPath("id").asText() != "null") {
                        id = UUID.fromString(json.findPath("id").asText());
                    }
                } catch (IllegalArgumentException nfe) {
                    return errorJsonResult("wrong type id");
                } finally {
//                    if (id == null) {
//                        play.Logger.info(json.findPath("id").asText().toString());
//                        return errorJsonResult("id must be specified");
//                    }
                }
                Product product = null;
                if (id != null) {
                    product = Product.find(id);
                }
                if (product == null) {
                    if (json.findPath("name").asText().length()>2){
                        product = new Product(json.findPath("name").asText());
                    }else{
                        return errorJsonResult("name must be longer then 2 symbols");
                    }
                }
                product.setAmount(json.findPath("amount").asDouble());
                product.setCost(json.findPath("cost").asDouble());
                product.setDescription(json.findPath("description").asText());
                play.Logger.info("trying to save to DB");
                product.save();
                return ok(Json.toJson(product));
            }
        }
        public static Result jsRoutes() {
            response().setContentType("text/javascript");
            return ok(
                    Routes.javascriptRouter("jsRoutes",
                            controllers.routes.javascript.CMS.products(),
                            controllers.routes.javascript.CMS.productsJson(),
                            controllers.routes.javascript.CMS.saveProductJson(),
                            controllers.routes.javascript.CMS.deleteProductJson()
            )
            );
        }
    }

