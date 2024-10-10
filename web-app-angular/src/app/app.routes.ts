import { Routes } from '@angular/router';

import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { IndexPageComponent } from './pages/index-page/index-page.component';
import { UsersPageComponent } from './pages/users-page/users-page.component';
import { TransactionsPageComponent } from './pages/transactions-page/transactions-page.component';
import { AccountPageComponent } from './pages/account-page/account-page.component';
import { ErrorNotFoundComponent } from './pages/error-not-found/error-not-found.component';

export const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', component: IndexPageComponent }, // Maps to "/"
      { path: 'home', component: IndexPageComponent },
      { path: 'users', component: UsersPageComponent },
      { path: 'transactions', component: TransactionsPageComponent },
      { path: 'account/:userId', component: AccountPageComponent },
    ],
  },
  // Wildcard route for a 404 page
  { path: '**', component: ErrorNotFoundComponent },
];
