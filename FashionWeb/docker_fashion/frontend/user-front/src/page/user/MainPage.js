import { useNavigate } from 'react-router-dom';

function MainPage() {

    const navigate = useNavigate();
  
    const navigateToUserInfoPage = (e) => {
      navigate("/UserInfoPage");
    };
    
    const navigateToItemListPage = (e) => {
      navigate("/ItemListPage");
    };

    const navigateToOrderListPage = (e) => {
      navigate("/OrderListPage");
    };

    return (
        <div className="App">
        <h1>로그인 화면</h1>
        <div>
          <button onClick={() =>  navigateToUserInfoPage()}>
            회원 정보 변경
          </button>
          <button onClick={() =>  navigateToItemListPage()}>
            상품 목록
          </button>
          <button onClick={() =>  navigateToOrderListPage()}>
            사용자 주문 정보
          </button>
        </div>
      </div>
    )
}

export default MainPage;