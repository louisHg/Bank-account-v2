import { Component, OnInit } from '@angular/core';
import { NotificationService, Notification } from '../../services/notification/notification.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {
  notifications: Notification[] = [];

  constructor(private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.notificationService.notifications$.subscribe(notification => {
      this.notifications.push(notification);
      if (notification.timeout !== 0) {
        setTimeout(() => this.removeNotification(notification), notification.timeout || 3000);
      }
    });
  }

  removeNotification(notification: Notification): void {
    const index = this.notifications.indexOf(notification);
    if (index >= 0) {
      this.notifications.splice(index, 1);
    }
  }

  handleAction(notification: Notification, action: any): void {
    action.handler();
    this.removeNotification(notification);
  }
}
