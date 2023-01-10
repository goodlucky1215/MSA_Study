import axios from 'axios';

export async function itemList(setItems, setItemCounts, setErrorMessage) {
    await axios
        .get('/order-service/itemList',
        )
        .then(function (response){
            console.log(response);
            let items = response.data.data;
            if(response.data.code!=="S") setErrorMessage(response.data.msg);
            else if(items.length === 0) setErrorMessage("상품 목록이 없습니다.");
            else {
                setItems(items);
                let counts = Array.apply(null, new Array(items.length)).map(Number.prototype.valueOf,1);
                setItemCounts(counts);
            }
        })
        .catch(function (error){
            console.log(error);
            setErrorMessage("재 로그인 해주세요.");
        });
    
};

export async function order(setItems,navigate) {
    await axios
        .post('/order-service/order',
        )
        .then(function (response){
            console.log(response);
            if(response.data.code==="S") navigate("/");
            else alert(response.data.msg+" : 상품 주문을 다시 해주세요.");

        })
        .catch(function (error){
            console.log(error);
            alert("상품 구매에 실패하였습니다. 다시 시도해주세요.");
        });
    
};
