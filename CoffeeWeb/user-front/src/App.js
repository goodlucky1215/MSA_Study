import React from "react";
import LoginPage from './page/LoginPage';
import RegisterPage from './page/RegisterPage';
import MainPage from './page/MainPage';
import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";
import UserInfoPage from "./page/UserInfoPage";

function App(){
  return(
      <BrowserRouter>
        <Routes>
					<Route path="/" element={<LoginPage />}></Route>
          <Route path="/RegisterPage" element={<RegisterPage />}></Route>
          <Route path="/MainPage" element={<MainPage />}></Route>
          <Route path="/UserInfoPage" element={<UserInfoPage />}></Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
