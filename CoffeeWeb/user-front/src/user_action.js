import axios from 'axios';

export function loginUser(dataToSubmit) {

    const request = axios.get('/login', dataToSubmit)
        .then(response => response.data)

}

export default function registerUser(registerUserData,setErrorMessage,navigate) {
    axios
        .post('/user-service/join', {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: registerUserData,
        })
        .then(function (response){
            //setErrorMessage(res.body);
            if(response.body === "true") navigate("/");
            else  navigate("/");
            })
        .catch(function (error){
            console.log("Sdfsdf");
            setErrorMessage("Sdfsdf");
        })
    
}