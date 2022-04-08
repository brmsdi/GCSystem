import { CurrentStateForm, StateFormAction, StateFormEnum, TableAction, TypeEnumActionTables } from "types/action";
import { ActionCondominium, Condominium, PaginationCondominium, SelectedCondominiumAction } from "types/condominium"
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";


let initPagination : PaginationCondominium = {
    pageNumber: 0,
    paged: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: -1,
    empty: true
}

let initCondominium : Condominium = {
    name: '',
    description: '',
    numberApartments: 0,
    status: {
        name: ''
    },
    localization: {
        number: '0',
        localization: {
            name: '',
            road: '',
            zipCode: ''
        }
    }
} 

export default function getAllCondominiumsReducer(state: PaginationCondominium = initPagination, action: ActionCondominium) {
    switch(action.type)
    {
        case 'GET-ALL-CONDOMINIUMS':
            return state;
        case 'UPDATE-TABLE-CONDOMINIUMS':
            if (action.payload?.pagination) {
                return action.payload?.pagination;
            }
            return state;
        default:
            return state;
    }
}

export function setStateFormCondominiumReducer(currentStateForm: CurrentStateForm = {activity: StateFormEnum.NOACTION, active: false} , action: StateFormAction ) {
    switch(action.type) {
        case 'SET-STATE-FORM-CONDOMINIUM':
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

export function setCurrentPaginationCondominiumReducer(state: PaginationTableAction = {type: StatePaginationEnum.SETCURRENTPAGINATIONTABLECONDOMINIUMS, currentPage: 1, search: undefined }, action: PaginationTableAction) {
    switch(action.type) {
        case StatePaginationEnum.SETCURRENTPAGINATIONTABLECONDOMINIUMS:
            return action;
        default:
            return state;
    }
}


export function stateSelectionCondominiumReducer(state: Condominium = initCondominium, action: SelectedCondominiumAction) {
    switch (action.type) {
        case TypeEnumActionTables.SELECTED_CONDOMINIUM:
            return action.payload
        case TypeEnumActionTables.REMOVING_CONDOMINIUM:
            return initCondominium
        default:
            return state
    }
}

export function updateTableCondominiumReducer(state: boolean = false, action: TableAction) {
    switch (action.type) {
        case TypeEnumActionTables.UPDATE:
            return !state 
        default:
            return state
    }
}