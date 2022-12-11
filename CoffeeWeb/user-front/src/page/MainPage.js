import React from 'react';
import { useNavigate } from 'react-router-dom';

function MainPage() {
    const navigate = useNavigate();
  
    const navigateToUserInfoPage = () => {
      navigate("/UserInfoPage");
    };
    
    const navigateToCoffeOrderPage = () => {
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
      </div>
    )
}

export default MainPage;