import React, { useState } from 'react';
import Navbar from './components/Navbar';
import FitPulseLanding from './pages/FitPulseLanding';
import Login from './pages/Login';
import OwnerDashboard from './pages/OwnerDashboard';
import TrainerDashboard from './pages/TrainerDashboard';
import CustomerDashboard from './pages/CustomerDashboard';
import Modal from './components/Modal';
import { api } from './services/api';
import { Sparkles, Shield, User, Dumbbell } from 'lucide-react';

export default function App() {
  const [currentUser, setCurrentUser] = useState(() => {
    const saved = localStorage.getItem('gym_user');
    return saved ? JSON.parse(saved) : null;
  });
  const [activeTab, setActiveTab] = useState('dashboard');
  const [authModalOpen, setAuthModalOpen] = useState(false);

  const handleLoginSuccess = (user) => {
    setCurrentUser(user);
    localStorage.setItem('gym_user', JSON.stringify(user));
    setActiveTab('dashboard');
    setAuthModalOpen(false);
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
        onOpenAuth={() => setAuthModalOpen(true)}
      />

      <main className="content-wrapper">
        {!currentUser ? (
          <FitPulseLanding
            onLoginSuccess={handleLoginSuccess}
            onOpenAuthModal={() => setAuthModalOpen(true)}
          />
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

      {/* Auth Modal for Visitors */}
      {authModalOpen && !currentUser && (
        <div style={{
          position: 'fixed',
          inset: 0,
          backgroundColor: 'rgba(0, 0, 0, 0.85)',
          backdropFilter: 'blur(8px)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          zIndex: 9999,
          padding: '1rem'
        }}>
          <Login 
            onLoginSuccess={handleLoginSuccess} 
            onClose={() => setAuthModalOpen(false)} 
          />
        </div>
      )}

      {/* Floating Presentation Quick Role Switcher Bar */}
      {currentUser && (
        <div style={{
          position: 'fixed',
          bottom: '1.25rem',
          right: '1.25rem',
          zIndex: 999,
          background: 'rgba(10, 10, 10, 0.98)',
          border: '1px solid #333333',
          borderTop: '2px solid #FF0000',
          padding: '0.5rem 0.9rem',
          display: 'flex',
          alignItems: 'center',
          gap: '0.6rem',
          boxShadow: '0 12px 36px rgba(0, 0, 0, 0.95), 0 0 16px rgba(255, 0, 0, 0.25)'
        }}>
          <span style={{ 
            fontFamily: "'Oswald', sans-serif", 
            fontSize: '0.78rem', 
            fontWeight: 700, 
            textTransform: 'uppercase', 
            letterSpacing: '1px', 
            color: '#FF0000', 
            display: 'flex', 
            alignItems: 'center', 
            gap: '0.4rem' 
          }}>
            <Sparkles size={14} color="#FF0000" /> Viva Switch:
          </span>
          <button
            className={`btn btn-sm ${currentUser.role === 'OWNER' ? 'btn-primary' : 'btn-outline'}`}
            style={{ padding: '0.35rem 0.75rem' }}
            onClick={() => quickSwitch('admin', 'admin123')}
          >
            Owner
          </button>
          <button
            className={`btn btn-sm ${currentUser.role === 'TRAINER' ? 'btn-primary' : 'btn-outline'}`}
            style={{ padding: '0.35rem 0.75rem' }}
            onClick={() => quickSwitch('kasun', 'trainer123')}
          >
            Trainer
          </button>
          <button
            className={`btn btn-sm ${currentUser.role === 'CUSTOMER' ? 'btn-primary' : 'btn-outline'}`}
            style={{ padding: '0.35rem 0.75rem' }}
            onClick={() => quickSwitch('kamal', 'customer123')}
          >
            Customer
          </button>
        </div>
      )}

      {/* FitPulse Footer */}
      <footer style={{
        background: '#080808',
        borderTop: '1px solid #222222',
        padding: '2.5rem 1.5rem',
        textAlign: 'center',
        marginTop: 'auto'
      }}>
        <div style={{ maxWidth: '1200px', margin: '0 auto' }}>
          <div style={{ 
            fontFamily: "'Oswald', sans-serif", 
            fontSize: '1.25rem', 
            fontWeight: 700, 
            letterSpacing: '2px', 
            textTransform: 'uppercase', 
            color: '#FFFFFF',
            marginBottom: '0.5rem'
          }}>
            FITPULSE <span style={{ color: '#FF0000' }}>GYM &amp; FITNESS</span>
          </div>
          <div style={{ fontSize: '0.85rem', color: '#888888', marginBottom: '0.25rem' }}>
            National Institute of Business Management (NIBM) &bull; School of Computing and Engineering
          </div>
          <div style={{ fontSize: '0.78rem', color: '#666666', textTransform: 'uppercase', letterSpacing: '1px' }}>
            HDSE 26.2FT &bull; Enterprise Application Development 02 (EAD02)
          </div>
        </div>
      </footer>
    </div>
  );
}
