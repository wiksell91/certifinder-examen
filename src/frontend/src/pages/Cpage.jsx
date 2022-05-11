import React from 'react';
import {useState, useEffect} from "react";
import {getAllCert, getAllOrders} from "../client";
import { useSelector } from "react-redux";
import authToken from "../utils/authToken";
import { useNavigate } from "react-router-dom";
import ajax from "../Service/fetchService";
import { useUser } from "../UserProvider";
import {Empty, Layout, Menu, Radio, Spin, Table, Button} from "antd";
import NewOrderDrawer from "../NewOrderDrawer";
import {ContactsOutlined, LoadingOutlined, MailOutlined, UploadOutlined, UserOutlined} from "@ant-design/icons";
import '../App.css';
import {errorNotification, successNotification} from "../Notification";


const {Header, Content, Footer, Sider} = Layout;




const orders =  [
    {
        title: 'Namn',
        dataIndex: ['certuser', 'fullName'],
        key: 'fullName',
    },
    {
        title: 'Behörighetstyp',
        dataIndex: 'ordertype',
        key: 'ordertype',
    },
    {
        title: 'Kommentar',
        dataIndex:  'comment',
        key: 'comment',
    },
    {
        title: 'Arbetsdatum',
        dataIndex:  'orderdate',
        key: 'orderdate',
    },
    {
        title: 'Företag',
        dataIndex: ['company', 'fullName'],
        key: 'fullName',
    },
    {
        title: 'Orderstatus',
        dataIndex: 'status',
        key: 'status'
    },
];


const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>;

    function Cpage ()  {
     const [certstatus, setCertstatus] = useState([]);
    const [orderreqs, setOrderreqs] = useState([]);
    const [fetching, setFetching] = useState(true);
    const [showDrawer, setShowDrawer] = useState(false);
    const [certuserId, setCertuserId] = useState(null);
    const user = useUser();
    const [company, setCompany] = useState(null);
        const navigate = useNavigate();

        useEffect(() => {
            console.log("Value of user", user);
            ajax("api/auth/userinfo", "GET", user.jwt).then((companyData) => {
                setCompany(companyData);
            });
            if (!user.jwt) {
                console.warn("No valid jwt found, redirecting to login page");
                navigate("/");
            }
        }, [user.jwt]);

        const logOut = () =>{
            localStorage.clear();
                navigate('/');
        }


    const users  =  [
        {
            title: 'Namn',
            dataIndex: ['certuser', 'fullName'],
            key: 'name',
        },
        {
            title: 'Stad',
            dataIndex: ['certuser', 'city'],
            key: 'city',
        },

        {
            title: 'Behörighet',
            dataIndex: ['certificate', 'certType'],
            key: 'cert_type',
        },
        {
            title: 'Giltig t.o.m',
            dataIndex: 'validto',
            key: 'validto',
        },

        {
            title: 'Boka',
            key: 'action',
            render: (text, certstatus) =>
                <Radio.Group>
                    <Radio.Button  value="small"onClick={() => {
                        setCertuserId(certstatus.certuser.id)
                        setShowDrawer(!showDrawer)}}
                    >Book</Radio.Button>

                </Radio.Group>

        }
    ];



    const fetchCertstatus = () =>
        getAllCert()
            .then(res => res.json())
            .then(data => {
                console.log(data);
                setCertstatus(data);
            }).catch(err => {
            console.log(err.response)
            err.response.json().then(res => {
                console.log(res);
                errorNotification(
                    "There was an issue",
                    `${res.message} [${res.status}] [${res.error}]`
                )
            });
        }).finally(() => setFetching(false))

    useEffect(() => {
        fetchCertstatus();
    }, []);

    const renderCertstatus = () => {
        if(fetching) {
            return <Spin indicator={antIcon}/>
        }
        if (certstatus.length <= 0) {
            return <Empty />;
        }
        return <>
            <NewOrderDrawer
                showDrawer={showDrawer}
                setShowDrawer={setShowDrawer}
                // fetchOrderreqs={fetchOrderreqs}
                certuserId = {certuserId}
                fetchCertstatus={fetchCertstatus}

            />
            <Table dataSource={certstatus}
                   columns={users}
                   bordered
                   title={() => 'Våra Certifinders'}
                   pagination={{pageSize: 15}}
                //scroll={{y:250}}
                   rowKey={(certstatus) => certstatus.certuser.id}
            />
        </>
    }

    const fetchOrderreqs = () =>
        getAllOrders()
            .then(res => res.json())
            .then(data => {
                setOrderreqs(data);
            }).catch(err => {
            console.log(err.response)
            err.response.json().then(res => {
                console.log(res);
                errorNotification(
                    "There was an issue",
                    `${res.message} [${res.status}] [${res.error}]`
                )
            });
        }).finally(() => setFetching(false))

    useEffect(() => {
        fetchOrderreqs();
    }, []);

    const renderOrderreqs = () => {
        if(fetching) {
            return <Spin indicator={antIcon}/>
        }
        if (orderreqs.length <= 0) {
            return <Empty />;
        }
        return <Table dataSource={orderreqs}
                      columns={orders}
                      bordered
                      title={() => 'Arbetsförfrågningar'}
                      pagination={{pageSize: 15}}
            //scroll={{y:250}}
                      rowKey={(orderreqs) => orderreqs.id}
        />;

    }


    const [selectedMenuItem, setSelectedMenuItem] = useState("1")
    const handleItemClick = (key) => {
        switch (key) {
            case "1":
                return renderCertstatus();
            case "2":
                return  renderOrderreqs();
            case "3":
                return <h4>Hello {company && `${company.id} ${company.fullName}`}</h4>;
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



export default Cpage;

