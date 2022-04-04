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