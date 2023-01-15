import React from "react";
import LoginPage from './page/user/LoginPage';
import RegisterPage from './page/user/RegisterPage';
import MainPage from './page/user/MainPage';
import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";
import UserInfoPage from "./page/user/UserInfoPage";
import ItemListPage from "./page/order/ItemListPage";
import OrderListPage from "./page/order/OrderListpage";

function App(){
  return(
      <BrowserRouter>
        <Routes>
					<Route path="/" element={<LoginPage />}></Route>
          <Route path="/RegisterPage" element={<RegisterPage />}></Route>
          <Route path="/MainPage" element={<MainPage />}></Route>
          <Route path="/UserInfoPage" element={<UserInfoPage />}></Route>
          <Route path="/ItemListPage" element={<ItemListPage />}></Route>
          <Route path="/OrderListPage" element={<OrderListPage />}></Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
