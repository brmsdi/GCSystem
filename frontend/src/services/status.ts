import { Status } from "types/status"
import http from "utils/http"
import { REQUEST_STATUS, REQUEST_STATUS_FROM_VIEW_CONDOMINIUM, REQUEST_STATUS_FROM_VIEW_CONTRACT, REQUEST_STATUS_FROM_VIEW_DEBT, REQUEST_STATUS_FROM_VIEW_ORDER_SERVICE, REQUEST_STATUS_FROM_VIEW_REPAIR_REQUEST } from "utils/requests"

export const getAllStatus = () => {
    return http.get<Status[]>(`${REQUEST_STATUS}`)
}

export const getAllStatusFromViewCondominium = () => {
    return http.get<Status[]>(`${REQUEST_STATUS_FROM_VIEW_CONDOMINIUM}`)
}

export const getAllStatusFromViewContract = () => {
    return http.get<Status[]>(`${REQUEST_STATUS_FROM_VIEW_CONTRACT}`)
}

export const getAllStatusFromViewDebt = () => {
    return http.get<Status[]>(`${REQUEST_STATUS_FROM_VIEW_DEBT}`)
}

export const getAllStatusFromViewRepairRequest = () => {
    return http.get<Status[]>(`${REQUEST_STATUS_FROM_VIEW_REPAIR_REQUEST}`)
}

export const getAllStatusFromViewOrderService = () => {
    return http.get<Status[]>(`${REQUEST_STATUS_FROM_VIEW_ORDER_SERVICE}`)
}
