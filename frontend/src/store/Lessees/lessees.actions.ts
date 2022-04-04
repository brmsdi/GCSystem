import { StateFormAction, TableAction, TypeEnumActionTables } from "types/action";
import { ActionLessee, Lessee, PaginationLessee, SelectedLesseeAction } from "types/lessee";
import { PaginationTableAction } from "types/pagination";

export function updateLesseesTable(page = 1, pagination: PaginationLessee) {
    let getAllLesseeAction: ActionLessee = {
        type: 'UPDATE-TABLE-LESSEE',
        payload: {
            page: page,
            pagination: pagination
        }
    }
    return getAllLesseeAction;
}

export function setStateFormLesseeAction(state: string) {
    let stateForm: StateFormAction = {
        type: 'SET-STATE-FORM-LESSEE',
        activity: state
    }
    return stateForm;
} 

export function paginationLesseeTableAction(paginationTableAction: PaginationTableAction) {
    let action : PaginationTableAction = { 
        type: paginationTableAction.type,
        currentPage: paginationTableAction.currentPage,
        search: paginationTableAction.search        
    }
    return action;
}

export function updateLesseeTableAction() {
    let action: TableAction = {
        type: TypeEnumActionTables.UPDATE
    }
    return action
}

export function selectLesseeTableAction(lessee: Lessee) {
    let action: SelectedLesseeAction = {
        type: TypeEnumActionTables.SELECTED,
        payload: lessee
    }
    return action
}

export function removeSelectedLesseeTableAction() {
    let action: SelectedLesseeAction = {
        type: TypeEnumActionTables.REMOVED
    }
    return action
}
