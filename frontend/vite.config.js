import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      // Owner and Customer endpoints (Port 8080)
      '/api/owner': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/api/customers': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      // Trainer and Supplement endpoints (Port 8081)
      '/api/trainers': {
        target: 'http://localhost:8081',
        changeOrigin: true
      },
      '/api/workout-plans': {
        target: 'http://localhost:8081',
        changeOrigin: true
      },
      '/api/supplements': {
        target: 'http://localhost:8081',
        changeOrigin: true
      },
      '/api/supplement-orders': {
        target: 'http://localhost:8081',
        changeOrigin: true
      }
    }
  }
})
