import axios from 'axios';

export async function login(loginUserData,setErrorMessage,navigate) {
    await axios
        .post('/user-service/login',
            loginUserData,
        )
        .then(function (response){
            if(response.data.result === true) navigate("/MainPage");
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