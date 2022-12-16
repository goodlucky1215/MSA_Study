import axios from 'axios';

export async function login(loginUserData,setErrorMessage,navigate) {
    await axios
        .post('/user-service/login',
            loginUserData,
        )
        .then(function (response){
            if(response.data.result === true) {
                successfulLoginForJwt(response.headers.token,response.headers.userid);
                navigate("/MainPage");
            }
            else setErrorMessage(response.data.message);
        })
        .catch(function (error){
            setErrorMessage("로그인을 다시 시도해주세요.");
        })

}

export async function register(registerUserData,setErrorMessage,navigate) {
    await axios
        .post('/user-service/join',
            registerUserData,
        )
        .then(function (response){
            if(response.data === true) navigate("/");
            else if(response.data === false) setErrorMessage("이미 존재하는 회원입니다.");
            else setErrorMessage(response.data.message);
            })
        .catch(function (error){
            setErrorMessage("회원가입을 다시 시도해주세요.");
        })
    
}

export async function userInfo(setErrorMessage) {
    await axios
        .get('/user-service/userInfo',
        )
        .then(function (response){
            console.log(response);
        })
        .catch(function (error){
            console.log(error);
            setErrorMessage("재 로그인 해주세요.");
        });
    
};

function successfulLoginForJwt(token,userId) {
    localStorage.setItem('token', token);
    localStorage.setItem('userId', userId);
    setupAxiosInterceptors();
};

function setupAxiosInterceptors() {
    axios.interceptors.request.use(
        config => {
            const token = localStorage.getItem('token');
            const userId = localStorage.getItem('userId');
            if(token  && userId){
                config.headers['Authorization'] = 'Bearer' + token;
                config.headers['userId'] = userId;
                console.log("ddd2" + config);
            }
            return config;
        },
        error => {
            Promise.reject(error);
        }
    );
};