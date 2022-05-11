// import React, { useState } from "react";
// import {Form, Input, Button} from "antd";
// import { useNavigate } from "react-router-dom";
// import { useUser } from "../UserProvider";
// //import { authenticateUser } from "../services/index";
//
// const LoginPage = () => {
//     const user = useUser();
//     const navigate = useNavigate();
//     const [username, setUsername] = useState("");
//     const [password, setPassword] = useState("");
//     const [errorMsg, setErrorMsg] = useState(null);
//
//
//     function sendLoginRequest() {
//         setErrorMsg("");
//         const reqBody = {
//             username: username,
//             password: password,
//         };
//
//         fetch("api/auth/companylogin", {
//             headers: {
//                 "Content-Type": "application/json",
//             },
//             method: "post",
//             body: JSON.stringify(reqBody),
//         })
//             .then((response) => {
//                 if (response.status === 200) return response.text();
//                 else if (response.status === 401 || response.status === 403) {
//                     setErrorMsg("Invalid username or password");
//                 } else {
//                     setErrorMsg(
//                         "Something went wrong, try again later or reach out to trevor@coderscampus.com"
//                     );
//                 }
//             })
//             .then((data) => {
//                 if (data) {
//                     user.setJwt(data);
//                     navigate("/cpage");
//                 }
//             });
//     }
//
//
//
//
//
//     return (
//
//         <Form
//                 name="normal_login"
//                 className="login-form login-width"
//                 initialValues={{remember: true}}>
//                 <Form.Item
//                     controlId="username"
//                     type="text"
//                     rules={[{required: true, message: 'Please input your Username!'}]}>
//                     <Input      type="text"
//                                 name="username"
//                                 value={username}
//                                 onChange={(e) => setUsername(e.target.value)}
//                                 className={"bg-dark text-white"}
//                                 placeholder="Enter Email Address"/>
//                 </Form.Item>
//                 <Form.Item
//                     required
//                     autoComplete="off"
//                     type="password"
//                     controlId="password"
//                     rules={[{required: true, message: 'Please input your Password!'}]}>
//                     <Input      type="password"
//                                 name="password"
//                                 value={user.password}
//                                 onChange={(e) => setPassword(e.target.value)}
//                                 className={"bg-dark text-white"}
//                                 placeholder="Enter Password"
//                     />
//                     <Button type="primary" htmlType="submit" className="login-form-button" variant="success" onClick={() => sendLoginRequest()}>
//                         Log in
//                     </Button>
//                 </Form.Item>
//             </Form>
//         )
//
// };
//
// export default LoginPage;