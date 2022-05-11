// import * as UT from "./userTypes";
// import axios from "axios";
//
//
// export const fetchUsers = () => {
//     return (dispatch) => {
//         dispatch(userRequest());
//         axios
//             .get(
//                 '/api/v1/auth/userinfo'
//             )
//             .then((response) => {
//                 dispatch(userSuccess(response.data));
//             })
//             .catch((error) => {
//                 dispatch(userFailure(error.message));
//             });
//     };
// };
//
//
// const userRequest = () => {
//     return {
//         type: UT.USER_REQUEST,
//     };
// };
//
//
// const userSuccess = (users) => {
//     return {
//         type: UT.USER_SUCCESS,
//         payload: users,
//     };
// };
//
// const userFailure = (error) => {
//     return {
//         type: UT.USER_FAILURE,
//         payload: error,
//     };
// };