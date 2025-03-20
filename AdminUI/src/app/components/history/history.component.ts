import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnInit {
  history: any[] = [];
  filteredHistory: any[] = [];
  loading = false;
  error = '';
  selectedUserId: string = '';

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.loadAllHistory();
  }

  loadAllHistory() {
    this.loading = true;
    this.userService.getAllHistory().subscribe({
      next: (data) => {
        this.history = data;
        this.filteredHistory = [...this.history];
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load history';
        this.loading = false;
      }
    });
  }

  loadUserHistory(userId: string) {
    this.loading = true;
    this.selectedUserId = userId;
    this.userService.getUserHistory(userId).subscribe({
      next: (data) => {
        this.history = data;
        this.filteredHistory = [...this.history];
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load user history';
        this.loading = false;
      }
    });
  }

  filterHistory(event: Event) {
    const searchTerm = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredHistory = this.history.filter(item => 
      item.url.toLowerCase().includes(searchTerm) ||
      item.username.toLowerCase().includes(searchTerm)
    );
  }

  getThreatLevelClass(level: string): string {
    switch (level.toLowerCase()) {
      case 'high':
        return 'threat-high';
      case 'medium':
        return 'threat-medium';
      case 'low':
        return 'threat-low';
      default:
        return 'threat-safe';
    }
  }

  viewDetails(item: any) {
    // For now, just show the details in the console
    // Later we can implement a modal or details page
    console.log('Scan Details:', {
      url: item.url,
      username: item.username,
      scanDate: item.scanDate,
      threatLevel: item.threatLevel,
      details: item.details || 'No detailed information available'
    });
  }
}
