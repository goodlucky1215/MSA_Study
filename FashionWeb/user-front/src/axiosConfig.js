// axiosConfig.js
import React from 'react';
import axios from 'axios';
const axiosInstance = axios.create({
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});
axiosInstance.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
    const pkId = localStorage.getItem('pkId');
    console.log(token);
    console.log(pkId);
    if(token  && pkId){
        config.headers['Authorization'] = 'Bearer' + token;
        config.headers['pkId'] = pkId;
    }
    console.log("이게 맞냐");
    return config;
  },
  err => {
    console.log("이게 에러냐");
    return Promise.reject(err);
  },
);

function setupAxiosInterceptors() {
  axios.interceptors.request.use(
      async config => {
          const token = localStorage.getItem('token');
          const sellerid = localStorage.getItem('sellerid');
          console.log(token);
          console.log(sellerid);
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

/*
axiosInstance.interceptors.response.use(
  config => {
    return config;
  },
  err => {
    return Promise.reject(err);
  },
);
*/
export default axiosInstance;