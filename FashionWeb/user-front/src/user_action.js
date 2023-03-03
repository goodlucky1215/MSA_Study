import axios from 'axios';

export async function login(loginUserData,setErrorMessage,navigate) {
    await axios
        .post('/user-service/login',
            loginUserData,
        )
        .then(function (response){
            if(response.data.code === "S") {
                successfulLoginForJwt(response.headers.token,response.headers.pkid);
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
            if(response.data.code === "S") navigate("/");
            else setErrorMessage(response.data.message);
            })
        .catch(function (error){
            setErrorMessage("회원가입을 다시 시도해주세요.");
        })
    
}

export async function userInfo(setNickname,setGrade,setErrorMessage) {
    await axios
        .get('/user-service/userInfo',
            {
                headers:{
                    Authorization: 'Bearer' + localStorage.getItem('token'),
                    pkId: localStorage.getItem('pkId')
                }
            }
        )
        .then(function (response){
            if(response.data.code !== "S")setErrorMessage(response.data.message);
            else {
                setNickname(response.data.data.nickname);
                setGrade(response.data.data.grade);
            }
        })
        .catch(function (error){
            setErrorMessage("재 로그인 해주세요.");
        });
    
};

export async function nicknameChange(UserInfoDto,setNickname,setErrorMessage) {
    await axios
        .post('/user-service/nicknamechange',
            UserInfoDto,
        )
        .then(function (response){
            if(response.data.code !== "S") setErrorMessage(response.data.message);
            else {
                setNickname(response.data.data.nickname);
                setErrorMessage("");
            }
        })
        .catch(function (error){
            setErrorMessage("재 로그인 해주세요.");
        });
    
};

////////////////////////////로그인 시 토큰 정보 가지고 있기///////////////////////////
function successfulLoginForJwt(token,pkId) {
    localStorage.setItem('token', token);
    localStorage.setItem('pkId', pkId);
    setupAxiosInterceptors();
};

function setupAxiosInterceptors() {
    axios.interceptors.request.use(
        config => {
            const token = localStorage.getItem('token');
            const pkId = localStorage.getItem('pkId');
            if(token  && pkId){
                config.headers['Authorization'] = 'Bearer' + token;
                config.headers['pkId'] = pkId;
            }
            return config;
        },
        error => {
            Promise.reject(error);
        }
    );
};