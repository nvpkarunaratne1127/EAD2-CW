// Central API Service connecting React with Owner, Trainer, and Customer APIs
async function request(url, options = {}) {
  const defaultHeaders = {
    'Content-Type': 'application/json',
  };

  const response = await fetch(url, {
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
  // ================= 1. OWNER ENDPOINTS (Port 8080) =================
  getOwnerStats: () => request('/api/owner/dashboard/stats'),

  // Equipment CRUD
  getEquipment: () => request('/api/owner/equipment'),
  getEquipmentByCategory: (cat) => request(`/api/owner/equipment/category/${cat}`),
  searchEquipment: (name) => request(`/api/owner/equipment/search?name=${encodeURIComponent(name)}`),
  createEquipment: (item) => request('/api/owner/equipment', { method: 'POST', body: JSON.stringify(item) }),
  updateEquipment: (id, item) => request(`/api/owner/equipment/${id}`, { method: 'PUT', body: JSON.stringify(item) }),
  deleteEquipment: (id) => request(`/api/owner/equipment/${id}`, { method: 'DELETE' }),

  // Invoices CRUD
  getInvoices: () => request('/api/owner/invoices'),
  createInvoice: (inv) => request('/api/owner/invoices', { method: 'POST', body: JSON.stringify(inv) }),
  deleteInvoice: (id) => request(`/api/owner/invoices/${id}`, { method: 'DELETE' }),

  // ================= 2. TRAINER ENDPOINTS (Port 8081) =================
  // Trainer Profiles CRUD
  getTrainers: () => request('/api/trainers'),
  createTrainer: (trainer) => request('/api/trainers', { method: 'POST', body: JSON.stringify(trainer) }),
  updateTrainer: (id, trainer) => request(`/api/trainers/${id}`, { method: 'PUT', body: JSON.stringify(trainer) }),
  deleteTrainer: (id) => request(`/api/trainers/${id}`, { method: 'DELETE' }),

  // Workout Plans CRUD
  getWorkoutPlans: () => request('/api/workout-plans'),
  createWorkoutPlan: (plan) => request('/api/workout-plans', { method: 'POST', body: JSON.stringify(plan) }),
  deleteWorkoutPlan: (id) => request(`/api/workout-plans/${id}`, { method: 'DELETE' }),

  // Supplements CRUD
  getSupplements: () => request('/api/supplements'),
  createSupplement: (sup) => request('/api/supplements', { method: 'POST', body: JSON.stringify(sup) }),
  deleteSupplement: (id) => request(`/api/supplements/${id}`, { method: 'DELETE' }),

  // Supplement Orders CRUD
  getSupplementOrders: () => request('/api/supplement-orders'),
  createSupplementOrder: (order) => request('/api/supplement-orders', { method: 'POST', body: JSON.stringify(order) }),

  // ================= 3. CUSTOMER ENDPOINTS (Port 8080) =================
  getCustomers: () => request('/api/customers'),
  getCustomerById: (id) => request(`/api/customers/${id}`),
  registerCustomer: (customer) => request('/api/customers/register', { method: 'POST', body: JSON.stringify(customer) }),
  updateCustomer: (id, customer) => request(`/api/customers/${id}`, { method: 'PUT', body: JSON.stringify(customer) }),
  deleteCustomer: (id) => request(`/api/customers/${id}`, { method: 'DELETE' }),
};
