import { Lessee, PaginationLessee } from "types/lessee"
import http from "utils/http"
import { REQUEST_LESSEES, REQUEST_LESSEE_SEARCH } from "utils/requests"

export const getAllLessees = (page: number = 0, size: number = 5) => {
    return http.get<PaginationLessee>(`${REQUEST_LESSEES}?page=${(page - 1)}&size=${size}`)
}

export const findByCPFService = (CPF: string, page: number = 0, size: number = 5) => {
    return http.get<PaginationLessee>(`${REQUEST_LESSEES + REQUEST_LESSEE_SEARCH}?cpf=${CPF}&page=${page}&size=${size}`)
}

export const saveLessee = async (data: Lessee) => {
    const response = await http.post(`${REQUEST_LESSEES}`, data)
    return response.data
}

export const updateLessee = async (data: Lessee) => {
    const response = await http.put(`${REQUEST_LESSEES}`, data)
    return response.data
}

export const deleteLessee = async (ID: number) => {
    const response = await http.delete(`${REQUEST_LESSEES}?id=${ID}`)
    return response.data
}