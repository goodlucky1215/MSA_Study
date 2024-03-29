import axios from 'axios';

export async function itemList(setItems, setItemCounts, setErrorMessage) {
    await axios
        .get('/order-service/itemList',
            {
                headers:{
                    Authorization: 'Bearer' + localStorage.getItem('token'),
                    pkId: localStorage.getItem('pkId')
                }
            }
        )
        .then(function (response){
            let items = response.data.data;
            if(response.data.code!=="S") setErrorMessage(response.data.message);
            else if(items.length === 0) setErrorMessage("상품 목록이 없습니다.");
            else {
                setItems(items);
                let counts = Array.apply(null, new Array(items.length)).map(Number.prototype.valueOf,1);
                setItemCounts(counts);
            }
        })
        .catch(function (error){
            setErrorMessage("재 로그인 해주세요.");
        });
    
};

export async function order(orderItemDto,navigate) {
    await axios
        .post('/order-service/order',
            orderItemDto,
            {
                headers:{
                    Authorization: 'Bearer' + localStorage.getItem('token'),
                    pkId: localStorage.getItem('pkId')
                }
            }
        )
        .then(function (response){
            if(response.data.code==="S") navigate("/OrderListPage");
            else alert(response.data.message+" 상품 주문을 다시 해주세요.");

        })
        .catch(function (error){
            alert("상품 구매에 실패하였습니다. 다시 시도해주세요.");
        });
    
};

export async function orderList(setOrders) {
    await axios
        .get('/order-service/orderList',
            {
                headers:{
                    Authorization: 'Bearer' + localStorage.getItem('token'),
                    pkId: localStorage.getItem('pkId')
                }
            }
        )
        .then(function (response){
            console.log(response);
            if(response.data.code!=="S") alert(response.data.message+" 주문 목록을 다시 새로고침 해주세요.");
            else setOrders(response.data.data);

        })
        .catch(function (error){
            alert("다시 시도해주세요.");
        });
    
};
