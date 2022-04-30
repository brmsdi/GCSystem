import { StateFormAction, TableAction, TypeEnumActionTables } from "types/action";
import { PaginationTableAction } from "types/pagination";
import { ActionRepairRequest, PaginationRepairRequest, RepairRequest, SelectedRepairRequestAction } from "types/repair-request";

export function updateRepairRequestTable(page = 1, pagination: PaginationRepairRequest) {
    let getAllRepairRequestAction: ActionRepairRequest = {
        type: 'UPDATE-TABLE-REPAIR-REQUEST',
        payload: {
            page: page,
            pagination: pagination
        }
    }
    return getAllRepairRequestAction;
}

export function setStateFormRepairRequestAction(state: string) {
    let stateForm: StateFormAction = {
        type: 'SET-STATE-FORM-REPAIR-REQUEST',
        activity: state
    }
    return stateForm;
} 

export function paginationRepairRequestTableAction(paginationTableAction: PaginationTableAction) {
    let action : PaginationTableAction = { 
        type: paginationTableAction.type,
        currentPage: paginationTableAction.currentPage,
        search: paginationTableAction.search        
    }
    return action;
}

export function updateRepairRequestTableAction() {
    let action: TableAction = {
        type: TypeEnumActionTables.UPDATE
    }
    return action
}

export function selectRepairRequestTableAction(repairRequest: RepairRequest) {
    let action: SelectedRepairRequestAction = {
        type: TypeEnumActionTables.SELECTED_REPAIR_REQUEST,
        payload: repairRequest
    }
    return action
}

export function removeSelectedRepairRequestTableAction() {
    let action: SelectedRepairRequestAction = {
        type: TypeEnumActionTables.REMOVING_REPAIR_REQUEST
    }
    return action
}
