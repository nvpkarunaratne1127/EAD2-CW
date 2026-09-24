import React, { useState, useEffect } from 'react';
import { api } from '../services/api';
import StatsCard from '../components/StatsCard';
import Modal from '../components/Modal';
import { 
  DollarSign, Users, Dumbbell, Package, Plus, Edit, Trash2, 
  Search, Filter, CheckCircle, AlertCircle, RefreshCw 
} from 'lucide-react';

export default function OwnerDashboard({ activeTab }) {
  const [stats, setStats] = useState(null);
  const [equipmentList, setEquipmentList] = useState([]);
  const [supplementsList, setSupplementsList] = useState([]);
  const [trainersList, setTrainersList] = useState([]);
  const [membershipsList, setMembershipsList] = useState([]);
  const [invoicesList, setInvoicesList] = useState([]);
  
  // Filters & State
  const [equipmentCategory, setEquipmentCategory] = useState('');
  const [supplementCategory, setSupplementCategory] = useState('');
  const [loading, setLoading] = useState(true);
  const [alert, setAlert] = useState(null);

  // Modal State for Equipment
  const [isEquipModalOpen, setIsEquipModalOpen] = useState(false);
  const [editingEquip, setEditingEquip] = useState(null);
  const [equipForm, setEquipForm] = useState({
    name: '',
    category: 'DUMBBELL',
    quantity: 1,
    weightSpecs: '',
    status: 'AVAILABLE',
    description: ''
  });

  // Modal State for Supplement
  const [isSuppModalOpen, setIsSuppModalOpen] = useState(false);
  const [editingSupp, setEditingSupp] = useState(null);
  const [suppForm, setSuppForm] = useState({
    name: '',
    category: 'CREATINE',
    brand: '',
    priceLkr: 8500,
    stockQuantity: 10,
    servingSize: '',
    description: ''
  });

  const fetchData = async () => {
    setLoading(true);
    try {
      const [sData, eData, supData, tData, mData, iData] = await Promise.all([
        api.getBillingStats(),
        api.getEquipment(equipmentCategory || undefined),
        api.getSupplements(supplementCategory || undefined),
        api.getTrainers(),
        api.getMemberships(),
        api.getAllInvoices()
      ]);
      setStats(sData);
      setEquipmentList(eData || []);
      setSupplementsList(supData || []);
      setTrainersList(tData || []);
      setMembershipsList(mData || []);
      setInvoicesList(iData || []);
    } catch (err) {
      console.error(err);
      setAlert({ type: 'error', message: err.message || 'Failed to load dashboard data' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [equipmentCategory, supplementCategory]);

  // Equipment CRUD
  const handleOpenEquipModal = (equip = null) => {
    if (equip) {
      setEditingEquip(equip);
      setEquipForm({
        name: equip.name,
        category: equip.category,
        quantity: equip.quantity,
        weightSpecs: equip.weightSpecs || '',
        status: equip.status,
        description: equip.description || ''
      });
    } else {
      setEditingEquip(null);
      setEquipForm({
        name: '',
        category: 'DUMBBELL',
        quantity: 1,
        weightSpecs: '',
        status: 'AVAILABLE',
        description: ''
      });
    }
    setIsEquipModalOpen(true);
  };

  const handleSaveEquipment = async (e) => {
    e.preventDefault();
    try {
      if (editingEquip) {
        await api.updateEquipment(editingEquip.id, equipForm);
        setAlert({ type: 'success', message: 'Equipment updated successfully!' });
      } else {
        await api.createEquipment(equipForm);
        setAlert({ type: 'success', message: 'New equipment added successfully!' });
      }
      setIsEquipModalOpen(false);
      fetchData();
    } catch (err) {
      setAlert({ type: 'error', message: err.message || 'Operation failed' });
    }
  };

  const handleDeleteEquipment = async (id) => {
    if (window.confirm('Are you sure you want to remove this equipment item?')) {
      try {
        await api.deleteEquipment(id);
        setAlert({ type: 'success', message: 'Equipment item deleted' });
        fetchData();
      } catch (err) {
        setAlert({ type: 'error', message: err.message });
      }
    }
  };

  // Supplement CRUD
  const handleOpenSuppModal = (supp = null) => {
    if (supp) {
      setEditingSupp(supp);
      setSuppForm({
        name: supp.name,
        category: supp.category,
        brand: supp.brand || '',
        priceLkr: supp.priceLkr,
        stockQuantity: supp.stockQuantity,
        servingSize: supp.servingSize || '',
        description: supp.description || ''
      });
    } else {
      setEditingSupp(null);
      setSuppForm({
        name: '',
        category: 'CREATINE',
        brand: '',
        priceLkr: 8500,
        stockQuantity: 10,
        servingSize: '',
        description: ''
      });
    }
    setIsSuppModalOpen(true);
  };

  const handleSaveSupplement = async (e) => {
    e.preventDefault();
    try {
      if (editingSupp) {
        await api.updateSupplement(editingSupp.id, suppForm);
        setAlert({ type: 'success', message: 'Supplement updated successfully!' });
      } else {
        await api.createSupplement(suppForm);
        setAlert({ type: 'success', message: 'New supplement added to catalog!' });
      }
      setIsSuppModalOpen(false);
      fetchData();
    } catch (err) {
      setAlert({ type: 'error', message: err.message || 'Operation failed' });
    }
  };

  const handleDeleteSupplement = async (id) => {
    if (window.confirm('Are you sure you want to remove this supplement from catalog?')) {
      try {
        await api.deleteSupplement(id);
        setAlert({ type: 'success', message: 'Supplement removed from catalog' });
        fetchData();
      } catch (err) {
        setAlert({ type: 'error', message: err.message });
      }
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
      {/* Alert banner */}
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

      {/* Overview Stats Cards */}
      {(activeTab === 'dashboard' || !activeTab) && (
        <div style={{ marginBottom: '2.5rem' }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '1.25rem' }}>
            <div>
              <h2 style={{ fontSize: '1.6rem', fontWeight: 800 }}>Gym Management Overview</h2>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>
                Complete live metrics for FitPulse Gym, financial revenues, and inventory status
              </p>
            </div>
            <button className="btn btn-outline btn-sm" onClick={fetchData}>
              <RefreshCw size={15} /> Refresh Data
            </button>
          </div>

          <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))',
            gap: '1.25rem',
            marginBottom: '2rem'
          }}>
            <StatsCard
              title="Total Paid Revenue"
              value={formatLkr(stats?.totalRevenueLkr)}
              subtitle="All membership + supplement collections"
              icon={DollarSign}
              color="#10b981"
            />
            <StatsCard
              title="Active Members"
              value={stats?.activeMemberships || 0}
              subtitle="Subscribed gym-goers"
              icon={Users}
              color="#ef4444"
            />
            <StatsCard
              title="Certified Trainers"
              value={stats?.totalTrainers || 0}
              subtitle="Coaching staff"
              icon={Users}
              color="#f59e0b"
            />
            <StatsCard
              title="Equipment Items"
              value={stats?.totalEquipmentItems || 0}
              subtitle="Dumbbells, plates, bars, machines, bands"
              icon={Dumbbell}
              color="#8b5cf6"
            />
            <StatsCard
              title="Supplements in Store"
              value={stats?.totalSupplements || 0}
              subtitle="Creatine, Whey, Protein, Pre-workout"
              icon={Package}
              color="#ec4899"
            />
          </div>
        </div>
      )}

      {/* Equipment Tab or Dashboard section */}
      {(activeTab === 'equipment' || activeTab === 'dashboard') && (
        <div className="glass-card" style={{ marginBottom: '2.5rem' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem', marginBottom: '1.5rem' }}>
            <div>
              <h3 style={{ fontSize: '1.25rem', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                <Dumbbell size={20} color="#ef4444" /> Gym Equipment Inventory
              </h3>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.8rem' }}>
                Dumbbells, weight plates, bars, machine kinds, and resistance bands
              </p>
            </div>
            
            <div style={{ display: 'flex', gap: '0.75rem', alignItems: 'center', flexWrap: 'wrap' }}>
              <select
                className="form-select"
                style={{ width: 'auto', minWidth: '180px' }}
                value={equipmentCategory}
                onChange={(e) => setEquipmentCategory(e.target.value)}
              >
                <option value="">All Categories</option>
                <option value="DUMBBELL">Dumbbells</option>
                <option value="WEIGHT_PLATE">Weight Plates</option>
                <option value="BAR">Bars</option>
                <option value="MACHINE">Machine Kinds</option>
                <option value="RESISTANCE_BAND">Resistance Bands</option>
              </select>

              <button className="btn btn-primary btn-sm" onClick={() => handleOpenEquipModal()}>
                <Plus size={16} /> Add Equipment
              </button>
            </div>
          </div>

          <div className="table-responsive">
            <table className="custom-table">
              <thead>
                <tr>
                  <th>Equipment Name</th>
                  <th>Category</th>
                  <th>Quantity</th>
                  <th>Specifications</th>
                  <th>Status</th>
                  <th style={{ textAlign: 'right' }}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {equipmentList.length === 0 ? (
                  <tr>
                    <td colSpan="6" style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-muted)' }}>
                      No equipment items found. Click "Add Equipment" to create one.
                    </td>
                  </tr>
                ) : (
                  equipmentList.map((item) => (
                    <tr key={item.id}>
                      <td>
                        <strong>{item.name}</strong>
                        {item.description && (
                          <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)', maxWidth: '320px' }}>
                            {item.description}
                          </div>
                        )}
                      </td>
                      <td>
                        <span className="badge badge-cyan">{item.category.replace('_', ' ')}</span>
                      </td>
                      <td>
                        <strong>{item.quantity}</strong> units
                      </td>
                      <td style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>
                        {item.weightSpecs || '—'}
                      </td>
                      <td>
                        <span className={`badge ${item.status === 'AVAILABLE' ? 'badge-emerald' : 'badge-amber'}`}>
                          {item.status}
                        </span>
                      </td>
                      <td style={{ textAlign: 'right' }}>
                        <div style={{ display: 'inline-flex', gap: '0.4rem' }}>
                          <button
                            className="btn btn-outline btn-sm"
                            onClick={() => handleOpenEquipModal(item)}
                            title="Edit"
                          >
                            <Edit size={14} />
                          </button>
                          <button
                            className="btn btn-danger btn-sm"
                            onClick={() => handleDeleteEquipment(item.id)}
                            title="Delete"
                          >
                            <Trash2 size={14} />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* Supplements Tab */}
      {(activeTab === 'supplements' || activeTab === 'dashboard') && (
        <div className="glass-card" style={{ marginBottom: '2.5rem' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem', marginBottom: '1.5rem' }}>
            <div>
              <h3 style={{ fontSize: '1.25rem', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                <Package size={20} color="#ec4899" /> Supplement Store &amp; Stock
              </h3>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.8rem' }}>
                Creatine, Whey Protein, Protein Powders, and Pre-workout products
              </p>
            </div>

            <div style={{ display: 'flex', gap: '0.75rem', alignItems: 'center', flexWrap: 'wrap' }}>
              <select
                className="form-select"
                style={{ width: 'auto', minWidth: '180px' }}
                value={supplementCategory}
                onChange={(e) => setSupplementCategory(e.target.value)}
              >
                <option value="">All Categories</option>
                <option value="CREATINE">Creatine</option>
                <option value="WHEY_PROTEIN">Whey Protein</option>
                <option value="PROTEIN">Protein (Plant/Casein)</option>
                <option value="PRE_WORKOUT">Pre-Workout</option>
                <option value="OTHER">Other</option>
              </select>

              <button className="btn btn-primary btn-sm" onClick={() => handleOpenSuppModal()}>
                <Plus size={16} /> Add Supplement
              </button>
            </div>
          </div>

          <div className="table-responsive">
            <table className="custom-table">
              <thead>
                <tr>
                  <th>Product Name</th>
                  <th>Category</th>
                  <th>Brand</th>
                  <th>Price (LKR)</th>
                  <th>Stock</th>
                  <th>Size / Servings</th>
                  <th style={{ textAlign: 'right' }}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {supplementsList.map((sup) => (
                  <tr key={sup.id}>
                    <td>
                      <strong>{sup.name}</strong>
                    </td>
                    <td>
                      <span className="badge badge-purple">{sup.category.replace('_', ' ')}</span>
                    </td>
                    <td style={{ color: 'var(--text-muted)' }}>{sup.brand || '—'}</td>
                    <td>
                      <strong style={{ color: '#10b981' }}>{formatLkr(sup.priceLkr)}</strong>
                    </td>
                    <td>
                      <span className={`badge ${sup.stockQuantity > 5 ? 'badge-emerald' : 'badge-amber'}`}>
                        {sup.stockQuantity} in stock
                      </span>
                    </td>
                    <td style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>{sup.servingSize}</td>
                    <td style={{ textAlign: 'right' }}>
                      <div style={{ display: 'inline-flex', gap: '0.4rem' }}>
                        <button
                          className="btn btn-outline btn-sm"
                          onClick={() => handleOpenSuppModal(sup)}
                          title="Edit"
                        >
                          <Edit size={14} />
                        </button>
                        <button
                          className="btn btn-danger btn-sm"
                          onClick={() => handleDeleteSupplement(sup.id)}
                          title="Delete"
                        >
                          <Trash2 size={14} />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* Trainers & Members Tab */}
      {(activeTab === 'trainers' || activeTab === 'dashboard') && (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(450px, 1fr))', gap: '1.5rem', marginBottom: '2.5rem' }}>
          {/* Trainers */}
          <div className="glass-card">
            <h3 style={{ fontSize: '1.2rem', fontWeight: 700, marginBottom: '1rem', color: '#f59e0b' }}>
              🏋️ Certified Gym Trainers
            </h3>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
              {trainersList.map((t) => (
                <div key={t.id} style={{
                  background: 'rgba(255, 255, 255, 0.02)',
                  border: '1px solid var(--border-color)',
                  borderRadius: '10px',
                  padding: '1rem'
                }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                    <div>
                      <strong style={{ fontSize: '1rem', color: '#f8fafc' }}>{t.user?.fullName}</strong>
                      <div style={{ fontSize: '0.8rem', color: '#f87171' }}>{t.specialization}</div>
                    </div>
                    <span className="badge badge-amber">{t.experienceYears} Years Exp</span>
                  </div>
                  <p style={{ fontSize: '0.8rem', color: 'var(--text-muted)', marginTop: '0.5rem' }}>
                    {t.bio}
                  </p>
                  <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)', marginTop: '0.5rem', display: 'flex', justifyContent: 'space-between' }}>
                    <span>Monthly Add-on Rate: <strong>2,000 LKR</strong></span>
                    <span>Contact: {t.user?.phone || '0775566778'}</span>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Members & Package Subscriptions */}
          <div className="glass-card">
            <h3 style={{ fontSize: '1.2rem', fontWeight: 700, marginBottom: '1rem', color: '#ef4444' }}>
              📋 Gym Members &amp; Package Plans
            </h3>
            <div className="table-responsive">
              <table className="custom-table">
                <thead>
                  <tr>
                    <th>Member</th>
                    <th>Package Addons</th>
                    <th>Monthly Fee</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {membershipsList.map((m) => (
                    <tr key={m.id}>
                      <td>
                        <strong>{m.customer?.fullName}</strong>
                        <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>{m.customer?.email}</div>
                      </td>
                      <td style={{ fontSize: '0.85rem' }}>
                        <div>Base: 2,500 LKR</div>
                        {m.hasTrainer && (
                          <div style={{ color: '#f87171' }}>+ Trainer ({m.trainer?.user?.fullName || 'Assigned'})</div>
                        )}
                        {m.hasTreadmill && (
                          <div style={{ color: '#34d399' }}>+ Treadmill Access</div>
                        )}
                      </td>
                      <td>
                        <strong style={{ color: '#10b981' }}>{formatLkr(m.totalMonthlyFeeLkr)}</strong>
                      </td>
                      <td>
                        <span className="badge badge-emerald">{m.status}</span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      )}

      {/* Revenue & Invoices Tab */}
      {(activeTab === 'billing' || activeTab === 'dashboard') && (
        <div className="glass-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.25rem' }}>
            <div>
              <h3 style={{ fontSize: '1.25rem', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                <DollarSign size={20} color="#10b981" /> Financial Invoices &amp; Revenue Breakdown
              </h3>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.8rem' }}>
                All generated billing transactions in Sri Lankan Rupees (LKR)
              </p>
            </div>
            <div style={{ fontSize: '1.1rem', fontWeight: 700, color: '#10b981' }}>
              Total: {formatLkr(stats?.totalRevenueLkr)}
            </div>
          </div>

          <div className="table-responsive">
            <table className="custom-table">
              <thead>
                <tr>
                  <th>Invoice No</th>
                  <th>Customer</th>
                  <th>Description</th>
                  <th>Amount (LKR)</th>
                  <th>Status</th>
                  <th>Date</th>
                </tr>
              </thead>
              <tbody>
                {invoicesList.map((inv) => (
                  <tr key={inv.id}>
                    <td>
                      <code style={{ color: '#f87171', fontWeight: 600 }}>{inv.invoiceNo}</code>
                    </td>
                    <td>
                      <strong>{inv.customer?.fullName}</strong>
                    </td>
                    <td style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>
                      {inv.description}
                    </td>
                    <td>
                      <strong style={{ color: '#10b981' }}>{formatLkr(inv.amountLkr)}</strong>
                    </td>
                    <td>
                      <span className="badge badge-emerald">{inv.paymentStatus}</span>
                    </td>
                    <td style={{ fontSize: '0.8rem', color: 'var(--text-dim)' }}>
                      {inv.invoiceDate ? new Date(inv.invoiceDate).toLocaleDateString() : 'Today'}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* Equipment Add/Edit Modal */}
      <Modal
        isOpen={isEquipModalOpen}
        onClose={() => setIsEquipModalOpen(false)}
        title={editingEquip ? 'Edit Equipment' : 'Add New Gym Equipment'}
      >
        <form onSubmit={handleSaveEquipment}>
          <div className="form-group">
            <label className="form-label">Equipment Name</label>
            <input
              type="text"
              className="form-input"
              required
              placeholder="e.g. Olympic Barbell 20kg"
              value={equipForm.name}
              onChange={(e) => setEquipForm({ ...equipForm, name: e.target.value })}
            />
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">Category</label>
              <select
                className="form-select"
                value={equipForm.category}
                onChange={(e) => setEquipForm({ ...equipForm, category: e.target.value })}
              >
                <option value="DUMBBELL">Dumbbell</option>
                <option value="WEIGHT_PLATE">Weight Plate</option>
                <option value="BAR">Bar</option>
                <option value="MACHINE">Machine Kind</option>
                <option value="RESISTANCE_BAND">Resistance Band</option>
              </select>
            </div>

            <div className="form-group">
              <label className="form-label">Quantity</label>
              <input
                type="number"
                min="1"
                className="form-input"
                required
                value={equipForm.quantity}
                onChange={(e) => setEquipForm({ ...equipForm, quantity: parseInt(e.target.value) || 1 })}
              />
            </div>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">Weight Specs</label>
              <input
                type="text"
                className="form-input"
                placeholder="e.g. Pairs 2.5kg - 35kg"
                value={equipForm.weightSpecs}
                onChange={(e) => setEquipForm({ ...equipForm, weightSpecs: e.target.value })}
              />
            </div>

            <div className="form-group">
              <label className="form-label">Status</label>
              <select
                className="form-select"
                value={equipForm.status}
                onChange={(e) => setEquipForm({ ...equipForm, status: e.target.value })}
              >
                <option value="AVAILABLE">AVAILABLE</option>
                <option value="MAINTENANCE">MAINTENANCE</option>
              </select>
            </div>
          </div>

          <div className="form-group">
            <label className="form-label">Description</label>
            <textarea
              className="form-textarea"
              rows="3"
              placeholder="Specifications and training benefits"
              value={equipForm.description}
              onChange={(e) => setEquipForm({ ...equipForm, description: e.target.value })}
            />
          </div>

          <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '0.75rem', marginTop: '1.5rem' }}>
            <button type="button" className="btn btn-outline" onClick={() => setIsEquipModalOpen(false)}>Cancel</button>
            <button type="submit" className="btn btn-primary">{editingEquip ? 'Update Equipment' : 'Create Equipment'}</button>
          </div>
        </form>
      </Modal>

      {/* Supplement Add/Edit Modal */}
      <Modal
        isOpen={isSuppModalOpen}
        onClose={() => setIsSuppModalOpen(false)}
        title={editingSupp ? 'Edit Supplement' : 'Add New Supplement to Store'}
      >
        <form onSubmit={handleSaveSupplement}>
          <div className="form-group">
            <label className="form-label">Product Name</label>
            <input
              type="text"
              className="form-input"
              required
              placeholder="e.g. 100% Gold Standard Whey"
              value={suppForm.name}
              onChange={(e) => setSuppForm({ ...suppForm, name: e.target.value })}
            />
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">Category</label>
              <select
                className="form-select"
                value={suppForm.category}
                onChange={(e) => setSuppForm({ ...suppForm, category: e.target.value })}
              >
                <option value="CREATINE">Creatine</option>
                <option value="WHEY_PROTEIN">Whey Protein</option>
                <option value="PROTEIN">Protein (Plant/Casein)</option>
                <option value="PRE_WORKOUT">Pre-Workout</option>
                <option value="OTHER">Other</option>
              </select>
            </div>

            <div className="form-group">
              <label className="form-label">Brand</label>
              <input
                type="text"
                className="form-input"
                placeholder="e.g. Optimum Nutrition"
                value={suppForm.brand}
                onChange={(e) => setSuppForm({ ...suppForm, brand: e.target.value })}
              />
            </div>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">Price (LKR)</label>
              <input
                type="number"
                min="0"
                step="50"
                className="form-input"
                required
                value={suppForm.priceLkr}
                onChange={(e) => setSuppForm({ ...suppForm, priceLkr: parseFloat(e.target.value) || 0 })}
              />
            </div>

            <div className="form-group">
              <label className="form-label">Stock Quantity</label>
              <input
                type="number"
                min="0"
                className="form-input"
                required
                value={suppForm.stockQuantity}
                onChange={(e) => setSuppForm({ ...suppForm, stockQuantity: parseInt(e.target.value) || 0 })}
              />
            </div>
          </div>

          <div className="form-group">
            <label className="form-label">Serving Size / Weight</label>
            <input
              type="text"
              className="form-input"
              placeholder="e.g. 5 lbs / 2.27kg (74 servings)"
              value={suppForm.servingSize}
              onChange={(e) => setSuppForm({ ...suppForm, servingSize: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label className="form-label">Description</label>
            <textarea
              className="form-textarea"
              rows="3"
              placeholder="Nutritional facts and features"
              value={suppForm.description}
              onChange={(e) => setSuppForm({ ...suppForm, description: e.target.value })}
            />
          </div>

          <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '0.75rem', marginTop: '1.5rem' }}>
            <button type="button" className="btn btn-outline" onClick={() => setIsSuppModalOpen(false)}>Cancel</button>
            <button type="submit" className="btn btn-primary">{editingSupp ? 'Update Supplement' : 'Add to Catalog'}</button>
          </div>
        </form>
      </Modal>
    </div>
  );
}
