import fetch from 'unfetch';

const checkStatus = response => {
    if (response.ok){
        return response;
    }

    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}


export const gettAllCertusers = () =>
    fetch("api/v1/certuser")
        .then(checkStatus)

export const getAllCert = () =>
    fetch("api/v1/certstatus")
        .then(checkStatus)


export const getAllCertificate = () =>
    fetch("api/v1/certificate")
        .then(checkStatus)


export const getCompanyOrders = (username) =>
    fetch(`/api/v1/orderreq/company?username=${username}`)
        .then(checkStatus)


export const getUserOrders = (username) =>
    fetch(`/api/v1/orderreq/user?username=${username}`)
        .then(checkStatus)

export const getUserByUserName = (username) =>
    fetch(`/api/v1/account/${username}`)
        .then(checkStatus)

export const getAllOrders = () =>
    fetch("/api/v1/orderreq/all")
        .then(checkStatus)

export const addNewOrder = (certuserId,companyId,certstatusId, orderreq) =>
    fetch(`/api/v1/orderreq/addorder/user/${certuserId}/company/${companyId}/cert/${certstatusId}`,{
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(orderreq)
    })
        .then(checkStatus)

export const addNewCert = (username,certificateId, certstatus) =>
    fetch(`/api/v1/certstatus/addstatus/user/${username}/cert/${certificateId}`,{
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(certstatus)
    })
        .then(checkStatus)

export const deleteUser = username =>
    fetch(`api/v1/account/delete/${username}`, {
        method: 'DELETE'
    }).then(checkStatus);

export const deleteCompany = username =>
    fetch(`api/v1/account/deletecomp/${username}`, {
        method: 'DELETE'
    }).then(checkStatus);

export const updateUser = (username, certuser) =>
    fetch(`/api/v1/account/update/${username}`,{
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'PATCH',
        body: JSON.stringify(certuser)
    })
        .then(checkStatus)

export const updateOrderStatus = (orderreqId,orderreq) =>
    fetch(`/api/v1/orderreq/updatestatus/${orderreqId}`,{
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'PUT',
        body: JSON.stringify(orderreq)
    })
        .then(checkStatus)


export const addNewCompany = user =>
    fetch(`/api/v1/account/company`,{
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(user)
    })
        .then(checkStatus)

export const addNewUser = user =>
    fetch(`/api/v1/account/user`,{
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(user)
    })
        .then(checkStatus)
