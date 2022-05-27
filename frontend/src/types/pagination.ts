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
    SET_CURRENT_PAGINATION_TABLE_REPAIR_REQUESTS="SET-CURRENT-PAGINATION-TABLE-REPAIR-REQUESTS",
    SET_CURRENT_PAGINATION_TABLE_ORDER_SERVICES="SET-CURRENT-PAGINATION-TABLE-ORDER-SERVICES",
    SEARCH="SEARCH",
}

export type PaginationTableAction = {
    type: StatePaginationEnum, 
    currentPage: number,
    search?: string
}

export type PaginationTableActionSearchPerNumber = {
    type: StatePaginationEnum, 
    currentPage: number,
    search?: number
}
