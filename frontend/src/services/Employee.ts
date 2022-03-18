import { Employee, Pagination } from "types/Employee"
import http from "utils/http"
import { REQUEST_EMPLOYEES, REQUEST_EMPLOYEE_SEARCH } from "utils/requests"

export const getAllEmployees = (page: number = 0, size: number = 5) => {
    return http.get<Pagination>(`${REQUEST_EMPLOYEES}?page=${(page - 1)}&size=${size}`)
}

export const findByCPFService = (CPF: string, page: number = 0, size: number = 5) => {
    return http.get<Pagination>(`${REQUEST_EMPLOYEES + REQUEST_EMPLOYEE_SEARCH}?cpf=${CPF}&page=${page}&size=${size}`)
}

export const saveEmployee = async (data: Employee) => {
    const response = await http.post(`${REQUEST_EMPLOYEES}`, data)
    return response.data
}

export const updateEmployee = async (data: Employee) => {
    const response = await http.put(`${REQUEST_EMPLOYEES}`, data)
    return response.data
}