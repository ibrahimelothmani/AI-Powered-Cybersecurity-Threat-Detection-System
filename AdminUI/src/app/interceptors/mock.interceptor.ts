import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable()
export class MockInterceptor implements HttpInterceptor {
  private mockAdmin = {
    username: 'admin',
    password: 'admin123'
  };

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
    if (request.url.includes('/api/admin/login') && request.method === 'POST') {
      const body = request.body;
      if (body.username === this.mockAdmin.username && body.password === this.mockAdmin.password) {
        return of(new HttpResponse({
          status: 200,
          body: {
            token: 'mock-jwt-token',
            username: this.mockAdmin.username,
            role: 'admin'
          }
        }));
      } else {
        throw new Error('Invalid credentials');
      }
    }
    return next.handle(request);
  }
}
