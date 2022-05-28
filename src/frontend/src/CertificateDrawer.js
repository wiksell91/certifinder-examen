import React from 'react';
import {Drawer, Input, Col, Select, Form, Row, Button, Spin} from 'antd';
import TextArea from "antd/es/input/TextArea";
import {LoadingOutlined} from "@ant-design/icons";
import {useState} from 'react';
import {addNewCert} from "./client";

import {successNotification, errorNotification} from "./Notification";

const {Option} = Select;

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

function CertificateDrawer({showCertDrawer, setShowCertDrawer, username,certificateId, fetchCertificate}) {

    const onCLose = () => setShowCertDrawer(false);
    const [submitting, setSubmitting] = useState(false);


    const onFinish = certstatus => {
        setSubmitting(true)
        addNewCert(username, certificateId,certstatus)
            .then(() => {
                onCLose();
                successNotification(
                    "Behörighet tillagd"
                )
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
            fetchCertificate();
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
        visible={showCertDrawer}
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
                        name="validto"
                        label="Giltig till"
                        rules={[{required: true, message: 'Vänligen ange datum'}]}
                    >
                        <TextArea placeholder="Vänligen ange datum"/>
                    </Form.Item>
                </Col>
                <Col span={8}>
                    <Form.Item
                        name="regnumber"
                        label="ID nummen"
                        rules={[{required: true, message: 'Vänligen ange ID nummer'}]}
                    >
                        <TextArea placeholder="Vänligen ange ID numme"/>
                    </Form.Item>
                </Col>
            </Row>
            <Col span={12}>
                <Form.Item
                    name="generalinfo"
                    label="Övrig info"
                >
                    <TextArea placeholder="Övrig info - Frivilligt"/>
                </Form.Item>
            </Col>
            <Row>
                <Col span={12}>
                    <Form.Item >
                        <Button type="primary" htmlType="submit">
                            Submit
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

export default CertificateDrawer;