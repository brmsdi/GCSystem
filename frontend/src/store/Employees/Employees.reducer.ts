import { getAllEmployeesMock } from 'mocks/EmployeesMock'; 
import { CurrentStateForm, StateFormEnum, StateFormAction } from 'types/Action';
import { ActionEmployee, Employee, Pagination, SelectedEmployeeAction, TableAction, TypeEnumActionEmployee } from "types/Employee";
import { PaginationTableAction, StatePaginationEnum } from 'types/Pagination';

let initPagination : Pagination = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

let initEmployee : Employee = {
    name: '',
    rg: '',
    cpf: '',
    birthDate: '',
    email: '',
    hiringDate: '',
    role: {
        name: ''
    },
    status: {
        name: ''
    },
    password: ''
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

export function setCurrentPagination(state: PaginationTableAction = {type: StatePaginationEnum.SETCURRENTPAGINATIONTABLEEMPLOYEES, currentPage: 1, search: undefined }, action: PaginationTableAction) {
    switch(action.type) {
        case StatePaginationEnum.SETCURRENTPAGINATIONTABLEEMPLOYEES:
            return action;
        default:
            return state;
    }
}

export function stateSelectionEmployeeReducer(state: Employee = initEmployee, action: SelectedEmployeeAction) {
    switch (action.type) {
        case TypeEnumActionEmployee.SELECTED:
            return action.payload
        case TypeEnumActionEmployee.REMOVED:
            return initEmployee
        default:
            return state
    }
}

export function updateTableEmployeeReducer(state: boolean = false, action: TableAction) {
    
    switch (action.type) {
        case TypeEnumActionEmployee.UPDATE:
            return !state 
        default:
            return state
    }
}