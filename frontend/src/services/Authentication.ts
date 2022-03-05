import { Token } from "types/AuthenticationToken";
import { AuthCpfAndPassword, EmailRequestCode } from "types/Login";
import http from "utils/http";
import { REQUEST_LOGIN_EMPLOYEES, REQUEST_PASSWORD_CHANGE, REQUEST_REQUEST_CODE, REQUEST_VALIDATE_CODE, REQUEST_VALIDATE_TOKEN } from "utils/requests";

export const autheticate = (auth : AuthCpfAndPassword) => {
    return http.post<Token>(`${REQUEST_LOGIN_EMPLOYEES}?username=${auth.cpf}&password=${auth.password}`)
    .then(response => response.data);
}

export const requestCode = (data : EmailRequestCode) => {
    return http.post(`${REQUEST_REQUEST_CODE}?email=${data.email}&type=${data.type}`)
} 

export const tokenValidate = () => {
    return http.get(REQUEST_VALIDATE_TOKEN)
} 

export const validateCode = (data: EmailRequestCode ) =>  {
    return http.post(`${REQUEST_VALIDATE_CODE}?email=${data.email}&type=${data.type}&code=${data.code}`)
}

export const change = (data : EmailRequestCode) => {
    return http.post(REQUEST_PASSWORD_CHANGE, data.token)
}

export const setToken = (token: Token) => {
    window.localStorage.setItem("token", token.token)
}

export const getToken = () => {
    return window.localStorage.getItem("token");
}

export const clearToken = () => {
    window.localStorage.removeItem("token");
}