import { useEffect, useRef, useState } from 'react';
import { Button } from 'react-bootstrap';
import Select from 'react-select';
import styles from './App.module.css';
import TableManager, { REC_METHOD, SELECTION, SIMILARITY, TRecParams } from './components/TableManager';


interface IUser {
  id: number
  name: string
}

interface IUserSelectionObject {
  value: number,
  label: string
}

function App() {
  const [count, setCount] = useState(3)
  const [tableSelection, setTableSelection] = useState<SELECTION>(SELECTION.NOTHING)
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [users, setUsers] = useState<IUser[]>([]);
  const userRef = useRef<IUser>({ id: -1, name: "" })
  const similarityRef = useRef<SIMILARITY>(SIMILARITY.Euclidean)
  const recMethodRef = useRef<REC_METHOD>(REC_METHOD.ItemBased)
  const [userSelection, setUserSelection] = useState<IUserSelectionObject[]>([])
  const [params, setParams] = useState<TRecParams>({
    userName: userRef.current.name,
    method: recMethodRef.current,
    similarity: similarityRef.current,
    count: count
  })

  useEffect(() => {
    fetch("http://localhost:8080/api/users")
      .then(res => res.json())
      .then(
        (result: IUser[]) => {
          setIsLoaded(true);
          setUsers(result);
        },
        (error) => {
          setIsLoaded(true);
          setError(error);
        }
      )
  }, [])

  useEffect(() => {
    let newUsers: IUserSelectionObject[] = users.map(user => {
      return { value: user.id, label: `${user.id}: ${user.name}` }
    })
    setUserSelection(newUsers)
  }, [users])


  useEffect(() => {
    setParams({
      userName: userRef.current.name,
      method: REC_METHOD.UserBased,
      similarity: similarityRef.current,
      count: count
    })
  }, [userRef, tableSelection, count, similarityRef])

  const handleUserChange = (selectedOption: any) => {
    userRef.current = users.find(user => user.id === selectedOption.value)!
    setParams({ ...params, userName: userRef.current.name })
  }

  const handleSimilarityChange = (selectedOption: any) => {
    similarityRef.current = selectedOption.label
    setParams({ ...params, similarity: similarityRef.current })
  }

  const handleCorrelationChange = (selectedOption: any) => {
    recMethodRef.current = selectedOption.label
    setParams({ ...params, method: recMethodRef.current })
  }

  const handleCountChange = (event: any) => {
    const value = parseInt(event.target.value)
    if (value > 0 && value < users.length) {
      setCount(value)
    }
  }

  if (error) {
    return <div>Error: {error}</div>
  } else if (!isLoaded) {
    return <div>Loading...</div>
  } else {
    return (
      <div className={styles.container}>
        <div className={styles.tableOptions}>
          {userSelection.length > 0 && <Select placeholder="Choose user..." className={styles.nameSelection} options={userSelection} onChange={handleUserChange} />}
          <Select placeholder="Choose similarity..." className={styles.similaritySelection} options={[{ value: 0, label: "Euclidean" }, { value: 1, label: "Pearson" }]} onChange={handleSimilarityChange} />
          <Select placeholder="Choose method..." className={styles.methodSelection} options={[{ value: 0, label: "ItemBased" }, { value: 1, label: "UserBased" }]} onChange={handleCorrelationChange} />
          <input type="number" className={styles.countInput} value={count} onChange={handleCountChange} />
        </div>
        {userRef.current.id !== 0 && <p>Selected user: {userRef.current.name}</p>}
        {userRef.current.name !== "" && (
          <div className={styles.buttonContainer}>
            <Button className={styles.button} variant="dark" onClick={() => setTableSelection(SELECTION.TOP_MATCHES)}>Find top matching users</Button>
            <Button className={styles.button} variant="dark" onClick={() => setTableSelection(SELECTION.REC_MOVIES)}>Find recommended movies</Button>
            <Button className={styles.button} variant="dark" onClick={() => setTableSelection(SELECTION.REC_MOVIES_IB)}>Find recommendations, item-based</Button>
          </div>
        )}

        {tableSelection !== SELECTION.NOTHING && <TableManager
          tableSelection={tableSelection}
          listCount={count}
          params={params}
        />}

      </div >
    )
  }
}

export default App
