import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // Admin user management
  getAllAdminUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/admin-users`);
  }

  getAdminById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/admin-users/${id}`);
  }

  createAdmin(adminData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/admin-users`, adminData);
  }

  updateAdmin(id: string, adminData: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/admin-users/${id}`, adminData);
  }

  deleteAdmin(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/admin-users/${id}`);
  }

  updateAccessLevel(id: string, level: string): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/admin-users/${id}/access-level`, { level });
  }

  updateLastLogin(id: string): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/admin-users/${id}/last-login`, {});
  }

  getByUsername(username: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/admin-users/by-username/${username}`);
  }

  getByEmail(email: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/admin-users/by-email/${email}`);
  }
}