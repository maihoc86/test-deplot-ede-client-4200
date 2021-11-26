import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapAddressComponent } from './map-address.component';

describe('MapAddressComponent', () => {
  let component: MapAddressComponent;
  let fixture: ComponentFixture<MapAddressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MapAddressComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapAddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
