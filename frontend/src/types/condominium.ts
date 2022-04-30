import { LocalizationCondominium } from "./localization-condominium"
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

export const PaginationCondominiumEmpty : PaginationCondominium = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

export const CondominiumEmpty: Condominium = {
    name: '',
    description: '',
    numberApartments: 0,
    status: {
        name: ''
    },
    localization: {
        number: '0',
        localization: {
            name: '',
            road: '',
            zipCode: ''
        }
    }
}