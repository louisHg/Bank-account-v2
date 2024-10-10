// src/app/services/api.service.spec.ts
import { TestBed } from '@angular/core/testing';
import { ApiService } from './api.service';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

describe('ApiService', () => {
  let service: ApiService;
  let mock: MockAdapter;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiService);
    mock = new MockAdapter(axios);
  });

  afterEach(() => {
    mock.restore();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should perform a GET request', async () => {
    const mockData = { data: 'test data' };
    mock.onGet('/test-endpoint').reply(200, mockData);

    const response = await service.getData('/test-endpoint');
    expect(response.data).toEqual(mockData);
  });

  it('should perform a POST request', async () => {
    const postData = { name: 'John Doe' };
    const mockResponse = { success: true };
    mock.onPost('/test-endpoint', postData).reply(201, mockResponse);

    const response = await service.postData('/test-endpoint', postData);
    expect(response.data).toEqual(mockResponse);
  });

  it('should handle errors gracefully', async () => {
    mock.onGet('/error-endpoint').reply(500);

    try {
      await service.getData('/error-endpoint');
      fail('Expected an error to be thrown');
    } catch (error) {
      expect(error).toBeTruthy();
    }
  });
});
