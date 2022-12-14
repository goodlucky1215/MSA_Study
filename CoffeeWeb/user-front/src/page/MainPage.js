import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { userInfo } from '../user_action';

function MainPage() {
  const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();
  
    const navigateToUserInfoPage = (e) => {
      //e.preventDefault();
      userInfo(setErrorMessage);
      //navigate("/UserInfoPage");
    };
    
    const navigateToCoffeOrderPage = (e) => {
      
      navigate("/CoffeOrderPage");
    };

    return (
        <div className="App">
        <h1>로그인 화면</h1>
        <div>
          <button onClick={() =>  navigateToUserInfoPage()}>
            회원 정보 변경
          </button>
          <button onClick={() =>  navigateToCoffeOrderPage()}>
            커피 주문
          </button>
        </div>
        {errorMessage!==undefined && errorMessage.length>0 && <div>{errorMessage}</div>}
      </div>
    )
}

export default MainPage;