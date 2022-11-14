import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    port: 3000,
    host: '0.0.0.0'
  },
  define: {
    'process.env': process.env
  },
  plugins: [react({
    include: "**/*.tsx"
  })]
})
