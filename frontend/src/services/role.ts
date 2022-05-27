import { Role } from "types/role"
import http from "utils/http"
import { REQUEST_ROLES } from "utils/requests"

export const getAllRoles = () => {
    return http.get<Role[]>(`${REQUEST_ROLES}`)
}
