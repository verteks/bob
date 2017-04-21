function Product(id, name, description, cost, amount) {
    var self = this;
    self.name = ko.observable(name);
    self.description = ko.observable(description);
    self.cost = ko.observable(cost);
    self.amount = ko.observable(amount);
    self.id = id;
}
function ProductsViewModel() {
    var self = this;

    self.products = ko.observableArray([]);

    self.id = ko.observable(null);
    self.name = ko.observable("");
    self.description = ko.observable("");
    self.amount = ko.observable(0);
    self.cost = ko.observable(0);

    self.load = function () {

        jsRoutes.controllers.CMS.productsJson().ajax({
            dataType: 'json',
            success: function (data) {
                console.log("Успешно обработан json запрос. Записи загружены");
                for (i = 0; i < data.length; i++) {
                    self.products.push(new Product(data[i].id, data[i].name, data[i].description, data[i].cost, data[i].amount));
                }
            },
            error: function (data) {
                alert("error! " + data.error);
                console.log('error! Не могу отправить json запрос');
                console.log(data);
            }
        });
    };

    self.reload = function () {
        self.products.destroyAll();
        self.load();
    };
    self.cleanForm = function () {
        self.name("");
        self.description("");
        self.cost(0);
        self.amount(0);
        self.id(null)
    };
    self.editProduct = function (product) {
        console.log(product);
        self.id(product.id);
        self.name(product.name());
        self.description(product.description());
        self.amount(product.amount());
        self.cost(product.cost());
    };
    self.removeProduct = function (product) {
        var jsonData = ko.toJSON(product);
        console.log(jsonData);
        jsRoutes.controllers.CMS.deleteProductJson().ajax({
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: jsonData,
            success: function () {
                console.log("Успешно обработан ajax запрос. Запись удалена");
                self.products.remove(product);
            },
            error: function (data) {
                alert(data.responseText);
                console.log('error! Не могу отправить json запрос');
                console.log(data);
            }
        });


    };
    self.saveProduct = function () {
        var product = new Product();
        product.id = self.id();
        product.name(self.name());
        product.description(self.description());
        product.cost(self.cost());
        product.amount(self.amount());
        console.log("hellobithes");
        var jsonData = ko.toJSON(product);
        console.log(jsonData);

        jsRoutes.controllers.CMS.saveProductJson().ajax({
            dataType: 'json',   //
            contentType: 'application/json; charset=utf-8',
            data: jsonData,
            success: function (data) {
                console.log("Успешно обработан ajax запрос. Запись добавлена в DB");
                console.log(data);

                if (product.id == null){
                    var isNew = true;}
                else
                {
                    var isNew = false;
                }
                //todo флаг, является ли данная запись в форме - новой записью. Если редактируемая - выдает false

                if (isNew) { /*создание нового */
                    product.id = data.id;
                    self.products.push(product);
                } else {
                    //todo редактирование - ищем и обновляем
                    console.log(self.products());
                    for (i = 0; i < self.products().length; i++) {
                        var found = false; //todo исправить на корректное условие
                        if (product.id == self.products()[i].id) {
                            self.products()[i].name(data.name);
                            self.products()[i].description(data.description);
                            self.products()[i].cost(data.cost);
                            self.products()[i].amount(data.amount);

                            console.log(self.products()[i]);
                            break;
                        }
                    }
                }

                self.cleanForm();
            },    error: function (data) {
                alert(data.responseText);
                console.log('error! Не могу отправить json запрос');
                console.log(data);
            }
            //todo обработка ошибок от сервера
        });
    };

    self.load();
}
//создаем экземпляр ViewModel
var productViewModel = new ProductsViewModel();
//Запускаем Knockout.JS. Организовываем связывание Model с View через ViewModel
ko.applyBindings(productViewModel);