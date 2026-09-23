import React, { useState } from 'react';
import OwnerPage from './pages/OwnerPage';
import TrainerPage from './pages/TrainerPage';
import CustomerPage from './pages/CustomerPage';
import { Dumbbell, Shield, Users, UserCheck } from 'lucide-react';

export default function App() {
  const [currentModule, setCurrentModule] = useState('OWNER');

  return (
    <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      {/* Top Navbar */}
      <header style={{
        background: '#1e293b',
        borderBottom: '1px solid #334155',
        padding: '1rem 2rem',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        flexWrap: 'wrap',
        gap: '1rem'
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.8rem' }}>
          <div style={{ background: '#0284c7', padding: '0.5rem', borderRadius: '8px', color: '#fff', display: 'flex' }}>
            <Dumbbell size={24} />
          </div>
          <div>
            <h2 style={{ fontSize: '1.25rem', fontWeight: 700, color: '#f8fafc', margin: 0 }}>
              FitPulse Gym System
            </h2>
            <div style={{ fontSize: '0.75rem', color: '#94a3b8' }}>
              NIBM HDSE 26.2FT &bull; Enterprise Application Development 02 (EAD 02)
            </div>
          </div>
        </div>

        {/* 3 Module Switcher Tabs */}
        <div style={{ display: 'flex', background: '#0f172a', padding: '0.3rem', borderRadius: '8px', gap: '0.4rem' }}>
          <button
            onClick={() => setCurrentModule('OWNER')}
            style={{
              background: currentModule === 'OWNER' ? '#0284c7' : 'transparent',
              color: currentModule === 'OWNER' ? '#fff' : '#94a3b8',
              border: 'none',
              padding: '0.5rem 1.1rem',
              borderRadius: '6px',
              cursor: 'pointer',
              fontWeight: 600,
              fontSize: '0.85rem',
              display: 'flex',
              alignItems: 'center',
              gap: '0.4rem'
            }}
          >
            <Shield size={16} /> Owner Module
          </button>

          <button
            onClick={() => setCurrentModule('TRAINER')}
            style={{
              background: currentModule === 'TRAINER' ? '#0284c7' : 'transparent',
              color: currentModule === 'TRAINER' ? '#fff' : '#94a3b8',
              border: 'none',
              padding: '0.5rem 1.1rem',
              borderRadius: '6px',
              cursor: 'pointer',
              fontWeight: 600,
              fontSize: '0.85rem',
              display: 'flex',
              alignItems: 'center',
              gap: '0.4rem'
            }}
          >
            <Users size={16} /> Trainer Module
          </button>

          <button
            onClick={() => setCurrentModule('CUSTOMER')}
            style={{
              background: currentModule === 'CUSTOMER' ? '#10b981' : 'transparent',
              color: currentModule === 'CUSTOMER' ? '#fff' : '#94a3b8',
              border: 'none',
              padding: '0.5rem 1.1rem',
              borderRadius: '6px',
              cursor: 'pointer',
              fontWeight: 600,
              fontSize: '0.85rem',
              display: 'flex',
              alignItems: 'center',
              gap: '0.4rem'
            }}
          >
            <UserCheck size={16} /> Customer Module
          </button>
        </div>
      </header>

      {/* Main Content Area */}
      <main style={{ flex: 1, padding: '2rem', maxWidth: '1400px', width: '100%', margin: '0 auto' }}>
        {currentModule === 'OWNER' && <OwnerPage />}
        {currentModule === 'TRAINER' && <TrainerPage />}
        {currentModule === 'CUSTOMER' && <CustomerPage />}
      </main>

      {/* Footer */}
      <footer style={{ borderTop: '1px solid #334155', padding: '1rem 2rem', textAlign: 'center', fontSize: '0.8rem', color: '#64748b' }}>
        National Institute of Business Management (NIBM) &bull; School of Computing and Engineering &bull; Group Coursework
      </footer>
    </div>
  );
}
