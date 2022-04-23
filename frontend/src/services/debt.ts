import { Debt, PaginationDebt } from "types/debt"
import http from "utils/http"
import { REQUEST_DEBTS, REQUEST_DEBT_SEARCH } from "utils/requests"

export const getAllDebts = (page: number = 0, size: number = 5) => {
    return http.get<PaginationDebt>(`${REQUEST_DEBTS}?page=${(page - 1)}&size=${size}`)
} 

export const findByCPFService = (CPF: string, page: number = 0, size: number = 5) => {
    return http.get<PaginationDebt>(`${REQUEST_DEBTS + REQUEST_DEBT_SEARCH}?cpf=${CPF}&page=${page}&size=${size}`)
}

export const saveDebt = async (data: Debt) => {
    const response = await http.post(`${REQUEST_DEBTS}`, data)
    return response.data
}

export const updateDebt = async (data: Debt) => {
    const response = await http.put(`${REQUEST_DEBTS}`, data)
    return response.data
}

export const deleteDebt = async (ID: number) => {
    const response = await http.delete(`${REQUEST_DEBTS}?id=${ID}`)
    return response.data
}