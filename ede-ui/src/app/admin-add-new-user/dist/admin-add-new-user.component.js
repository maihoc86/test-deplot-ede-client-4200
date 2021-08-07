"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.ADMINAddNewUserComponent = void 0;
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var ADMINAddNewUserComponent = /** @class */ (function () {
    function ADMINAddNewUserComponent(router, route, registerService, apiAddressService) {
        this.router = router;
        this.route = route;
        this.registerService = registerService;
        this.apiAddressService = apiAddressService;
        this.register = new forms_1.FormGroup({
            username: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern('^[a-z0-9_-]{6,50}$'),
            ]),
            password: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern('^(?=.*d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,50}$'),
            ]),
            first_name: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern("^\\S([a-zA-Z\\xC0-\\uFFFF]{0,}[ \\-\\']{0,}){2,50}$"),
            ]),
            address: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{1,}[ \\-\\' \\.-/,]{0,}){5,}$"),
            ]),
            last_name: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{0,}[ \\-\\' \\.-]{0,}){2,50}$"),
            ]),
            gender: new forms_1.FormControl('', forms_1.Validators.required),
            photo: new forms_1.FormControl(null),
            email: new forms_1.FormControl('', [forms_1.Validators.required, forms_1.Validators.email]),
            is_delete: new forms_1.FormControl(false),
            is_active: new forms_1.FormControl(false),
            role: new forms_1.FormControl('US'),
            otp: new forms_1.FormControl(null),
            city: new forms_1.FormControl(''),
            district: new forms_1.FormControl(''),
            wards: new forms_1.FormControl(''),
            phone: new forms_1.FormControl('', [
                forms_1.Validators.required,
                forms_1.Validators.pattern('(84|0[3|5|7|8|9])+([0-9]{8})\\b'),
            ]),
            confirmPassword: new forms_1.FormControl('', forms_1.Validators.required)
        });
        this.listCitys = [];
        this.listDistricts = [];
        this.listWards = [];
    }
    ADMINAddNewUserComponent.prototype.ngOnInit = function () {
    };
    ADMINAddNewUserComponent.prototype.getApiCity = function () {
        var _this = this;
        this.apiAddressService.getApiCity().subscribe(function (data) {
            var listCity = data.map(function (obj) {
                return obj;
            });
            _this.listCitys = listCity;
        });
    };
    ADMINAddNewUserComponent.prototype.getApiDistricts = function (id) {
        var _this = this;
        this.apiAddressService.getApiDistricts(id).subscribe(function (data) {
            var listDistrict = data.map(function (obj) {
                return obj;
            });
            _this.listDistricts = listDistrict;
        });
    };
    ADMINAddNewUserComponent.prototype.getApiWards = function (id) {
        var _this = this;
        this.apiAddressService.getApiWards(id).subscribe(function (data) {
            var listWard = data.map(function (obj) {
                return obj;
            });
            _this.listWards = listWard;
        });
    };
    ADMINAddNewUserComponent = __decorate([
        core_1.Component({
            selector: 'app-admin-add-new-user',
            templateUrl: './admin-add-new-user.component.html',
            styleUrls: ['./admin-add-new-user.component.css']
        })
    ], ADMINAddNewUserComponent);
    return ADMINAddNewUserComponent;
}());
exports.ADMINAddNewUserComponent = ADMINAddNewUserComponent;
