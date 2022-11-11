import { useEffect, useState } from "react";
import styles from './styles/TableManager.module.css';
import TopMatchingTable from "./TopMatchingTable";
import TopRecommendedTable from "./TopRecommendedTable";

export enum SELECTION {
  NOTHING,
  TOP_MATCHES,
  REC_MOVIES,
  REC_MOVIES_IB
}

export enum SIMILARITY {
  Euclidean = "Euclidean",
  Pearson = "Pearson"
}

export enum REC_METHOD {
  ItemBased = "ItemBased",
  UserBased = "UserBased"
}

export type TRecParams = {
  userName: string;
  method: REC_METHOD;
  similarity: SIMILARITY;
  count: number;
}

export interface ITableManagerProps {
  tableSelection: SELECTION,
  listCount: number,
  params: TRecParams
}

function TableManager({ tableSelection, params }: ITableManagerProps) {
  const [selection, setSelection] = useState<SELECTION>(tableSelection)
  const [parameters, setParameters] = useState<TRecParams>(params)



  const renderState = (sel: SELECTION) => {
    switch (sel) {
      case SELECTION.TOP_MATCHES:
        return (
          <TopMatchingTable
            params={parameters} />
        )
      case SELECTION.REC_MOVIES:
        return (
          <TopRecommendedTable
            params={parameters} />
        )
      case SELECTION.REC_MOVIES_IB:
        return (
          <div>Recommended movies, item-based to come...</div>
        )
      case SELECTION.NOTHING:
        return (
          <div>Select one of the tables above</div>
        )
    }
  }

  useEffect(() => {
    setParameters(params)
    setSelection(tableSelection)
  }, [tableSelection, params])

  return (
    <div className={styles.container}>
      {renderState(selection)}
    </div>
  )
}

export default TableManager