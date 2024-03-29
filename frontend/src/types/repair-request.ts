import { ModalStateInformation } from "./action";
import { Condominium, CondominiumEmpty } from "./condominium";
import { Item } from "./item";
import { Lessee, LesseeEmpty } from "./lessee";
import { Status, StatusEmpty } from "./status";
import { TypeProblem, TypeProblemEmpty } from "./type-problem";

export type RepairRequest = {
    id?: number;
    problemDescription: string;
    date: Date | null;
    lessee: Lessee;
    condominium: Condominium;
    typeProblem: TypeProblem;
    status: Status;
    items?: Item[];
    apartmentNumber: string;
}

export type PaginationRepairRequest = {
    content?: RepairRequest[];
    pageNumber: number;
    paged: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    empty: boolean;
}

export type ActionRepairRequest = {
    type: string;
    payload?: {
        page: number,
        pagination?: PaginationRepairRequest
    }
}

export type SelectedRepairRequestAction = {
    type: string,
    payload?: RepairRequest 
}

export const PaginationRepairRequestEmpty : PaginationRepairRequest = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

export const RepairRequestEmpty : RepairRequest = {
    problemDescription: '',
    date: null,
    status: StatusEmpty,
    condominium: CondominiumEmpty,
    lessee: LesseeEmpty,
    typeProblem: TypeProblemEmpty,
    apartmentNumber: ''
}

export type SelectedRepairRequestsOrderServiceAction = {
    type: string;
    payload: RepairRequest[];
}

export type StateModalOrderServiceRepairRequestsAction = {
    type: string,
    payload: ModalStateInformation
}