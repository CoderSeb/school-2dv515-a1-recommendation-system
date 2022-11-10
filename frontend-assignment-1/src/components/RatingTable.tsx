import { useEffect, useState } from 'react';
import Table from 'react-bootstrap/Table';
import styles from './styles/RatingTable.module.css'

interface IRating {
  id: number,
  userId: number,
  movieId: number,
  rating: number
}

function RatingTable() {
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<IRating[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/ratings")
      .then(res => res.json())
      .then(
        (result: IRating[]) => {
          setIsLoaded(true);
          setItems(result);
        },
        (error) => {
          setIsLoaded(true);
          setError(error);
        }
      )
  }, [])

  if (error) {
    return <div>Error: {error}</div>
  } else if (!isLoaded) {
    return <div>Loading...</div>
  } else {
    return (
      <div className={styles.container}>
        <Table striped bordered hover variant="dark">
          <thead>
            <tr>
              <th>User ID</th>
              <th>Movie ID</th>
              <th>Rating</th>
            </tr>
          </thead>
          <tbody>
            {items.map(item => (
              <tr key={item.id}>
                <td>{item.userId}</td>
                <td>{item.movieId}</td>
                <td>{item.rating}</td>
              </tr>
            ))}
          </tbody>

        </Table>

      </div>

    )
  }
}

export default RatingTable