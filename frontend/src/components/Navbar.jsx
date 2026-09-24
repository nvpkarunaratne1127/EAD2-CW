import React from 'react';
import { Dumbbell, Shield, UserCheck, Users, LogOut, Activity } from 'lucide-react';

export default function Navbar({ currentUser, onLogout, activeTab, setActiveTab, onQuickSwitchRole }) {
  return (
    <nav style={{
      background: 'rgba(12, 12, 16, 0.94)',
      backdropFilter: 'blur(18px)',
      borderBottom: '1px solid rgba(255, 255, 255, 0.08)',
      position: 'sticky',
      top: 0,
      zIndex: 100
    }}>
      <div style={{
        maxWidth: '1320px',
        margin: '0 auto',
        padding: '0.85rem 1.5rem',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        flexWrap: 'wrap',
        gap: '1rem'
      }}>
        {/* Brand */}
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', cursor: 'pointer' }} onClick={() => setActiveTab('dashboard')}>
          <div style={{
            background: 'linear-gradient(135deg, #ef4444, #991b1b)',
            width: '42px',
            height: '42px',
            clipPath: 'polygon(0 0, calc(100% - 8px) 0, 100% 8px, 100% 100%, 8px 100%, 0 calc(100% - 8px))',
            borderRadius: '2px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            boxShadow: '0 4px 14px rgba(239, 68, 68, 0.45)'
          }}>
            <Dumbbell size={22} color="#ffffff" />
          </div>
          <div>
            <div style={{ fontSize: '1.2rem', fontWeight: 800, letterSpacing: '-0.02em', color: '#f8fafc', display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
              FITPULSE <span style={{ color: '#ef4444' }}>GYM</span>
            </div>
            <div style={{ fontSize: '0.7rem', color: '#a1a1aa', letterSpacing: '0.04em' }}>
              NIBM HDSE 26.2FT &bull; EAD02
            </div>
          </div>
        </div>

        {/* Role-Specific Navigation Links */}
        {currentUser && (
          <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
            {currentUser.role === 'OWNER' && (
              <>
                <button
                  className={`btn btn-sm ${activeTab === 'dashboard' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('dashboard')}
                >
                  <Activity size={16} /> Overview
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'equipment' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('equipment')}
                >
                  <Dumbbell size={16} /> Equipment
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'supplements' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('supplements')}
                >
                  Supplements
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'trainers' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('trainers')}
                >
                  Trainers
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'billing' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('billing')}
                >
                  Revenue &amp; Invoices
                </button>
              </>
            )}

            {currentUser.role === 'TRAINER' && (
              <>
                <button
                  className={`btn btn-sm ${activeTab === 'clients' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('clients')}
                >
                  <Users size={16} /> Assigned Clients
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'equipment' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('equipment')}
                >
                  <Dumbbell size={16} /> Gym Equipment
                </button>
              </>
            )}

            {currentUser.role === 'CUSTOMER' && (
              <>
                <button
                  className={`btn btn-sm ${activeTab === 'membership' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('membership')}
                >
                  Plan &amp; Pricing
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'workout' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('workout')}
                >
                  My Routine &amp; Diet
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'equipment' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('equipment')}
                >
                  Equipment Catalog
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'supplements' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('supplements')}
                >
                  Supplement Store
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'invoices' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('invoices')}
                >
                  Invoices
                </button>
              </>
            )}
          </div>
        )}

        {/* User Badge & Logout */}
        {currentUser ? (
          <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
            <div style={{ textAlign: 'right' }}>
              <div style={{ fontSize: '0.85rem', fontWeight: 700, color: '#f8fafc' }}>
                {currentUser.fullName}
              </div>
              <span className={`badge ${
                currentUser.role === 'OWNER' ? 'badge-purple' :
                currentUser.role === 'TRAINER' ? 'badge-amber' : 'badge-cyan'
              }`}>
                {currentUser.role}
              </span>
            </div>

            <button className="btn btn-outline btn-sm btn-danger" onClick={onLogout} title="Logout">
              <LogOut size={16} />
            </button>
          </div>
        ) : (
          <div style={{ fontSize: '0.85rem', color: '#94a3b8' }}>
            Please log in
          </div>
        )}
      </div>
    </nav>
  );
}
