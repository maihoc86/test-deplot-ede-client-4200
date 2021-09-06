"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.ProductShopComponent = void 0;
var core_1 = require("@angular/core");
var sweetalert2_1 = require("sweetalert2");
var keycodes_1 = require("@angular/cdk/keycodes");
var forms_1 = require("@angular/forms");
var moment = require("moment");
var ProductShopComponent = /** @class */ (function () {
    function ProductShopComponent(router, route, Addservice, AddresseService, cookieService, headerService, imageService) {
        this.router = router;
        this.route = route;
        this.Addservice = Addservice;
        this.AddresseService = AddresseService;
        this.cookieService = cookieService;
        this.headerService = headerService;
        this.imageService = imageService;
        // TODO: Thêm TAG
        this.selectable = true;
        this.removable = true;
        this.addOnBlur = true;
        this.separatorKeysCodes = [keycodes_1.ENTER, keycodes_1.COMMA];
        this.tags = [];
        this.minDate = moment(new Date()).format('YYYY-MM-DD');
        this.maxDate = moment(new Date(2023, 1, 1)).format('YYYY-MM-DD');
        this.images = [];
        this.imageArray = [];
        this.imageArrayDelete = [];
        this.tagArray = [];
        this.isHiddenEndDate = true;
        this.isHiddenChildParent = true;
        this.isHiddenChild = true;
        this.isHiddenDiscount = true;
        this.listChildCategory = [];
        this.listParent_ChildCategory = [];
        this.listParentCategory = [];
        this.listBrands = [];
        this.listCountry = [];
        this.listCities = [];
        this.selectedFiles = [];
        this.product = new forms_1.FormGroup({
            id: new forms_1.FormControl(''),
            origin: new forms_1.FormControl('', [forms_1.Validators.required]),
            location: new forms_1.FormControl('', [forms_1.Validators.required]),
            name: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,25}[ \\-\\']{0,}){2,25}$"),
            ]),
            description: new forms_1.FormControl(''),
            enable: new forms_1.FormControl('true'),
            deleted: new forms_1.FormControl('false'),
            brand: new forms_1.FormControl('', forms_1.Validators.required),
            child_category: new forms_1.FormControl('', forms_1.Validators.required),
            parent_category: new forms_1.FormControl('', forms_1.Validators.required),
            parent_child_category: new forms_1.FormControl('', forms_1.Validators.required)
        });
        this.product_options = new forms_1.FormGroup({
            id: new forms_1.FormControl(''),
            file: new forms_1.FormControl(''),
            display_name: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,25}[ \\-\\']{0,}){1,25}$"),
            ]),
            price: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern('([0-9]{0,9})\\b'),
            ]),
            size: new forms_1.FormControl(''),
            quantity: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern('([0-9]{0,4})\\b'),
            ]),
            product: new forms_1.FormControl(''),
            is_delete: new forms_1.FormControl(false)
        });
        this.product_options_image = new forms_1.FormGroup({
            id: new forms_1.FormControl(''),
            productoption: new forms_1.FormControl(''),
            image: new forms_1.FormControl('')
        });
        this.product_discount = new forms_1.FormGroup({
            id: new forms_1.FormControl(''),
            productdiscount: new forms_1.FormControl(''),
            discount: new forms_1.FormControl('', forms_1.Validators.required),
            startdate: new forms_1.FormControl('', forms_1.Validators.required),
            enddate: new forms_1.FormControl('', forms_1.Validators.required)
        });
        this.product_tags = new forms_1.FormGroup({
            id: new forms_1.FormControl(''),
            producttag: new forms_1.FormControl(''),
            tag: new forms_1.FormControl('')
        });
        this.sizeGroups = [
            {
                name: 'Size số',
                size: [
                    { value: 'so_31', viewValue: '31' },
                    { value: 'so_32', viewValue: '32' },
                    { value: 'so_33', viewValue: '33' },
                    { value: 'so_34', viewValue: '34' },
                    { value: 'so_35', viewValue: '35' },
                    { value: 'so_36', viewValue: '36' },
                    { value: 'so_37', viewValue: '37' },
                    { value: 'so_38', viewValue: '38' },
                    { value: 'so_39', viewValue: '39' },
                    { value: 'so_40', viewValue: '40' },
                    { value: 'so_41', viewValue: '41' },
                    { value: 'so_42', viewValue: '42' },
                    { value: 'so_43', viewValue: '43' },
                    { value: 'so_44', viewValue: '44' },
                    { value: 'so_45', viewValue: '45' },
                ]
            },
            {
                name: 'Size chữ',
                size: [
                    { value: 'chu_s', viewValue: 'S' },
                    { value: 'chu_m', viewValue: 'M' },
                    { value: 'chu_L', viewValue: 'L' },
                    { value: 'chu_XL', viewValue: 'XL' },
                    { value: 'chu_XX', viewValue: 'XXL' },
                    { value: 'chu_XXXL', viewValue: 'XXXL' },
                ]
            },
        ];
        this.blobToFile = function (theBlob, fileName) {
            var b = theBlob;
            //A Blob() is almost a File() - it's just missing the two properties below which we will add
            b.lastModifiedDate = new Date();
            b.name = fileName;
            //Cast to a File() type
            return theBlob;
        };
        this.objectComparisonFunction = function (option, value) {
            return option.id === value.id;
        };
    }
    ProductShopComponent.prototype.ngOnInit = function () {
        this.getBrands();
        this.getParentCategory();
        this.getCountry();
        this.getCities();
        this.getProductById();
    };
    ProductShopComponent.prototype.createDataProduct = function () {
        var newProduct = {};
        for (var controlName in this.product.controls) {
            if (controlName) {
                newProduct[controlName] = this.product.controls[controlName].value;
            }
        }
        return newProduct;
    };
    ProductShopComponent.prototype.createNewOption = function () {
        var newOption = {};
        for (var controlName in this.product_options.controls) {
            if (controlName) {
                newOption[controlName] =
                    this.product_options.controls[controlName].value;
            }
        }
        return newOption;
    };
    ProductShopComponent.prototype.createNewOptionImage = function () {
        var newProduct = {};
        for (var controlName in this.product_options_image.controls) {
            if (controlName) {
                newProduct[controlName] =
                    this.product_options_image.controls[controlName].value;
            }
        }
        return newProduct;
    };
    ProductShopComponent.prototype.createDataTag = function () {
        var newProduct = {};
        for (var controlName in this.product_tags.controls) {
            if (controlName) {
                newProduct[controlName] = this.product_tags.controls[controlName].value;
            }
        }
        return newProduct;
    };
    ProductShopComponent.prototype.createNewDataDiscount = function () {
        var newProduct = {};
        for (var controlName in this.product_discount.controls) {
            if (controlName) {
                newProduct[controlName] =
                    this.product_discount.controls[controlName].value;
            }
        }
        return newProduct;
    };
    ProductShopComponent.prototype.addTag = function (event) {
        var value = (event.value || '').trim();
        if (value) {
            this.tags.push({ name: value });
            this.tagArray.push(value);
            this.product_tags.patchValue({
                tag: this.tagArray.toString()
            });
        }
        event.chipInput.clear();
    };
    ProductShopComponent.prototype.removeTag = function (tag) {
        var index = this.tags.indexOf(tag);
        if (index >= 0) {
            this.tags.splice(index, 1);
            console.log(this.tagArray);
            this.tagArray.splice(index, 1);
            this.product_tags.patchValue({
                tag: this.tagArray.toString()
            });
            console.log(this.tagArray);
        }
    };
    ProductShopComponent.prototype.numberOnly = function (event) {
        var charCode = event.which ? event.which : event.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        }
        return true;
    };
    ProductShopComponent.prototype.showParent_Child = function () {
        this.isHiddenChild = true;
        this.isHiddenChildParent = false;
        this.getParentChildCategory(this.product.controls['parent_category'].value);
    };
    ProductShopComponent.prototype.showChild = function () {
        this.isHiddenChild = false;
        this.getChildCategory(this.product.controls['parent_child_category'].value);
    };
    ProductShopComponent.prototype.showDiscount = function () {
        this.isHiddenDiscount = false;
    };
    ProductShopComponent.prototype.changeDate = function (event) {
        console.log(event);
        this.isHiddenEndDate = false;
    };
    ProductShopComponent.prototype.onFileChange = function (event) {
        var _this = this;
        this.image_option = '';
        if (event.target.files && event.target.files[0]) {
            var filesAmount = event.target.files.length;
            for (var i = 0; i < filesAmount; i++) {
                var reader = new FileReader();
                reader.onload = function (event) {
                    _this.images.push(event.target.result);
                };
                reader.readAsDataURL(event.target.files[i]);
                this.imageArray.push(event.target.files[i]);
                console.log(typeof event.target.files[i]);
                console.log(this.imageArray);
            }
        }
    };
    ProductShopComponent.prototype.deleteImage = function (event, url) {
        // TODO: delete image from server
        var item = url;
        var length = this.images.length;
        for (var i = 0; i < length; i++) {
            if (this.images[i] == item) {
                this.imageArray.splice(i, 1);
                console.log(this.imageArray);
                this.images.splice(i, 1);
            }
        }
    };
    ProductShopComponent.prototype.updateProduct = function () {
        var _this = this;
        this.product.controls['deleted'].setValue('false');
        console.log(this.product.value);
        this.Addservice.updateProduct(this.createDataProduct()).subscribe(function (data) {
            if (_this.product_discount.controls['id'].value != '') {
                _this.product_discount.controls['productdiscount'].setValue(data);
                _this.Addservice.updateProductDiscount(_this.createNewDataDiscount())
                    .toPromise()
                    .then(function (data) { }),
                    function (error) {
                        sweetalert2_1["default"].fire({
                            title: 'Thông báo!',
                            text: 'Cập nhật giảm giá bại !!!',
                            icon: 'success'
                        });
                    };
            }
            if (_this.imageArray.length > 0) {
                console.log(_this.product_tags.controls['id'].value);
                _this.product_tags.controls['producttag'].setValue(data);
                _this.Addservice.updateProductTag(_this.createDataTag())
                    .toPromise()
                    .then(function (data) { }),
                    function (error) {
                        sweetalert2_1["default"].fire({
                            title: 'Thông báo!',
                            text: 'Cập nhật tag thất bại !!!',
                            icon: 'success'
                        });
                    };
            }
            _this.product_options.controls['product'].setValue(data);
            _this.Addservice.updateProductOption(_this.createNewOption()).subscribe(function (dataOption) {
                if (_this.imageArray.length > 0) {
                    _this.Addservice.getProductOptionImageByIdOption(dataOption.id).subscribe(function (data) {
                        data.forEach(function (element) {
                            _this.imageArrayDelete.push(element.image);
                        });
                        _this.imageService
                            .deleteMultiImageProductOption(_this.imageArrayDelete)
                            .subscribe(function (data) {
                            _this.updateMultiImage(dataOption);
                        }, function (error) {
                            alert('error deletemuti');
                        });
                    }, function (error) {
                        alert('error getimage');
                    });
                }
            }),
                function (error) {
                    sweetalert2_1["default"].fire({
                        title: 'Thông báo!',
                        text: 'Cập nhật thuộc tính sản phẩm thất bại !!!',
                        icon: 'error'
                    });
                };
            sweetalert2_1["default"].fire({
                title: 'Thông báo!',
                text: 'Cập nhật sản phẩm thành công',
                icon: 'success'
            });
        }),
            function (error) {
                sweetalert2_1["default"].fire({
                    title: 'Thông báo!',
                    text: 'Cập nhật sản phẩm thất bại !!!',
                    icon: 'error'
                });
            };
    };
    ProductShopComponent.prototype.addNewProductOption = function () {
        var _this = this;
        if (this.tagArray.length > 0) {
            this.product_tags.controls['producttag'].setValue(this.product.value);
            this.Addservice.addProductTags(this.createDataTag()).subscribe(function (data) { }),
                function (error) {
                    sweetalert2_1["default"].fire({
                        title: 'Thông báo!',
                        text: 'Thêm thẻ sản phẩm thất bại !!!',
                        icon: 'error'
                    });
                };
        }
        this.product_discount.controls['productdiscount'].setValue(this.product.value);
        this.Addservice.addProductDiscount(this.createNewDataDiscount())
            .toPromise()
            .then(function (data) { }),
            function (error) { };
        this.product_options.controls['product'].setValue(this.product.value);
        this.Addservice.addProductOption(this.createNewOption()).subscribe(function (data) {
            _this.addMultiImage(data);
            sweetalert2_1["default"].fire({
                title: 'Thông báo!',
                text: 'Thêm thuộc tính sản phẩm thành công',
                icon: 'success'
            });
        }, function (error) {
            if (error.status == 400) {
                sweetalert2_1["default"].fire({
                    title: 'Thông báo!',
                    text: error.error.errors[0].defaultMessage,
                    icon: 'error'
                });
            }
        });
    };
    ProductShopComponent.prototype.addProduct = function () {
        var _this = this;
        this.product.controls['deleted'].setValue('false');
        this.Addservice.addProductShop(this.createDataProduct()).subscribe(function (data) {
            sweetalert2_1["default"].fire({
                title: 'Thêm sản phẩm thành công !!',
                text: 'Bạn có muốn đăng bán sản phẩm luôn không!',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Đăng bán!'
            }).then(function (result) {
                if (_this.tagArray.length > 0) {
                    _this.product_tags.controls['producttag'].setValue(data);
                    _this.Addservice.addProductTags(_this.createDataTag()).subscribe(function (data) { }),
                        function (error) {
                            sweetalert2_1["default"].fire({
                                title: 'Thông báo!',
                                text: 'Thêm thẻ sản phẩm thất bại !!!',
                                icon: 'error'
                            });
                        };
                }
                _this.product_discount.controls['productdiscount'].setValue(data);
                _this.Addservice.addProductDiscount(_this.createNewDataDiscount())
                    .toPromise()
                    .then(function (data) { }),
                    function (error) { };
                _this.product_options.controls['product'].setValue(data);
                _this.Addservice.addProductOption(_this.createNewOption())
                    .toPromise()
                    .then(function (data) {
                    console.log(data);
                    _this.addMultiImage(data);
                }, function (error) {
                    console.log(error);
                });
                if (result.isConfirmed) {
                    _this.Addservice.enableProductShop(data).subscribe(function (data) {
                        console.log(data);
                        sweetalert2_1["default"].fire({
                            title: 'Thông báo!',
                            text: 'Sản phẩm đã được đăng bán',
                            icon: 'success'
                        }).then(function () {
                            window.location.reload();
                        });
                    }, function (error) {
                        alert(error);
                        console.log(error);
                    });
                }
                else {
                    window.location.reload();
                }
            });
        }, function (err) {
            if (err.status == 404) {
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: 'Chưa đăng nhập'
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
    ProductShopComponent.prototype.getCities = function () {
        var _this = this;
        this.AddresseService.getApiCity().subscribe(function (data) {
            var listCities = data.map(function (obj) {
                return obj;
            });
            _this.listCities = listCities;
        });
    };
    ProductShopComponent.prototype.getCountry = function () {
        var _this = this;
        this.AddresseService.getCountry().subscribe(function (data) {
            var listCountry = data.map(function (obj) {
                return obj;
            });
            _this.listCountry = listCountry;
        });
    };
    ProductShopComponent.prototype.getBrands = function () {
        var _this = this;
        this.Addservice.getBrand().subscribe(function (data) {
            var listBrands = data.map(function (obj) {
                return obj;
            });
            _this.listBrands = listBrands;
        });
    };
    ProductShopComponent.prototype.getParentChildCategory = function (id) {
        var _this = this;
        this.Addservice.getChildParentCategoriesByIdParent(id).subscribe(function (data) {
            var listCategories = data.map(function (obj) {
                return obj;
            });
            _this.listParent_ChildCategory = listCategories;
        });
    };
    ProductShopComponent.prototype.getParentCategory = function () {
        var _this = this;
        this.Addservice.getParentCategories().subscribe(function (data) {
            var listCategories = data.map(function (obj) {
                return obj;
            });
            _this.listParentCategory = listCategories;
        });
    };
    ProductShopComponent.prototype.getChildCategory = function (id) {
        var _this = this;
        this.Addservice.getChildCategoriesByChildParent(id).subscribe(function (data) {
            var listCategories = data.map(function (obj) {
                return obj;
            });
            _this.listChildCategory = listCategories;
        });
    };
    ProductShopComponent.prototype.loadProdutedit = function () {
        var id = '';
        this.route.params.subscribe(function (params) {
            id = params['id'];
        });
        return id;
    };
    ProductShopComponent.prototype.getProductById = function () {
        var _this = this;
        if (this.loadProdutedit()) {
            this.Addservice.getProductByid(this.loadProdutedit()).subscribe(function (data) {
                _this.headerService
                    .getShopByToken(_this.cookieService.get('auth'))
                    .subscribe(function (shop) {
                    if (data.shop.id != shop.id) {
                        _this.router.navigate(['/shop/product/manager']);
                    }
                });
                for (var controlName in _this.product.controls) {
                    for (var node in data) {
                        if (controlName && controlName == node) {
                            _this.product.controls[controlName].setValue(data[node]);
                        }
                    }
                    console.log(_this.product.value);
                }
                if (data['brand']) {
                    _this.product.controls['brand'].setValue(data['brand']);
                }
                _this.Addservice.getProductOptionByid(_this.loadProdutedit()).subscribe(function (data) {
                    for (var controlName in _this.product_options.controls) {
                        for (var node in data) {
                            if (controlName && controlName == node) {
                                _this.product_options.controls[controlName].setValue(data[node]);
                                console.log(_this.product_options.controls['id'].value);
                            }
                        }
                    }
                    _this.Addservice.getProductOptionImageByIdOption(_this.product_options.controls['id'].value).subscribe(function (dataOptionImage) {
                        var _loop_1 = function (index) {
                            _this.product_options_image.controls['id'].setValue(dataOptionImage[0].id);
                            _this.product_options_image.controls['productoption'].setValue(dataOptionImage[0].productoption);
                            _this.imageService
                                .getData(dataOptionImage[index].image)
                                .subscribe(function (data) {
                                if (data != null) {
                                    _this.images.push(data);
                                    _this.imageService
                                        .blobToFile(dataOptionImage[index].image)
                                        .subscribe(function (data) {
                                        var myFile = _this.blobToFile(data, dataOptionImage[index].image);
                                        var fileRender = new File([myFile], dataOptionImage[index].image, { type: 'image/jpeg' });
                                        _this.imageArray.push(fileRender);
                                    });
                                }
                                else {
                                    alert('data null');
                                }
                            }),
                                function (error) {
                                    alert('Lỗi update hình ảnh');
                                };
                        };
                        for (var index in dataOptionImage) {
                            _loop_1(index);
                        }
                    });
                });
                _this.Addservice.getCategoryByidProduct(_this.loadProdutedit()).subscribe(function (child) {
                    _this.Addservice.getParent_Child_CategoryByid(child.id).subscribe(function (parent_child) {
                        _this.Addservice.getParent_CategoryByid(parent_child.id).subscribe(function (parent) {
                            _this.product.controls['parent_category'].setValue(parent.id);
                            _this.product.controls['parent_child_category'].setValue(parent_child.id);
                            _this.product.controls['child_category'].setValue(child);
                            _this.showParent_Child();
                            _this.showChild();
                        });
                    });
                });
                _this.Addservice.getTagbyProductid(_this.product.value.id).subscribe(function (tags) {
                    tags.forEach(function (element) {
                        _this.tags.push({ name: element.tag.trim() });
                        _this.tagArray.push(element.tag.trim());
                        _this.product_tags.patchValue({
                            tag: _this.tagArray.toString()
                        });
                    });
                    console.log(_this.tags);
                    console.log(_this.tagArray);
                });
            }, function (err) {
                sweetalert2_1["default"].fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: 'Không tìm thấy sản phẩm'
                }).then(function (data) {
                    _this.router.navigateByUrl('/shop/product/manager');
                });
            });
        }
    };
    ProductShopComponent.prototype.deleteProduct = function (id) {
        var _this = this;
        sweetalert2_1["default"].fire({
            icon: 'question',
            title: 'Option',
            text: 'Bạn có chắc muốn xóa option này không ?',
            confirmButtonText: 'Xóa',
            cancelButtonText: 'Hủy',
            showCancelButton: true
        }).then(function (resuft) {
            if (resuft.isConfirmed) {
                _this.Addservice.deleteProductByid(id).subscribe(function (data) {
                    sweetalert2_1["default"].fire({
                        icon: 'success',
                        title: 'success',
                        text: 'Đã xóa sản phẩm'
                    });
                    _this.router.navigate(['/shop/product/manager']);
                }, function (err) {
                    sweetalert2_1["default"].fire({
                        icon: 'error',
                        title: 'Error',
                        text: err
                    });
                });
            }
        });
    };
    ProductShopComponent.prototype.addMultiImage = function (data) {
        var _this = this;
        var formData = new FormData();
        for (var i = 0; i < this.imageArray.length; i++) {
            formData.append('files', this.imageArray[i]);
        }
        this.imageService
            .createMultiImageProductOption(formData)
            .toPromise()
            .then(function (valueFile) {
            _this.product_options_image.patchValue({
                image: valueFile.toString()
            });
            console.log(valueFile);
            _this.product_options_image.controls['productoption'].setValue(data);
            console.log(_this.product_options_image.value);
            if (valueFile.length > 0) {
                _this.Addservice.addProductOptionImage(_this.createNewOptionImage())
                    .toPromise()
                    .then(function (data) {
                    console.log(data);
                }, function (error) {
                    alert(error);
                });
            }
        }),
            function (error) {
                alert('Lỗi thêm Image FTP');
            };
    };
    ProductShopComponent.prototype.updateMultiImage = function (data) {
        var _this = this;
        var formData = new FormData();
        for (var i = 0; i < this.imageArray.length; i++) {
            console.log(this.imageArray[i]);
            formData.append('files', this.imageArray[i]);
        }
        this.imageService
            .createMultiImageProductOption(formData)
            .toPromise()
            .then(function (valueFile) {
            _this.product_options_image.patchValue({
                image: valueFile.toString()
            });
            _this.product_options_image.controls['productoption'].setValue(data);
            _this.Addservice.updateProductOptionImage(_this.createNewOptionImage()).subscribe(function (data) {
                console.log(data);
            }),
                function (error) {
                    sweetalert2_1["default"].fire({
                        title: 'Thông báo!',
                        text: 'Cập nhật hình ảnh thất bại !!!',
                        icon: 'error'
                    });
                };
        }),
            function (error) {
                alert('Lỗi thêm Image FTP');
            };
    };
    ProductShopComponent.prototype.resetForm = function () {
        window.location.reload();
    };
    ProductShopComponent = __decorate([
        core_1.Component({
            selector: 'app-product-shop',
            templateUrl: './product-shop.component.html',
            styleUrls: ['./product-shop.component.css']
        })
    ], ProductShopComponent);
    return ProductShopComponent;
}());
exports.ProductShopComponent = ProductShopComponent;
