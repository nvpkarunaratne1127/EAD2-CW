import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import OwnerDashboard from './pages/OwnerDashboard';
import TrainerDashboard from './pages/TrainerDashboard';
import CustomerDashboard from './pages/CustomerDashboard';
import { api } from './services/api';
import { Sparkles, Shield, User, Dumbbell } from 'lucide-react';

export default function App() {
  const [currentUser, setCurrentUser] = useState(() => {
    const saved = localStorage.getItem('gym_user');
    return saved ? JSON.parse(saved) : null;
  });
  const [activeTab, setActiveTab] = useState('dashboard');

  const handleLoginSuccess = (user) => {
    setCurrentUser(user);
    localStorage.setItem('gym_user', JSON.stringify(user));
    setActiveTab('dashboard');
  };

  const handleLogout = () => {
    setCurrentUser(null);
    localStorage.removeItem('gym_user');
    setActiveTab('dashboard');
  };

  const quickSwitch = async (username, password) => {
    try {
      const res = await api.login({ username, password });
      handleLoginSuccess(res);
    } catch (e) {
      console.error('Quick switch failed:', e);
    }
  };

  return (
    <div className="app-container">
      <Navbar
        currentUser={currentUser}
        onLogout={handleLogout}
        activeTab={activeTab}
        setActiveTab={setActiveTab}
      />

      <main className="content-wrapper">
        {!currentUser ? (
          <Login onLoginSuccess={handleLoginSuccess} />
        ) : (
          <>
            {currentUser.role === 'OWNER' && (
              <OwnerDashboard activeTab={activeTab} />
            )}
            {currentUser.role === 'TRAINER' && (
              <TrainerDashboard currentUser={currentUser} activeTab={activeTab} />
            )}
            {currentUser.role === 'CUSTOMER' && (
              <CustomerDashboard currentUser={currentUser} activeTab={activeTab} />
            )}
          </>
        )}
      </main>

      {/* Floating Presentation Quick Role Switcher Bar */}
      {currentUser && (
        <div style={{
          position: 'fixed',
          bottom: '1rem',
          right: '1rem',
          zIndex: 999,
          background: 'rgba(14, 14, 18, 0.96)',
          backdropFilter: 'blur(18px)',
          border: '1px solid rgba(255, 255, 255, 0.12)',
          borderTop: '2px solid #ef4444',
          borderRadius: '3px',
          padding: '0.45rem 0.8rem',
          display: 'flex',
          alignItems: 'center',
          gap: '0.5rem',
          boxShadow: '0 10px 30px rgba(0, 0, 0, 0.85), 0 0 15px rgba(239, 68, 68, 0.2)'
        }}>
          <span style={{ fontSize: '0.72rem', fontWeight: 800, textTransform: 'uppercase', letterSpacing: '0.06em', color: '#f87171', display: 'flex', alignItems: 'center', gap: '0.35rem' }}>
            <Sparkles size={13} color="#ef4444" /> Quick Role:
          </span>
          <button
            className={`btn btn-sm ${currentUser.role === 'OWNER' ? 'btn-primary' : 'btn-outline'}`}
            style={{ padding: '0.3rem 0.65rem' }}
            onClick={() => quickSwitch('admin', 'admin123')}
          >
            Owner
          </button>
          <button
            className={`btn btn-sm ${currentUser.role === 'TRAINER' ? 'btn-primary' : 'btn-outline'}`}
            style={{ padding: '0.3rem 0.65rem' }}
            onClick={() => quickSwitch('kasun', 'trainer123')}
          >
            Trainer
          </button>
          <button
            className={`btn btn-sm ${currentUser.role === 'CUSTOMER' ? 'btn-primary' : 'btn-outline'}`}
            style={{ padding: '0.3rem 0.65rem' }}
            onClick={() => quickSwitch('kamal', 'customer123')}
          >
            Customer
          </button>
        </div>
      )}

      {/* Footer */}
      <footer style={{
        borderTop: '1px solid rgba(255, 255, 255, 0.06)',
        padding: '1.5rem',
        textAlign: 'center',
        fontSize: '0.8rem',
        color: 'var(--text-dim)',
        marginTop: 'auto'
      }}>
        <div>National Institute of Business Management (NIBM) &bull; School of Computing and Engineering</div>
        <div style={{ marginTop: '0.25rem' }}>HDSE 26.2FT &bull; Enterprise Application Development 02 (EAD 02) Coursework System</div>
      </footer>
    </div>
  );
}
