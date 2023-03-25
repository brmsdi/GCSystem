import { Token } from "types/authentication-token";
import { AuthCpfAndPassword, EmailRequestCode, UserAuthenticatedView } from "types/authentication-types";
import http from "utils/http";
import { REQUEST_LOGIN_EMPLOYEES, REQUEST_VALIDATE_TOKEN } from "utils/requests";

export const autheticate = (auth : AuthCpfAndPassword) => {
    return http.post<Token>(`${REQUEST_LOGIN_EMPLOYEES}?username=${auth.cpf}&password=${auth.password}`)
    .then(response => response.data);
}

export const requestCode = (uri: string, data : EmailRequestCode) => {
    return http.post(`${uri}?email=${data.email}`)
    .then()
} 

export const tokenValidate = async () => {
    return await http.get<UserAuthenticatedView>(REQUEST_VALIDATE_TOKEN).then(response => response.data)
} 

export const checkPermissionView = async (path : string) => {
    return await http.get(path).then(response => response.data)
} 

export const validateCode = (uri: string, data: EmailRequestCode) =>  {
    return http.post<Token>(`${uri}?email=${data.email}&code=${data.code}`)
    .then(response => response.data)
}

export const changePassword = (uri: string, data : EmailRequestCode) => {
    return http.put(uri, data.token)
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
