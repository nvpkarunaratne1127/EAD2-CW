import React, { useState, useEffect } from 'react';
import { api } from '../services/api';
import Modal from '../components/Modal';
import { Users, Dumbbell, ClipboardList, CheckCircle, Save, Phone, Mail, Award } from 'lucide-react';

export default function TrainerDashboard({ currentUser, activeTab }) {
  const [trainerProfile, setTrainerProfile] = useState(null);
  const [assignedClients, setAssignedClients] = useState([]);
  const [equipmentList, setEquipmentList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [alert, setAlert] = useState(null);

  // Workout Plan Modal
  const [isPlanModalOpen, setIsPlanModalOpen] = useState(false);
  const [selectedClient, setSelectedClient] = useState(null);
  const [planForm, setPlanForm] = useState({
    routineNotes: '',
    dietNotes: '',
    progressNotes: ''
  });

  const fetchData = async () => {
    setLoading(true);
    try {
      // 1. Fetch trainer profile for current user
      const trainer = await api.getTrainerByUserId(currentUser.id);
      setTrainerProfile(trainer);

      // 2. Fetch assigned clients
      const clients = await api.getTrainerClients(trainer.id);
      setAssignedClients(clients || []);

      // 3. Fetch equipment list
      const eq = await api.getEquipment();
      setEquipmentList(eq || []);
    } catch (err) {
      console.error(err);
      setAlert({ type: 'error', message: err.message || 'Failed to load trainer data' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [currentUser]);

  const handleOpenPlanModal = async (clientMembership) => {
    setSelectedClient(clientMembership);
    try {
      const existingPlan = await api.getCustomerWorkoutPlan(clientMembership.customer.id);
      if (existingPlan) {
        setPlanForm({
          routineNotes: existingPlan.routineNotes || '',
          dietNotes: existingPlan.dietNotes || '',
          progressNotes: existingPlan.progressNotes || ''
        });
      } else {
        setPlanForm({
          routineNotes: 'Day 1 (Chest & Triceps):\n- Bench Press: 4 sets x 8 reps\n- Incline Dumbbell Press: 3 sets x 10 reps\n- Cable Flyes: 3 sets x 12 reps\n- Tricep Pushdowns: 4 sets x 12 reps',
          dietNotes: 'Target 160g protein daily. 1 scoop Whey Protein post-workout + 5g Creatine Monohydrate.',
          progressNotes: 'Initial fitness assessment completed.'
        });
      }
    } catch (err) {
      // default template
      setPlanForm({
        routineNotes: '',
        dietNotes: '',
        progressNotes: ''
      });
    }
    setIsPlanModalOpen(true);
  };

  const handleSavePlan = async (e) => {
    e.preventDefault();
    try {
      await api.saveWorkoutPlan({
        trainerId: trainerProfile.id,
        customerId: selectedClient.customer.id,
        routineNotes: planForm.routineNotes,
        dietNotes: planForm.dietNotes,
        progressNotes: planForm.progressNotes
      });
      setAlert({ type: 'success', message: `Workout & diet plan saved for ${selectedClient.customer.fullName}!` });
      setIsPlanModalOpen(false);
    } catch (err) {
      setAlert({ type: 'error', message: err.message || 'Failed to save workout plan' });
    }
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

      {/* Trainer Profile Banner */}
      <div className="glass-card" style={{ marginBottom: '2rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1.5rem' }}>
        <div>
          <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginBottom: '0.5rem' }}>
            <h2 style={{ fontSize: '1.6rem', fontWeight: 800 }}>Trainer Portal &bull; {currentUser.fullName}</h2>
            <span className="badge badge-amber">{trainerProfile?.experienceYears || 5} Years Experience</span>
          </div>
          <div style={{ color: '#f87171', fontWeight: 600, fontSize: '0.95rem' }}>
            Specialization: {trainerProfile?.specialization || 'Strength & Conditioning'}
          </div>
          <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem', marginTop: '0.4rem', maxWidth: '700px' }}>
            {trainerProfile?.bio || 'Certified fitness trainer guiding gym goers with customized strength, diet, and hypertrophy regimes.'}
          </p>
        </div>
        <div style={{
          background: 'rgba(245, 158, 11, 0.1)',
          border: '1px solid rgba(245, 158, 11, 0.3)',
          borderRadius: '12px',
          padding: '1rem 1.5rem',
          textAlign: 'center'
        }}>
          <div style={{ fontSize: '0.75rem', textTransform: 'uppercase', color: '#fbbf24', fontWeight: 700 }}>
            Active Assigned Clients
          </div>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#f8fafc' }}>
            {assignedClients.length}
          </div>
        </div>
      </div>

      {/* Assigned Clients Section */}
      {(activeTab === 'clients' || !activeTab || activeTab === 'dashboard') && (
        <div className="glass-card" style={{ marginBottom: '2.5rem' }}>
          <div style={{ marginBottom: '1.25rem' }}>
            <h3 style={{ fontSize: '1.3rem', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
              <Users size={22} color="#ef4444" /> My Assigned Clients (Gym Goers)
            </h3>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>
              Gym-goers enrolled with you as their personal coach (+2,000 LKR plan addon). Manage their training split &amp; diet.
            </p>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(360px, 1fr))', gap: '1.25rem' }}>
            {assignedClients.length === 0 ? (
              <div style={{ padding: '2rem', color: 'var(--text-muted)', textAlign: 'center', gridColumn: '1 / -1' }}>
                No clients currently assigned to your roster.
              </div>
            ) : (
              assignedClients.map((item) => (
                <div key={item.id} style={{
                  background: 'rgba(255, 255, 255, 0.03)',
                  border: '1px solid var(--border-color)',
                  borderRadius: '12px',
                  padding: '1.25rem',
                  display: 'flex',
                  flexDirection: 'column',
                  justifyContent: 'space-between',
                  gap: '1rem'
                }}>
                  <div>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                      <strong style={{ fontSize: '1.1rem', color: '#f8fafc' }}>
                        {item.customer?.fullName}
                      </strong>
                      <span className="badge badge-emerald">Active Client</span>
                    </div>

                    <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)', marginTop: '0.5rem', display: 'flex', flexDirection: 'column', gap: '0.25rem' }}>
                      <span style={{ display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
                        <Mail size={13} color="#94a3b8" /> {item.customer?.email || 'N/A'}
                      </span>
                      <span style={{ display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
                        <Phone size={13} color="#94a3b8" /> {item.customer?.phone || '0771234567'}
                      </span>
                    </div>

                    <div style={{ marginTop: '0.75rem', padding: '0.6rem', background: 'rgba(0,0,0,0.2)', borderRadius: '8px', fontSize: '0.8rem' }}>
                      <div>Package: <strong>{item.totalMonthlyFeeLkr} LKR/mo</strong></div>
                      <div style={{ color: item.hasTreadmill ? '#34d399' : 'var(--text-dim)' }}>
                        {item.hasTreadmill ? '✓ Includes Treadmill / Cardio Access' : '— Standard Gym Access'}
                      </div>
                    </div>
                  </div>

                  <button
                    className="btn btn-primary btn-sm"
                    style={{ width: '100%' }}
                    onClick={() => handleOpenPlanModal(item)}
                  >
                    <ClipboardList size={16} /> Manage Workout &amp; Diet Plan
                  </button>
                </div>
              ))
            )}
          </div>
        </div>
      )}

      {/* Gym Equipment View for Trainer */}
      {(activeTab === 'equipment') && (
        <div className="glass-card">
          <div style={{ marginBottom: '1.25rem' }}>
            <h3 style={{ fontSize: '1.25rem', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
              <Dumbbell size={20} color="#ef4444" /> Available Gym Equipment for Training
            </h3>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.8rem' }}>
              Reference equipment list to build exercises for your clients
            </p>
          </div>

          <div className="table-responsive">
            <table className="custom-table">
              <thead>
                <tr>
                  <th>Equipment</th>
                  <th>Category</th>
                  <th>Specifications</th>
                  <th>Available Qty</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {equipmentList.map((eq) => (
                  <tr key={eq.id}>
                    <td><strong>{eq.name}</strong></td>
                    <td><span className="badge badge-cyan">{eq.category.replace('_', ' ')}</span></td>
                    <td style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>{eq.weightSpecs}</td>
                    <td><strong>{eq.quantity}</strong></td>
                    <td>
                      <span className={`badge ${eq.status === 'AVAILABLE' ? 'badge-emerald' : 'badge-amber'}`}>
                        {eq.status}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* Workout Plan Modal */}
      <Modal
        isOpen={isPlanModalOpen}
        onClose={() => setIsPlanModalOpen(false)}
        title={`Workout & Diet Plan: ${selectedClient?.customer?.fullName}`}
      >
        <form onSubmit={handleSavePlan}>
          <div className="form-group">
            <label className="form-label">Workout Routine &amp; Training Split</label>
            <textarea
              className="form-textarea"
              rows="6"
              required
              placeholder="e.g. Monday: Bench Press 4x8, Squats 5x5..."
              value={planForm.routineNotes}
              onChange={(e) => setPlanForm({ ...planForm, routineNotes: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label className="form-label">Diet &amp; Nutrition Guidelines (Supplements)</label>
            <textarea
              className="form-textarea"
              rows="4"
              placeholder="e.g. 160g protein, 1 scoop Whey Protein post-workout, 5g Creatine..."
              value={planForm.dietNotes}
              onChange={(e) => setPlanForm({ ...planForm, dietNotes: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label className="form-label">Client Progress &amp; Form Feedback</label>
            <textarea
              className="form-textarea"
              rows="3"
              placeholder="e.g. Target 72kg, squat weight up by 15kg, excellent form..."
              value={planForm.progressNotes}
              onChange={(e) => setPlanForm({ ...planForm, progressNotes: e.target.value })}
            />
          </div>

          <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '0.75rem', marginTop: '1.5rem' }}>
            <button type="button" className="btn btn-outline" onClick={() => setIsPlanModalOpen(false)}>Cancel</button>
            <button type="submit" className="btn btn-primary">
              <Save size={16} /> Save Plan for Client
            </button>
          </div>
        </form>
      </Modal>
    </div>
  );
}
