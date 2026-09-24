import React, { useState, useEffect } from 'react';
import { api } from '../services/api';
import Modal from '../components/Modal';
import { 
  CreditCard, Check, Dumbbell, Package, ShoppingCart, 
  Calendar, UserCheck, Flame, Zap, FileText, CheckCircle2 
} from 'lucide-react';

export default function CustomerDashboard({ currentUser, activeTab }) {
  const [membership, setMembership] = useState(null);
  const [trainers, setTrainers] = useState([]);
  const [equipment, setEquipment] = useState([]);
  const [supplements, setSupplements] = useState([]);
  const [invoices, setInvoices] = useState([]);
  const [workoutPlan, setWorkoutPlan] = useState(null);

  // Dynamic Pricing Calculator State
  const [calcHasTrainer, setCalcHasTrainer] = useState(false);
  const [calcHasTreadmill, setCalcHasTreadmill] = useState(false);
  const [calcTrainerId, setCalcTrainerId] = useState('');
  const [calculatedPrice, setCalculatedPrice] = useState(null);
  
  // Ordering modal
  const [orderModalOpen, setOrderModalOpen] = useState(false);
  const [selectedSupplement, setSelectedSupplement] = useState(null);
  const [orderQuantity, setOrderQuantity] = useState(1);

  const [loading, setLoading] = useState(true);
  const [alert, setAlert] = useState(null);

  const fetchCustomerData = async () => {
    setLoading(true);
    try {
      const [m, t, eq, sup, inv, wp] = await Promise.all([
        api.getCustomerMembership(currentUser.id).catch(() => null),
        api.getTrainers(),
        api.getEquipment(),
        api.getSupplements(),
        api.getCustomerInvoices(currentUser.id).catch(() => []),
        api.getCustomerWorkoutPlan(currentUser.id).catch(() => null)
      ]);

      setMembership(m);
      setTrainers(t || []);
      setEquipment(eq || []);
      setSupplements(sup || []);
      setInvoices(inv || []);
      setWorkoutPlan(wp);

      if (m) {
        setCalcHasTrainer(m.hasTrainer);
        setCalcHasTreadmill(m.hasTreadmill);
        if (m.trainer) setCalcTrainerId(m.trainer.id.toString());
      } else {
        setCalcHasTrainer(false);
        setCalcHasTreadmill(false);
      }
    } catch (err) {
      console.error(err);
      setAlert({ type: 'error', message: err.message || 'Failed to load member data' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCustomerData();
  }, [currentUser]);

  // Recalculate dynamic price when checkboxes change
  useEffect(() => {
    const updatePrice = async () => {
      try {
        const res = await api.calculatePrice(calcHasTrainer, calcHasTreadmill);
        setCalculatedPrice(res);
      } catch (err) {
        console.error(err);
      }
    };
    updatePrice();
  }, [calcHasTrainer, calcHasTreadmill]);

  const handleSubscribe = async () => {
    try {
      await api.subscribeMembership({
        customerId: currentUser.id,
        trainerId: calcHasTrainer && calcTrainerId ? parseInt(calcTrainerId) : null,
        hasTrainer: calcHasTrainer,
        hasTreadmill: calcHasTreadmill
      });
      setAlert({ type: 'success', message: 'Membership package activated & invoice generated!' });
      fetchCustomerData();
    } catch (err) {
      setAlert({ type: 'error', message: err.message || 'Subscription failed' });
    }
  };

  const handleOpenOrderModal = (sup) => {
    setSelectedSupplement(sup);
    setOrderQuantity(1);
    setOrderModalOpen(true);
  };

  const handleConfirmOrder = async (e) => {
    e.preventDefault();
    try {
      await api.orderSupplement({
        customerId: currentUser.id,
        supplementId: selectedSupplement.id,
        quantity: orderQuantity
      });
      setAlert({ type: 'success', message: `Order confirmed for ${orderQuantity}x ${selectedSupplement.name}!` });
      setOrderModalOpen(false);
      fetchCustomerData();
    } catch (err) {
      setAlert({ type: 'error', message: err.message || 'Order failed' });
    }
  };

  const formatLkr = (amount) => {
    return new Intl.NumberFormat('en-LK', {
      style: 'currency',
      currency: 'LKR',
      maximumFractionDigits: 0
    }).format(amount || 0);
  };

  return (
    <div>
      {/* Alert */}
      {alert && (
        <div style={{
          background: alert.type === 'success' ? 'rgba(16, 185, 129, 0.15)' : 'rgba(239, 68, 68, 0.15)',
          border: `1px solid ${alert.type === 'success' ? 'rgba(16, 185, 129, 0.3)' : 'rgba(239, 68, 68, 0.3)'}`,
          color: alert.type === 'success' ? '#34d399' : '#f87171',
          padding: '0.85rem 1.25rem',
          borderRadius: '10px',
          marginBottom: '1.5rem',
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center'
        }}>
          <span>{alert.message}</span>
          <button className="btn btn-sm btn-outline" onClick={() => setAlert(null)}>Dismiss</button>
        </div>
      )}

      {/* Dynamic Membership Pricing Calculator Section */}
      {(activeTab === 'membership' || !activeTab || activeTab === 'dashboard') && (
        <div style={{ marginBottom: '2.5rem' }}>
          <div style={{ marginBottom: '1.25rem' }}>
            <h2 style={{ fontSize: '1.6rem', fontWeight: 800 }}>Gym Membership &amp; Dynamic Pricing</h2>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>
              Base membership starts at <strong>2,500 LKR</strong>. Add a personal coach for <strong>+2,000 LKR</strong> (4,500 LKR) or cardio treadmill access for <strong>+1,000 LKR</strong>.
            </p>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(360px, 1fr))', gap: '1.5rem' }}>
            {/* Interactive Calculator Card */}
            <div className="pricing-calculator-card">
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
                <span className="badge badge-cyan">Membership Package Builder</span>
                {membership && <span className="badge badge-emerald">Current Status: {membership.status}</span>}
              </div>

              <div style={{ marginBottom: '1.5rem' }}>
                <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Calculated Monthly Fee</div>
                <div className="price-display">
                  {formatLkr(calculatedPrice?.totalMonthlyFeeLkr || 2500)}
                  <span className="price-currency">/ month</span>
                </div>
                <div style={{ fontSize: '0.8rem', color: '#f87171', marginTop: '0.25rem' }}>
                  {calculatedPrice?.breakdownExplanation}
                </div>
              </div>

              <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem', marginBottom: '1.5rem' }}>
                {/* Base Plan */}
                <div className="checkbox-option selected" style={{ cursor: 'default' }}>
                  <input type="checkbox" checked readOnly />
                  <div style={{ flex: 1 }}>
                    <div style={{ fontWeight: 700, fontSize: '0.95rem' }}>Base Gym Membership</div>
                    <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>
                      Full floor access to dumbbells, weight plates, bars, &amp; machines
                    </div>
                  </div>
                  <strong>2,500 LKR</strong>
                </div>

                {/* Trainer Add-on */}
                <label className={`checkbox-option ${calcHasTrainer ? 'selected' : ''}`}>
                  <input
                    type="checkbox"
                    checked={calcHasTrainer}
                    onChange={(e) => setCalcHasTrainer(e.target.checked)}
                  />
                  <div style={{ flex: 1 }}>
                    <div style={{ fontWeight: 700, fontSize: '0.95rem', display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
                      <UserCheck size={16} color="#ef4444" /> Personal Trainer Add-on
                    </div>
                    <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>
                      Dedicated 1-on-1 coaching, personalized routine &amp; diet plan (+2,000 LKR)
                    </div>
                  </div>
                  <strong style={{ color: '#ef4444' }}>+2,000 LKR</strong>
                </label>

                {/* Trainer Dropdown if trainer checked */}
                {calcHasTrainer && (
                  <div style={{ paddingLeft: '1.5rem', marginBottom: '0.5rem' }}>
                    <label className="form-label" style={{ color: '#f87171' }}>Select Your Personal Trainer</label>
                    <select
                      className="form-select"
                      value={calcTrainerId}
                      onChange={(e) => setCalcTrainerId(e.target.value)}
                    >
                      <option value="">Select a certified coach...</option>
                      {trainers.map((t) => (
                        <option key={t.id} value={t.id}>
                          {t.user?.fullName} &bull; {t.specialization} ({t.experienceYears} yrs exp)
                        </option>
                      ))}
                    </select>
                  </div>
                )}

                {/* Treadmill Add-on */}
                <label className={`checkbox-option ${calcHasTreadmill ? 'selected' : ''}`}>
                  <input
                    type="checkbox"
                    checked={calcHasTreadmill}
                    onChange={(e) => setCalcHasTreadmill(e.target.checked)}
                  />
                  <div style={{ flex: 1 }}>
                    <div style={{ fontWeight: 700, fontSize: '0.95rem', display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
                      <Zap size={16} color="#10b981" /> Treadmill &amp; Cardio Zone Access
                    </div>
                    <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>
                      Unlimited cardio deck, incline running &amp; HIIT bikes (+1,000 LKR)
                    </div>
                  </div>
                  <strong style={{ color: '#10b981' }}>+1,000 LKR</strong>
                </label>
              </div>

              <button
                className="btn btn-primary"
                style={{ width: '100%', padding: '0.9rem', fontSize: '1rem' }}
                onClick={handleSubscribe}
              >
                <CreditCard size={18} /> {membership ? 'Upgrade / Renew Membership' : 'Activate Membership'}
              </button>
            </div>

            {/* Current Active Plan Status */}
            <div className="glass-card" style={{ display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
              <div>
                <h3 style={{ fontSize: '1.2rem', fontWeight: 700, marginBottom: '1rem', color: '#f8fafc' }}>
                  📌 Current Membership Summary
                </h3>

                {membership ? (
                  <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    <div style={{ padding: '1rem', background: 'rgba(255, 255, 255, 0.02)', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
                      <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)', textTransform: 'uppercase', fontWeight: 700 }}>Package</div>
                      <div style={{ fontSize: '1.4rem', fontWeight: 800, color: '#ef4444' }}>
                        {formatLkr(membership.totalMonthlyFeeLkr)} / month
                      </div>
                      <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', marginTop: '0.25rem' }}>
                        Base (2500 LKR) 
                        {membership.hasTrainer && ' + Trainer (2000 LKR)'} 
                        {membership.hasTreadmill && ' + Treadmill (1000 LKR)'}
                      </div>
                    </div>

                    <div style={{ padding: '1rem', background: 'rgba(255, 255, 255, 0.02)', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
                      <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)', textTransform: 'uppercase', fontWeight: 700 }}>Assigned Personal Coach</div>
                      <div style={{ fontSize: '1.1rem', fontWeight: 700, color: '#f8fafc', marginTop: '0.25rem' }}>
                        {membership.trainer ? membership.trainer.user?.fullName : 'No coach assigned (Floor support only)'}
                      </div>
                      {membership.trainer && (
                        <div style={{ fontSize: '0.8rem', color: '#f87171', marginTop: '0.2rem' }}>
                          Specialization: {membership.trainer.specialization}
                        </div>
                      )}
                    </div>

                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '0.75rem' }}>
                      <div style={{ padding: '0.75rem', background: 'rgba(255, 255, 255, 0.02)', borderRadius: '8px', border: '1px solid var(--border-color)' }}>
                        <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Start Date</div>
                        <div style={{ fontWeight: 600, fontSize: '0.9rem' }}>{membership.startDate}</div>
                      </div>
                      <div style={{ padding: '0.75rem', background: 'rgba(255, 255, 255, 0.02)', borderRadius: '8px', border: '1px solid var(--border-color)' }}>
                        <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Renewal Date</div>
                        <div style={{ fontWeight: 600, fontSize: '0.9rem', color: '#10b981' }}>{membership.endDate}</div>
                      </div>
                    </div>
                  </div>
                ) : (
                  <div style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-muted)' }}>
                    You currently have no active membership. Use the package builder to activate your plan!
                  </div>
                )}
              </div>

              <div style={{ marginTop: '1.5rem', fontSize: '0.8rem', color: 'var(--text-dim)', borderTop: '1px solid var(--border-color)', paddingTop: '1rem' }}>
                All payments are denominated in Sri Lankan Rupees (LKR). Valid at all FitPulse branches.
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Routine & Diet Section */}
      {(activeTab === 'workout' || activeTab === 'dashboard') && (
        <div className="glass-card" style={{ marginBottom: '2.5rem' }}>
          <div style={{ marginBottom: '1.25rem' }}>
            <h3 style={{ fontSize: '1.3rem', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
              <Flame size={22} color="#f59e0b" /> Prescribed Workout Routine &amp; Diet Plan
            </h3>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>
              Custom instructions prescribed by your personal coach
            </p>
          </div>

          {workoutPlan ? (
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '1.25rem' }}>
              <div style={{ background: 'rgba(255, 255, 255, 0.02)', border: '1px solid var(--border-color)', borderRadius: '10px', padding: '1.25rem' }}>
                <h4 style={{ color: '#f87171', fontSize: '1rem', fontWeight: 700, marginBottom: '0.75rem' }}>
                  🏋️ Workout Training Split
                </h4>
                <pre style={{ whiteSpace: 'pre-wrap', fontFamily: 'inherit', fontSize: '0.85rem', color: 'var(--text-main)', lineHeight: '1.7' }}>
                  {workoutPlan.routineNotes}
                </pre>
              </div>

              <div style={{ background: 'rgba(255, 255, 255, 0.02)', border: '1px solid var(--border-color)', borderRadius: '10px', padding: '1.25rem' }}>
                <h4 style={{ color: '#34d399', fontSize: '1rem', fontWeight: 700, marginBottom: '0.75rem' }}>
                  🥗 Diet &amp; Nutrition Targets
                </h4>
                <pre style={{ whiteSpace: 'pre-wrap', fontFamily: 'inherit', fontSize: '0.85rem', color: 'var(--text-main)', lineHeight: '1.7' }}>
                  {workoutPlan.dietNotes}
                </pre>
              </div>

              <div style={{ background: 'rgba(255, 255, 255, 0.02)', border: '1px solid var(--border-color)', borderRadius: '10px', padding: '1.25rem' }}>
                <h4 style={{ color: '#fbbf24', fontSize: '1rem', fontWeight: 700, marginBottom: '0.75rem' }}>
                  📈 Progress Notes &amp; Feedback
                </h4>
                <pre style={{ whiteSpace: 'pre-wrap', fontFamily: 'inherit', fontSize: '0.85rem', color: 'var(--text-main)', lineHeight: '1.7' }}>
                  {workoutPlan.progressNotes || 'Keep up the good form and log daily protein intake.'}
                </pre>
              </div>
            </div>
          ) : (
            <div style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-muted)' }}>
              No custom workout routine assigned yet. Add the <strong>Personal Trainer addon</strong> (+2,000 LKR) to receive tailored workout and nutrition routines!
            </div>
          )}
        </div>
      )}

      {/* Equipment Catalog View */}
      {(activeTab === 'equipment') && (
        <div className="glass-card" style={{ marginBottom: '2.5rem' }}>
          <div style={{ marginBottom: '1.25rem' }}>
            <h3 style={{ fontSize: '1.3rem', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
              <Dumbbell size={22} color="#ef4444" /> Gym Equipment &amp; Free Weights Catalog
            </h3>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>
              Explore available Dumbbells, Weight Plates, Barbells, Machine Kinds, and Resistance Bands
            </p>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '1.25rem' }}>
            {equipment.map((eq) => (
              <div key={eq.id} style={{
                background: 'rgba(255, 255, 255, 0.02)',
                border: '1px solid var(--border-color)',
                borderRadius: '12px',
                padding: '1.25rem',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between'
              }}>
                <div>
                  <span className="badge badge-red" style={{ marginBottom: '0.5rem' }}>
                    {eq.category.replace('_', ' ')}
                  </span>
                  <h4 style={{ fontSize: '1.05rem', fontWeight: 700, color: '#f8fafc', marginBottom: '0.35rem' }}>
                    {eq.name}
                  </h4>
                  <div style={{ fontSize: '0.8rem', color: '#f87171', fontWeight: 600 }}>
                    {eq.weightSpecs}
                  </div>
                  <p style={{ fontSize: '0.8rem', color: 'var(--text-muted)', marginTop: '0.5rem' }}>
                    {eq.description}
                  </p>
                </div>
                <div style={{ marginTop: '1rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderTop: '1px solid var(--border-color)', paddingTop: '0.75rem' }}>
                  <span style={{ fontSize: '0.8rem', color: 'var(--text-dim)' }}>{eq.quantity} units available</span>
                  <span className="badge badge-emerald">{eq.status}</span>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Supplement Store */}
      {(activeTab === 'supplements' || activeTab === 'dashboard') && (
        <div className="glass-card" style={{ marginBottom: '2.5rem' }}>
          <div style={{ marginBottom: '1.25rem' }}>
            <h3 style={{ fontSize: '1.3rem', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
              <Package size={22} color="#ec4899" /> FitPulse Supplement Store
            </h3>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>
              Order Creatine, Whey Protein, Protein Powders, and Pre-workout supplements directly from gym reception
            </p>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '1.25rem' }}>
            {supplements.map((sup) => (
              <div key={sup.id} style={{
                background: 'rgba(255, 255, 255, 0.02)',
                border: '1px solid var(--border-color)',
                borderRadius: '12px',
                padding: '1.25rem',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                gap: '1rem'
              }}>
                <div>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '0.5rem' }}>
                    <span className="badge badge-purple">{sup.category.replace('_', ' ')}</span>
                    <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>{sup.brand}</span>
                  </div>
                  <h4 style={{ fontSize: '1.05rem', fontWeight: 700, color: '#f8fafc', marginBottom: '0.35rem' }}>
                    {sup.name}
                  </h4>
                  <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>
                    {sup.servingSize}
                  </div>
                  <div style={{ fontSize: '1.35rem', fontWeight: 800, color: '#10b981', marginTop: '0.5rem' }}>
                    {formatLkr(sup.priceLkr)}
                  </div>
                  <p style={{ fontSize: '0.78rem', color: 'var(--text-dim)', marginTop: '0.4rem', lineHeight: '1.5' }}>
                    {sup.description}
                  </p>
                </div>

                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderTop: '1px solid var(--border-color)', paddingTop: '0.75rem' }}>
                  <span style={{ fontSize: '0.8rem', color: sup.stockQuantity > 0 ? '#34d399' : '#f87171' }}>
                    {sup.stockQuantity > 0 ? `${sup.stockQuantity} in stock` : 'Out of stock'}
                  </span>
                  <button
                    className="btn btn-accent btn-sm"
                    disabled={sup.stockQuantity <= 0}
                    onClick={() => handleOpenOrderModal(sup)}
                  >
                    <ShoppingCart size={15} /> Buy Now
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Invoices History */}
      {(activeTab === 'invoices' || activeTab === 'dashboard') && (
        <div className="glass-card">
          <div style={{ marginBottom: '1.25rem' }}>
            <h3 style={{ fontSize: '1.3rem', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
              <FileText size={22} color="#ef4444" /> My Invoices &amp; Receipts (LKR)
            </h3>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>
              Itemized billing history for gym memberships and supplement orders
            </p>
          </div>

          <div className="table-responsive">
            <table className="custom-table">
              <thead>
                <tr>
                  <th>Invoice No</th>
                  <th>Description</th>
                  <th>Amount</th>
                  <th>Status</th>
                  <th>Date</th>
                </tr>
              </thead>
              <tbody>
                {invoices.length === 0 ? (
                  <tr>
                    <td colSpan="5" style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-muted)' }}>
                      No invoices recorded yet.
                    </td>
                  </tr>
                ) : (
                  invoices.map((inv) => (
                    <tr key={inv.id}>
                      <td><code style={{ color: '#f87171', fontWeight: 600 }}>{inv.invoiceNo}</code></td>
                      <td>{inv.description}</td>
                      <td><strong style={{ color: '#10b981' }}>{formatLkr(inv.amountLkr)}</strong></td>
                      <td><span className="badge badge-emerald">{inv.paymentStatus}</span></td>
                      <td style={{ fontSize: '0.8rem', color: 'var(--text-dim)' }}>
                        {inv.invoiceDate ? new Date(inv.invoiceDate).toLocaleDateString() : 'Today'}
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* Supplement Purchase Modal */}
      <Modal
        isOpen={orderModalOpen}
        onClose={() => setOrderModalOpen(false)}
        title={`Purchase: ${selectedSupplement?.name}`}
      >
        <form onSubmit={handleConfirmOrder}>
          <div style={{ marginBottom: '1.25rem', padding: '1rem', background: 'rgba(255, 255, 255, 0.03)', borderRadius: '10px' }}>
            <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Unit Price</div>
            <div style={{ fontSize: '1.4rem', fontWeight: 800, color: '#10b981' }}>
              {formatLkr(selectedSupplement?.priceLkr)}
            </div>
            <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)', marginTop: '0.2rem' }}>
              Available Stock: {selectedSupplement?.stockQuantity} units
            </div>
          </div>

          <div className="form-group">
            <label className="form-label">Order Quantity</label>
            <input
              type="number"
              min="1"
              max={selectedSupplement?.stockQuantity || 1}
              className="form-input"
              value={orderQuantity}
              onChange={(e) => setOrderQuantity(Math.max(1, parseInt(e.target.value) || 1))}
              required
            />
          </div>

          <div style={{ marginBottom: '1.5rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderTop: '1px solid var(--border-color)', paddingTop: '1rem' }}>
            <span style={{ fontWeight: 600 }}>Total Charge (LKR):</span>
            <span style={{ fontSize: '1.5rem', fontWeight: 800, color: '#10b981' }}>
              {formatLkr((selectedSupplement?.priceLkr || 0) * orderQuantity)}
            </span>
          </div>

          <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '0.75rem' }}>
            <button type="button" className="btn btn-outline" onClick={() => setOrderModalOpen(false)}>Cancel</button>
            <button type="submit" className="btn btn-accent">
              <CheckCircle2 size={16} /> Confirm Order &amp; Generate Invoice
            </button>
          </div>
        </form>
      </Modal>
    </div>
  );
}
