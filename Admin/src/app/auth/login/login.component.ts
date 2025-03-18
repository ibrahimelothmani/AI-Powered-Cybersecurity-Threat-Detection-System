import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    // Check if already logged in
    const token = localStorage.getItem('admin_token');
    if (token) {
      this.router.navigate(['/dashboard']);
    }
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.isLoading = true;
      // TODO: Implement authentication service call
      const { email, password } = this.loginForm.value;
      
      // Temporary mock login - replace with actual service call
      setTimeout(() => {
        if (email === 'admin@example.com' && password === 'admin123') {
          localStorage.setItem('admin_token', 'mock_jwt_token');
          this.router.navigate(['/dashboard']);
        } else {
          this.snackBar.open('Invalid credentials', 'Close', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top'
          });
        }
        this.isLoading = false;
      }, 1000);
    }
  }
}