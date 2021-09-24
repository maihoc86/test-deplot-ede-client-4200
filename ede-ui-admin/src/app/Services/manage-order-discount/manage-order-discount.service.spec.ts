import { TestBed } from '@angular/core/testing';

import { ManageOrderDiscountService } from './manage-order-discount.service';

describe('ManageOrderDiscountService', () => {
  let service: ManageOrderDiscountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManageOrderDiscountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
