import { Condominium, CondominiumEmpty } from "./condominium";
import { Lessee, LesseeEmpty } from "./lessee";
import { Status } from "./status";

export type Contract = {
    id?: number;
    contractDate: string;
    contractValue: number;
    monthlyPaymentDate: number;
    monthlyDueDate: number;
    contractExpirationDate: string;
    apartmentNumber: string;
    status: Status;
    condominium: Condominium;
    lessee: Lessee;
}

export type PaginationContract = {
    content?: Contract[];
    pageNumber: number;
    paged: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    empty: boolean;
}

export type ActionContract = {
    type: string;
    payload?: {
        page: number,
        pagination?: PaginationContract
    }
}

export type SelectedContractAction = {
    type: string,
    payload?: Contract 
}

export const PaginationContractEmpty : PaginationContract = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

export const ContractEmpty : Contract = {
    contractDate: '',
    contractValue: 0,
    monthlyPaymentDate: 0,
    monthlyDueDate: 0,
    contractExpirationDate: '',
    apartmentNumber: '',
    status: {
        name: ''
    },
    condominium: CondominiumEmpty,
    lessee: LesseeEmpty
}
