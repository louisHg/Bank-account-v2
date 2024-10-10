import {
  ComponentFixture,
  TestBed,
  fakeAsync,
  tick,
} from '@angular/core/testing';
import { NotificationComponent } from './notification.component';
import {
  NotificationService,
  Notification,
} from '../../services/notification/notification.service';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

describe('NotificationComponent', () => {
  let component: NotificationComponent;
  let fixture: ComponentFixture<NotificationComponent>;
  let notificationService: NotificationService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotificationComponent],
      providers: [NotificationService],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationComponent);
    component = fixture.componentInstance;
    notificationService = TestBed.inject(NotificationService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display a success notification', fakeAsync(() => {
    const message = 'Success!';
    notificationService.success(message);
    fixture.detectChanges();
    tick();

    const notificationElements = fixture.debugElement.queryAll(
      By.css('.notification.success')
    );
    expect(notificationElements.length).toBe(1);
    expect(notificationElements[0].nativeElement.textContent).toContain(
      message
    );
    expect(
      notificationElements[0].nativeElement.querySelector('.progress-bar')
    ).toBeTruthy();
  }));

  it('should remove notification after timeout', fakeAsync(() => {
    const message = 'This will disappear';
    notificationService.info(message);
    fixture.detectChanges();
    tick();

    let notificationElements = fixture.debugElement.queryAll(
      By.css('.notification.info')
    );
    expect(notificationElements.length).toBe(1);

    // Advance time by 3 seconds (default timeout)
    tick(3000);
    fixture.detectChanges();

    notificationElements = fixture.debugElement.queryAll(
      By.css('.notification.info')
    );
    expect(notificationElements.length).toBe(0);
  }));

  it('should display a permanent error notification with action', () => {
    const message = 'Permanent Error';
    const mockHandler = jasmine.createSpy('handler');
    notificationService.errorPermanent(message);

    // Manually push the action handler for testing
    component.notifications[0].actions = [
      { icon: 'close', color: 'white', round: true, handler: mockHandler },
    ];
    fixture.detectChanges();

    const actionButton: DebugElement = fixture.debugElement.query(
      By.css('.notification.error button')
    );
    expect(actionButton).toBeTruthy();
    actionButton.triggerEventHandler('click', null);
    expect(mockHandler).toHaveBeenCalled();
    expect(component.notifications.length).toBe(0);
  });
});
