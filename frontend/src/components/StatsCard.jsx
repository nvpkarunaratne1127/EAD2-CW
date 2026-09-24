import React from 'react';

export default function StatsCard({ title, value, subtitle, icon: Icon, color = '#FF0000' }) {
  return (
    <div className="glass-card" style={{ position: 'relative', overflow: 'hidden' }}>
      <div style={{
        position: 'absolute',
        top: 0,
        right: 0,
        width: '80px',
        height: '80px',
        background: `radial-gradient(circle, ${color}25 0%, transparent 70%)`,
        pointerEvents: 'none'
      }} />
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '0.75rem' }}>
        <span style={{ 
          fontFamily: "'Oswald', sans-serif",
          fontSize: '0.85rem', 
          fontWeight: 600, 
          color: '#A0A0A0',
          textTransform: 'uppercase',
          letterSpacing: '1px'
        }}>
          {title}
        </span>
        {Icon && (
          <div style={{
            background: `${color}18`,
            color: color,
            padding: '0.5rem',
            border: `1px solid ${color}45`,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center'
          }}>
            <Icon size={18} />
          </div>
        )}
      </div>
      <div style={{ 
        fontFamily: "'Oswald', sans-serif", 
        fontSize: '2rem', 
        fontWeight: 700, 
        color: '#FFFFFF', 
        letterSpacing: '0.5px' 
      }}>
        {value}
      </div>
      {subtitle && (
        <div style={{ fontSize: '0.78rem', color: '#777777', marginTop: '0.35rem' }}>
          {subtitle}
        </div>
      )}
    </div>
  );
}
