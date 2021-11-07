import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserUpdatePhoneComponent } from './user-update-phone.component';

describe('UserUpdatePhoneComponent', () => {
  let component: UserUpdatePhoneComponent;
  let fixture: ComponentFixture<UserUpdatePhoneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserUpdatePhoneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserUpdatePhoneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
