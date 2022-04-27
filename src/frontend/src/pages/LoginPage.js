import React, { useState } from "react";
import {Form, Input, Button} from "antd";
import { useDispatch } from "react-redux";
import { authenticateUser } from "../services/index";

const LoginPage = (props) => {
    const [error, setError] = useState();


    const initialState = {
        username: "",
        password: "",
    };

    const [user, setUser] = useState(initialState);

    const credentialChange = (event) => {
        const { name, value } = event.target;
        setUser({ ...user, [name]: value });
    };

    const dispatch = useDispatch();

    const validateUser = () => {
        dispatch(authenticateUser(user.username, user.password))
            .then((response) => {
                console.log(response.data);
                return props.history.push('/cpage');
            })
            .catch((error) => {
                console.log(error.message);
                //'resetLoginForm();
                setError("Invalid email and password");
            });
    };

    // const resetLoginForm = () => {
    //     setUser(initialState);
    // };

    return (

        <Form
                name="normal_login"
                className="login-form login-width"
                initialValues={{remember: true}}>
                <Form.Item
                    type="text"
                    rules={[{required: true, message: 'Please input your Username!'}]}>
                    <Input      type="text"
                                                                   name="username"
                                                                   value={user.username}
                                                                   onChange={credentialChange}
                                                                   className={"bg-dark text-white"}
                                                                   placeholder="Enter Email Address"/>
                </Form.Item>
                <Form.Item
                    required
                    autoComplete="off"
                    type="password"
                    rules={[{required: true, message: 'Please input your Password!'}]}>
                    <Input                                           type="password"
                                                                     name="password"
                                                                     value={user.password}
                                                                     onChange={credentialChange}
                                                                     className={"bg-dark text-white"}
                                                                     placeholder="Enter Password"
                    />
                    <Button type="primary" htmlType="submit" className="login-form-button" variant="success" onClick={validateUser}>
                        Log in
                    </Button>
                </Form.Item>
            </Form>
        )

};

export default LoginPage;