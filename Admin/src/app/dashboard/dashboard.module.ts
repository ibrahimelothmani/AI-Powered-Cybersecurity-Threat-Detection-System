import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';

import { DashboardComponent } from './dashboard.component';
import { UsersComponent } from './users/users.component';
import { ContactsComponent } from './contacts/contacts.component';
import { HistoryComponent } from './history/history.component';

@NgModule({
  declarations: [
    DashboardComponent,
    UsersComponent,
    ContactsComponent,
    HistoryComponent
  ],
  imports: [
    RouterModule,
    CommonModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule
  ],
  exports: [DashboardComponent]
})
export class DashboardModule {}