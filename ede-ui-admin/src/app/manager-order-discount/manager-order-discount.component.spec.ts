import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerOrderDiscountComponent } from './manager-order-discount.component';

describe('ManagerOrderDiscountComponent', () => {
  let component: ManagerOrderDiscountComponent;
  let fixture: ComponentFixture<ManagerOrderDiscountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerOrderDiscountComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerOrderDiscountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
