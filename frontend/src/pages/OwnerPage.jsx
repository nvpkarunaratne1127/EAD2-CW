import React, { useState, useEffect } from 'react';
import { api } from '../api';
import { Plus, Trash2, Dumbbell, Receipt, BarChart3, Search } from 'lucide-react';

export default function OwnerPage() {
  const [stats, setStats] = useState(null);
  const [activeTab, setActiveTab] = useState('equipment');
  const [equipmentList, setEquipmentList] = useState([]);
  const [invoices, setInvoices] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');

  // Equipment Form
  const [name, setName] = useState('');
  const [category, setCategory] = useState('DUMBBELL');
  const [quantity, setQuantity] = useState(10);
  const [weightSpecs, setWeightSpecs] = useState('');
  const [status, setStatus] = useState('AVAILABLE');
  const [location, setLocation] = useState('Zone A');

  // Invoice Form
  const [custName, setCustName] = useState('');
  const [custId, setCustId] = useState(1);
  const [invDesc, setInvDesc] = useState('');
  const [invAmount, setInvAmount] = useState(2500);

  const loadData = async () => {
    try {
      const s = await api.getOwnerStats().catch(() => null);
      setStats(s);
      const eq = await api.getEquipment().catch(() => []);
      setEquipmentList(eq || []);
      const inv = await api.getInvoices().catch(() => []);
      setInvoices(inv || []);
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleAddEquipment = async (e) => {
    e.preventDefault();
    if (!name) return;
    try {
      await api.createEquipment({
        name,
        category,
        quantity: Number(quantity),
        weightSpecs: weightSpecs || 'Standard',
        status,
        location
      });
      setName('');
      setWeightSpecs('');
      loadData();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleDeleteEquipment = async (id) => {
    if (!window.confirm('Delete this equipment item?')) return;
    await api.deleteEquipment(id);
    loadData();
  };

  const handleAddInvoice = async (e) => {
    e.preventDefault();
    if (!custName || !invDesc) return;
    try {
      await api.createInvoice({
        customerName: custName,
        customerId: Number(custId),
        description: invDesc,
        amountLkr: Number(invAmount),
        paymentStatus: 'PAID',
        category: 'MEMBERSHIP'
      });
      setCustName('');
      setInvDesc('');
      loadData();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleDeleteInvoice = async (id) => {
    if (!window.confirm('Delete this invoice?')) return;
    await api.deleteInvoice(id);
    loadData();
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchQuery.trim()) {
      loadData();
      return;
    }
    const res = await api.searchEquipment(searchQuery).catch(() => []);
    setEquipmentList(res || []);
  };

  return (
    <div>
      {/* KPI Overview */}
      {stats && (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(220px, 1fr))', gap: '1rem', marginBottom: '1.5rem' }}>
          <div style={{ background: '#1e293b', padding: '1.2rem', borderRadius: '8px', borderLeft: '4px solid #38bdf8' }}>
            <div style={{ color: '#94a3b8', fontSize: '0.85rem' }}>Total Equipment Units</div>
            <div style={{ fontSize: '1.8rem', fontWeight: 700, color: '#f8fafc', marginTop: '0.2rem' }}>{stats.totalEquipmentQuantity || 0}</div>
          </div>
          <div style={{ background: '#1e293b', padding: '1.2rem', borderRadius: '8px', borderLeft: '4px solid #10b981' }}>
            <div style={{ color: '#94a3b8', fontSize: '0.85rem' }}>Total Paid Revenue (LKR)</div>
            <div style={{ fontSize: '1.8rem', fontWeight: 700, color: '#10b981', marginTop: '0.2rem' }}>{(stats.totalRevenueLkr || 0).toLocaleString()} LKR</div>
          </div>
          <div style={{ background: '#1e293b', padding: '1.2rem', borderRadius: '8px', borderLeft: '4px solid #f59e0b' }}>
            <div style={{ color: '#94a3b8', fontSize: '0.85rem' }}>Total Invoices Recorded</div>
            <div style={{ fontSize: '1.8rem', fontWeight: 700, color: '#f8fafc', marginTop: '0.2rem' }}>{stats.totalInvoices || 0}</div>
          </div>
          <div style={{ background: '#1e293b', padding: '1.2rem', borderRadius: '8px', borderLeft: '4px solid #ef4444' }}>
            <div style={{ color: '#94a3b8', fontSize: '0.85rem' }}>Under Maintenance</div>
            <div style={{ fontSize: '1.8rem', fontWeight: 700, color: '#f8fafc', marginTop: '0.2rem' }}>{stats.underMaintenanceCount || 0}</div>
          </div>
        </div>
      )}

      {/* Tabs */}
      <div style={{ display: 'flex', gap: '1rem', borderBottom: '1px solid #334155', marginBottom: '1.5rem' }}>
        <button
          onClick={() => setActiveTab('equipment')}
          style={{
            background: 'none', border: 'none',
            color: activeTab === 'equipment' ? '#38bdf8' : '#94a3b8',
            borderBottom: activeTab === 'equipment' ? '2px solid #38bdf8' : 'none',
            padding: '0.5rem 1rem', cursor: 'pointer', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '0.4rem'
          }}
        >
          <Dumbbell size={16} /> Equipment Inventory (CRUD)
        </button>
        <button
          onClick={() => setActiveTab('invoices')}
          style={{
            background: 'none', border: 'none',
            color: activeTab === 'invoices' ? '#38bdf8' : '#94a3b8',
            borderBottom: activeTab === 'invoices' ? '2px solid #38bdf8' : 'none',
            padding: '0.5rem 1rem', cursor: 'pointer', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '0.4rem'
          }}
        >
          <Receipt size={16} /> Invoices & Billing (CRUD)
        </button>
      </div>

      {/* Equipment View */}
      {activeTab === 'equipment' && (
        <div>
          {/* Add Equipment Form */}
          <form onSubmit={handleAddEquipment} style={{ background: '#1e293b', padding: '1rem', borderRadius: '8px', marginBottom: '1rem', display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(140px, 1fr))', gap: '0.6rem' }}>
            <input placeholder="Equipment Name *" value={name} onChange={e => setName(e.target.value)} required style={{ padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <select value={category} onChange={e => setCategory(e.target.value)} style={{ padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }}>
              <option value="DUMBBELL">Dumbbell</option>
              <option value="WEIGHT_PLATE">Weight Plate</option>
              <option value="BAR">Barbell</option>
              <option value="MACHINE">Machine</option>
              <option value="RESISTANCE_BAND">Resistance Band</option>
            </select>
            <input type="number" placeholder="Qty" value={quantity} onChange={e => setQuantity(e.target.value)} min="0" style={{ padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <input placeholder="Weight specs (e.g. 20kg)" value={weightSpecs} onChange={e => setWeightSpecs(e.target.value)} style={{ padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <select value={status} onChange={e => setStatus(e.target.value)} style={{ padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }}>
              <option value="AVAILABLE">AVAILABLE</option>
              <option value="UNDER_MAINTENANCE">UNDER_MAINTENANCE</option>
            </select>
            <button type="submit" style={{ background: '#0284c7', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 600, display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '0.3rem' }}>
              <Plus size={16} /> Add Equipment
            </button>
          </form>

          {/* Search bar */}
          <form onSubmit={handleSearch} style={{ display: 'flex', gap: '0.5rem', marginBottom: '1rem' }}>
            <input placeholder="Search equipment by name..." value={searchQuery} onChange={e => setSearchQuery(e.target.value)} style={{ flex: 1, padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <button type="submit" style={{ background: '#334155', color: '#fff', border: 'none', padding: '0.5rem 1rem', borderRadius: '4px', cursor: 'pointer', display: 'flex', alignItems: 'center', gap: '0.3rem' }}>
              <Search size={14} /> Search
            </button>
            {searchQuery && (
              <button type="button" onClick={() => { setSearchQuery(''); loadData(); }} style={{ background: '#475569', color: '#fff', border: 'none', padding: '0.5rem 0.8rem', borderRadius: '4px', cursor: 'pointer' }}>
                Reset
              </button>
            )}
          </form>

          {/* Table */}
          <table style={{ background: '#1e293b', borderRadius: '8px', overflow: 'hidden' }}>
            <thead>
              <tr style={{ background: '#334155', color: '#94a3b8' }}>
                <th>Name</th>
                <th>Category</th>
                <th>Quantity</th>
                <th>Specs</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {equipmentList.length === 0 ? (
                <tr><td colSpan="6" style={{ textAlign: 'center', color: '#64748b' }}>No equipment records found</td></tr>
              ) : (
                equipmentList.map(item => (
                  <tr key={item.id}>
                    <td><strong>{item.name}</strong></td>
                    <td>{item.category}</td>
                    <td>{item.quantity}</td>
                    <td style={{ color: '#94a3b8' }}>{item.weightSpecs || '—'}</td>
                    <td>
                      <span style={{ padding: '0.2rem 0.5rem', borderRadius: '4px', fontSize: '0.75rem', background: item.status === 'AVAILABLE' ? 'rgba(16, 185, 129, 0.2)' : 'rgba(239, 68, 68, 0.2)', color: item.status === 'AVAILABLE' ? '#10b981' : '#ef4444' }}>
                        {item.status}
                      </span>
                    </td>
                    <td>
                      <button onClick={() => handleDeleteEquipment(item.id)} style={{ background: '#ef4444', color: '#fff', border: 'none', padding: '0.3rem 0.6rem', borderRadius: '4px', cursor: 'pointer' }}>
                        <Trash2 size={14} />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}

      {/* Invoices View */}
      {activeTab === 'invoices' && (
        <div>
          {/* Add Invoice Form */}
          <form onSubmit={handleAddInvoice} style={{ background: '#1e293b', padding: '1rem', borderRadius: '8px', marginBottom: '1rem', display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
            <input placeholder="Customer Name *" value={custName} onChange={e => setCustName(e.target.value)} required style={{ flex: 1, minWidth: '150px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <input placeholder="Description *" value={invDesc} onChange={e => setInvDesc(e.target.value)} required style={{ flex: 2, minWidth: '200px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <input type="number" placeholder="Amount (LKR)" value={invAmount} onChange={e => setInvAmount(e.target.value)} min="0" style={{ width: '130px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <button type="submit" style={{ background: '#10b981', color: '#fff', border: 'none', padding: '0.5rem 1rem', borderRadius: '4px', cursor: 'pointer', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '0.3rem' }}>
              <Plus size={16} /> Create Invoice
            </button>
          </form>

          {/* Table */}
          <table style={{ background: '#1e293b', borderRadius: '8px', overflow: 'hidden' }}>
            <thead>
              <tr style={{ background: '#334155', color: '#94a3b8' }}>
                <th>Invoice No</th>
                <th>Customer</th>
                <th>Description</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {invoices.length === 0 ? (
                <tr><td colSpan="6" style={{ textAlign: 'center', color: '#64748b' }}>No invoices issued yet</td></tr>
              ) : (
                invoices.map(inv => (
                  <tr key={inv.id}>
                    <td><code>{inv.invoiceNo}</code></td>
                    <td>{inv.customerName}</td>
                    <td>{inv.description}</td>
                    <td style={{ color: '#38bdf8', fontWeight: 600 }}>{Number(inv.amountLkr).toLocaleString()} LKR</td>
                    <td>
                      <span style={{ padding: '0.2rem 0.5rem', borderRadius: '4px', fontSize: '0.75rem', background: 'rgba(16, 185, 129, 0.2)', color: '#10b981' }}>
                        {inv.paymentStatus}
                      </span>
                    </td>
                    <td>
                      <button onClick={() => handleDeleteInvoice(inv.id)} style={{ background: '#ef4444', color: '#fff', border: 'none', padding: '0.3rem 0.6rem', borderRadius: '4px', cursor: 'pointer' }}>
                        <Trash2 size={14} />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
