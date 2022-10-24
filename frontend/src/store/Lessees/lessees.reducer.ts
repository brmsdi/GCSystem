import { CurrentStateForm, StateFormAction, StateFormEnum, TableAction, TypeEnumActionTables } from "types/action";
import { ActionLessee, Lessee, LesseeEmpty, PaginationLessee, PaginationLesseeEmpty, SelectedLesseeAction } from "types/lessee";
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";

let initPagination : PaginationLessee = PaginationLesseeEmpty;
let initLessee : Lessee = LesseeEmpty;

export default function getAllLesseesReducer(state: PaginationLessee = initPagination, action: ActionLessee) {
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

export function setCurrentPaginationLesseeReducer(state: PaginationTableAction = {type: StatePaginationEnum.SET_CURRENT_PAGINATION_TABLE_LESSEES, currentPage: 1, search: undefined }, action: PaginationTableAction) {
    switch(action.type) {
        case StatePaginationEnum.SET_CURRENT_PAGINATION_TABLE_LESSEES:
            return action;
        default:
            return state;
    }
}

export function stateSelectionLesseeReducer(state: Lessee = initLessee, action: SelectedLesseeAction) {
    switch (action.type) {
        case TypeEnumActionTables.SELECTED_LESSEE:
            return action.payload
        case TypeEnumActionTables.REMOVING_LESSEE:
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