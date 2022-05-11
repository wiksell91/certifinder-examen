import {Layout, Menu, Spin, Form, Input, Button, Row, Checkbox } from "antd";
import React, {useState} from 'react';
//import { useDispatch } from "react-redux";
//import { authenticateUser } from "../services/index";
//import { authenticate, authFailure, authSuccess } from '../redux/authActions';
//import { connect } from 'react-redux';
//import {userLogin} from "../api/authenticationService";
import {addNewCompany} from "../client";
import '../App.css';
import {errorNotification, successNotification} from "../Notification";
import { UserOutlined, LockOutlined, LoadingOutlined } from '@ant-design/icons';
import {useUser} from "../UserProvider";
import {useNavigate} from "react-router-dom";
const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;
const { Header, Content, Footer, Sider } = Layout;




function Mainpage  () {

    const [submitting, setSubmitting] = useState(false);
    const user = useUser();
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMsg, setErrorMsg] = useState(null);

    const loginCompany = () => {


    function sendLoginRequest() {
        setErrorMsg("");
        const reqBody = {
            username: username,
            password: password,
        };

        fetch("api/auth/companylogin", {
            headers: {
                "Content-Type": "application/json",
            },
            method: "post",
            body: JSON.stringify(reqBody),
        })
            .then((response) => {
                if (response.status === 200) return response.text();
                else if (response.status === 401 || response.status === 403) {
                    setErrorMsg("Invalid username or password");
                } else {
                    setErrorMsg(
                        "Something went wrong, try again later or reach out to trevor@coderscampus.com"
                    );
                }
            })
            .then((data) => {
                if (data) {
                    user.setJwt(data);
                    navigate("/cpage");
                }
            });
    }


    return (

        <Form
            name="normal_login"
            className="login-form login-width"
            initialValues={{remember: true}}>
            <Form.Item
                controlId="username"
                type="text"
                rules={[{required: true, message: 'Please input your Username!'}]}>
                <Input type="text"
                       name="username"
                       value={username}
                       onChange={(e) => setUsername(e.target.value)}
                       className={"bg-dark text-white"}
                       placeholder="Enter Email Address"/>
            </Form.Item>
            <Form.Item
                required
                autoComplete="off"
                type="password"
                controlId="password"
                rules={[{required: true, message: 'Please input your Password!'}]}>
                <Input type="password"
                       name="password"
                       value={user.password}
                       onChange={(e) => setPassword(e.target.value)}
                       className={"bg-dark text-white"}
                       placeholder="Enter Password"
                />
                <Button type="primary" htmlType="submit" className="login-form-button" variant="success"
                        onClick={() => sendLoginRequest()}>
                    Log in
                </Button>
            </Form.Item>
        </Form>
    )
}
    const listlayout = {
        labelCol: { span: 4 },
        wrapperCol: { span: 4 },
    };

    /* eslint-disable no-template-curly-in-string */
    const validateMessages = {
        required: '${label} is required!',
        types: {
            email: '${label} is not a valid email!'
        },
    };


    const addCompany = () => {
        const onFinish = company => {
            setSubmitting(true)
            addNewCompany(company)
                .then(() =>{
                    successNotification(
                        "Snyggt! Nu är du en del av Certifinder"
                    )
                }).catch(err => {
                err.response.json().then(res => {
                    errorNotification(
                        "Nu vart det lite struligt..",
                        `${res.message} [${res.status}] [${res.error}]`,
                        "bottomLeft"
                    )
                });
            }).finally(() => {
                setSubmitting(false);
            })
        };

        return (
            <Form {...listlayout} name="nest-messages" onFinish={onFinish} validateMessages={validateMessages}>
                <Form.Item name="fullName" label="Företagsnamn" rules={[{ required: false }]}>
                    <Input />
                </Form.Item>
                <Form.Item name= "username" label="Email" rules={[{ type: 'email' , required: true}]}>
                    <Input />
                </Form.Item>
                <Form.Item name= "password" label="Lösenord" rules={[{ required: false }]}>
                    <Input />
                </Form.Item>
                <Form.Item name= "city" label="Stad" rules={[{ required: false }]}>
                    <Input />
                </Form.Item>
                <Form.Item wrapperCol={{ ...listlayout.wrapperCol, offset: 8 }}>
                    <Button type="primary" htmlType="submit">
                        Submit
                    </Button>
                </Form.Item>
                <Row>
                    {submitting && <Spin indicator={antIcon} />}
                </Row>
            </Form>
        );
    }



    const [selectedMenuItem, setSelectedMenuItem] = useState("1")
    const handleItemClick = (key) => {
        switch (key) {
            case "1":
                return "skapa konto privat";
            case "2":
                return  addCompany();
            case "3":
                return "feeeeeeliyggi";
            case "4":
                return loginCompany();
            default:
                break;

        }
    };
    return  <Layout>
        <Sider
            breakpoint="lg"
            collapsedWidth="0"
            onBreakpoint={broken => {
                console.log(broken);
            }}
            onCollapse={(collapsed, type) => {
                console.log(collapsed, type);
            }}
        >
            <div className="logo"/>
            <Menu selectedKeys={selectedMenuItem} theme="dark" mode="inline" onClick={(e) =>
                setSelectedMenuItem(e.key)}>
                <Menu.Item key="1" >
                    Nytt konto privat
                </Menu.Item>
                <Menu.Item key="2" >
                    Nytt konto företag
                </Menu.Item>
                <Menu.Item key="3" >
                    Logga in privat
                </Menu.Item>
                <Menu.Item key="4" >
                    Logga in företag
                </Menu.Item>
            </Menu>
        </Sider>
        <Layout>
            <Header className="site-layout-sub-header-background" style={{padding: 0}}></Header>
            <Content style={{margin: '24px 16px 0'}}>
                <div className="site-layout-background" style={{padding: 24, minHeight: 360}}>

                   {handleItemClick(selectedMenuItem)}
                </div>
            </Content>
            <Footer style={{textAlign: 'center'}}>Certifinder ©2022 Created by Victor Wiksell</Footer>
        </Layout>
    </Layout>
}
// const mapStateToProps=({auth})=>{
//     console.log("state ",auth)
//     return {
//         loading:auth.loading,
//         error:auth.error
//     }}
//
//
// const mapDispatchToProps=(dispatch)=>{
//
//     return {
//         authenticate :()=> dispatch(authenticate()),
//         setCompany:(data)=> dispatch(authSuccess(data)),
//         loginFailure:(message)=>dispatch(authFailure(message))
//     }
// }

export default Mainpage;