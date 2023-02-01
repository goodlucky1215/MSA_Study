import axios from 'axios';

export async function register(registerUserData,setErrorMessage,navigate) {
    await axios
        .post('/seller-service/joinSeller',
            registerUserData,
        )
        .then(function (response){
            if(response.data.code === "S") navigate("/SellerLoginPage");
            else setErrorMessage(response.data.message);
            })
        .catch(function (error){
            setErrorMessage("회원가입을 다시 시도해주세요.");
        })
    
}

export async function login(loginUserData,setErrorMessage,navigate) {
    await axios
        .post('/login',
            loginUserData,
        )
        .then(function (response){
            if(response.data.code === "S") {
                successfulLoginForJwt(response.headers.token,response.headers.sellerid);
                navigate("/SellerMainPage");
            }
            else setErrorMessage(response.data.message);
        })
        .catch(function (error){
            setErrorMessage("로그인을 다시 시도해주세요.");
        })

}

export async function saveItem(itemRegisterData,setErrorMessage,navigate) {
    await axios
        .post('/seller-service/saveItem',
            itemRegisterData,
        )
        .then(function (response){
            if(response.data.code === "S") navigate("/SellerItemsPage");
            else setErrorMessage(response.data.message);
        })
        .catch(function (error){
            setErrorMessage("로그인을 다시 시도해주세요.");
        })

}

export async function sellerItems(setOrders, setErrorMessage) {
    await axios
        .get('/seller-service/sellerItems',
        )
        .then(function (response){
            if(response.data.code!=="S") alert(response.data.message+" 주문 목록을 다시 새로고침 해주세요.");
            else if(response.data.data.length===0) setErrorMessage("등록된 상품이 없습니다.");
            else setOrders(response.data.data);

        })
        .catch(function (error){
            alert("다시 시도해주세요.");
        });
    
};

export async function checkOrderDetails(setOrders,setErrorMessage) {
    await axios
    .get('/seller-service/checkOrderDetails'
    )
    .then(function (response){
        console.log(response);
        if(response.data.code!=="S") alert(response.data.message+" 주문 목록을 다시 새로고침 해주세요.");
        else if(response.data.data.length===0) setErrorMessage("주문된 상품 목록이 없습니다.");
        else setOrders(response.data.data);

    })
    .catch(function (error){
        alert("다시 시도해주세요.");
    });    
};

export async function memberOrderitemStatus(orderitemId,setErrorMessage) {
    await axios
        .post('/seller-service/memberOrderitemStatus/'+orderitemId)
        .then(function (response){
            console.log(response);
            if(response.data.code!=="S") alert(response.data.message);
        })
        .catch(function (error){
            alert("다시 시도해주세요.");
        });
};




////////////////////////////로그인 시 토큰 정보 가지고 있기///////////////////////////
function successfulLoginForJwt(token,sellerid) {
    localStorage.setItem('token', token);
    localStorage.setItem('sellerid', sellerid);
    setupAxiosInterceptors();
};

function setupAxiosInterceptors() {
    axios.interceptors.request.use(
        config => {
            const token = localStorage.getItem('token');
            const sellerid = localStorage.getItem('sellerid');
            if(token  && sellerid){
                config.headers['Authorization'] = 'Bearer' + token;
                config.headers['sellerid'] = sellerid;
            }
            return config;
        },
        error => {
            Promise.reject(error);
        }
    );
};