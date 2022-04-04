import { CurrentStateForm, StateFormAction, StateFormEnum, TableAction, TypeEnumActionTables } from "types/action";
import { ActionLessee, Lessee, PaginationLessee, SelectedLesseeAction } from "types/lessee";
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";

let initPagination : PaginationLessee = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

let initLessee : Lessee = {
    name: '',
    rg: '',
    cpf: '',
    birthDate: '',
    email: '',
    contactNumber: '',
    status: {
        name: ''
    },
    password: ''
} 


export default function getAllELesseesReducer(state: PaginationLessee = initPagination, action: ActionLessee) {
    switch(action.type)
    {
        case 'GET-ALL-LESSEES':
            return state;
        case 'UPDATE-TABLE-LESSEE':
            if (action.payload?.pagination) {
                return action.payload?.pagination;
            }
            return state;
        default:
            return state;
    }
}

export function setStateFormLesseeReducer(currentStateForm: CurrentStateForm = {activity: StateFormEnum.NOACTION, active: false} , action: StateFormAction ) {
    switch(action.type) {
        case 'SET-STATE-FORM-LESSEE':
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

export function setCurrentPaginationLesseeReducer(state: PaginationTableAction = {type: StatePaginationEnum.SETCURRENTPAGINATIONTABLELESSEES, currentPage: 1, search: undefined }, action: PaginationTableAction) {
    switch(action.type) {
        case StatePaginationEnum.SETCURRENTPAGINATIONTABLELESSEES:
            return action;
        default:
            return state;
    }
}

export function stateSelectionLesseeReducer(state: Lessee = initLessee, action: SelectedLesseeAction) {
    switch (action.type) {
        case TypeEnumActionTables.SELECTED:
            return action.payload
        case TypeEnumActionTables.REMOVED:
            return initLessee
        default:
            return state
    }
}

export function updateTableLesseeReducer(state: boolean = false, action: TableAction) {
    switch (action.type) {
        case TypeEnumActionTables.UPDATE:
            return !state 
        default:
            return state
    }
}