import { Status } from "./status";

export type Lessee = {
    id?: number;
    name: string;
    rg: string;
    cpf: string;
    birthDate: string;
    email: string;
    contactNumber: string;
    status: Status;
    password: string;
}

export type PaginationLessee = {
    content?: Lessee[];
    pageNumber: number;
    paged: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    empty: boolean;
}

export type ActionLessee = {
    type: string;
    payload?: {
        page: number,
        pagination?: PaginationLessee
    }
}

export type SelectedLesseeAction = {
    type: string,
    payload?: Lessee 
}

export const PaginationLesseeEmpty : PaginationLessee = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

export const LesseeEmpty : Lessee = {
    name: '',
    rg: '',
    cpf: '',
    birthDate: '',
    email: '',
    contactNumber: '',
    status: {
        name: ''
    },
    password: ''
} 