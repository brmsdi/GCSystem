import { Condominium, PaginationCondominium } from "types/condominium"
import http from "utils/http"
import { REQUEST_CONDOMINIUM, REQUEST_CONDOMINIUM_SEARCH } from "utils/requests"

export const getAllCondominiums = (page: number = 0, size: number = 5) => {
    return http.get<PaginationCondominium>(`${REQUEST_CONDOMINIUM}?page=${(page - 1)}&size=${size}`)
}

export const findByNameService = (name: string, page: number = 0, size: number = 5) => {
    return http.get<PaginationCondominium>(`${REQUEST_CONDOMINIUM + REQUEST_CONDOMINIUM_SEARCH}?name=${name}&page=${page}&size=${size}`)
}

export const saveCondominium = async (data: Condominium) => {
    const response = await http.post(`${REQUEST_CONDOMINIUM}`, data)
    return response.data
}

export const updateCondominium = async (data: Condominium) => {
    const response = await http.put(`${REQUEST_CONDOMINIUM}`, data)
    return response.data
}

export const deleteCondominium = async (ID: number) => {
    const response = await http.delete(`${REQUEST_CONDOMINIUM}?id=${ID}`)
    return response.data
}