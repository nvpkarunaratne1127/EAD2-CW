import React, { useState, useEffect } from 'react';
import { api } from '../api';
import { Plus, Trash2, Users, FileText, ShoppingBag } from 'lucide-react';

export default function TrainerPage() {
  const [activeTab, setActiveTab] = useState('trainers');
  const [trainers, setTrainers] = useState([]);
  const [workoutPlans, setWorkoutPlans] = useState([]);
  const [supplements, setSupplements] = useState([]);

  // Trainer Form
  const [trName, setTrName] = useState('');
  const [trEmail, setTrEmail] = useState('');
  const [trPhone, setTrPhone] = useState('');
  const [trSpec, setTrSpec] = useState('');
  const [trExp, setTrExp] = useState('3 Years');

  // Workout Plan Form
  const [wpTrainerId, setWpTrainerId] = useState(1);
  const [wpCustId, setWpCustId] = useState(1);
  const [wpRoutine, setWpRoutine] = useState('');
  const [wpDiet, setWpDiet] = useState('');

  // Supplement Form
  const [supName, setSupName] = useState('');
  const [supCategory, setSupCategory] = useState('CREATINE');
  const [supPrice, setSupPrice] = useState(8500);
  const [supQty, setSupQty] = useState(20);

  const loadData = async () => {
    try {
      const tr = await api.getTrainers().catch(() => []);
      setTrainers(tr || []);
      const wp = await api.getWorkoutPlans().catch(() => []);
      setWorkoutPlans(wp || []);
      const sp = await api.getSupplements().catch(() => []);
      setSupplements(sp || []);
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleAddTrainer = async (e) => {
    e.preventDefault();
    if (!trName || !trSpec) return;
    try {
      await api.createTrainer({
        name: trName,
        email: trEmail || `${trName.toLowerCase().replace(/\s+/g, '')}@fitpulse.lk`,
        phone: trPhone || '0771234567',
        specialization: trSpec,
        experience: trExp
      });
      setTrName('');
      setTrSpec('');
      loadData();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleDeleteTrainer = async (id) => {
    if (!window.confirm('Delete trainer profile?')) return;
    await api.deleteTrainer(id);
    loadData();
  };

  const handleAddWorkoutPlan = async (e) => {
    e.preventDefault();
    if (!wpRoutine) return;
    try {
      await api.createWorkoutPlan({
        trainerId: Number(wpTrainerId),
        customerId: Number(wpCustId),
        routineNotes: wpRoutine,
        dietNotes: wpDiet || 'Standard high-protein hydration plan',
        progressNotes: 'Initial onboarding plan'
      });
      setWpRoutine('');
      setWpDiet('');
      loadData();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleAddSupplement = async (e) => {
    e.preventDefault();
    if (!supName) return;
    try {
      await api.createSupplement({
        name: supName,
        category: supCategory,
        priceLkr: Number(supPrice),
        stockQuantity: Number(supQty)
      });
      setSupName('');
      loadData();
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div>
      {/* Sub Tabs */}
      <div style={{ display: 'flex', gap: '1rem', borderBottom: '1px solid #334155', marginBottom: '1.5rem' }}>
        <button
          onClick={() => setActiveTab('trainers')}
          style={{
            background: 'none', border: 'none',
            color: activeTab === 'trainers' ? '#38bdf8' : '#94a3b8',
            borderBottom: activeTab === 'trainers' ? '2px solid #38bdf8' : 'none',
            padding: '0.5rem 1rem', cursor: 'pointer', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '0.4rem'
          }}
        >
          <Users size={16} /> Certified Trainers (CRUD)
        </button>
        <button
          onClick={() => setActiveTab('workoutPlans')}
          style={{
            background: 'none', border: 'none',
            color: activeTab === 'workoutPlans' ? '#38bdf8' : '#94a3b8',
            borderBottom: activeTab === 'workoutPlans' ? '2px solid #38bdf8' : 'none',
            padding: '0.5rem 1rem', cursor: 'pointer', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '0.4rem'
          }}
        >
          <FileText size={16} /> Workout & Diet Plans (CRUD)
        </button>
        <button
          onClick={() => setActiveTab('supplements')}
          style={{
            background: 'none', border: 'none',
            color: activeTab === 'supplements' ? '#38bdf8' : '#94a3b8',
            borderBottom: activeTab === 'supplements' ? '2px solid #38bdf8' : 'none',
            padding: '0.5rem 1rem', cursor: 'pointer', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '0.4rem'
          }}
        >
          <ShoppingBag size={16} /> Supplement Catalog (CRUD)
        </button>
      </div>

      {/* Trainers Tab */}
      {activeTab === 'trainers' && (
        <div>
          <form onSubmit={handleAddTrainer} style={{ background: '#1e293b', padding: '1rem', borderRadius: '8px', marginBottom: '1rem', display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
            <input placeholder="Trainer Full Name *" value={trName} onChange={e => setTrName(e.target.value)} required style={{ flex: 1, minWidth: '150px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <input placeholder="Specialization (e.g. Hypertrophy, CSCS) *" value={trSpec} onChange={e => setTrSpec(e.target.value)} required style={{ flex: 1, minWidth: '180px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <input placeholder="Experience (e.g. 5 Years)" value={trExp} onChange={e => setTrExp(e.target.value)} style={{ width: '130px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <button type="submit" style={{ background: '#0284c7', color: '#fff', border: 'none', padding: '0.5rem 1rem', borderRadius: '4px', cursor: 'pointer', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '0.3rem' }}>
              <Plus size={16} /> Add Trainer
            </button>
          </form>

          <table style={{ background: '#1e293b', borderRadius: '8px', overflow: 'hidden' }}>
            <thead>
              <tr style={{ background: '#334155', color: '#94a3b8' }}>
                <th>ID</th>
                <th>Name</th>
                <th>Specialization</th>
                <th>Experience</th>
                <th>Email</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {trainers.length === 0 ? (
                <tr><td colSpan="6" style={{ textAlign: 'center', color: '#64748b' }}>No trainers added yet</td></tr>
              ) : (
                trainers.map(t => (
                  <tr key={t.id}>
                    <td>#{t.id}</td>
                    <td><strong>{t.name}</strong></td>
                    <td style={{ color: '#38bdf8' }}>{t.specialization}</td>
                    <td>{t.experience}</td>
                    <td style={{ color: '#94a3b8' }}>{t.email || '—'}</td>
                    <td>
                      <button onClick={() => handleDeleteTrainer(t.id)} style={{ background: '#ef4444', color: '#fff', border: 'none', padding: '0.3rem 0.6rem', borderRadius: '4px', cursor: 'pointer' }}>
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

      {/* Workout Plans Tab */}
      {activeTab === 'workoutPlans' && (
        <div>
          <form onSubmit={handleAddWorkoutPlan} style={{ background: '#1e293b', padding: '1rem', borderRadius: '8px', marginBottom: '1rem', display: 'flex', flexDirection: 'column', gap: '0.6rem' }}>
            <div style={{ display: 'flex', gap: '0.5rem' }}>
              <input type="number" placeholder="Trainer ID" value={wpTrainerId} onChange={e => setWpTrainerId(e.target.value)} style={{ width: '120px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
              <input type="number" placeholder="Customer ID" value={wpCustId} onChange={e => setWpCustId(e.target.value)} style={{ width: '120px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            </div>
            <textarea placeholder="Workout Split Routine Notes (e.g. Mon: Squats 5x5, Wed: Bench 4x8)..." value={wpRoutine} onChange={e => setWpRoutine(e.target.value)} rows="3" required style={{ padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <input placeholder="Diet & Supplementation Notes (e.g. 160g Protein, 5g Creatine)..." value={wpDiet} onChange={e => setWpDiet(e.target.value)} style={{ padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <button type="submit" style={{ alignSelf: 'flex-start', background: '#0284c7', color: '#fff', border: 'none', padding: '0.5rem 1.2rem', borderRadius: '4px', cursor: 'pointer', fontWeight: 600 }}>
              Save Workout Plan
            </button>
          </form>

          <table style={{ background: '#1e293b', borderRadius: '8px', overflow: 'hidden' }}>
            <thead>
              <tr style={{ background: '#334155', color: '#94a3b8' }}>
                <th>Plan ID</th>
                <th>Trainer ID</th>
                <th>Customer ID</th>
                <th>Routine Notes</th>
                <th>Diet Notes</th>
              </tr>
            </thead>
            <tbody>
              {workoutPlans.length === 0 ? (
                <tr><td colSpan="5" style={{ textAlign: 'center', color: '#64748b' }}>No workout plans created yet</td></tr>
              ) : (
                workoutPlans.map(wp => (
                  <tr key={wp.id}>
                    <td>#{wp.id}</td>
                    <td>Trainer #{wp.trainerId}</td>
                    <td>Customer #{wp.customerId}</td>
                    <td>{wp.routineNotes}</td>
                    <td style={{ color: '#10b981' }}>{wp.dietNotes}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}

      {/* Supplements Tab */}
      {activeTab === 'supplements' && (
        <div>
          <form onSubmit={handleAddSupplement} style={{ background: '#1e293b', padding: '1rem', borderRadius: '8px', marginBottom: '1rem', display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
            <input placeholder="Product Name *" value={supName} onChange={e => setSupName(e.target.value)} required style={{ flex: 2, minWidth: '160px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <select value={supCategory} onChange={e => setSupCategory(e.target.value)} style={{ flex: 1, padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }}>
              <option value="CREATINE">Creatine</option>
              <option value="WHEY_PROTEIN">Whey Protein</option>
              <option value="PROTEIN">Protein Powder</option>
              <option value="PRE_WORKOUT">Pre-Workout</option>
            </select>
            <input type="number" placeholder="Price (LKR)" value={supPrice} onChange={e => setSupPrice(e.target.value)} min="0" style={{ width: '120px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <input type="number" placeholder="Stock Qty" value={supQty} onChange={e => setSupQty(e.target.value)} min="0" style={{ width: '100px', padding: '0.5rem', borderRadius: '4px', border: '1px solid #475569', background: '#0f172a', color: '#fff' }} />
            <button type="submit" style={{ background: '#0284c7', color: '#fff', border: 'none', padding: '0.5rem 1rem', borderRadius: '4px', cursor: 'pointer', fontWeight: 600 }}>Add Product</button>
          </form>

          <table style={{ background: '#1e293b', borderRadius: '8px', overflow: 'hidden' }}>
            <thead>
              <tr style={{ background: '#334155', color: '#94a3b8' }}>
                <th>Product Name</th>
                <th>Category</th>
                <th>Price (LKR)</th>
                <th>Stock</th>
              </tr>
            </thead>
            <tbody>
              {supplements.length === 0 ? (
                <tr><td colSpan="4" style={{ textAlign: 'center', color: '#64748b' }}>No supplement products found</td></tr>
              ) : (
                supplements.map(s => (
                  <tr key={s.id}>
                    <td><strong>{s.name}</strong></td>
                    <td>{s.category}</td>
                    <td style={{ color: '#38bdf8', fontWeight: 600 }}>{Number(s.priceLkr || s.price).toLocaleString()} LKR</td>
                    <td style={{ color: '#10b981' }}>{s.stockQuantity || s.stock || 0} in stock</td>
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
