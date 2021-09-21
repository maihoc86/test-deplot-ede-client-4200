import { TestBed } from '@angular/core/testing';

import { ManagerShopService } from './manager-shop.service';

describe('ManagerShopService', () => {
  let service: ManagerShopService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManagerShopService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
