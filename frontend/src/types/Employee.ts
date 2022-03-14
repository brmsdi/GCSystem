export type Role = {
    id?: number;
    name: string;
}

export type Specialty = {
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
    specialties?: Specialty[];
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