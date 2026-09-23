import React, { useState, useEffect } from 'react';
import { api } from '../api';
import { UserCheck, Trash2, Plus, Calculator, CheckCircle2 } from 'lucide-react';

export default function CustomerPage() {
  const [customers, setCustomers] = useState([]);
  
  // Registration Form State
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [phone, setPhone] = useState('');

  // Dynamic Pricing Calculator State
  const [hasTrainer, setHasTrainer] = useState(false);
  const [hasTreadmill, setHasTreadmill] = useState(false);

  // Pricing formula
  const basePrice = 2500;
  const trainerPrice = hasTrainer ? 2000 : 0;
  const treadmillPrice = hasTreadmill ? 1000 : 0;
  const totalPrice = basePrice + trainerPrice + treadmillPrice;

  const loadCustomers = async () => {
    try {
      const res = await api.getCustomers().catch(() => []);
      setCustomers(res || []);
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => {
    loadCustomers();
  }, []);

  const handleRegister = async (e) => {
    e.preventDefault();
    if (!name || !email || !password) return;
    try {
      await api.registerCustomer({
        name,
        email,
        password,
        phone: phone || '0771234567'
      });
      setName('');
      setEmail('');
      setPassword('');
      setPhone('');
      alert('Customer registered successfully!');
      loadCustomers();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleDeleteCustomer = async (id) => {
    if (!window.confirm('Delete this customer account?')) return;
    await api.deleteCustomer(id);
    loadCustomers();
  };

  return (
    <div>
      {/* Top Banner: Dynamic Membership Pricing Calculator */}
      <div style={{ background: '#1e293b', padding: '1.5rem', borderRadius: '8px', marginBottom: '1.5rem', border: '1px solid #334155' }}>
        <h3 style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', color: '#38bdf8', margin: '0 0 1rem 0' }}>
          <Calculator size={22} /> Dynamic Membership Pricing Engine (LKR)
        </h3>
        <p style={{ color: '#94a3b8', fontSize: '0.9rem', marginBottom: '1rem' }}>
          Real-time package fee calculator conforming to NIBM coursework business logic:
        </p>

        <div style={{ display: 'flex', gap: '1.5rem', flexWrap: 'wrap', alignItems: 'center' }}>
          <label style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', cursor: 'pointer', background: '#0f172a', padding: '0.6rem 1rem', borderRadius: '6px' }}>
            <input type="checkbox" checked={true} disabled />
            <span>Base Gym Access (<strong>2,500 LKR</strong>)</span>
          </label>

          <label style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', cursor: 'pointer', background: '#0f172a', padding: '0.6rem 1rem', borderRadius: '6px', border: hasTrainer ? '1px solid #0284c7' : '1px solid transparent' }}>
            <input type="checkbox" checked={hasTrainer} onChange={e => setHasTrainer(e.target.checked)} />
            <span>Personal Trainer (<strong>+2,000 LKR</strong>)</span>
          </label>

          <label style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', cursor: 'pointer', background: '#0f172a', padding: '0.6rem 1rem', borderRadius: '6px', border: hasTreadmill ? '1px solid #0284c7' : '1px solid transparent' }}>
            <input type="checkbox" checked={hasTreadmill} onChange={e => setHasTreadmill(e.target.checked)} />
            <span>Treadmill / Cardio Pass (<strong>+1,000 LKR</strong>)</span>
          </label>

          <div style={{ marginLeft: 'auto', background: '#0f172a', padding: '0.6rem 1.2rem', borderRadius: '6px', borderLeft: '4px solid #10b981' }}>
            <span style={{ color: '#94a3b8', fontSize: '0.8rem' }}>Total Monthly Fee: </span>
            <strong style={{ color: '#10b981', fontSize: '1.3rem' }}>{totalPrice.toLocaleString()} LKR</strong>
          </div>
        </div>
      </div>

      {/* Grid: Registration Form & Customers Directory */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '1.5rem' }}>
        {/* Register Customer Form */}
        <div style={{ background: '#1e293b', padding: '1.5rem', borderRadius: '8px' }}>
          <h4 style={{ color: '#f8fafc', margin: '0 0 1rem 0', display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
            <Plus size={18} color="#38bdf8" /> Customer Registration
          </h4>

          <form onSubmit={handleRegister} style={{ display: 'flex', flexDirection: 'column', gap: '0.8rem' }}>
            <div>
              <label style={{ fontSize: '0.8rem', color: '#94a3b8' }}>Full Name *</label>
              <input placeholder="e.g. Kamal Bandara" value={name} onChange={e => setName(e.target.value)} required style={{ width: '100%', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff', marginTop: '0.2rem' }} />
            </div>

            <div>
              <label style={{ fontSize: '0.8rem', color: '#94a3b8' }}>Email Address *</label>
              <input type="email" placeholder="kamal@gmail.com" value={email} onChange={e => setEmail(e.target.value)} required style={{ width: '100%', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff', marginTop: '0.2rem' }} />
            </div>

            <div>
              <label style={{ fontSize: '0.8rem', color: '#94a3b8' }}>Password *</label>
              <input type="password" placeholder="••••••••" value={password} onChange={e => setPassword(e.target.value)} required style={{ width: '100%', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff', marginTop: '0.2rem' }} />
            </div>

            <div>
              <label style={{ fontSize: '0.8rem', color: '#94a3b8' }}>Phone Number</label>
              <input placeholder="0771234567" value={phone} onChange={e => setPhone(e.target.value)} style={{ width: '100%', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff', marginTop: '0.2rem' }} />
            </div>

            <button type="submit" style={{ marginTop: '0.5rem', background: '#0284c7', color: '#fff', border: 'none', padding: '0.7rem', borderRadius: '4px', cursor: 'pointer', fontWeight: 600 }}>
              Register Customer (201 Created)
            </button>
          </form>
        </div>

        {/* Registered Customers Directory */}
        <div style={{ background: '#1e293b', padding: '1.5rem', borderRadius: '8px' }}>
          <h4 style={{ color: '#f8fafc', margin: '0 0 1rem 0', display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
            <UserCheck size={18} color="#10b981" /> Registered Customers ({customers.length})
          </h4>

          <table style={{ background: '#0f172a', borderRadius: '6px', overflow: 'hidden' }}>
            <thead>
              <tr style={{ background: '#334155', color: '#94a3b8' }}>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {customers.length === 0 ? (
                <tr><td colSpan="5" style={{ textAlign: 'center', color: '#64748b' }}>No customer accounts registered yet</td></tr>
              ) : (
                customers.map(c => (
                  <tr key={c.id}>
                    <td>#{c.id}</td>
                    <td><strong>{c.name}</strong></td>
                    <td style={{ color: '#38bdf8' }}>{c.email}</td>
                    <td>{c.phone || '—'}</td>
                    <td>
                      <button onClick={() => handleDeleteCustomer(c.id)} style={{ background: '#ef4444', color: '#fff', border: 'none', padding: '0.3rem 0.6rem', borderRadius: '4px', cursor: 'pointer' }}>
                        <Trash2 size={14} />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
