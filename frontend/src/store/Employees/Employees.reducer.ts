import { getAllEmployeesMock } from 'mocks/EmployeesMock'; 
import { CurrentStateForm, StateFormEnum, StateFormAction } from 'types/Action';
import { ActionEmployee, Employee, Pagination } from "types/Employee";
import { PaginationTableAction, statePaginationEnum } from 'types/Pagination';

let initPagination : Pagination = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

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

export default function getAllEmployeesReducer(state: Pagination = initPagination, action: ActionEmployee) {
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

export function setStateFormReducer(currentStateForm: CurrentStateForm = {activity: StateFormEnum.NOACTION, active: false} , action: StateFormAction ) {
    switch(action.type) {
        case 'SET-STATE-FORM':
            if(action.activity ===  StateFormEnum.NOACTION) {
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
            }
            return currentStateForm;
        default:
            return currentStateForm;
    }
}

export function setCurrentPagination(state: PaginationTableAction = {type: statePaginationEnum.SETCURRENTPAGINATIONTABLEEMPLOYEES, currentPage: 1, search: undefined }, action: PaginationTableAction) {
    switch(action.type) {
        case statePaginationEnum.SETCURRENTPAGINATIONTABLEEMPLOYEES:
            return action;
        default:
            return state;
    }
}