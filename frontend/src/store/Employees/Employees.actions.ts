import { Action, StateFormAction} from "types/Action";

export function getAllEmployees(page = 1) {
    let getAllEmployeesAction: Action = {
        type: 'GET-ALL-EMPLOYEES',
        payload: {page: page}
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