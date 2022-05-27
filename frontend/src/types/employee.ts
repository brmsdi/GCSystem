import { Role } from "./role";
import { Status } from "./status";

export type Employee = {
    id?: number;
    name: string;
    rg: string;
    cpf: string;
    birthDate: string;
    email: string;
    hiringDate: string;
    role: Role;
    status: Status;
    password: string;
}

export type PaginationEmployee = {
    content?: Employee[];
    pageNumber: number;
    paged: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    empty: boolean;
}

export type ActionEmployee = {
    type: string;
    payload?: {
        page: number,
        pagination?: PaginationEmployee
    }
}

export type SelectedEmployeeAction = {
    type: string,
    payload?: Employee 
}

export const PaginationEmployeeEmpty : PaginationEmployee = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

export const EmployeeEmpty : Employee = {
    name: '',
    rg: '',
    cpf: '',
    birthDate: '',
    email: '',
    hiringDate: '',
    role: {
        name: ''
    },
    status: {
        name: ''
    },
    password: ''
}
