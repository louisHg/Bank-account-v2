import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

export type NotificationType = 'error' | 'success' | 'warning' | 'info';

export interface Notification {
  message: string;
  type: NotificationType;
  color: string;
  timeout?: number;
  progress?: boolean; // Added property
  actions?: Array<{
    icon: string;
    color: string;
    round: boolean;
    handler: () => void;
  }>;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationSubject = new Subject<Notification>();

  // Observable to be subscribed by the NotificationComponent
  public notifications$: Observable<Notification> = this.notificationSubject.asObservable();

  constructor() { }

  error(message: string): void {
    this.notify({
      message,
      type: 'error',
      color: 'red',
      progress: true // Now valid
    });
  }

  errorPermanent(message: string): void {
    this.notify({
      message,
      type: 'error',
      color: 'red',
      timeout: 0,
      actions: [
        { icon: 'close', color: 'white', round: true, handler: () => { /* Close handler */ } }
      ]
    });
  }

  success(message: string): void {
    this.notify({
      message,
      type: 'success',
      color: 'green',
      progress: true // Now valid
    });
  }

  warning(message: string): void {
    this.notify({
      message,
      type: 'warning',
      color: 'yellow',
      progress: true // Now valid
    });
  }

  info(message: string): void {
    this.notify({
      message,
      type: 'info',
      color: 'blue',
      progress: true // Now valid
    });
  }

  private notify(notification: Notification): void {
    this.notificationSubject.next(notification);
  }
}
