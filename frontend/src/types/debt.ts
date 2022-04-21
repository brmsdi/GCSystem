import { Lessee, LesseeEmpty } from "./lessee";
import { Movement } from "./movement";
import { Status } from "./status";

export type Debt = {
    id?: number;
    dueDate: string;
    value: number;
    movement?: Movement;
    status: Status;
    lessee: Lessee;
}

export type PaginationDebt = {
    content?: Debt[];
    pageNumber: number;
    paged: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    empty: boolean;
}

export type ActionDebt = {
    type: string;
    payload?: {
        page: number,
        pagination?: PaginationDebt
    }
}

export type SelectedDebtAction = {
    type: string,
    payload?: Debt 
}

export const PaginationDebtEmpty : PaginationDebt = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

export const DebtEmpty : Debt = {
    dueDate: '',
    value: 0,
    status: {
        name: ''
    },
    lessee: LesseeEmpty
}
