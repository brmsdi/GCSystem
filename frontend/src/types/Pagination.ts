export type PropsPagination = {
    pagesNumbers: number[],
    currentPage?: number;
    next: boolean,
    first: boolean
    nextPage?: number;
    previousPagination: number
}

export enum StatePaginationEnum {
    SET_CURRENT_PAGINATION_TABLE_EMPLOYEES="SET-CURRENT-PAGINATION-TABLE-EMPLOYEES",
    SET_CURRENT_PAGINATION_TABLE_LESSEES="SET-CURRENT-PAGINATION-TABLE-LESSEES",
    SET_CURRENT_PAGINATION_TABLE_CONDOMINIUMS="SET-CURRENT-PAGINATION-TABLE-CONDOMINIUMS",
    SET_CURRENT_PAGINATION_TABLE_CONTRACTS="SET-CURRENT-PAGINATION-TABLE-CONTRACTS",
    SET_CURRENT_PAGINATION_TABLE_DEBTS="SET-CURRENT-PAGINATION-TABLE-DEBTS",
    SET_CURRENT_PAGINATION_TABLE_REPAIR_REQUEST="SET-CURRENT-PAGINATION-TABLE-DEBTS",
    SEARCH="SEARCH",
}

export type PaginationTableAction = {
    type: StatePaginationEnum, 
    currentPage: number,
    search?: string
}

