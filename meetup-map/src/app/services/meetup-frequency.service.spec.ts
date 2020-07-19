import { TestBed } from '@angular/core/testing';

import { MeetupFrequencyService } from './meetup-frequency.service';

describe('MeetupFrequencyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MeetupFrequencyService = TestBed.get(MeetupFrequencyService);
    expect(service).toBeTruthy();
  });
});
