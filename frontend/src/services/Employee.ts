import { Pagination } from "types/Employee"
import http from "utils/http"
import { REQUEST_BASE_URL, REQUEST_EMPLOYEES } from "utils/requests"

export const getAllEmployees = (page: number = 0, size: number = 5) => {
    return http.get<Pagination>(`${REQUEST_BASE_URL + REQUEST_EMPLOYEES}?page=${(page - 1)}&size=${size}`)
}