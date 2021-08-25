import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerAccountsComponent } from './manager-accounts.component';

describe('ManagerAccountsComponent', () => {
  let component: ManagerAccountsComponent;
  let fixture: ComponentFixture<ManagerAccountsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerAccountsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerAccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
