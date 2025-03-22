import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// User endpoints
export const userService = {
  getAllUsers: () => api.get('/user'),
  getUserById: (id: string) => api.get(`/user/${id}`),
  createUser: (userData: any) => api.post('/user', userData),
  updateUser: (id: string, userData: any) => api.put(`/user/${id}`, userData),
  deleteUser: (id: string) => api.delete(`/user/${id}`),
  loginUser: (id: string) => api.post(`/user/${id}/login`),
};

// Admin user endpoints
export const adminService = {
  getAllAdminUsers: () => api.get('/admin-users'),
  getAdminById: (id: string) => api.get(`/admin-users/${id}`),
  createAdmin: (adminData: any) => api.post('/admin-users', adminData),
  updateAdmin: (id: string, adminData: any) => api.put(`/admin-users/${id}`, adminData),
  deleteAdmin: (id: string) => api.delete(`/admin-users/${id}`),
  updateAccessLevel: (id: string, level: string) => api.put(`/admin-users/${id}/access-level`, { level }),
  updateLastLogin: (id: string) => api.put(`/admin-users/${id}/last-login`),
  getByUsername: (username: string) => api.get(`/admin-users/by-username/${username}`),
  getByEmail: (email: string) => api.get(`/admin-users/by-email/${email}`),
};

// Contact endpoints
export const contactService = {
  getAllContacts: () => api.get('/contacts'),
  getPendingContacts: () => api.get('/contacts/pending'),
  getContactById: (id: string) => api.get(`/contacts/${id}`),
  createContact: (contactData: any) => api.post('/contacts', contactData),
  updateContact: (id: string, contactData: any) => api.put(`/contacts/${id}`, contactData),
  respondToContact: (id: string, response: any) => api.post(`/contacts/${id}/respond`, response),
  deleteContact: (id: string) => api.delete(`/contacts/${id}`),
  getAdminContacts: (adminId: string) => api.get(`/contacts/admin/${adminId}`),
};