import React from 'react';
import { Dumbbell, Shield, UserCheck, Users, LogOut, Activity, Lock } from 'lucide-react';

export default function Navbar({ currentUser, onLogout, activeTab, setActiveTab, onOpenAuth }) {
  return (
    <nav style={{
      background: 'rgba(8, 8, 8, 0.96)',
      backdropFilter: 'blur(16px)',
      borderBottom: '1px solid #222222',
      borderTop: '2px solid #FF0000',
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
        {/* Zacson Brand Logo */}
        <div 
          style={{ display: 'flex', alignItems: 'center', gap: '0.85rem', cursor: 'pointer' }} 
          onClick={() => setActiveTab('dashboard')}
        >
          <img 
            src="/zacson/logo/logo.png" 
            alt="FitPulse Zacson Gym" 
            style={{ height: '36px', objectFit: 'contain' }}
            onError={(e) => { e.target.style.display = 'none'; }}
          />
          <div>
            <div style={{ 
              fontFamily: "'Oswald', sans-serif", 
              fontSize: '1.45rem', 
              fontWeight: 700, 
              letterSpacing: '1.5px', 
              textTransform: 'uppercase', 
              color: '#FFFFFF', 
              display: 'flex', 
              alignItems: 'center', 
              gap: '0.4rem',
              lineHeight: 1
            }}>
              FITPULSE <span style={{ color: '#FF0000' }}>GYM</span>
            </div>
            <div style={{ fontSize: '0.68rem', color: '#888888', letterSpacing: '2px', textTransform: 'uppercase', marginTop: '3px' }}>
              NIBM HDSE 26.2FT &bull; EAD02 SYSTEM
            </div>
          </div>
        </div>

        {/* Navigation Links for Logged-In User */}
        {currentUser ? (
          <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center', flexWrap: 'wrap' }}>
            {currentUser.role === 'OWNER' && (
              <>
                <button
                  className={`btn btn-sm ${activeTab === 'dashboard' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('dashboard')}
                >
                  <Activity size={15} /> Overview
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'equipment' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('equipment')}
                >
                  <Dumbbell size={15} /> Equipment
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
                  <Users size={15} /> Assigned Clients
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'equipment' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('equipment')}
                >
                  <Dumbbell size={15} /> Gym Equipment
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
                  Routine &amp; Diet
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'equipment' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('equipment')}
                >
                  Equipment
                </button>
                <button
                  className={`btn btn-sm ${activeTab === 'supplements' ? 'btn-primary' : 'btn-outline'}`}
                  onClick={() => setActiveTab('supplements')}
                >
                  Supplements
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
        ) : (
          /* Public Navigation for Non-Logged-In Visitors (Zacson Style) */
          <div style={{ display: 'flex', gap: '1.25rem', alignItems: 'center' }}>
            <a href="#hero" style={{ color: '#E0E0E0', textDecoration: 'none', fontFamily: "'Oswald', sans-serif", fontSize: '0.88rem', letterSpacing: '1.5px', textTransform: 'uppercase' }}>
              Home
            </a>
            <a href="#features" style={{ color: '#A0A0A0', textDecoration: 'none', fontFamily: "'Oswald', sans-serif", fontSize: '0.88rem', letterSpacing: '1.5px', textTransform: 'uppercase' }}>
              Facilities
            </a>
            <a href="#pricing" style={{ color: '#A0A0A0', textDecoration: 'none', fontFamily: "'Oswald', sans-serif", fontSize: '0.88rem', letterSpacing: '1.5px', textTransform: 'uppercase' }}>
              Pricing
            </a>
            <a href="#trainers" style={{ color: '#A0A0A0', textDecoration: 'none', fontFamily: "'Oswald', sans-serif", fontSize: '0.88rem', letterSpacing: '1.5px', textTransform: 'uppercase' }}>
              Trainers
            </a>
            <a href="#supplements" style={{ color: '#A0A0A0', textDecoration: 'none', fontFamily: "'Oswald', sans-serif", fontSize: '0.88rem', letterSpacing: '1.5px', textTransform: 'uppercase' }}>
              Supplements
            </a>
          </div>
        )}

        {/* User Profile Badge & Logout / Sign In */}
        {currentUser ? (
          <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
            <div style={{ textAlign: 'right' }}>
              <div style={{ fontSize: '0.88rem', fontWeight: 700, color: '#FFFFFF' }}>
                {currentUser.fullName}
              </div>
              <span className={`badge ${
                currentUser.role === 'OWNER' ? 'badge-red' :
                currentUser.role === 'TRAINER' ? 'badge-amber' : 'badge-emerald'
              }`}>
                {currentUser.role}
              </span>
            </div>

            <button 
              className="btn btn-outline btn-sm btn-danger" 
              onClick={onLogout} 
              title="Logout"
              style={{ padding: '0.45rem 0.75rem' }}
            >
              <LogOut size={15} />
            </button>
          </div>
        ) : (
          <button 
            className="btn btn-primary btn-sm"
            onClick={onOpenAuth}
            style={{ padding: '0.55rem 1.35rem' }}
          >
            <Lock size={15} /> Sign In / Demo
          </button>
        )}
      </div>
    </nav>
  );
}
