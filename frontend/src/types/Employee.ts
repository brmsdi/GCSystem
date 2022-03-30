export type Role = {
    id?: number;
    name: string;
}

export type Status = {
    id?: number;
    name: string;
}

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

export type Pagination = {
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
        pagination?: Pagination
    }
}

export type SelectedEmployeeAction = {
    type: string,
    payload?: Employee 
}

export enum TypeEnumActionEmployee {
    SELECTED="SELECTED_EMPLOYEE",
    REMOVED="REMOVENDO",
    UPDATE="UPDATE"
}

export type TableAction = {
    type: string
}