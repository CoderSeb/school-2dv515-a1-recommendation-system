import { useEffect, useState } from 'react';
import { Table } from 'react-bootstrap';
import styles from './styles/TopMatchingTable.module.css';

interface IMatchingUser {
  id: number,
  name: string,
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

function TopMatchingTable() {
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<IMatchingUser[]>([]);
  const [user, setUser] = useState<string>("Toby")
  const [similarity, setSimilarity] = useState<SIMILARITY>(SIMILARITY.Euclidean)
  const [recMethod, setRecMethod] = useState<REC_METHOD>(REC_METHOD.ItemBased)
  const [count, setCount] = useState<number>(3)
  const baseRec: TRecParams = {
    userName: user,
    method: recMethod,
    similarity: similarity
  }

  const [recParams, setRecParams] = useState<TRecParams>(baseRec)


  useEffect(() => {
    fetch(`http://localhost:8080/api/recommendation/top-matching-users?user=${recParams.userName}&method=${recParams.method}&similarity=${recParams.similarity}&count=${count}`)
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
              <th>User</th>
              <th>User ID</th>
              <th>Score</th>
            </tr>
          </thead>
          <tbody>
            {items.map(item => (
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