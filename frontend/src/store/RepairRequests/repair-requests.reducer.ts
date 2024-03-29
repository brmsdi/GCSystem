import { CurrentStateForm, ModalStateInformation, StateFormAction, StateFormEnum, TableAction, TypeEnumActionTables } from "types/action";
import { ModalStateInformationsInit } from "types/order-service";
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";
import { ActionRepairRequest, PaginationRepairRequest, PaginationRepairRequestEmpty, RepairRequest, RepairRequestEmpty, SelectedRepairRequestAction, SelectedRepairRequestsOrderServiceAction, StateModalOrderServiceRepairRequestsAction } from "types/repair-request";

let initPagination : PaginationRepairRequest = PaginationRepairRequestEmpty;
let initRepairRequest : RepairRequest = RepairRequestEmpty;
let initStateModalOrderServiceRepairRequests : ModalStateInformation = ModalStateInformationsInit;

export default function getAllRepairRequestReducer(state: PaginationRepairRequest = initPagination, action: ActionRepairRequest) {
    switch(action.type)
    {
        case 'GET-ALL-REPAIR-REQUESTS':
            return state;
        case 'UPDATE-TABLE-REPAIR-REQUEST':
            if (action.payload?.pagination) {
                return action.payload?.pagination;
            }
            return state;
        default:
            return state;
    }
}

export function setStateFormRepairRequestReducer(currentStateForm: CurrentStateForm = {activity: StateFormEnum.NOACTION, active: false} , action: StateFormAction ) {
    switch(action.type) {
        case 'SET-STATE-FORM-REPAIR-REQUEST':
            if(action.activity === StateFormEnum.NOACTION) {
                let stateCurrent: CurrentStateForm = {
                    activity: StateFormEnum.NOACTION,
                    active: false
                }
                return stateCurrent;
            } else if(action.activity ===  StateFormEnum.SAVING ) {
                let stateCurrent: CurrentStateForm = {
                    activity: StateFormEnum.NEW,
                    active: true
                }
                return stateCurrent;
            } else if(action.activity ===  StateFormEnum.UPDATE ) {
                let stateCurrent: CurrentStateForm = {
                    activity: StateFormEnum.UPDATE,
                    active: true
                }
                return stateCurrent;
            }
            return currentStateForm;
        default:
            return currentStateForm;
    }
}

export function setCurrentPaginationRepairRequestReducer(state: PaginationTableAction = {type: StatePaginationEnum.SET_CURRENT_PAGINATION_TABLE_REPAIR_REQUESTS, currentPage: 1, search: undefined }, action: PaginationTableAction) {
    switch(action.type) {
        case StatePaginationEnum.SET_CURRENT_PAGINATION_TABLE_REPAIR_REQUESTS:
            return action;
        default:
            return state;
    }
}

export function stateSelectionRepairRequestReducer(state: RepairRequest = initRepairRequest, action: SelectedRepairRequestAction) {
    switch (action.type) {
        case TypeEnumActionTables.SELECTED_REPAIR_REQUEST:
            return action.payload
        case TypeEnumActionTables.REMOVING_REPAIR_REQUEST:
            return initRepairRequest
        default:
            return state
    }
}

export function updateTableRepairRequestReducer(state: boolean = false, action: TableAction) {
    switch (action.type) {
        case TypeEnumActionTables.UPDATE:
            return !state 
        default:
            return state
    }
}

export function selectedRepairRequestsOrderServiceReducer(selectedRepairRequests: RepairRequest[] = [], action: SelectedRepairRequestsOrderServiceAction) {
    switch (action.type) {
        case 'UPDATE-SELECTED-REPAIR-REQUESTS-ORDER-SERVICE':
            return action.payload
        default:
            return selectedRepairRequests
    }
}

export function changeStateModalOrderServiceRepairRequestsReducer(state: ModalStateInformation = initStateModalOrderServiceRepairRequests, action: StateModalOrderServiceRepairRequestsAction) {
    switch(action.type) {
        case 'CHANGE-STATE-MODAL-ORDER-SERVICE-REPAIR-REQUESTS':
            return action.payload
        default:
            return state
    }
}