import { StateFormAction, TableAction, TypeEnumActionTables } from "types/action";
import { ActionContract, Contract, PaginationContract, SelectedContractAction } from "types/contract";
import { PaginationTableAction } from "types/pagination";

export function updateContractsTable(page = 1, pagination: PaginationContract) {
    let getAllContractAction: ActionContract = {
        type: 'UPDATE-TABLE-CONTRACT',
        payload: {
            page: page,
            pagination: pagination
        }
    }
    return getAllContractAction;
}

export function setStateFormContractAction(state: string) {
    let stateForm: StateFormAction = {
        type: 'SET-STATE-FORM-CONTRACT',
        activity: state
    }
    return stateForm;
} 

export function paginationContractTableAction(paginationTableAction: PaginationTableAction) {
    let action : PaginationTableAction = { 
        type: paginationTableAction.type,
        currentPage: paginationTableAction.currentPage,
        search: paginationTableAction.search        
    }
    return action;
}

export function updateContractTableAction() {
    let action: TableAction = {
        type: TypeEnumActionTables.UPDATE
    }
    return action
}

export function selectContractTableAction(contract: Contract) {
    let action: SelectedContractAction = {
        type: TypeEnumActionTables.SELECTED_CONTRACT,
        payload: contract
    }
    return action
}

export function removeSelectedContractTableAction() {
    let action: SelectedContractAction = {
        type: TypeEnumActionTables.REMOVING_CONTRACT
    }
    return action
}
