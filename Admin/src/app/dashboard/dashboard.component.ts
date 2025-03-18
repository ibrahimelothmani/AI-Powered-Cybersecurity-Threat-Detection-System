import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  menuItems = [
    { icon: 'people', label: 'Users', route: '/dashboard/users' },
    { icon: 'email', label: 'Contacts', route: '/dashboard/contacts' },
    { icon: 'history', label: 'User History', route: '/dashboard/history' }
  ];

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  get currentUser() {
    return this.authService.getCurrentUser();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}