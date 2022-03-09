import { StateFormAction} from "types/Action";
import { ActionEmployee, Pagination } from "types/Employee";

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

export function setCurrentPaginationAction(state: number) {
    return { 
        type: 'SET-CURRENT-PAGINATION-TABLE-EMPLOYEES',
        currentPage: state
     }
}