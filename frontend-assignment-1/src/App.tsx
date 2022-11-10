import { useState } from 'react'
import './App.css'



import TopMatchingTable from './components/TopMatchingTable'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className="App">
      <TopMatchingTable />
    </div>
  )
}

export default App
