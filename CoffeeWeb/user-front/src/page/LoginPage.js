import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function LoginPage() {

    const navigate = useNavigate();
 
    const navigateToRegisterPage = () => {
      navigate("/RegisterPage");
    };

    const [userId, setEmail] = useState('');
    const [password, setPassword] = useState('');
  
    const handleSubmit = (e) => {
      e.preventDefault();
      console.log(`submit! ${userId} ${password}`);
    };

    
    return (
        <div className="App">
        <h1>로그인 화면</h1>
        <form onSubmit={handleSubmit}>
          <input
            name="userId"
            value={userId}
            placeholder="아이디"
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="password"
            name="password"
            value={password}
            placeholder="비밀번호"
            onChange={(e) => setPassword(e.target.value)}
          />
          <div>
              <button type="submit">로그인</button>
              <button onClick={() =>  navigateToRegisterPage()}>
                회원가입
              </button>
          </div>
        </form>
      </div>
    )
}

export default LoginPage;