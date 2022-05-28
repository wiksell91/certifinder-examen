import React from 'react';
import {useState, useEffect} from "react";
import {getAllCert, getCompanyOrders, getUserByUserName, updateUser, deleteCompany} from "../client";
import { useNavigate } from "react-router-dom";
import ajax from "../Service/fetchService";
import { useUser } from "../UserProvider";
import {Empty, Layout, Menu, Radio, Spin, Table, Button, Form, Input, Row, Popconfirm} from "antd";
import NewOrderDrawer from "../NewOrderDrawer";
import {ContactsOutlined, LoadingOutlined, MailOutlined, UploadOutlined, UserOutlined} from "@ant-design/icons";
import '../App.css';
import {errorNotification, successNotification} from "../Notification";
import jwt_decode from "jwt-decode";
import fetch from "unfetch";



const {Header, Content, Footer, Sider} = Layout;







const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>;

function Cpage ()  {
    const [fetching, setFetching] = useState(false);
    const [username, setUsername] = useState(null);
    const [certstatus, setCertstatus] = useState([]);
    const [orderreqs, setOrderreqs] = useState([]);
    const [showDrawer, setShowDrawer] = useState(false);
    const [certuserId, setCertuserId] = useState(null);
    const user = useUser();
    const [company, setCompany] = useState(null);
    const navigate = useNavigate();
    const [companyId, setCompanyId] =useState(null);
    const [submitting, setSubmitting] = useState(false);
    const [userInfos, setUserInfos] = useState([]);
    const [certStatusId, setCertStatusId] = useState(null);



        useEffect(() => {
            console.log("Value of user", user);
            ajax("api/auth/userinfo", "GET", user.jwt).then((companyData) => {
                setCompany(companyData);
                setCompanyId(companyData.id);
                setUsername(companyData.username);
                console.log(companyData.username);

            });
            if (!user.jwt) {
                console.warn("No valid jwt found, redirecting to login page");
                navigate("/");
            }
        }, [user.jwt]);


        const logOut = () =>{
            user.setJwt(null);
            localStorage.clear();
                navigate('/');
        }


    const removeUser = (username) => {
        deleteCompany(username).then(() => {
            successNotification("Användaren raderad");
            setUserInfos([]);
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

    const fetchOrderreqs = () =>
        getCompanyOrders([username])
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
    }, [username]);

    const fetchUsers = () =>
        getUserByUserName([username])
            .then(res => res.json())
            .then(data => {
                setUserInfos(data);
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
        fetchUsers();
    },[username] );

        const certs  = fetchCertstatus  => [
            {
                title: 'Namn',
                dataIndex: ['certuser', 'fullName'],
                key: 'fullName',
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
                            setCertStatusId(certstatus.id)
                            setShowDrawer(!showDrawer)}}
                        >Boka</Radio.Button>
                    </Radio.Group>

            }
        ];




    const orders = fetchOrderreqs => [
        {
            title: 'Namn',
            dataIndex: ['person', 'fullName'],
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
            title: 'Företag',
            dataIndex: ['company', 'fullName'],
            key: 'FullName',
        },
        {
            title: 'status',
            dataIndex: 'orderstatus',
            key: 'orderstatus'
        },
    ];
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
                certuserId = {certuserId}
                companyId={companyId}
                certStatusId={certStatusId}
                fetchCertstatus={fetchCertstatus}
                fetchOrderreqs={fetchOrderreqs}

            />
            <Table dataSource={certstatus}
                   columns={certs(fetchCertstatus)}
                   bordered
                   title={() => 'Våra Certifinders'}
                   pagination={{pageSize: 15}}
                //scroll={{y:250}}
                   rowKey={(certstatus) => certstatus.certuser.id}
            />
        </>
    }

        const renderOrderreqs = () => {
            fetchOrderreqs();
            if(fetching) {
                return <Spin indicator={antIcon}/>
            }
            if (orderreqs.length <= 0) {
                return <Empty />;
            }
            return <Table dataSource={orderreqs}
                          columns={orders(fetchOrderreqs)}
                          bordered
                          title={() => 'Arbetsförfrågningar'}
                          pagination={{pageSize: 15}}
                //scroll={{y:250}}
                          rowKey={(orderreqs) => orderreqs.id}
            />;
        }


    const updateComp = () => {
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
                fetchUsers();
                setSubmitting(false);
            })
        };

        return (
            <Form name="nest-messages" onFinish={onFinish} >
                <Form.Item name="fullName" label="Företagsnamn" rules={[{ required: false }]}>
                    <Input placeholder={userInfos.fullName} />
                </Form.Item>
                <Form.Item name= "username" label="Email" rules={[{ type: 'email' , required: true}]}>
                    <Input placeholder={userInfos.username} />
                </Form.Item>
                <Form.Item name= "city" label="Stad" rules={[{ required: false }]}>
                    <Input placeholder= {userInfos.city} />
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
                return renderCertstatus();
            case "2":
                return renderOrderreqs();
            case "3":
                return updateComp();
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
                    Kontoinformation
                </Menu.Item>
            </Menu>
        </Sider>
        <Layout>
            <Header className="site-layout-sub-header-background" style={{padding: 0}}><div style={{ display: "flex" }}><Button style={{ marginLeft: "auto" }}  type="primary" onClick={() =>logOut()}>Logga ut</Button></div></Header>
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

