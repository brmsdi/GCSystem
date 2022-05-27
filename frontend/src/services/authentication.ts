import { Token } from "types/authentication-token";
import { AuthCpfAndPassword, EmailRequestCode, UserAuthenticatedView } from "types/authentication-types";
import http from "utils/http";
import { REQUEST_LOGIN_EMPLOYEES, REQUEST_PASSWORD_CHANGE, REQUEST_REQUEST_CODE, REQUEST_VALIDATE_CODE, REQUEST_VALIDATE_TOKEN } from "utils/requests";

export const autheticate = (auth : AuthCpfAndPassword) => {
    return http.post<Token>(`${REQUEST_LOGIN_EMPLOYEES}?username=${auth.cpf}&password=${auth.password}`)
    .then(response => response.data);
}

export const requestCode = (data : EmailRequestCode) => {
    return http.post(`${REQUEST_REQUEST_CODE}?email=${data.email}&type=${data.type}`)
    .then()
} 

export const tokenValidate = async () => {
    return await http.get<UserAuthenticatedView>(REQUEST_VALIDATE_TOKEN).then(response => response.data)
} 

export const checkPermissionView = async (path : string) => {
    return await http.get(path).then(response => response.data)
} 

export const validateCode = (data: EmailRequestCode ) =>  {
    return http.post<Token>(`${REQUEST_VALIDATE_CODE}?email=${data.email}&type=${data.type}&code=${data.code}`)
    .then(response => response.data)
}

export const changePassword = (data : EmailRequestCode) => {
    return http.post(REQUEST_PASSWORD_CHANGE, data.token)
    .then(response => response.data)
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
