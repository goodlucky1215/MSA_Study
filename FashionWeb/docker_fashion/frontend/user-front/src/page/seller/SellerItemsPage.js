import React, { useEffect, useState } from 'react';
import { sellerItems } from '../../seller_action';

function SellerItemsPage() {

  const [items, setItems] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    sellerItems(setItems, setErrorMessage);
  },[]);


  const getitemList = () => {
      const result = [];
      for(let i = 0 ; i<items.length ; i++){
        result.push(
          <div key={i} style={{ border:"1px solid", padding:"10px", width:"30%" ,marginBottom:"10px" }}>
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
            </ul>
          </div>
        )
      }

      return result;
  }

    return (
        <div className="App">
        <h1>판매자가 판매하는 상품 목록들</h1>
        <div>
          {items!==undefined && items.length>0 && getitemList()}
          {errorMessage!==undefined && errorMessage.length>0 && <div>{errorMessage}</div>}
        </div>
      </div>
    )
}

export default SellerItemsPage;