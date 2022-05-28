import React from 'react';
import {Empty, Layout, Menu, Radio, Spin, Table, Button, Popconfirm, Form, Select, Input, Row} from "antd";
import {useState, useEffect} from "react";
import {ContactsOutlined, LoadingOutlined, MailOutlined, UploadOutlined, UserOutlined} from "@ant-design/icons";
import '../App.css';
import UpdateOrderDrawer from "../UpdateOrderDrawer";
import CertificateDrawer from "../CertificateDrawer";
import {errorNotification, successNotification} from "../Notification"
import ajax from "../Service/fetchService";
import {useUser} from "../UserProvider";
import {useNavigate} from "react-router-dom";

import {getUserOrders, getUserByUserName, updateUser, getAllCertificate, deleteUser} from "../client";
import {Option} from "antd/es/mentions";
const {Header, Content, Footer, Sider} = Layout;



const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>;

function UserPage (){

    const [fetching, setFetching] = useState(false);
    const [username, setUsername] = useState(null);
    const [certstatus, setCertstatus] = useState([]);
    const [orderreqs, setOrderreqs] = useState([]);
    const [showOrderDrawer, setShowOrderDrawer] = useState(false);
    const [showCertDrawer, setShowCertDrawer] = useState(false);
    const [orderreqId, setOrderreqId] = useState(null);
    const [certuserId, setCertuserId] = useState(null);
    const user = useUser();
    const [certuser, setCertuser] = useState(null);
    const [certificates, setCertificates] = useState([]);
    const navigate = useNavigate();
    const [userInfo, setUserIfo] = useState([]);
    const [submitting, setSubmitting] = useState(false);
    const [certificateId, setCertificateId] = useState(null);




    useEffect(() => {
        console.log("Value of user", user);
        ajax("api/auth/userinfo", "GET", user.jwt)
            .then((certuserData) => {
            setCertuser(certuserData);
            setCertuserId(certuserData.id);
            setUsername(certuserData.username);
            console.log(certuserData.username);
        });
    }, []);

    const fetchUser = () =>
        getUserByUserName([username])
            .then(res => res.json())
            .then(data => {
                setUserIfo(data);
            // }).catch(err => {
            // console.log(err.response)
            // err.response.json().then(res => {
            //     console.log(res);
            //     errorNotification(
            //         "There was an issue",
            //         `${res.message} [${res.status}] [${res.error}]`
            //     )
            // });
        }).finally(() => setFetching(false))

    useEffect(() => {
        fetchUser();
    },[username] );

    const fetchCertificate = () =>
        getAllCertificate()
            .then(res => res.json())
            .then(data => {
                setCertificates(data);
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
        fetchCertificate();
    },[] );

    const fetchOrderreq = () =>
        getUserOrders([username])
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
        fetchOrderreq();
    },[username]);


    const orders = fetchOrderreq =>  [
        {
            title: 'Företag',
            dataIndex: ['company', 'fullName'],
            key: 'FullName',
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
            title: 'status',
            dataIndex: 'orderstatus',
            key: 'orderstatus'
        },
        {
            title: 'Svara',
            key: 'actions',
            render: (text, orderreqs) =>
                <Radio.Group>
                    <Radio.Button  value="small"onClick={() => {
                        setOrderreqId(orderreqs.id)
                        setShowOrderDrawer(!showOrderDrawer)}}
                    >Svara</Radio.Button>
                </Radio.Group>

        }
    ];

    const certtable = fetchCertificate =>  [
        {
            title: 'Behörighets typ',
            dataIndex: 'certType',
            key: 'certType',
        },
        {
            title: 'Bransch',
            dataIndex: 'bransch',
            key: 'bransch',
        },
        {
            title: 'Lägg till',
            key: 'actions',
            render: (text, certificates) =>
                <Radio.Group>
                    <Radio.Button  value="small"onClick={() => {
                        setCertificateId(certificates.id)
                        setShowCertDrawer(!showCertDrawer)}}
                    >Lägg till Behörighet</Radio.Button>
                </Radio.Group>

        }
    ];




    const logOut = () =>{
        user.setJwt(null);
        navigate('/');
        localStorage.clear();
    }

    const removeUser = (username) => {
        deleteUser(username).then(() => {
            successNotification("Användaren raderad");
            setUserIfo([]);
            user.setJwt(null);
            localStorage.clear();
            navigate('/');
        }).catch(err => {
            err.response.json().then(res => {
                console.log(res);
                errorNotification(
                    "There was an issue",
                    `${res.message} [${res.status}] [${res.error}]`
                )
            });
        })
    }



    const renderOrderreqs = () => {
        if(fetching) {
            return <Spin indicator={antIcon}/>
        }
        if (orderreqs.length <= 0) {
            return <Empty />;
        }
        return <>
            <UpdateOrderDrawer
              showOrderDrawer={showOrderDrawer}
              setShowOrderDrawer = {setShowOrderDrawer}
              orderreqId = {orderreqId}
              fetchOrderreq = {fetchOrderreq}
            />
            <Table dataSource={orderreqs}
                      columns={orders(fetchOrderreq)}
                      bordered
                      title={() => 'Arbetsförfrågningar'}
                      pagination={{pageSize: 15}}
            //scroll={{y:250}}
                      rowKey={(orderreqs) => orderreqs.id}
        />
        </>
    }




    const renderCerts = () => {
        if(fetching) {
            return <Spin indicator={antIcon}/>
        }
        if (certificates.length <= 0) {
            return <Empty />;
        }
        return <>
            <CertificateDrawer
                showCertDrawer={showCertDrawer}
                setShowCertDrawer = {setShowCertDrawer}
                certificateId = {certificateId}
                username = {username}
                fetchCertificate = {fetchCertificate}
            />
            <Table dataSource={certificates}
                   columns={certtable(fetchCertificate)}
                   bordered
                   title={() => 'Godkända behörigheter'}
                   pagination={{pageSize: 15}}
                //scroll={{y:250}}
                   rowKey={(certificates) => certificates.id}
            />
        </>
    }

    const  updateUsers = () => {
        fetchUser();
        const onFinish = user => {
            setSubmitting(true)
            updateUser([username], user)
                .then(() =>{
                    successNotification(
                        "Snyggt! Nu är dina upgifter ändrade"
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
                fetchUser();
                setSubmitting(false);
            })
        };

        return (
            <Form name="nest-messages" onFinish={onFinish} >
                <Form.Item name="fullName" label="Fullständigt namn" rules={[{ required: false }]}>
                    <Input placeholder={userInfo.fullName} />
                </Form.Item>
                <Form.Item name= "username" label="Email" rules={[{ type: 'email' , required: true}]}>
                    <Input placeholder={userInfo.username} />
                </Form.Item>
                <Form.Item name= "city" label="Stad" rules={[{ required: false }]}>
                    <Input placeholder= {userInfo.city} />
                </Form.Item>
                <Form.Item >
                    <Button type="primary" htmlType="submit">
                        Ändra
                    </Button>
                </Form.Item>
                <Form.Item>
                    <Popconfirm
                        placement='topRight'
                        title={`Vill du verkligen radera ${username}`}
                        onConfirm={() => removeUser(username)}
                        okText='Ja'
                        cancelText='Nej'>
                        <Button value="small">Radera</Button>
                    </Popconfirm>
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
                return renderCerts();
            case "2":
                return  renderOrderreqs();
            case "3":
                return updateUsers() ;
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
                    Lägg till behörigheter
                </Menu.Item>
                <Menu.Item key="2" icon={<MailOutlined/>}>
                    Nya Förfrågningar
                </Menu.Item>
                <Menu.Item key="3" icon={<UploadOutlined/>}>
                    Min info
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