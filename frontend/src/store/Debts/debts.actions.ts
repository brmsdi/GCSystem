import { StateFormAction, TableAction, TypeEnumActionTables } from "types/action";
import { ActionDebt, Debt, PaginationDebt, SelectedDebtAction } from "types/debt";
import { PaginationTableAction } from "types/pagination";

export function updateDebtsTable(page = 1, pagination: PaginationDebt) {
    let getAllDebtAction: ActionDebt = {
        type: 'UPDATE-TABLE-DEBT',
        payload: {
            page: page,
            pagination: pagination
        }
    }
    return getAllDebtAction;
}

export function setStateFormDebtAction(state: string) {
    let stateForm: StateFormAction = {
        type: 'SET-STATE-FORM-DEBT',
        activity: state
    }
    return stateForm;
} 

export function paginationDebtTableAction(paginationTableAction: PaginationTableAction) {
    let action : PaginationTableAction = { 
        type: paginationTableAction.type,
        currentPage: paginationTableAction.currentPage,
        search: paginationTableAction.search        
    }
    return action;
}

export function updateDebtTableAction() {
    let action: TableAction = {
        type: TypeEnumActionTables.UPDATE
    }
    return action
}

export function selectDebtTableAction(debt: Debt) {
    let action: SelectedDebtAction = {
        type: TypeEnumActionTables.SELECTED_DEBT,
        payload: debt
    }
    return action
}

export function removeSelectedDebtTableAction() {
    let action: SelectedDebtAction = {
        type: TypeEnumActionTables.REMOVING_DEBT
    }
    return action
}
