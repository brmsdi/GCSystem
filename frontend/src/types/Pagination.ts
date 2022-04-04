export type PropsPagination = {
    pagesNumbers: number[],
    currentPage?: number;
    next: boolean,
    first: boolean
    nextPage?: number;
    previousPagination: number
}

export enum StatePaginationEnum {
    SETCURRENTPAGINATIONTABLEEMPLOYEES="SET-CURRENT-PAGINATION-TABLE-EMPLOYEES",
    SETCURRENTPAGINATIONTABLELESSEES="SET-CURRENT-PAGINATION-TABLE-LESSEES",
    SEARCH="SEARCH",
}

export type PaginationTableAction = {
    type: StatePaginationEnum, 
    currentPage: number,
    search?: string
}

