export type PropsPagination = {
    pagesNumbers: number[],
    currentPage?: number;
    next: boolean,
    first: boolean
    nextPage?: number;
    previousPagination: number
}

export enum statePaginationEnum {
    SETCURRENTPAGINATIONTABLEEMPLOYEES="SET-CURRENT-PAGINATION-TABLE-EMPLOYEES",
    SEARCH="SEARCH",
}

export type PaginationTableAction = {
    type: statePaginationEnum, 
    currentPage: number,
    search?: string
}

