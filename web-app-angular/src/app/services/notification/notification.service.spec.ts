import { TestBed } from '@angular/core/testing';
import { NotificationService, Notification } from './notification.service';

describe('NotificationService', () => {
  let service: NotificationService;
  let notifications: Notification[] = [];

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotificationService);
    notifications = [];
    service.notifications$.subscribe((notification) => {
      notifications.push(notification);
    });
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should emit an error notification', () => {
    const message = 'This is an error';
    service.error(message);
    expect(notifications.length).toBe(1);
    expect(notifications[0].message).toBe(message);
    expect(notifications[0].type).toBe('error');
    expect(notifications[0].color).toBe('red');
  });

  it('should emit a permanent error notification', () => {
    const message = 'Permanent error';
    service.errorPermanent(message);
    expect(notifications.length).toBe(1);
    expect(notifications[0].timeout).toBe(0);
    expect(notifications[0].actions?.length).toBe(1);
  });

  it('should emit a success notification', () => {
    const message = 'Operation successful';
    service.success(message);
    expect(notifications.length).toBe(1);
    expect(notifications[0].type).toBe('success');
    expect(notifications[0].color).toBe('green');
  });

  it('should emit a warning notification', () => {
    const message = 'This is a warning';
    service.warning(message);
    expect(notifications.length).toBe(1);
    expect(notifications[0].type).toBe('warning');
    expect(notifications[0].color).toBe('yellow');
  });

  it('should emit an info notification', () => {
    const message = 'This is an info message';
    service.info(message);
    expect(notifications.length).toBe(1);
    expect(notifications[0].type).toBe('info');
    expect(notifications[0].color).toBe('blue');
  });
});
