import { getAllEmployeesMock } from 'mocks/EmployeesMock'; 
import { CurrentStateForm, StateFormEnum, StateFormAction, TypeEnumActionTables, TableAction } from 'types/action';
import { ActionEmployee, Employee, EmployeeEmpty, PaginationEmployee, PaginationEmployeeEmpty, SelectedEmployeeAction } from "types/employee";
import { PaginationTableAction, StatePaginationEnum } from 'types/pagination';

let initPagination : PaginationEmployee = PaginationEmployeeEmpty
let initEmployee : Employee = EmployeeEmpty;

export function getAll(state: Employee[] = getAllEmployeesMock(1), action: ActionEmployee) {
    switch(action.type)
    {
        case 'GET-ALL-EMPLOYEES-MOCK':
            let page = action.payload?.page ? action.payload?.page : 1; 
            return getAllEmployeesMock(page);
        default:
            return state;
    }
} 

export default function getAllEmployeesReducer(state: PaginationEmployee = initPagination, action: ActionEmployee) {
    switch(action.type)
    {
        case 'GET-ALL-EMPLOYEES':
            return state;
        case 'UPDATE-TABLE':
            if (action.payload?.pagination) {
                return action.payload?.pagination;
            }
            return state;
        default:
            return state;
    }
}

export function setStateFormEmployeeReducer(currentStateForm: CurrentStateForm = {activity: StateFormEnum.NOACTION, active: false} , action: StateFormAction ) {
    switch(action.type) {
        case 'SET-STATE-FORM-EMPLOYEE':
            if(action.activity === StateFormEnum.NOACTION) {
                let stateCurrent: CurrentStateForm = {
                    activity: StateFormEnum.NOACTION,
                    active: false
                }
                return stateCurrent;
            } else if(action.activity === StateFormEnum.SAVING ) {
                let stateCurrent: CurrentStateForm = {
                    activity: StateFormEnum.NEW,
                    active: true
                }
                return stateCurrent;
            } else if(action.activity === StateFormEnum.UPDATE ) {
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

export function setCurrentPaginationEmployeeReducer(state: PaginationTableAction = {type: StatePaginationEnum.SETCURRENTPAGINATIONTABLEEMPLOYEES, currentPage: 1, search: undefined }, action: PaginationTableAction) {
    switch(action.type) {
        case StatePaginationEnum.SETCURRENTPAGINATIONTABLEEMPLOYEES:
            return action;
        default:
            return state;
    }
}

export function stateSelectionEmployeeReducer(state: Employee = initEmployee, action: SelectedEmployeeAction) {
    switch (action.type) {
        case TypeEnumActionTables.SELECTED_EMPLOYEE:
            return action.payload
        case TypeEnumActionTables.REMOVING_EMPLOYEE:
            return initEmployee
        default:
            return state
    }
}

export function updateTableEmployeeReducer(state: boolean = false, action: TableAction) {
    switch (action.type) {
        case TypeEnumActionTables.UPDATE:
            return !state 
        default:
            return state
    }
}