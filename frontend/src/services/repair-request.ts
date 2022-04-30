import { PaginationRepairRequest, RepairRequest } from "types/repair-request"
import http from "utils/http"
import { REQUEST_REPAIR_REQUESTS, REQUEST_REPAIR_REQUESTS_SEARCH } from "utils/requests"

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