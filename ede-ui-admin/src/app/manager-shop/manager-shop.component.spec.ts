import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerShopComponent } from './manager-shop.component';

describe('ManagerShopComponent', () => {
  let component: ManagerShopComponent;
  let fixture: ComponentFixture<ManagerShopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerShopComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerShopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
