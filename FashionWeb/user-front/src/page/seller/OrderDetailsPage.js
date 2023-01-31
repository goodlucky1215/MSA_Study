import React, { useEffect, useState } from 'react';
import { checkOrderDetails, memberOrderitemStatus } from '../../seller_action';

function OrderDetailsPage() {
  const [orders, setOrders] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    checkOrderDetails(setOrders,setErrorMessage);
  },[]);

  const orderStatusButton = (orderitemId) => {
    memberOrderitemStatus(orderitemId,setErrorMessage);
  };

  const getOrderList = () => {
    const result = [];
    result.push(
      <>
        <table border="1" bordercolor="blue" width="1000" height="100">
          <tbody>
            <tr align = "center" bgcolor="skybule">
              <td width ="600">상품 이름</td>
              <td width ="200">아이디</td>
              <td width ="100">갯수</td>
              <td width ="200">가격</td>
              <td width ="600">주문 날짜</td>
              <td width ="100">주문 상태</td>
              <td width ="200">주문 확인</td>
            </tr>
            {
              orders.map((value) => (
                <tr key={value.orderitemId} align = "center">
                  <td width ="600">{value.itemName}</td>
                  <td width ="200">{value.id}</td>
                  <td width ="100">{value.orderQuantity}</td>
                  <td width ="200">{value.orderPrice}</td>
                  <td width ="600">{value.orderDate}</td>
                  <td width ="100">{value.orderStatus}</td>
                  {value.orderStatus==="ORDER" &&
                  <td>
                    <button onClick={() => orderStatusButton(value.orderitemId)}>확인</button>
                  </td>
                  }
                </tr>
              ))
            }
          </tbody>
        </table>
      </>
    )

    return result;
  }
  return (
    <div className="App">
      <h1>고객 주문 목록</h1>
      <div>
        {orders!==undefined && orders.length>0 && getOrderList()}
        {errorMessage!==undefined && errorMessage.length>0 && <div>{errorMessage}</div>}
      </div>
    </div>
  )
}

export default OrderDetailsPage;