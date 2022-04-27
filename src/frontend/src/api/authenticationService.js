// import React from 'react';
// import axios from 'axios';
//
//
// const getToken=()=>{
//     return localStorage.getItem('USER_KEY');
// }
//
// export const userLogin=(authRequest)=>{
//     return axios({
//         'method':'POST',
//         'url':`/api/v1/auth/logincompany`,
//         'data':authRequest
//     })
// }
//
// export const fetchUserData=(authRequest)=>{
//     return axios({
//         method:'GET',
//         url:`/api/v1/auth/userinfo`,
//         headers:{
//             'Authorization': 'Bearer '+getToken()
//         }
//     })
// }