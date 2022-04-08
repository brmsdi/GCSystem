import { StateFormAction, TableAction, TypeEnumActionTables } from "types/action";
import { ActionCondominium, Condominium, PaginationCondominium, SelectedCondominiumAction } from "types/condominium";
import { PaginationTableAction } from "types/pagination";

export function getAllCondominiumsAction(page = 1) {
    let getAllEmployeesAction: ActionCondominium = {
        type: 'GET-ALL-CONDOMINIUMS',
        payload: {page: page}
    }
    return getAllEmployeesAction;
}

export function updateCondominiumTable(page = 1, pagination: PaginationCondominium) {
    let getAllCondominiumsAction: ActionCondominium = {
        type: 'UPDATE-TABLE-CONDOMINIUMS',
        payload: {
            page: page,
            pagination: pagination
        }
    }
    return getAllCondominiumsAction;
}

export function setStateFormCondominiumAction(state: string) {
    let stateForm: StateFormAction = {
        type: 'SET-STATE-FORM-CONDOMINIUM',
        activity: state
    }
    return stateForm;
} 

export function paginationCondominiumTableAction(paginationTableAction: PaginationTableAction) {
    let action : PaginationTableAction = { 
        type: paginationTableAction.type,
        currentPage: paginationTableAction.currentPage,
        search: paginationTableAction.search        
    }
    return action;
}

export function selectCondominiumTableAction(employee: Condominium) {
    let action: SelectedCondominiumAction = {
        type: TypeEnumActionTables.SELECTED_CONDOMINIUM,
        payload: employee
    }
    return action
}

export function removeSelectedCondominiumTableAction() {
    let action: SelectedCondominiumAction = {
        type: TypeEnumActionTables.REMOVING_CONDOMINIUM
    }
    return action
}

export function updateCondominiumTableAction() {
    let action: TableAction = {
        type: TypeEnumActionTables.UPDATE
    }
    return action
}