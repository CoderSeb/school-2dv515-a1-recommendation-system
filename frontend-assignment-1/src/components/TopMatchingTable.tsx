import { useEffect, useRef, useState } from 'react';
import { Table } from 'react-bootstrap';
import styles from './styles/TopMatchingTable.module.css';
import { TRecParams } from './TableManager';

interface IMatchingUser {
  id: number,
  name: string,
  score: number
}

function TopMatchingTable({ params }: { params: TRecParams }) {
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<IMatchingUser[]>([]);
  const userRef = useRef<String>(params.userName)
  const [recParams, setRecParams] = useState<TRecParams>(params)

  useEffect(() => {
    setRecParams(params)
  }, [params])

  const backendUrl: string = process.env.BACKEND_URL || "http://localhost:8080/"
  useEffect(() => {
    fetch(`${backendUrl}api/recommendation/top-matching-users?userId=${recParams.userId}&method=${recParams.method}&similarity=${recParams.similarity}&count=${recParams.count}`)
      .then(res => res.json())
      .then(
        (result: IMatchingUser[]) => {
          setIsLoaded(true);
          setItems(result);
        },
        (error) => {
          setIsLoaded(true);
          setError(error);
        }
      )
  }, [recParams, userRef])

  if (error) {
    return <div>Error: {error}</div>
  } else if (!isLoaded) {
    return <div>Loading...</div>
  } else {
    return (
      <div className={styles.container}>
        <h3>Top matching users for {params.userName} using {params.similarity}</h3>
        <Table striped bordered hover variant="dark">
          <thead>

            <tr>
              <th>User</th>
              <th>User ID</th>
              <th>Score</th>
            </tr>
          </thead>
          <tbody>
            {items.length > 0 && items.map(item => (
              <tr key={item.id + item.score}>
                <td>{item.name}</td>
                <td>{item.id}</td>
                <td>{item.score.toFixed(4)}</td>
              </tr>
            ))}
          </tbody>

        </Table>

      </div>

    )
  }
}

export default TopMatchingTable