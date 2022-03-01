import axios from "axios";
import { Token } from "types/AuthenticationToken";
import { REQUEST_BASE_URL } from "./requests";

console.log("==== AXIOS ====");
const http = axios.create({
    baseURL: REQUEST_BASE_URL
});

const token = window.localStorage.getItem('token');

if(token)
{
    http.defaults.headers.common['Authorization'] = `Bearer ${token}`;
}

export const setAuthorization = (token : Token) => {
    http.defaults.headers.common['Authorization'] = `${token.type} ${token.token}`;
}

export const clearAuth = () => {
    http.defaults.headers.common['Authorization'] = '';
}

export default http;