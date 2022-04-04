import { StateFormAction, TableAction, TypeEnumActionTables} from "types/action";
import { ActionEmployee, Employee, PaginationEmployee, SelectedEmployeeAction } from "types/employee";
import { PaginationTableAction } from "types/pagination";

export function getAllEmployeesAction(page = 1) {
    let getAllEmployeesAction: ActionEmployee = {
        type: 'GET-ALL-EMPLOYEES',
        payload: {page: page}
    }
    return getAllEmployeesAction;
}

export function updateEmployeesTable(page = 1, pagination: PaginationEmployee) {
    let getAllEmployeesAction: ActionEmployee = {
        type: 'UPDATE-TABLE',
        payload: {
            page: page,
            pagination: pagination
        }
    }
    return getAllEmployeesAction;
}

export function setStateFormEmployeeAction(state: string) {
    let stateForm: StateFormAction = {
        type: 'SET-STATE-FORM-EMPLOYEE',
        activity: state
    }
    return stateForm;
} 

export function paginationEmployeeTableAction(paginationTableAction: PaginationTableAction) {
    let action : PaginationTableAction = { 
        type: paginationTableAction.type,
        currentPage: paginationTableAction.currentPage,
        search: paginationTableAction.search        
    }
    return action;
}

export function selectEmployeeTableAction(employee: Employee) {
    let action: SelectedEmployeeAction = {
        type: TypeEnumActionTables.SELECTED,
        payload: employee
    }
    return action
}

export function removeSelectedEmployeeTableAction() {
    let action: SelectedEmployeeAction = {
        type: TypeEnumActionTables.REMOVED
    }
    return action
}

export function updateEmployeeTableAction() {
    let action: TableAction = {
        type: TypeEnumActionTables.UPDATE
    }
    return action
}
