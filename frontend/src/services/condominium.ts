import { Condominium, PaginationCondominium } from "types/condominium"
import http from "utils/http"
import { REQUEST_CONDOMINIUMS, REQUEST_CONDOMINIUM_LIST_ALL, REQUEST_CONDOMINIUM_SEARCH } from "utils/requests"

export const getAllCondominiums = (page: number = 1, size: number = 5) => {
    return http.get<PaginationCondominium>(`${REQUEST_CONDOMINIUMS}?page=${(page - 1)}&size=${size}`)
}

export const listAllCondominiums = () => {
    return http.get<Condominium[]>(`${REQUEST_CONDOMINIUM_LIST_ALL}`)
}

export const findByNameService = (name: string, page: number = 0, size: number = 5) => {
    return http.get<PaginationCondominium>(`${REQUEST_CONDOMINIUMS + REQUEST_CONDOMINIUM_SEARCH}?name=${name}&page=${page}&size=${size}`)
}

export const saveCondominium = async (data: Condominium) => {
    const response = await http.post(`${REQUEST_CONDOMINIUMS}`, data)
    return response.data
}

export const updateCondominium = async (data: Condominium) => {
    const response = await http.put(`${REQUEST_CONDOMINIUMS}`, data)
    return response.data
}

export const deleteCondominium = async (ID: number) => {
    const response = await http.delete(`${REQUEST_CONDOMINIUMS}?id=${ID}`)
    return response.data
}