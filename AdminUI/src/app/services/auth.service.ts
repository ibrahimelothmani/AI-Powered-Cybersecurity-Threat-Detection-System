import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentAdminSubject: BehaviorSubject<any>;
  public currentAdmin: Observable<any>;
  private apiUrl = 'http://localhost:3000/api'; // Update with your backend API URL

  constructor(private http: HttpClient) {
    this.currentAdminSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentAdmin') || '{}'));
    this.currentAdmin = this.currentAdminSubject.asObservable();
  }

  public get currentAdminValue() {
    return this.currentAdminSubject.value;
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${this.apiUrl}/admin/login`, { username, password })
      .pipe(map(response => {
        if (response && response.token) {
          localStorage.setItem('currentAdmin', JSON.stringify(response));
          this.currentAdminSubject.next(response);
        }
        return response;
      }));
  }

  logout() {
    localStorage.removeItem('currentAdmin');
    this.currentAdminSubject.next(null);
  }

  isAuthenticated(): boolean {
    const admin = this.currentAdminValue;
    return !!(admin && admin.token);
  }
}
