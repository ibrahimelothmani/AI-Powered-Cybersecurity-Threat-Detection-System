import { Component } from '@angular/core';

@Component({
  selector: 'app-contacts',
  template: `
    <div class="container mt-4">
      <h2>Contact Management</h2>
      <div class="alert alert-info">
        Contact management functionality will be implemented here.
      </div>
    </div>
  `,
  styles: [`
    .container {
      padding: 20px;
    }
  `]
})
export class ContactsComponent {}