import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {login} from '../../seller_action';

function SellerLoginPage() {

    const navigate = useNavigate();

    const navigateToRegisterPage = () => {
      navigate("/SellerRegisterPage");
    };

    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = (e) => {
      e.preventDefault();
      let loginUserData = {
        id : userId,
        password : password
      };
      login(loginUserData,setErrorMessage,navigate);
    }
    
    return (
        <div className="App">
        <h1>판매자 로그인 화면</h1>
        <form onSubmit={handleSubmit}>
          <input
            name="userId"
            value={userId}
            placeholder="아이디"
            onChange={(e) => setUserId(e.target.value)}
          />
          <input
            type="password"
            name="password"
            value={password}
            placeholder="비밀번호"
            onChange={(e) => setPassword(e.target.value)}
          />
          <div>
            <input type="submit" value={"로그인"} />
            <button onClick={() =>  navigateToRegisterPage()}>
                  회원가입
            </button>
          </div>
          {errorMessage!==undefined && errorMessage.length>0 && <div>{errorMessage}</div>}
        </form>
      </div>
    )
}

export default SellerLoginPage;