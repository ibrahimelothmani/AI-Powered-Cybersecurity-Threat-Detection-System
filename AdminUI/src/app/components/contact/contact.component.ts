import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {
  contacts: any[] = [];
  loading = false;
  error = '';

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.loadContacts();
  }

  loadContacts() {
    this.loading = true;
    this.userService.getContacts().subscribe({
      next: (data) => {
        this.contacts = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load contacts';
        this.loading = false;
      }
    });
  }

  markAsRead(contactId: string) {
    // Implement mark as read functionality
    console.log('Mark as read:', contactId);
  }

  deleteContact(contactId: string) {
    // Implement delete contact functionality
    console.log('Delete contact:', contactId);
  }
}
