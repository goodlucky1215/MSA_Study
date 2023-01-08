import React, { useEffect, useState } from 'react';
import { itemList } from '../order_action';

function ItemListPage() {

  const [items, setItems] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    itemList(setItems, setErrorMessage);
  },[]);

  const getitemList = () => {
      console.log("ddd");
      const result = [];
      for(let i = 0 ; i<items.length ; i++){
        result.push(<div key={i}> {items[i].itemName} </div>)
      }
      return result;
  }

    return (
        <div className="App">
        <h1>아이템 목록</h1>
        <div>
          {items!==undefined && items.length>0 && getitemList()}
          {errorMessage!==undefined && errorMessage.length>0 && <div>{errorMessage}</div>}
        </div>
      </div>
    )
}

export default ItemListPage;