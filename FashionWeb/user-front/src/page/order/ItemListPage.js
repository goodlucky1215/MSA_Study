import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { itemList, order } from '../../order_action';

function ItemListPage() {

  const [items, setItems] = useState('');
  const [itemCounts, setItemCounts] = useState();
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    itemList(setItems, setItemCounts, setErrorMessage);
  },[]);

  const navigate = useNavigate();
  const OrderButton = (i) => {
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
          <div key={i} style={{ border:"1px solid", padding:"10px", width:"15%" ,marginBottom:"10px" }}>
            <ul style={{ listStyleType: "none" }}>
              <li>
                상품명 : {items[i].itemName}
              </li>
              <li>
                가격 : {items[i].price}
              </li>
              <li>
                수량 : {items[i].quantity}
              </li>
              <li>
                카테고리 : {items[i].category}
              </li>
              <li>
                판매회사 : {items[i].companyName}
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