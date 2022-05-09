import { OrderService, PaginationOrderService } from "types/order-service"
import http from "utils/http"
import { REQUEST_ORDER_SERVICES, REQUEST_ORDER_SERVICES_SEARCH } from "utils/requests"

export const getAllOrderServices = (page: number = 0, size: number = 5) => {
    return http.get<PaginationOrderService>(`${REQUEST_ORDER_SERVICES}?page=${(page - 1)}&size=${size}`)
}

export const findByIdOrderService = (ID: number, page: number = 0, size: number = 5) => {
    return http.get<PaginationOrderService>(`${REQUEST_ORDER_SERVICES + REQUEST_ORDER_SERVICES_SEARCH}?id=${ID}&page=${page}&size=${size}`)
}

export const saveOrderService = async (data: OrderService) => {
    const response = await http.post(`${REQUEST_ORDER_SERVICES}`, data)
    return response.data
}

export const updateOrderService = async (data: OrderService) => {
    const response = await http.put(`${REQUEST_ORDER_SERVICES}`, data)
    return response.data
}

export const deleteOrderService = async (ID: number) => {
    const response = await http.delete(`${REQUEST_ORDER_SERVICES}?id=${ID}`)
    return response.data
}
