import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductOptionsShopComponent } from './product-options-shop.component';

describe('ProductOptionsShopComponent', () => {
  let component: ProductOptionsShopComponent;
  let fixture: ComponentFixture<ProductOptionsShopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductOptionsShopComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductOptionsShopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
