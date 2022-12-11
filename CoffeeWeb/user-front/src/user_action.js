import axios from 'axios';

export async function login(loginUserData,setErrorMessage,navigate) {
    await axios
        .post('/login',
            loginUserData,
        )
        .then(function (response){
            console.log(loginUserData);
            if(response.body === "true") navigate("/Main");
            else setErrorMessage(response.body);
            //else setErrorMessage("입력 정보가 틀렸습니다.");
        })
        .catch(function (error){
            setErrorMessage("asdfsdf");
        })

}

export async function register(registerUserData,setErrorMessage,navigate) {
    await axios
        .post('/user-service/join',
            registerUserData,
        )
        .then(function (response){
            if(response.body === "true") navigate("/");
            else setErrorMessage("이미 존재하는 회원입니다.");
            })
        .catch(function (error){
            setErrorMessage("Sdfsdf");
        })
    
}