import React, {useEffect, useState} from 'react';
import {BrowserRouter, Switch, Route, Link} from 'react-router-dom'

function App() {
  return(
    <BrowserRouter>
        <div>
          <ul>
            <li>
              <Link to="/">로그인</Link>
            </li>
            <li>
              <Link to="/join">회원가입</Link>
            </li>
            <li>
              <Link to="/Order">주문</Link>
            </li>
            <li>
              <Link to="/UserInfo">사용자 정보</Link>
            </li>
          </ul>

          <switch>
            <Route path={"/"} exact={true}>
                <Login />
            </Route>
            <Route path={"/join"} exact={true}>
                <Join />
            </Route>
            <Route path={"/Order"} exact={true}>
                <Order />
            </Route>
            <Route path={"/UserInfo"} exact={true}>
                <UserInfo />
            </Route>
          </switch>
        </div>
    </BrowserRouter>
  );
}

export default App;
