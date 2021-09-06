"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.ProductAllComponent = void 0;
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var sweetalert2_1 = require("sweetalert2");
var ProductAllComponent = /** @class */ (function () {
    function ProductAllComponent(productService, router, route) {
        var _this = this;
        this.productService = productService;
        this.router = router;
        this.route = route;
        this.keywordProductAll = "";
        this.keywordEnableTrue = "";
        this.keywordEnableFalse = "";
        this.keywordQty0 = "";
        this.size = 5;
        this.sizeEnableTrue = 5;
        this.sizeEnableFalse = 5;
        this.sizeQuantity0 = 5;
        this.page = [];
        this.pageEnableTrue = [];
        this.pageEnableFalse = [];
        this.pageQty0 = [];
        this.listProductOption = {};
        this.p = 1;
        // page filter enable true
        this.pEnableTrue = 1;
        // page filter enable false
        this.pEnableFalse = 1;
        // page filter quantity 0
        this.pQty0 = 1;
        this.items = [];
        this.itemsEnableTrue = [];
        this.itemsEnableFalse = [];
        this.itemsQuantity0 = [];
        this.countOrder = "";
        this.router.events.subscribe(function (event) {
            if (event instanceof router_1.NavigationStart) {
                // Show loading indicator
            }
            if (event instanceof router_1.NavigationEnd) {
                _this.route.queryParams.subscribe(function (params) {
                    var getSize = params['size'];
                    var getPage = params['page'];
                    var getKeyword = params['keyword'];
                    if (getKeyword !== undefined) {
                        _this.keywordProductAll = getKeyword;
                    }
                    if (getPage !== undefined) {
                        _this.p = getPage;
                    }
                    if (getSize !== undefined) {
                        _this.size = getSize;
                    }
                });
            }
            if (event instanceof router_1.NavigationError) {
                // Hide loading indicator
                // Present error to user
                console.log(event.error);
            }
        });
    }
    ProductAllComponent.prototype.ngOnInit = function () {
        this.loadProductAll(this.keywordProductAll, this.p, this.size);
    };
    ProductAllComponent.prototype.filterEnableFalse = function () {
        this.loadProductEnableFalse("", false, 1, 5);
    };
    ProductAllComponent.prototype.filterEnableTrue = function () {
        this.loadProductEnableTrue("", true, 1, 5);
    };
    ProductAllComponent.prototype.filterQuantity0 = function () {
        this.loadProductQty0("", 1, 5);
    };
    ProductAllComponent.prototype.loadProductAll = function (keyword, page, size) {
        var _this = this;
        page = page - 1;
        this.productService.getAllProductOption(keyword, page, size).subscribe(function (data) {
            console.log(data);
            var item = data.content.map(function (obj) {
                return obj;
            });
            console.log(item);
            _this.items = item;
            _this.page = data;
            // this.arrays = [];
            _this.count = _this.page.totalElements;
            //this.arrays = Array(this.page.totalPages).fill(0).map((x, i) => ({ id: (i + 1), name: `Item ${i + 1}`}));
            _this.itemsEnableTrue = item;
            _this.itemsEnableFalse = item;
            _this.itemsQuantity0 = item;
            console.log(_this.itemsEnableTrue);
        }, function (err) {
            console.log("Chưa đăng nhập " + err.error);
            if (err.status == 401) {
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: "Chưa đăng nhập"
                });
                _this.router.navigate(['/login']);
            }
            else {
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: err.error.message
                });
            }
        });
    };
    // load all produt filter by quantity
    ProductAllComponent.prototype.loadProductQty0 = function (keyword, page, size) {
        var _this = this;
        page = page - 1;
        this.productService.getAllProductByQty0(keyword, page, size).subscribe(function (data) {
            _this.itemsQuantity0 = data.content.map(function (obj) {
                return obj;
            });
            console.log(_this.itemsQuantity0);
            _this.pageQty0 = data;
            console.log(_this.pageQty0);
            _this.countQty0 = _this.pageQty0.totalElements;
        }, function (err) {
            if (err.status == 401) {
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: "Chưa đăng nhập"
                });
                _this.router.navigate(['/login']);
            }
            else {
                console.log(err);
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: err.message
                });
            }
        });
    };
    // load all product filter by enable
    ProductAllComponent.prototype.loadProductEnableTrue = function (keyword, value, page, size) {
        var _this = this;
        page = page - 1;
        this.productService.getAllProductByEnable(keyword, value, page, size).subscribe(function (data) {
            if (value) {
                _this.itemsEnableTrue = data.content.map(function (obj) {
                    return obj;
                });
                console.log(_this.itemsEnableTrue);
                _this.pageEnableTrue = data;
                console.log(_this.pageEnableTrue);
                _this.countEnableTrue = _this.pageEnableTrue.totalElements;
            }
        }, function (error) {
            console.log(error);
            if (error.status == 404) {
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: "Chưa đăng nhập"
                });
                _this.router.navigate(['/login']);
            }
            else {
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: error.error.message
                });
            }
        });
    };
    ProductAllComponent.prototype.loadProductEnableFalse = function (keyword, value, page, size) {
        var _this = this;
        page = page - 1;
        this.productService.getAllProductByEnable(keyword, value, page, size).subscribe(function (data) {
            if (!value) {
                _this.itemsEnableFalse = data.content.map(function (obj) {
                    return obj;
                });
                console.log(_this.itemsEnableFalse);
                _this.pageEnableFalse = data;
                console.log(_this.pageEnableFalse);
                _this.countEnableFalse = _this.pageEnableFalse.totalElements;
            }
        }, function (error) {
            console.log(error);
            if (error.status == 401) {
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: "Chưa đăng nhập"
                });
                _this.router.navigate(['/login']);
            }
            else {
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: error.error.message
                });
            }
        });
    };
    ProductAllComponent.prototype.handlePageChange = function (event) {
        this.p = event;
        //this.router.navigate(["/shop/product/all?page=" + this.p+"&size="+this]);
        this.showParamsURL("", this.p, this.size);
        console.log("handlePageChange nè");
        this.loadProductAll(this.keywordProductAll, this.p, this.size);
    };
    ProductAllComponent.prototype.handlePageChangeEnableTrue = function (event) {
        this.pEnableTrue = event;
        console.log("pEnableTrue in handlePageChangeEnableTrue: " + this.pEnableTrue);
        this.showParamsURL("", this.pEnableTrue, this.sizeEnableTrue);
        this.loadProductEnableTrue(this.keywordEnableTrue, true, this.pEnableTrue, this.sizeEnableTrue);
    };
    ProductAllComponent.prototype.showParamsURL = function (keyword, page, size) {
        this.router.navigate([], {
            relativeTo: this.route,
            queryParams: this.getRequestParams(keyword, page, size),
            queryParamsHandling: 'merge'
        });
    };
    // action change page filter enable true
    ProductAllComponent.prototype.handlePageChangeEnableFalse = function (event) {
        this.pEnableFalse = event;
        this.showParamsURL("", this.pEnableFalse, this.sizeEnableFalse);
        this.loadProductEnableFalse(this.keywordEnableFalse, false, this.pEnableFalse, this.sizeEnableFalse);
    };
    // action change page filter quantity
    ProductAllComponent.prototype.handlePageChangeQty0 = function (event) {
        this.pQty0 = event;
        this.showParamsURL("", this.pQty0, this.sizeQuantity0);
        this.loadProductQty0(this.keywordQty0, this.pQty0, this.sizeQuantity0);
    };
    ProductAllComponent.prototype.getRequestParams = function (searchKeyword, page, pageSize) {
        var params = {};
        if (searchKeyword) {
            params["keyword"] = searchKeyword;
        }
        if (page) {
            params["page"] = page;
        }
        if (pageSize) {
            params["size"] = pageSize;
        }
        return params;
    };
    ProductAllComponent.prototype.changeSize = function (event) {
        this.p = 1;
        this.size = event.target.value;
        console.log("size nè: " + event.target.value);
        this.loadProductAll(this.keywordProductAll, this.p, this.size);
    };
    ProductAllComponent.prototype.changeSizeEnableTrue = function (event) {
        this.pEnableTrue = 1;
        this.sizeEnableTrue = event.target.value;
        console.log("size nè: " + event.target.value);
        this.loadProductEnableTrue(this.keywordEnableTrue, true, this.pEnableTrue, this.sizeEnableTrue);
    };
    ProductAllComponent.prototype.changeSizeEnableFalse = function (event) {
        this.pEnableFalse = 1;
        this.sizeEnableFalse = event.target.value;
        console.log("size nè: " + event.target.value);
        console.log("pEnableFalse in changeSizeEnableFalse: " + this.pEnableFalse);
        this.loadProductEnableFalse(this.keywordEnableFalse, false, this.pEnableFalse, this.sizeEnableFalse);
    };
    ProductAllComponent.prototype.changeSizeQuantity0 = function (event) {
        this.pQty0 = 1;
        this.sizeQuantity0 = event.target.value;
        console.log("size nè: " + event.target.value);
        console.log("pQty0 in changeSizeQuantity0: " + this.pQty0);
        this.loadProductQty0(this.keywordQty0, this.pQty0, this.sizeQuantity0);
    };
    ProductAllComponent.prototype.editProduct = function (id) {
        //routerLink="[`/shop/product/manager`,e.product.id,'id']"
        this.router.navigate(['shop/product/manager', id]);
    };
    ProductAllComponent.prototype.countProductOder = function (id) {
        var _this = this;
        this.productService.countProductOrder(id).subscribe(function (data) {
            _this.countOrder = data;
        });
    };
    ProductAllComponent.prototype.searchProductAll = function (keywordProductAll) {
        this.keywordProductAll = keywordProductAll;
        this.p = 1;
        console.log("keywordProductAll: " + this.keywordProductAll);
        this.showParamsURL(this.keywordProductAll, this.p, this.size);
        this.loadProductAll(this.keywordProductAll, this.p, this.size);
    };
    ProductAllComponent.prototype.searchEnableTrue = function (keywordEnableTrue) {
        this.keywordEnableTrue = keywordEnableTrue;
        this.pEnableTrue = 1;
        console.log("keywordEnableTrue: " + this.keywordEnableTrue);
        this.showParamsURL(this.keywordEnableTrue, this.pEnableTrue, this.sizeEnableTrue);
        this.loadProductEnableTrue(this.keywordEnableTrue, true, this.pEnableTrue, this.sizeEnableTrue);
    };
    ProductAllComponent.prototype.searchEnableFalse = function (keywordEnableFalse) {
        this.keywordEnableFalse = keywordEnableFalse;
        this.pEnableFalse = 1;
        console.log("keywordEnableFalse: " + this.keywordEnableFalse);
        this.showParamsURL(this.keywordEnableFalse, this.pEnableFalse, this.sizeEnableFalse);
        this.loadProductEnableFalse(this.keywordEnableFalse, false, this.pEnableFalse, this.sizeEnableFalse);
    };
    ProductAllComponent.prototype.searchQty0 = function (keywordQty0) {
        this.keywordQty0 = keywordQty0;
        this.pQty0 = 1;
        console.log("keywordQty0: " + this.keywordQty0);
        this.showParamsURL(this.keywordQty0, this.pQty0, this.sizeQuantity0);
        this.loadProductQty0(this.keywordQty0, this.pQty0, this.sizeQuantity0);
    };
    ProductAllComponent = __decorate([
        core_1.Component({
            selector: 'app-product-all',
            templateUrl: './product-all.component.html',
            styleUrls: ['./product-all.component.css']
        })
    ], ProductAllComponent);
    return ProductAllComponent;
}());
exports.ProductAllComponent = ProductAllComponent;
