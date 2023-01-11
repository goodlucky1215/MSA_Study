import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { itemList, order } from '../order_action';

function ItemListPage() {

  const [items, setItems] = useState('');
  const [itemCounts, setItemCounts] = useState();
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    itemList(setItems, setItemCounts, setErrorMessage);
  },[]);

  const navigate = useNavigate();
  const OrderButton = (i) => {
    console.log(items[i].itemId, itemCounts[i]);
    let orderItemDto = [ {
      itemId : items[i].itemId,
      orderQuantity : itemCounts[i],
    } ];
    order(orderItemDto,navigate); 
  };

  const Increase = (i)=>{
    let counts = [...itemCounts];
    counts[i] = counts[i]+1;
    setItemCounts(counts);
  }
  const Decrease = (i) =>{
    let counts = [...itemCounts];
    counts[i] = counts[i] > 1 ? counts[i]-1 : counts[i];
    setItemCounts(counts);
  }

  const getitemList = () => {
      const result = [];
      for(let i = 0 ; i<items.length ; i++){
        result.push(
          <div key={i}>
            <ul style={{ listStyleType: "none" }}>
              <li>
                {items[i].itemName}
              </li>
              <li>
                {items[i].price}
              </li>
              <li>
                {items[i].quantity}
              </li>
              <li>
                {items[i].category}
              </li>
              <li>
                {items[i].companyName}
              </li>
            </ul>
            {items[i].quantity!==0 &&
              <>
                <div>
                    <button onClick={() => Increase(i)}>+</button>
                    <span> {itemCounts[i]} </span>
                    <button onClick={() => Decrease(i)}>-</button>
                </div>
                <button onClick={() => OrderButton(i)}> 구매 </button>
              </>
            }
          </div>
        )
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