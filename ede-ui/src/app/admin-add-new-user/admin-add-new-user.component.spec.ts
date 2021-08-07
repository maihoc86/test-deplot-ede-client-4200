import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ADMINAddNewUserComponent } from './admin-add-new-user.component';

describe('ADMINAddNewUserComponent', () => {
  let component: ADMINAddNewUserComponent;
  let fixture: ComponentFixture<ADMINAddNewUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ADMINAddNewUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ADMINAddNewUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
