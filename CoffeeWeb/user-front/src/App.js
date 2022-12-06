import React from "react";
import LoginPage from './page/LoginPage';
import RegisterPage from './page/RegisterPage';
import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";

function App(){
  return(
      <BrowserRouter>
        <Routes>
					<Route path="/" element={<LoginPage />}></Route>
          <Route path="/RegisterPage" element={<RegisterPage />}></Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
