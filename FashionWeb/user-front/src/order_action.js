import axios from 'axios';

export async function itemList(setItems, setErrorMessage) {
    await axios
        .get('/order-service/itemList',
        )
        .then(function (response){
            console.log(response);
            let items = response.data;
            if(items.length === 0) setErrorMessage("상품 목록이 없습니다.");
            else setItems(items);
        })
        .catch(function (error){
            console.log(error);
            setErrorMessage("재 로그인 해주세요.");
        });
    
};
