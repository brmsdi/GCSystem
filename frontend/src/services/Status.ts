import { Status } from "types/Employee"
import http from "utils/http"
import { REQUEST_BASE_URL, REQUEST_STATUS } from "utils/requests"

export const getAllStatus = () => {
    return http.get<Status[]>(`${REQUEST_BASE_URL + REQUEST_STATUS}`)
}