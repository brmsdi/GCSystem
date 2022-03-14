import { Specialty } from "types/Employee"
import http from "utils/http"
import { REQUEST_SPECIALTIES } from "utils/requests"

export const getAllSpecialties = () => {
    return http.get<Specialty[]>(`${REQUEST_SPECIALTIES}`)
}