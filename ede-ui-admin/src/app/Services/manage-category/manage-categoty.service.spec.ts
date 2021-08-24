import { TestBed } from '@angular/core/testing';

import { ManageCategotyService } from './manage-categoty.service';

describe('ManageCategotyService', () => {
  let service: ManageCategotyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManageCategotyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
