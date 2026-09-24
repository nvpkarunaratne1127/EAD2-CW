import React, { useState } from 'react';
import { api } from '../services/api';
import { Dumbbell, Shield, User, Lock, ArrowRight, UserPlus, Sparkles, X } from 'lucide-react';

export default function Login({ onLoginSuccess, onClose }) {
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
      width: '100%',
      maxWidth: '460px',
      margin: '0 auto',
      position: 'relative'
    }}>
      {/* Header Branding */}
      <div style={{ textAlign: 'center', marginBottom: '1.75rem' }}>
        <div style={{
          display: 'inline-flex',
          alignItems: 'center',
          justifyContent: 'center',
          width: '54px',
          height: '54px',
          borderRadius: '2px',
          background: '#FF0000',
          boxShadow: '0 6px 20px rgba(255, 0, 0, 0.45)',
          marginBottom: '0.85rem'
        }}>
          <Dumbbell size={28} color="#FFFFFF" />
        </div>
        <h1 style={{ 
          fontFamily: "'Oswald', sans-serif", 
          fontSize: '2rem', 
          fontWeight: 700, 
          letterSpacing: '1.5px', 
          color: '#FFFFFF',
          textTransform: 'uppercase'
        }}>
          FITPULSE <span style={{ color: '#FF0000' }}>GYM</span>
        </h1>
        <span className="section-subtitle" style={{ letterSpacing: '1.5px', margin: '4px 0 0 0' }}>
          NIBM HDSE 26.2FT &bull; ENTERPRISE SYSTEM
        </span>
      </div>

      {/* FitPulse Solid Card */}
      <div className="glass-card" style={{ padding: '2.25rem', position: 'relative' }}>
        {onClose && (
          <button 
            onClick={onClose}
            style={{
              position: 'absolute',
              top: '1.25rem',
              right: '1.25rem',
              background: 'transparent',
              border: 'none',
              color: '#888',
              cursor: 'pointer'
            }}
          >
            <X size={20} />
          </button>
        )}

        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '1.75rem' }}>
          <h2 style={{ fontSize: '1.35rem', margin: 0 }}>
            {isRegister ? 'JOIN FITPULSE CLUB' : 'PORTAL SIGN IN'}
          </h2>
          <button
            type="button"
            className="btn btn-sm btn-outline"
            onClick={() => { setIsRegister(!isRegister); setError(''); }}
          >
            {isRegister ? 'BACK TO LOGIN' : 'NEW MEMBER?'}
          </button>
        </div>

        {error && (
          <div style={{
            background: 'rgba(255, 0, 0, 0.15)',
            border: '1px solid rgba(255, 0, 0, 0.4)',
            color: '#ff8888',
            padding: '0.75rem 1rem',
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
                  className="form-control"
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
                  className="form-control"
                  placeholder="name@gmail.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
              <div className="form-group">
                <label className="form-label">Phone Number</label>
                <input
                  type="tel"
                  className="form-control"
                  placeholder="0771234567"
                  value={phone}
                  onChange={(e) => setPhone(e.target.value)}
                />
              </div>
            </>
          )}

          <div className="form-group">
            <label className="form-label">Username</label>
            <input
              type="text"
              className="form-control"
              placeholder="Enter username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary"
            style={{ width: '100%', marginTop: '0.75rem', padding: '0.9rem' }}
            disabled={loading}
          >
            {loading ? 'AUTHENTICATING...' : (isRegister ? 'REGISTER & ENTER' : 'SIGN IN')}
            <ArrowRight size={16} />
          </button>
        </form>

        {/* Quick Demo Switcher */}
        {!isRegister && (
          <div style={{ marginTop: '2rem', borderTop: '1px solid #222222', paddingTop: '1.25rem' }}>
            <div style={{ 
              fontFamily: "'Oswald', sans-serif", 
              fontSize: '0.78rem', 
              fontWeight: 600, 
              color: '#FF0000', 
              textTransform: 'uppercase', 
              letterSpacing: '1.5px', 
              marginBottom: '0.75rem', 
              display: 'flex', 
              alignItems: 'center', 
              gap: '0.4rem' 
            }}>
              <Sparkles size={14} color="#FF0000" /> 1-Click Demo Accounts (Viva Presentation)
            </div>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
              <button
                type="button"
                className="checkbox-option"
                style={{ padding: '0.65rem 1rem', justifyContent: 'space-between', width: '100%' }}
                onClick={() => setDemoCredentials('admin', 'admin123')}
              >
                <span>👑 <strong style={{ color: '#FFFFFF' }}>Owner Panel:</strong> admin</span>
                <span style={{ fontSize: '0.78rem', color: '#888888' }}>admin123</span>
              </button>
              <button
                type="button"
                className="checkbox-option"
                style={{ padding: '0.65rem 1rem', justifyContent: 'space-between', width: '100%' }}
                onClick={() => setDemoCredentials('kasun', 'trainer123')}
              >
                <span>🏋️ <strong style={{ color: '#FFFFFF' }}>Trainer Dashboard:</strong> kasun</span>
                <span style={{ fontSize: '0.78rem', color: '#888888' }}>trainer123</span>
              </button>
              <button
                type="button"
                className="checkbox-option"
                style={{ padding: '0.65rem 1rem', justifyContent: 'space-between', width: '100%' }}
                onClick={() => setDemoCredentials('kamal', 'customer123')}
              >
                <span>🏃 <strong style={{ color: '#FFFFFF' }}>Customer Portal:</strong> kamal</span>
                <span style={{ fontSize: '0.78rem', color: '#888888' }}>customer123</span>
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
