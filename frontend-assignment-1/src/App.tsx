import { useState } from 'react'
import './App.css'



import RatingTable from './components/RatingTable'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className="App">
      <RatingTable />
    </div>
  )
}

export default App
