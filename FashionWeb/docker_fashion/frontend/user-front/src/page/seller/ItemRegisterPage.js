import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {saveItem} from '../../seller_action';

function ItemRegisterPage() {
    const [itemName, setItemName] = useState('');
    const [price, setPrice] = useState('');
    const [quantity, setQuantity] = useState('');
    const [category, setCategory] = useState('outer');
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();

    const handleSubmit =  (e) => {
      e.preventDefault();
      let itemRegisterData = {
        itemName : itemName,
        quantity : quantity,
        price : price,
        category : category
      };
      saveItem(itemRegisterData,setErrorMessage,navigate);
    }

    let categorys = ['outer', 'top', 'bottom', 'shoes', 'bag'];
    
    return (
      <div className="App">
        <h1>상품 등록</h1>
        <form onSubmit={handleSubmit}>
          <input
            name="itemName"
            value={itemName}
            placeholder="상품명"
            onChange={(e) => setItemName(e.target.value)}
          />
          <div />
          <input
            name="price"
            value={price}
            placeholder="가격"
            onChange={(e) => setPrice(e.target.value)}
          />
          <div />
          <input
            name="quantity"
            value={quantity}
            placeholder="수량"
            onChange={(e) => setQuantity(e.target.value)}
          />
          <div />
          <select
                value={category}
                onChange={(e) =>setCategory(e.target.value)}
            >
                {categorys.map(item => (
                    <option value={item} key={item}>
                        {item}
                    </option>
                ))}
            </select>
          <div />
          <input type="submit" value={"완료"} />
          {errorMessage!==undefined && errorMessage.length>0 && <div>{errorMessage}</div>}
        </form>
      </div>
    );
  }
export default ItemRegisterPage;