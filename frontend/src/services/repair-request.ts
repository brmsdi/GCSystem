import { OpenAndProgressAndLateRepairRequest } from "types/open-progress-late-repair-request"
import { PaginationRepairRequest, RepairRequest } from "types/repair-request"
import http from "utils/http"
import { REQUEST_REPAIR_REQUESTS, REQUEST_REPAIR_REQUESTS_PER_STATUS_OPEN_PROGRESS_LATE, REQUEST_REPAIR_REQUESTS_SEARCH, REQUEST_REPAIR_REQUESTS_TO_MODAL_ORDER_SERVICE } from "utils/requests"

export const getAllRepairRequests = (page: number = 0, size: number = 5) => {
    return http.get<PaginationRepairRequest>(`${REQUEST_REPAIR_REQUESTS}?page=${(page - 1)}&size=${size}`)
}

export const findByCPFService = (CPF: string, page: number = 0, size: number = 5) => {
    return http.get<PaginationRepairRequest>(`${REQUEST_REPAIR_REQUESTS + REQUEST_REPAIR_REQUESTS_SEARCH}?cpf=${CPF}&page=${page}&size=${size}`)
}

export const saveRepairRequest = async (data: RepairRequest) => {
    const response = await http.post(`${REQUEST_REPAIR_REQUESTS}`, data)
    return response.data
}

export const updateRepairRequest = async (data: RepairRequest) => {
    const response = await http.put(`${REQUEST_REPAIR_REQUESTS}`, data)
    return response.data
}

export const deleteRepairRequest = async (ID: number) => {
    const response = await http.delete(`${REQUEST_REPAIR_REQUESTS}?id=${ID}`)
    return response.data
}

export const perStatusRepairRequestService = () => {
    return http.get<OpenAndProgressAndLateRepairRequest>(`${REQUEST_REPAIR_REQUESTS_PER_STATUS_OPEN_PROGRESS_LATE}`)
}

export const findAllToModalOrderService = () => {
    return http.get<RepairRequest[]>(`${REQUEST_REPAIR_REQUESTS_TO_MODAL_ORDER_SERVICE}`)
}