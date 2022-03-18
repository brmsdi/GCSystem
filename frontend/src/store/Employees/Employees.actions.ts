import { StateFormAction} from "types/Action";
import { ActionEmployee, Employee, Pagination, SelectedEmployeeAction, TypeEnumActionEmployee } from "types/Employee";
import { PaginationTableAction } from "types/Pagination";

export function getAllEmployeesAction(page = 1) {
    let getAllEmployeesAction: ActionEmployee = {
        type: 'GET-ALL-EMPLOYEES',
        payload: {page: page}
    }
    return getAllEmployeesAction;
}

export function updateEmployeesTable(page = 1, pagination: Pagination) {
    let getAllEmployeesAction: ActionEmployee = {
        type: 'UPDATE-TABLE',
        payload: {
            page: page,
            pagination: pagination
        }
    }
    return getAllEmployeesAction;
}

export function setStateFormAction(state: string ) {
    let stateForm: StateFormAction = {
        type: 'SET-STATE-FORM',
        activity: state
    }
    return stateForm;
} 

export function paginationTableAction(paginationTableAction: PaginationTableAction) {
    let action : PaginationTableAction = { 
        type: paginationTableAction.type,
        currentPage: paginationTableAction.currentPage,
        search: paginationTableAction.search        
    }
    return action;
}

export function selectEmployeeTableAction(employee: Employee) {
    let action: SelectedEmployeeAction = {
        type: TypeEnumActionEmployee.SELECTED,
        payload: employee
    }
    return action
}

export function removeSelectedEmployeeTableAction() {
    let action: SelectedEmployeeAction = {
        type: TypeEnumActionEmployee.REMOVED
    }
    return action
}
