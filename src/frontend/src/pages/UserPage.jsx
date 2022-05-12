import React from 'react';
import {Empty, Layout, Menu, Radio, Spin, Table, Button} from "antd";
import {useState, useEffect} from "react";
import {ContactsOutlined, LoadingOutlined, MailOutlined, UploadOutlined, UserOutlined} from "@ant-design/icons";
import '../App.css';
import {errorNotification, successNotification} from "../Notification"
const {Header, Content, Footer, Sider} = Layout;


const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>;

function UserPage (){


    const [selectedMenuItem, setSelectedMenuItem] = useState("1")
    const handleItemClick = (key) => {
        switch (key) {
            case "1":
                return ;
            case "2":
                return  ;
            case "3":
                return ;
            case "4":
                return ;
            default:
                break;

        }
    };

    return <Layout>
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
                <Menu.Item key="1" icon={<ContactsOutlined/>}>
                    Sök behörigheter
                </Menu.Item>
                <Menu.Item key="2" icon={<MailOutlined/>}>
                    Förfrågningar
                </Menu.Item>
                <Menu.Item key="3" icon={<UploadOutlined/>}>
                    nav 3
                </Menu.Item>
                <Menu.Item key="4" icon={<UserOutlined/>}>
                    nav 4
                </Menu.Item>
            </Menu>
        </Sider>
        <Layout>
            <Header className="site-layout-sub-header-background" style={{padding: 0}} ><div style={{ display: "flex" }}><Button style={{ marginLeft: "auto" }}  type="primary" onClick={() =>logOut()}>Logga ut</Button></div></Header>
            <Content style={{margin: '24px 16px 0'}}>
                <div className="site-layout-background" style={{padding: 24, minHeight: 360}}>

                    {handleItemClick(selectedMenuItem)}

                </div>
            </Content>
            <Footer style={{textAlign: 'center'}}>Certifinder ©2022 Created by Victor Wiksell</Footer>
        </Layout>
    </Layout>

}
export default UserPage;