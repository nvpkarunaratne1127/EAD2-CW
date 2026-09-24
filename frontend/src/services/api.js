const API_BASE = '/api';

async function request(url, options = {}) {
  const defaultHeaders = {
    'Content-Type': 'application/json',
  };

  const response = await fetch(`${API_BASE}${url}`, {
    ...options,
    headers: {
      ...defaultHeaders,
      ...options.headers,
    },
  });

  if (response.status === 204) {
    return null;
  }

  const data = await response.json().catch(() => null);

  if (!response.ok) {
    const errorMsg = data?.message || data?.error || `Request failed with status ${response.status}`;
    throw new Error(errorMsg);
  }

  return data;
}

export const api = {
  // Auth
  login: (credentials) => request('/auth/login', { method: 'POST', body: JSON.stringify(credentials) }),
  register: (user) => request('/auth/register', { method: 'POST', body: JSON.stringify(user) }),
  getUsers: (role) => request(role ? `/auth/users?role=${role}` : '/auth/users'),

  // Equipment
  getEquipment: (category) => request(category ? `/equipment?category=${category}` : '/equipment'),
  getEquipmentById: (id) => request(`/equipment/${id}`),
  createEquipment: (item) => request('/equipment', { method: 'POST', body: JSON.stringify(item) }),
  updateEquipment: (id, item) => request(`/equipment/${id}`, { method: 'PUT', body: JSON.stringify(item) }),
  deleteEquipment: (id) => request(`/equipment/${id}`, { method: 'DELETE' }),

  // Supplements
  getSupplements: (category) => request(category ? `/supplements?category=${category}` : '/supplements'),
  getSupplementById: (id) => request(`/supplements/${id}`),
  createSupplement: (item) => request('/supplements', { method: 'POST', body: JSON.stringify(item) }),
  updateSupplement: (id, item) => request(`/supplements/${id}`, { method: 'PUT', body: JSON.stringify(item) }),
  deleteSupplement: (id) => request(`/supplements/${id}`, { method: 'DELETE' }),
  orderSupplement: (orderReq) => request('/supplements/order', { method: 'POST', body: JSON.stringify(orderReq) }),
  getSupplementOrders: (customerId) => request(customerId ? `/supplements/orders/customer/${customerId}` : '/supplements/orders'),

  // Trainers
  getTrainers: () => request('/trainers'),
  getTrainerById: (id) => request(`/trainers/${id}`),
  getTrainerByUserId: (userId) => request(`/trainers/user/${userId}`),
  getTrainerClients: (trainerId) => request(`/trainers/${trainerId}/clients`),
  createTrainer: (userId, trainer) => request(`/trainers/user/${userId}`, { method: 'POST', body: JSON.stringify(trainer) }),
  updateTrainer: (id, trainer) => request(`/trainers/${id}`, { method: 'PUT', body: JSON.stringify(trainer) }),

  // Memberships & Dynamic Pricing
  calculatePrice: (hasTrainer, hasTreadmill) => 
    request(`/memberships/calculate?hasTrainer=${hasTrainer}&hasTreadmill=${hasTreadmill}`, { method: 'POST' }),
  getMemberships: () => request('/memberships'),
  getCustomerMembership: (customerId) => request(`/memberships/customer/${customerId}`),
  subscribeMembership: (req) => request('/memberships', { method: 'POST', body: JSON.stringify(req) }),
  cancelMembership: (id) => request(`/memberships/${id}`, { method: 'DELETE' }),

  // Workout & Diet Plans
  getCustomerWorkoutPlan: (customerId) => request(`/workout-plans/customer/${customerId}`),
  getTrainerWorkoutPlans: (trainerId) => request(`/workout-plans/trainer/${trainerId}`),
  saveWorkoutPlan: (planReq) => request('/workout-plans', { method: 'POST', body: JSON.stringify(planReq) }),

  // Billing & Statistics
  getBillingStats: () => request('/billing/stats'),
  getAllInvoices: () => request('/billing/invoices'),
  getCustomerInvoices: (customerId) => request(`/billing/invoices/customer/${customerId}`),
};
