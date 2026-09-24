import React, { useState } from 'react';
import { api } from '../services/api';
import { Dumbbell, Shield, User, Lock, ArrowRight, UserPlus, Sparkles } from 'lucide-react';

export default function Login({ onLoginSuccess }) {
  const [isRegister, setIsRegister] = useState(false);
  const [username, setUsername] = useState('admin');
  const [password, setPassword] = useState('admin123');
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      if (isRegister) {
        const res = await api.register({
          username,
          password,
          fullName,
          email,
          phone,
          role: 'CUSTOMER'
        });
        onLoginSuccess(res);
      } else {
        const res = await api.login({ username, password });
        onLoginSuccess(res);
      }
    } catch (err) {
      setError(err.message || 'Authentication failed');
    } finally {
      setLoading(false);
    }
  };

  const setDemoCredentials = (u, p) => {
    setUsername(u);
    setPassword(p);
    setIsRegister(false);
    setError('');
  };

  return (
    <div style={{
      minHeight: '85vh',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      padding: '2rem 1rem'
    }}>
      <div style={{ width: '100%', maxWidth: '440px' }}>
        {/* Header Branding */}
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <div style={{
            display: 'inline-flex',
            alignItems: 'center',
            justifyContent: 'center',
            width: '68px',
            height: '68px',
            clipPath: 'polygon(0 0, calc(100% - 12px) 0, 100% 12px, 100% 100%, 12px 100%, 0 calc(100% - 12px))',
            borderRadius: '2px',
            background: 'linear-gradient(135deg, #ef4444, #991b1b)',
            boxShadow: '0 8px 24px rgba(239, 68, 68, 0.45)',
            marginBottom: '1rem'
          }}>
            <Dumbbell size={34} color="#ffffff" />
          </div>
          <h1 style={{ fontSize: '1.85rem', fontWeight: 800, color: '#f8fafc', letterSpacing: '-0.02em' }}>
            FITPULSE <span style={{ color: '#ef4444' }}>GYM</span>
          </h1>
          <p style={{ color: 'var(--text-muted)', fontSize: '0.9rem', marginTop: '0.25rem' }}>
            NIBM HDSE 26.2FT Enterprise Application System
          </p>
        </div>

        {/* Card */}
        <div className="glass-card" style={{ padding: '2rem' }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '1.5rem' }}>
            <h2 style={{ fontSize: '1.25rem', fontWeight: 700 }}>
              {isRegister ? 'Create Gym Account' : 'Welcome Back'}
            </h2>
            <button
              type="button"
              className="btn btn-sm btn-outline"
              onClick={() => { setIsRegister(!isRegister); setError(''); }}
            >
              {isRegister ? 'Back to Login' : 'Register'}
            </button>
          </div>

          {error && (
            <div style={{
              background: 'rgba(239, 68, 68, 0.15)',
              border: '1px solid rgba(239, 68, 68, 0.3)',
              color: '#f87171',
              padding: '0.75rem 1rem',
              borderRadius: '8px',
              fontSize: '0.85rem',
              marginBottom: '1.25rem'
            }}>
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit}>
            {isRegister && (
              <>
                <div className="form-group">
                  <label className="form-label">Full Name</label>
                  <input
                    type="text"
                    className="form-input"
                    placeholder="e.g. Kasun Perera"
                    value={fullName}
                    onChange={(e) => setFullName(e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label className="form-label">Email Address</label>
                  <input
                    type="email"
                    className="form-input"
                    placeholder="name@gmail.com"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <div className="form-group">
                  <label className="form-label">Phone Number</label>
                  <input
                    type="tel"
                    className="form-input"
                    placeholder="0771234567"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                  />
                </div>
              </>
            )}

            <div className="form-group">
              <label className="form-label">Username</label>
              <div style={{ position: 'relative' }}>
                <input
                  type="text"
                  className="form-input"
                  placeholder="Enter username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </div>
            </div>

            <div className="form-group">
              <label className="form-label">Password</label>
              <input
                type="password"
                className="form-input"
                placeholder="Enter password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            <button
              type="submit"
              className="btn btn-primary"
              style={{ width: '100%', marginTop: '0.5rem', padding: '0.85rem' }}
              disabled={loading}
            >
              {loading ? 'Processing...' : (isRegister ? 'Create Account' : 'Sign In')}
              <ArrowRight size={18} />
            </button>
          </form>

          {/* Quick Demo Switcher */}
          {!isRegister && (
            <div style={{ marginTop: '2rem', borderTop: '1px solid var(--border-color)', paddingTop: '1.25rem' }}>
              <div style={{ fontSize: '0.75rem', fontWeight: 700, color: 'var(--text-dim)', textTransform: 'uppercase', marginBottom: '0.75rem', display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
                <Sparkles size={14} color="#ef4444" /> Quick Demo Role Switcher
              </div>
              <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
                <button
                  type="button"
                  className="role-pill"
                  style={{ justifyContent: 'space-between', width: '100%' }}
                  onClick={() => setDemoCredentials('admin', 'admin123')}
                >
                  <span>👑 <strong>Owner:</strong> admin</span>
                  <span style={{ fontSize: '0.75rem', color: '#94a3b8' }}>admin123</span>
                </button>
                <button
                  type="button"
                  className="role-pill"
                  style={{ justifyContent: 'space-between', width: '100%' }}
                  onClick={() => setDemoCredentials('kasun', 'trainer123')}
                >
                  <span>🏋️ <strong>Trainer:</strong> kasun</span>
                  <span style={{ fontSize: '0.75rem', color: '#94a3b8' }}>trainer123</span>
                </button>
                <button
                  type="button"
                  className="role-pill"
                  style={{ justifyContent: 'space-between', width: '100%' }}
                  onClick={() => setDemoCredentials('kamal', 'customer123')}
                >
                  <span>🏃 <strong>Customer:</strong> kamal</span>
                  <span style={{ fontSize: '0.75rem', color: '#94a3b8' }}>customer123</span>
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
