import React, { useState, useEffect } from 'react';
import { api } from '../services/api';
import { 
  Dumbbell, Shield, UserCheck, Users, ArrowRight, 
  Check, Zap, Package, ShoppingCart, Award, Sparkles, Lock 
} from 'lucide-react';

export default function FitPulseLanding({ onLoginSuccess, onOpenAuthModal }) {
  const [equipmentCount, setEquipmentCount] = useState(16);
  const [supplements, setSupplements] = useState([]);
  const [trainers, setTrainers] = useState([]);
  
  // Interactive Dynamic Pricing Preview State
  const [hasTrainer, setHasTrainer] = useState(false);
  const [hasTreadmill, setHasTreadmill] = useState(false);
  const [pricingPreview, setPricingPreview] = useState(null);

  useEffect(() => {
    // Fetch initial showcase data from backend
    api.getTrainers().then(data => setTrainers(data || [])).catch(() => {});
    api.getSupplements().then(data => setSupplements((data || []).slice(0, 4))).catch(() => {});
    api.getEquipment().then(data => setEquipmentCount((data || []).length)).catch(() => {});
  }, []);

  useEffect(() => {
    api.calculatePrice(hasTrainer, hasTreadmill)
      .then(res => setPricingPreview(res))
      .catch(() => {});
  }, [hasTrainer, hasTreadmill]);

  const trainerImages = [
    '/gym/gallery/team1.png',
    '/gym/gallery/team2.png',
    '/gym/gallery/team3.png'
  ];

  return (
    <div>
      {/* ========================================================= */}
      {/* 1. HERO SECTION (Using BG1.png)                           */}
      {/* ========================================================= */}
      <section id="hero" className="fitpulse-hero">
        <div style={{ maxWidth: '850px' }}>
          <span className="section-subtitle">
            // HI, THIS IS FITPULSE GYM &amp; FITNESS CLUB
          </span>
          <h1 className="fitpulse-hero-title">
            BUILD PERFECT BODY WITH CLEAN DISCIPLINE
          </h1>
          <p className="fitpulse-hero-desc">
            Experience world-class gym facilities, certified 1-on-1 personal coaching, 
            high-grade Olympic equipment, and dynamic membership pricing designed to empower your fitness journey.
          </p>
          <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap' }}>
            <button className="btn btn-primary" onClick={onOpenAuthModal}>
              <Lock size={16} /> SIGN IN / ENTER SYSTEM
            </button>
            <a href="#pricing" className="btn btn-outline">
              VIEW PRICING PLANS
            </a>
            <a href="#trainers" className="btn btn-outline">
              OUR COACHES
            </a>
          </div>
        </div>
      </section>

      {/* ========================================================= */}
      {/* 2. TRAINING CATEGORIES & FACILITIES                       */}
      {/* ========================================================= */}
      <section id="features" style={{ marginBottom: '4rem' }}>
        <div style={{ textAlign: 'center', marginBottom: '2.5rem' }}>
          <span className="section-subtitle">// TRAINING CATEGORIES</span>
          <h2 style={{ fontSize: '2.3rem' }}>WHAT WE OFFER YOU</h2>
          <div style={{ width: '60px', height: '3px', background: '#FF0000', margin: '0.75rem auto 0 auto' }}></div>
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(360px, 1fr))', gap: '2rem' }}>
          {/* Card 1: Personal Training */}
          <div className="fitpulse-feature-card">
            <img 
              src="/gym/gallery/cat1.png" 
              alt="Personal Training" 
              className="fitpulse-feature-img"
              onError={(e) => { e.target.src = '/BG1.png'; }}
            />
            <div className="fitpulse-feature-body">
              <span className="section-subtitle">// 1-ON-1 COACHING</span>
              <h3 style={{ fontSize: '1.4rem', marginBottom: '0.75rem' }}>PERSONAL TRAINING</h3>
              <p style={{ color: '#A0A0A0', fontSize: '0.92rem', marginBottom: '1.25rem' }}>
                Dedicated certified personal coaches who tailor your workout routines, correct lifting mechanics, 
                and prescribe weekly nutritional guidance.
              </p>
              <a href="#trainers" className="btn btn-outline btn-sm">
                MEET OUR TRAINERS <ArrowRight size={14} />
              </a>
            </div>
          </div>

          {/* Card 2: Group & Strength Equipment */}
          <div className="fitpulse-feature-card">
            <img 
              src="/gym/gallery/cat2.png" 
              alt="Group Fitness" 
              className="fitpulse-feature-img"
              onError={(e) => { e.target.src = '/BG.png'; }}
            />
            <div className="fitpulse-feature-body">
              <span className="section-subtitle">// OLYMPIC EQUIPMENT</span>
              <h3 style={{ fontSize: '1.4rem', marginBottom: '0.75rem' }}>STRENGTH &amp; CONDITIONING</h3>
              <p style={{ color: '#A0A0A0', fontSize: '0.92rem', marginBottom: '1.25rem' }}>
                Full floor access to {equipmentCount} competition stations, bumper plates, 45-degree leg press, Smith machines, dumbbells up to 50kg, and dual cable crossovers.
              </p>
              <a href="#pricing" className="btn btn-outline btn-sm">
                SEE MEMBERSHIP ACCESS <ArrowRight size={14} />
              </a>
            </div>
          </div>
        </div>
      </section>

      {/* ========================================================= */}
      {/* 3. DYNAMIC PRICING SECTION                                */}
      {/* ========================================================= */}
      <section id="pricing" style={{ marginBottom: '4rem' }}>
        <div style={{ textAlign: 'center', marginBottom: '2.5rem' }}>
          <span className="section-subtitle">// OUR PRICING</span>
          <h2 style={{ fontSize: '2.3rem' }}>DYNAMIC MEMBERSHIP PACKAGES</h2>
          <div style={{ width: '60px', height: '3px', background: '#FF0000', margin: '0.75rem auto 0 auto' }}></div>
          <p style={{ color: '#A0A0A0', maxWidth: '600px', margin: '0.75rem auto 0 auto', fontSize: '0.95rem' }}>
            Transparent monthly pricing with zero hidden fees. Select optional add-ons to customize your workout access.
          </p>
        </div>

        {/* 3 Pricing Tier Cards */}
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '1.75rem', marginBottom: '2.5rem' }}>
          {/* Plan 1: Standard Floor Access */}
          <div className="glass-card" style={{ textAlign: 'center', padding: '2.5rem 1.75rem' }}>
            <span className="badge badge-emerald" style={{ marginBottom: '1rem' }}>BASE PACKAGE</span>
            <h3 style={{ fontSize: '1.5rem', marginBottom: '0.5rem' }}>STANDARD FLOOR PASS</h3>
            <div className="price-display" style={{ margin: '1rem 0' }}>
              2,500 <span className="price-currency">LKR / MO</span>
            </div>
            <ul style={{ listStyle: 'none', padding: 0, margin: '1.5rem 0', color: '#B0B0B0', fontSize: '0.92rem', lineHeight: '2.2' }}>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Full Gym Floor &amp; Dumbbells Access</li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Olympic Barbells &amp; Weight Plates</li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Pin-Selected Cable &amp; Lever Machines</li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Locker Room &amp; Shower Access</li>
              <li style={{ color: '#666666' }}>✕ Personal Coach (Optional Add-on)</li>
              <li style={{ color: '#666666' }}>✕ Cardio Treadmill Zone (Optional)</li>
            </ul>
            <button className="btn btn-outline" style={{ width: '100%' }} onClick={onOpenAuthModal}>
              SELECT PLAN
            </button>
          </div>

          {/* Plan 2: With Personal Coach (Featured) */}
          <div className="glass-card" style={{ textAlign: 'center', padding: '2.5rem 1.75rem', borderColor: '#FF0000', borderTopWidth: '4px', background: '#151515' }}>
            <span className="badge badge-red" style={{ marginBottom: '1rem' }}>MOST POPULAR</span>
            <h3 style={{ fontSize: '1.5rem', marginBottom: '0.5rem', color: '#FFFFFF' }}>PERSONAL COACH PASS</h3>
            <div className="price-display" style={{ margin: '1rem 0' }}>
              4,500 <span className="price-currency">LKR / MO</span>
            </div>
            <ul style={{ listStyle: 'none', padding: 0, margin: '1.5rem 0', color: '#D0D0D0', fontSize: '0.92rem', lineHeight: '2.2' }}>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> <strong>Includes Everything in Base Pass</strong></li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> <strong>Dedicated 1-on-1 Personal Trainer</strong></li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Customized Workout Split &amp; Diet Plan</li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Bi-Weekly Body Composition Tracking</li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Form &amp; Technique Video Analysis</li>
              <li style={{ color: '#666666' }}>✕ Cardio Treadmill Zone (Optional +1000 LKR)</li>
            </ul>
            <button className="btn btn-primary" style={{ width: '100%' }} onClick={onOpenAuthModal}>
              ACTIVATE COACH PASS
            </button>
          </div>

          {/* Plan 3: Elite All-Access */}
          <div className="glass-card" style={{ textAlign: 'center', padding: '2.5rem 1.75rem' }}>
            <span className="badge badge-amber" style={{ marginBottom: '1rem' }}>ALL-INCLUSIVE</span>
            <h3 style={{ fontSize: '1.5rem', marginBottom: '0.5rem' }}>ELITE ATHLETE PASS</h3>
            <div className="price-display" style={{ margin: '1rem 0' }}>
              5,500 <span className="price-currency">LKR / MO</span>
            </div>
            <ul style={{ listStyle: 'none', padding: 0, margin: '1.5rem 0', color: '#B0B0B0', fontSize: '0.92rem', lineHeight: '2.2' }}>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Full Gym Floor &amp; Heavy Weights</li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Certified Personal Trainer Included</li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> <strong>Unlimited Treadmill &amp; Cardio Deck</strong></li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Workout Routine &amp; Diet Guidance</li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> Priority Equipment Booking</li>
              <li><Check size={16} color="#FF0000" style={{ display: 'inline', marginRight: '6px' }} /> 10% Discount on Supplement Store</li>
            </ul>
            <button className="btn btn-outline" style={{ width: '100%' }} onClick={onOpenAuthModal}>
              SELECT PLAN
            </button>
          </div>
        </div>

        {/* Live Interactive Pricing Calculator */}
        <div className="pricing-calculator-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem', marginBottom: '1.5rem' }}>
            <div>
              <span className="section-subtitle">// LIVE ALGORITHM</span>
              <h3 style={{ fontSize: '1.4rem' }}>INTERACTIVE PACKAGE BUILDER</h3>
            </div>
            <div style={{ textAlign: 'right' }}>
              <div style={{ fontSize: '0.75rem', color: '#A0A0A0', textTransform: 'uppercase', letterSpacing: '1px' }}>Total Dynamic Fee</div>
              <div className="price-display" style={{ fontSize: '2.2rem', margin: 0 }}>
                {pricingPreview ? pricingPreview.totalMonthlyFeeLkr.toLocaleString('en-US') : '2,500'} 
                <span className="price-currency">LKR</span>
              </div>
            </div>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '1rem', marginBottom: '1.5rem' }}>
            <div className="checkbox-option selected" style={{ cursor: 'default' }}>
              <input type="checkbox" checked readOnly />
              <div style={{ flex: 1 }}>
                <div style={{ fontWeight: 700, fontSize: '0.95rem' }}>Base Gym Floor Membership</div>
                <div style={{ fontSize: '0.8rem', color: '#888888' }}>Full equipment &amp; locker access</div>
              </div>
              <strong style={{ color: '#FFFFFF' }}>2,500 LKR</strong>
            </div>

            <label className={`checkbox-option ${hasTrainer ? 'selected' : ''}`}>
              <input 
                type="checkbox" 
                checked={hasTrainer} 
                onChange={(e) => setHasTrainer(e.target.checked)} 
              />
              <div style={{ flex: 1 }}>
                <div style={{ fontWeight: 700, fontSize: '0.95rem', color: '#FFFFFF' }}>Personal Trainer Add-on</div>
                <div style={{ fontSize: '0.8rem', color: '#888888' }}>1-on-1 coaching &amp; diet plan</div>
              </div>
              <strong style={{ color: '#FF0000' }}>+2,000 LKR</strong>
            </label>

            <label className={`checkbox-option ${hasTreadmill ? 'selected' : ''}`}>
              <input 
                type="checkbox" 
                checked={hasTreadmill} 
                onChange={(e) => setHasTreadmill(e.target.checked)} 
              />
              <div style={{ flex: 1 }}>
                <div style={{ fontWeight: 700, fontSize: '0.95rem', color: '#FFFFFF' }}>Cardio / Treadmill Pass</div>
                <div style={{ fontSize: '0.8rem', color: '#888888' }}>Unlimited running deck &amp; HIIT</div>
              </div>
              <strong style={{ color: '#10b981' }}>+1,000 LKR</strong>
            </label>
          </div>

          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
            <div style={{ fontSize: '0.85rem', color: '#A0A0A0' }}>
              {pricingPreview?.breakdownExplanation || 'Base Membership: 2,500 LKR = Total: 2,500 LKR/month'}
            </div>
            <button className="btn btn-primary" onClick={onOpenAuthModal}>
              SIGN IN TO SUBSCRIBE <ArrowRight size={16} />
            </button>
          </div>
        </div>
      </section>

      {/* ========================================================= */}
      {/* 4. CERTIFIED TRAINERS                                     */}
      {/* ========================================================= */}
      <section id="trainers" style={{ marginBottom: '4rem' }}>
        <div style={{ textAlign: 'center', marginBottom: '2.5rem' }}>
          <span className="section-subtitle">// OUR EXPERT COACHES</span>
          <h2 style={{ fontSize: '2.3rem' }}>MEET OUR CERTIFIED TRAINERS</h2>
          <div style={{ width: '60px', height: '3px', background: '#FF0000', margin: '0.75rem auto 0 auto' }}></div>
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '2rem' }}>
          {trainers.map((t, idx) => (
            <div key={t.id} className="fitpulse-trainer-card">
              <img 
                src={trainerImages[idx % trainerImages.length]} 
                alt={t.user?.fullName} 
                className="fitpulse-trainer-img"
                onError={(e) => { e.target.src = '/BG1.png'; }}
              />
              <div style={{ padding: '1.5rem', textAlign: 'center' }}>
                <span className="badge badge-red" style={{ marginBottom: '0.5rem' }}>
                  {t.specialization}
                </span>
                <h3 style={{ fontSize: '1.35rem', margin: '0.5rem 0' }}>
                  {t.user?.fullName}
                </h3>
                <div style={{ color: '#FF0000', fontSize: '0.85rem', fontWeight: 600, textTransform: 'uppercase', letterSpacing: '1px', marginBottom: '0.75rem' }}>
                  {t.experienceYears} Years Professional Experience
                </div>
                <p style={{ color: '#909090', fontSize: '0.88rem', marginBottom: '1.25rem', minHeight: '44px' }}>
                  {t.bio}
                </p>
                <div style={{ borderTop: '1px solid #222222', paddingTop: '1rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <span style={{ fontSize: '0.85rem', color: '#AAAAAA' }}>Rate: <strong>{t.monthlyRateLkr} LKR/mo</strong></span>
                  <button className="btn btn-outline btn-sm" onClick={onOpenAuthModal}>
                    CHOOSE COACH
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* ========================================================= */}
      {/* 5. SUPPLEMENTS PREVIEW SECTION                            */}
      {/* ========================================================= */}
      <section id="supplements" style={{ marginBottom: '4rem' }}>
        <div style={{ textAlign: 'center', marginBottom: '2.5rem' }}>
          <span className="section-subtitle">// NUTRITION &amp; FUEL</span>
          <h2 style={{ fontSize: '2.3rem' }}>GENUINE SUPPLEMENT STORE</h2>
          <div style={{ width: '60px', height: '3px', background: '#FF0000', margin: '0.75rem auto 0 auto' }}></div>
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(260px, 1fr))', gap: '1.5rem' }}>
          {supplements.map((sup) => (
            <div key={sup.id} className="glass-card" style={{ display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
              <div>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.75rem' }}>
                  <span className="badge badge-amber">{sup.category}</span>
                  <span style={{ fontSize: '0.75rem', color: '#10b981', fontWeight: 700 }}>
                    In Stock: {sup.stockQuantity}
                  </span>
                </div>
                <h4 style={{ fontSize: '1.15rem', marginBottom: '0.4rem' }}>{sup.name}</h4>
                <div style={{ color: '#888888', fontSize: '0.82rem', marginBottom: '0.75rem' }}>
                  Brand: <strong style={{ color: '#E0E0E0' }}>{sup.brand}</strong> &bull; {sup.servingSize}
                </div>
                <p style={{ color: '#A0A0A0', fontSize: '0.85rem', marginBottom: '1rem' }}>
                  {sup.description}
                </p>
              </div>

              <div style={{ borderTop: '1px solid #222222', paddingTop: '1rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div style={{ fontFamily: "'Oswald', sans-serif", fontSize: '1.3rem', fontWeight: 700, color: '#FF0000' }}>
                  {sup.priceLkr.toLocaleString('en-US')} <span style={{ fontSize: '0.8rem', color: '#FFFFFF' }}>LKR</span>
                </div>
                <button className="btn btn-primary btn-sm" onClick={onOpenAuthModal}>
                  BUY NOW
                </button>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* ========================================================= */}
      {/* 6. CALL TO ACTION / ENTER SYSTEM                          */}
      {/* ========================================================= */}
      <section style={{
        background: 'linear-gradient(rgba(0, 0, 0, 0.75), rgba(0, 0, 0, 0.90)), url("/BG1.png")',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        border: '1px solid #282828',
        borderTop: '3px solid #FF0000',
        padding: '3.5rem 2rem',
        textAlign: 'center',
        marginBottom: '2rem'
      }}>
        <span className="section-subtitle">// READY TO LEVEL UP?</span>
        <h2 style={{ fontSize: '2.6rem', marginBottom: '1rem' }}>START YOUR TRANSFORMATION TODAY</h2>
        <p style={{ color: '#C0C0C0', maxWidth: '650px', margin: '0 auto 2rem auto', fontSize: '1.05rem' }}>
          Sign in to access your member dashboard, prescribe workout plans as a trainer, or manage gym operations as the owner.
        </p>
        <button className="btn btn-primary" style={{ padding: '0.9rem 2.5rem', fontSize: '1rem' }} onClick={onOpenAuthModal}>
          <Lock size={18} /> OPEN AUTHENTICATION &amp; DEMO PANEL
        </button>
      </section>
    </div>
  );
}
