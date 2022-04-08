import { LocalizationCondominium } from "./LocalizationCondominium"
import { Status } from "./status"

export type Condominium = {
    id?: number,
    name: string,
    description: string,
    numberApartments: number,
    status: Status,
    localization: LocalizationCondominium
}

export type PaginationCondominium = {
    content?: Condominium[];
    pageNumber: number;
    paged: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    empty: boolean;
}

export type ActionCondominium = {
    type: string;
    payload?: {
        page: number,
        pagination?: PaginationCondominium
    }
}

export type SelectedCondominiumAction = {
    type: string,
    payload?: Condominium 
}