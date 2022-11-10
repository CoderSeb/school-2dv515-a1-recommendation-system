import { useState } from 'react'
import './App.css'



import RatingTable from './components/RatingTable'
import RecTable from './components/RecTable'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className="App">
      <RecTable />
      <RatingTable />
    </div>
  )
}

export default App
