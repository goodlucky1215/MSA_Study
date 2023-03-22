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
import SellerRegisterPage from "./page/seller/SellerRegisterPage";
import SellerLoginPage from "./page/seller/SellerLoginPage";
import SellerMainPage from "./page/seller/SellerMainPage";
import SellerItemsPage from "./page/seller/SellerItemsPage";
import OrderDetailsPage from "./page/seller/OrderDetailsPage";
import ItemRegisterPage from "./page/seller/ItemRegisterPage";

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
          <Route path="/SellerRegisterPage" element={<SellerRegisterPage />}></Route>
          <Route path="/SellerLoginPage" element={<SellerLoginPage />}></Route>
          <Route path="/SellerMainPage" element={<SellerMainPage />}></Route>
          <Route path="/SellerItemsPage" element={<SellerItemsPage />}></Route>
          <Route path="/OrderDetailsPage" element={<OrderDetailsPage />}></Route>
          <Route path="/ItemRegisterPage" element={<ItemRegisterPage />}></Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
