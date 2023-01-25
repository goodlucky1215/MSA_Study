import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {register} from '../../seller_action';

function SellerRegisterPage() {
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [companyName, setCompanyName] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();

    const handleSubmit =  (e) => {
      e.preventDefault();
      let registerUserData = {
        id : userId,
        companyName : companyName,
        passwordEncrypt : password
      };
      register(registerUserData,setErrorMessage,navigate);
    }
    
    return (
      <div className="App">
        <h1>판매자 회원가입</h1>
        <form onSubmit={handleSubmit}>
          <input
            name="userId"
            value={userId}
            placeholder="아이디"
            onChange={(e) => setUserId(e.target.value)}
          />
          <div />
          <input
            type="password"
            name="password"
            value={password}
            placeholder="비밀번호"
            onChange={(e) => setPassword(e.target.value)}
          />
          <div />
          <input
            name="companyName"
            value={companyName}
            placeholder="브랜드 이름"
            onChange={(e) => setCompanyName(e.target.value)}
          />
          <div />
          <input type="submit" value={"완료"} />
          {errorMessage!==undefined && errorMessage.length>0 && <div>{errorMessage}</div>}
        </form>
      </div>
    );
  }
export default SellerRegisterPage;