import { ModalStateInformations } from "./action";
import { Employee } from "./employee";
import { RepairRequest } from "./repair-request";
import { Status, StatusEmpty } from "./status" 

export type OrderService = {
    id?: number;
    generationDate: string;
    reservedDate: string;
    completionDate: string;
    repairRequests: RepairRequest[];
    employees: Employee[];
    status: Status;
}

export type PaginationOrderService = {
    content?: OrderService[];
    pageNumber: number;
    paged: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    empty: boolean;
}

export type ActionOrderService = {
    type: string;
    payload?: {
        page: number,
        pagination?: PaginationOrderService
    }
}

export type SelectedOrderServiceAction = {
    type: string,
    payload?: OrderService 
}

export const PaginationOrderServiceEmpty : PaginationOrderService = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

export const OrderServiceEmpty : OrderService = {
    generationDate: '',
    reservedDate: '',
    completionDate: '',
    repairRequests: [],
    employees: [],
    status: StatusEmpty
} 

export type SelectedEmployeesOrderServiceAction = {
    type: string;
    payload: Employee[]
}

export type StateModalOrderServiceEmployeesAction = {
    type: string,
    payload: ModalStateInformations
}

export const ModalStateInformationsInit : ModalStateInformations= {
    isOpen: false
}