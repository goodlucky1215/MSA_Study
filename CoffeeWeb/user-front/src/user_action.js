import axios from 'axios';
import {
    LOGIN_USER,
    REGISTER_USER,
} from './types';

export function loginUser(dataToSubmit) {

    const request = axios.get('/login', dataToSubmit)
        .then(response => response.data)

    return {
        type: LOGIN_USER,
        payload: request
    }
}

export function registerUser(registerUserData) {

    axios
        .post('/user-service/join', {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: registerUserData,
        })
        .then(
            //setErrorMessage(res.body);
            //f(res.body === "true") navigate("/");
            //else  navigate("/");
        )
        .catch(err => {
            //setErrorMessage(err);
        })
    
}