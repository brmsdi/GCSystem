import { ModalStateInformations, StateFormAction, TableAction, TypeEnumActionTables } from "types/action";
import { Employee } from "types/employee";
import { ActionOrderService, OrderService, PaginationOrderService, SelectedEmployeesOrderServiceAction, SelectedOrderServiceAction, StateModalOrderServiceEmployeesAction } from "types/order-service";
import { PaginationTableActionSearchPerNumber } from "types/pagination";

export function updateOrderServiceTable(page = 1, pagination: PaginationOrderService) {
    let getAllOrderServiceAction: ActionOrderService = {
        type: 'UPDATE-TABLE-ORDER-SERVICE',
        payload: {
            page: page,
            pagination: pagination
        }
    }
    return getAllOrderServiceAction;
}

export function setStateFormOrderServiceAction(state: string) {
    let stateForm: StateFormAction = {
        type: 'SET-STATE-FORM-ORDER-SERVICE',
        activity: state
    }
    return stateForm;
} 

export function paginationOrderServiceTableAction(paginationTableAction: PaginationTableActionSearchPerNumber) {
    let action : PaginationTableActionSearchPerNumber = { 
        type: paginationTableAction.type,
        currentPage: paginationTableAction.currentPage,
        search: paginationTableAction.search        
    }
    return action;
}

export function updateOrderServiceTableAction() {
    let action: TableAction = {
        type: TypeEnumActionTables.UPDATE
    }
    return action
}

export function selectOrderServiceTableAction(orderService: OrderService) {
    let action: SelectedOrderServiceAction = {
        type: TypeEnumActionTables.SELECTED_ORDER_SERVICE,
        payload: orderService
    }
    return action
}

export function removeSelectedOrderServiceTableAction() {
    let action: SelectedOrderServiceAction = {
        type: TypeEnumActionTables.REMOVING_ORDER_SERVICE
    }
    return action
}

export function selectedEmployeesOrderServiceAction(selectedEmployees: Employee[]) {
    let action: SelectedEmployeesOrderServiceAction = {
        type: 'UPDATE-SELECTED-EMPLOYEES-ORDER-SERVICE',
        payload: selectedEmployees
    }
    return action
}

export function changeStateModalOrderServiceEmployees(state: ModalStateInformations) {
    let action: StateModalOrderServiceEmployeesAction = {
        type: 'CHANGE-STATE-MODAL-ORDER-SERVICE-EMPLOYEES',
        payload: state
    }
    return action
}

export function detailsModalOrderService(orderService: OrderService) {
    let action: SelectedOrderServiceAction = {
        type: 'DETAILS-MODAL-ORDER-SERVICE',
        payload: orderService
    }
    return action
}