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


export const getCompanyOrders = companyId =>
    fetch(`/api/v1/orderreq/company/${companyId}`)
        .then(checkStatus)


export const getUserOrders = (certuserId) =>
    fetch(`/api/v1/orderreq/user/${certuserId}`)
        .then(checkStatus)


export const getAllOrders = () =>
    fetch("/api/v1/orderreq")
        .then(checkStatus)

export const addNewOrder = (certuserId,companyId, orderreq) =>
    fetch(`/api/v1/orderreq/addorder/user/${certuserId}/company/${companyId}`,{
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(orderreq)
    })
        .then(checkStatus)

export const addNewCompany = company =>
    fetch(`/api/v1/company`,{
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(company)
    })
        .then(checkStatus)
