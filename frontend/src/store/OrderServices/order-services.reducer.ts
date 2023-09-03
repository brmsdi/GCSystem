import { CurrentStateForm, ModalStateInformation, StateFormAction, StateFormEnum, TableAction, TypeEnumActionTables } from "types/action";
import { Employee } from "types/employee";
import { ActionOrderService, ModalStateInformationsInit, OrderService, OrderServiceEmpty, PaginationOrderService, PaginationOrderServiceEmpty, SelectedEmployeesOrderServiceAction, SelectedOrderServiceAction, StateModalOrderServiceEmployeesAction } from "types/order-service";
import { PaginationTableActionSearchPerNumber, StatePaginationEnum } from "types/pagination";

let initPagination : PaginationOrderService = PaginationOrderServiceEmpty;
let initOrderService : OrderService = OrderServiceEmpty;
let initStateModalOrderServiceEmployees : ModalStateInformation = ModalStateInformationsInit;

export default function getAllOrderServiceReducer(state: PaginationOrderService = initPagination, action: ActionOrderService) {
    switch(action.type)
    {
        case 'GET-ALL-ORDER-SERVICES':
            return state;
        case 'UPDATE-TABLE-ORDER-SERVICE':
            if (action.payload?.pagination) {
                return action.payload?.pagination;
            }
            return state;
        default:
            return state;
    }
}

export function setStateFormOrderServiceReducer(currentStateForm: CurrentStateForm = {activity: StateFormEnum.NOACTION, active: false} , action: StateFormAction ) {
    switch(action.type) {
        case 'SET-STATE-FORM-ORDER-SERVICE':
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

export function setCurrentPaginationOrderServiceReducer(state: PaginationTableActionSearchPerNumber = {type: StatePaginationEnum.SET_CURRENT_PAGINATION_TABLE_ORDER_SERVICES, currentPage: 1, search: undefined }, action: PaginationTableActionSearchPerNumber) {
    switch(action.type) {
        case StatePaginationEnum.SET_CURRENT_PAGINATION_TABLE_ORDER_SERVICES:
            return action;
        default:
            return state;
    }
}

export function stateSelectionOrderServiceReducer(state: OrderService = initOrderService, action: SelectedOrderServiceAction) {
    switch (action.type) {
        case TypeEnumActionTables.SELECTED_ORDER_SERVICE:
            let pay = action.payload;
            if (pay)
            {
                pay.completionDate = pay.completionDate ? pay.completionDate : null;
            }          
            return action.payload
        case TypeEnumActionTables.REMOVING_ORDER_SERVICE:
            return initOrderService
        default:
            return state
    }
}

export function updateTableOrderServiceReducer(state: boolean = false, action: TableAction) {
    switch (action.type) {
        case TypeEnumActionTables.UPDATE:
            return !state 
        default:
            return state
    }
}

export function selectedEmployeesOrderServiceReducer(selectedEmployees: Employee[] = [], action: SelectedEmployeesOrderServiceAction) {
    switch (action.type) {
        case 'UPDATE-SELECTED-EMPLOYEES-ORDER-SERVICE':
            return action.payload
        default:
            return selectedEmployees
    }
}

export function changeStateModalOrderServiceEmployeesReducer(state: ModalStateInformation = initStateModalOrderServiceEmployees, action: StateModalOrderServiceEmployeesAction) {
    switch(action.type) {
        case 'CHANGE-STATE-MODAL-ORDER-SERVICE-EMPLOYEES':
            return action.payload
        default:
            return state
    }
}


export function detailsModalOrderServiceReducer(state: OrderService = initOrderService, action: SelectedOrderServiceAction) {
    switch(action.type) {
        case 'DETAILS-MODAL-ORDER-SERVICE':
            return action.payload
        default:
            return state
    }
}