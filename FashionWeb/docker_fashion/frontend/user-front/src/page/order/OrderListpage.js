import React, { useEffect, useState } from 'react';
import { orderList } from '../../order_action';

function OrderListPage() {
  const [orders, setOrders] = useState('');

  useEffect(() => {
    orderList(setOrders);
  },[]);

  const getOrderList = () => {
    const result = [];
    for(let i = 0 ; i<orders.length ; i++){
      result.push(
        <div key={i}>
          <table border="1" bordercolor="gray" width="600" height="100">
            <tbody>
              <tr align = "center" bgcolor="gray">
                <td width ="600">주문 날짜 : {orders[i].orderDate}</td>
              </tr>
            </tbody>
          </table>
          <table border="1" bordercolor="blue" width="600" height="100">
            <tbody>
              <tr align = "center" bgcolor="skybule">
                <td width ="200">상품 이름</td>
                <td width ="100">갯수</td>
                <td width ="200">가격</td>
                <td width ="100">주문 상태</td>
              </tr>
              {
                orders[i].orderItems.map((value,index) => (
                  <tr key={index} align = "center">
                    <td width ="200">{value.itemName}</td>
                    <td width ="100">{value.orderQuantity}</td>
                    <td width ="200">{value.orderPrice}</td>
                    <td width ="100">{value.orderStatus}</td>
                  </tr>
                ))
              }
            </tbody>
          </table>
          <pre></pre>
        </div>
      )
    }

    return result;
  }
  return (
    <div className="App">
      <h1>주문 목록</h1>
      <div>
        {orders!==undefined && orders.length>0 && getOrderList()}
      </div>
    </div>
  )
}

export default OrderListPage;