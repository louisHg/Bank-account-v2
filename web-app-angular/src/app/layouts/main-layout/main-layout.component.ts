import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, ViewChild, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatSidenav } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [RouterModule, MatListModule, MatToolbarModule, MatIconModule, MatSidenavModule],
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.scss'],
})
export class MainLayoutComponent {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  open = true;

  constructor(private observer: BreakpointObserver) {}

  toggleMenu() {
    this.open = !this.open;
  }
}
