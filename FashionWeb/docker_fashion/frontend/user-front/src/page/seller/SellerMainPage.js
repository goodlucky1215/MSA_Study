import { useNavigate } from 'react-router-dom';

function SellerMainPage() {

    const navigate = useNavigate();
  
    const navigateToRegisterItemPage = (e) => {
      navigate("/ItemRegisterPage");
    };
    
    const navigateToSellerItemsPage = (e) => {
      navigate("/SellerItemsPage");
    };

    const navigateToCheckOrderDetailsPage = (e) => {
      navigate("/OrderDetailsPage");
    };

    return (
        <div className="App">
        <h1>판매자 목차</h1>
        <div>
          <button onClick={() =>  navigateToRegisterItemPage()}>
            상품 등록
          </button>
          <button onClick={() =>  navigateToSellerItemsPage()}>
            등록한 상품 목록
          </button>
          <button onClick={() =>  navigateToCheckOrderDetailsPage()}>
            고객 주문건 목록
          </button>
        </div>
      </div>
    )
}

export default SellerMainPage;