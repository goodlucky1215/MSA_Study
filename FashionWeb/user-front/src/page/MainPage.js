import { useNavigate } from 'react-router-dom';

function MainPage() {

    const navigate = useNavigate();
  
    const navigateToUserInfoPage = (e) => {
      navigate("/UserInfoPage");
    };
    
    const navigateToOrderPage = (e) => {
      e.preventDefault();
      navigate("/ItemListPage");
    };

    return (
        <div className="App">
        <h1>로그인 화면</h1>
        <div>
          <button onClick={() =>  navigateToUserInfoPage()}>
            회원 정보 변경
          </button>
          <button onClick={() =>  navigateToOrderPage()}>
            상품 목록
          </button>
        </div>
      </div>
    )
}

export default MainPage;