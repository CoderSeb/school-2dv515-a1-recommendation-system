import { useEffect, useRef, useState } from 'react';
import { Table } from 'react-bootstrap';
import styles from './styles/TopRecommendedTable.module.css';
import { TRecParams } from './TableManager';

interface IRecommendedMovie {
  title: string,
  id: number,
  score: number
}

function TopRecommendedTable({ params }: { params: TRecParams }) {
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<IRecommendedMovie[]>([]);
  const userRef = useRef<String>(params.userName)
  const [recParams, setRecParams] = useState<TRecParams>(params)

  useEffect(() => {
    setRecParams(params)
  }, [params])

  const backendUrl: string = import.meta.env.VITE_BACKEND_URL || "http://localhost:8080/"

  useEffect(() => {
    fetch(`${backendUrl}api/recommendation?userId=${recParams.userId}&method=${recParams.method}&similarity=${recParams.similarity}&count=${recParams.count}`)
      .then(res => res.json())
      .then(
        (result: IRecommendedMovie[]) => {
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
              <th>Movie</th>
              <th>Movie ID</th>
              <th>Score</th>
            </tr>
          </thead>
          <tbody>
            {items.length > 0 && items.map(item => (
              <tr key={item.id + item.score}>
                <td>{item.title}</td>
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

export default TopRecommendedTable