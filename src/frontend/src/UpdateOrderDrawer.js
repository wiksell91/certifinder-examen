import React from 'react';
import {Drawer, Input, Col, Select, Form, Row, Button, Spin} from 'antd';
import TextArea from "antd/es/input/TextArea";
import {LoadingOutlined} from "@ant-design/icons";
import {useState} from 'react';
import {updateOrderStatus} from "./client";

import {successNotification, errorNotification} from "./Notification";

const {Option} = Select;

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

function UpdateOrderDrawer({showOrderDrawer, setShowOrderDrawer, orderreqId, fetchOrderreq}) {

    const onCLose = () => setShowOrderDrawer(false);

    const [submitting, setSubmitting] = useState(false);

    const onFinish = orderreq => {
        setSubmitting(true)
        updateOrderStatus(orderreqId, orderreq)
            .then(() => {
                onCLose();
                successNotification(
                    "Tack gör att du svarade"
                )
                fetchOrderreq();
            }).catch(err => {
            err.response.json().then(res => {
                console.log(err);
                errorNotification(
                    "There was an issue",
                    `${res.message} [${res.status}] [${res.error}]`,
                    "bottomLeft"
                )
            });
        }).finally(() => {
            setSubmitting(false);
        })
    };


    const onFinishFailed = errorInfo => {
        alert(JSON.stringify(errorInfo, null, 2));
    };

    const { TextArea } = Input;
    return <Drawer
        title="Skapa ny förfrågan"
        width={720}
        onClose={onCLose}
        visible={showOrderDrawer}
        bodyStyle={{paddingBottom: 80}}
        footer={
            <div
                style={{
                    textAlign: 'right',
                }}
            >
                <Button onClick={onCLose} style={{marginRight: 8}}>
                    Avbryt
                </Button>
            </div>
        }
    >
        <Form layout="vertical"
              onFinishFailed={onFinishFailed}
              onFinish={onFinish}
              hideRequiredMark>
            <Row gutter={12}>
                <Col span={8}>
                    <Form.Item
                        name="orderstatus"
                        label="status"
                    >
                        <Select>
                            <Option value="OBESVARAD">Obesvarad</Option>
                            <Option value="GODKÄND">Ja</Option>
                            <Option value="NEKAD">Nej</Option>
                        </Select>
                    </Form.Item>
                </Col>
            </Row>
            <Col span={12}>
                <Form.Item
                    name="comment"
                    label="Kommentar"
                >
                    <TextArea placeholder="Några övriga frågor?"/>
                </Form.Item>
            </Col>
            <Row>
                <Col span={12}>
                    <Form.Item >
                        <Button type="primary" htmlType="submit">
                            Svara
                        </Button>
                    </Form.Item>
                </Col>
            </Row>
            <Row>
                {submitting && <Spin indicator={antIcon} />}
            </Row>
        </Form>
    </Drawer>
}

export default UpdateOrderDrawer;