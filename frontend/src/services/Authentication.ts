import { Token } from "types/AuthenticationToken";
import { AuthCpfAndPassword } from "types/Login";
import http from "utils/http";
import { REQUEST_LOGIN_EMPLOYEES, REQUEST_VALIDATE_TOKEN } from "utils/requests";

export const autheticate = (auth : AuthCpfAndPassword) => {
    return http.post<Token>(`${REQUEST_LOGIN_EMPLOYEES}?username=${auth.cpf}&password=${auth.password}`)
    .then(response => response.data);
}

export const tokenValidate = () => {
    return http.get(REQUEST_VALIDATE_TOKEN)
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