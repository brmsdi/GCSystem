import { Status } from "types/status"
import http from "utils/http"
import { REQUEST_STATUS } from "utils/requests"

export const getAllStatus = () => {
    return http.get<Status[]>(`${REQUEST_STATUS}`)
}