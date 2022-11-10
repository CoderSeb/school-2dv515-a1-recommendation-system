import { useEffect, useState } from 'react';
import Table from 'react-bootstrap/Table';
import styles from './styles/RecTable.module.css';

interface IRecommendation {
  movieName: string,
  movieId: number,
  score: number
}

enum SIMILARITY {
  Euclidean = "Euclidean",
  Pearson = "Pearson"
}

enum REC_METHOD {
  ItemBased = "ItemBased",
  UserBased = "UserBased"
}

type TRecParams = {
  userName: string;
  method: REC_METHOD;
  similarity: SIMILARITY;
}

function RecTable() {
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<IRecommendation[]>([]);
  const [user, setUser] = useState<string>("Mike")
  const [similarity, setSimilarity] = useState<SIMILARITY>(SIMILARITY.Euclidean)
  const [recMethod, setRecMethod] = useState<REC_METHOD>(REC_METHOD.ItemBased)

  const baseRec: TRecParams = {
    userName: user,
    method: recMethod,
    similarity: similarity
  }

  const [recParams, setRecParams] = useState<TRecParams>(baseRec)


  useEffect(() => {
    fetch(`http://localhost:8080/api/recommendation?user=${recParams.userName}&method=${recParams.method}&similarity=${recParams.similarity}`)
      .then(res => res.json())
      .then(
        (result: IRecommendation[]) => {
          setIsLoaded(true);
          setItems(result);
        },
        (error) => {
          setIsLoaded(true);
          setError(error);
        }
      )
  }, [recParams])

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
              <th>Movie</th>
              <th>Movie ID</th>
              <th>Score</th>
            </tr>
          </thead>
          <tbody>
            {items.map(item => (
              <tr key={item.movieId + item.score}>
                <td>{item.movieName}</td>
                <td>{item.movieId}</td>
                <td>{item.score}</td>
              </tr>
            ))}
          </tbody>

        </Table>

      </div>

    )
  }
}

export default RecTable