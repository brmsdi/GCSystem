import { Contract, PaginationContract } from "types/contract"
import http from "utils/http"
import { REQUEST_CONTRACTS, REQUEST_CONTRACT_SEARCH } from "utils/requests"

export const getAllContracts = (page: number = 0, size: number = 5) => {
    return http.get<PaginationContract>(`${REQUEST_CONTRACTS}?page=${(page - 1)}&size=${size}`)
}

export const findByCPFService = (CPF: string, page: number = 0, size: number = 5) => {
    return http.get<PaginationContract>(`${REQUEST_CONTRACTS + REQUEST_CONTRACT_SEARCH}?cpf=${CPF}&page=${page}&size=${size}`)
}

export const saveContract = async (data: Contract) => {
    const response = await http.post(`${REQUEST_CONTRACTS}`, data)
    return response.data
}

export const updateContract = async (data: Contract) => {
    const response = await http.put(`${REQUEST_CONTRACTS}`, data)
    return response.data
}

export const deleteContract = async (ID: number) => {
    const response = await http.delete(`${REQUEST_CONTRACTS}?id=${ID}`)
    return response.data
}