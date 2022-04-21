import { CurrentStateForm, StateFormAction, StateFormEnum, TableAction, TypeEnumActionTables } from "types/action";
import { ActionDebt, Debt, DebtEmpty, PaginationDebt, PaginationDebtEmpty, SelectedDebtAction } from "types/debt";
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";

let initPagination : PaginationDebt = PaginationDebtEmpty;
let initDebt : Debt = DebtEmpty;

export default function getAllDebtsReducer(state: PaginationDebt = initPagination, action: ActionDebt) {
    switch(action.type)
    {
        case 'GET-ALL-CONTRACTS':
            return state;
        case 'UPDATE-TABLE-DEBT':
            if (action.payload?.pagination) {
                return action.payload?.pagination;
            }
            return state;
        default:
            return state;
    }
}

export function setStateFormDebtReducer(currentStateForm: CurrentStateForm = {activity: StateFormEnum.NOACTION, active: false} , action: StateFormAction ) {
    switch(action.type) {
        case 'SET-STATE-FORM-DEBT':
            if(action.activity === StateFormEnum.NOACTION) {
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

export function setCurrentPaginationDebtReducer(state: PaginationTableAction = {type: StatePaginationEnum.SETCURRENTPAGINATIONTABLEDEBTS, currentPage: 1, search: undefined }, action: PaginationTableAction) {
    switch(action.type) {
        case StatePaginationEnum.SETCURRENTPAGINATIONTABLEDEBTS:
            return action;
        default:
            return state;
    }
}

export function stateSelectionDebtReducer(state: Debt = initDebt, action: SelectedDebtAction) {
    switch (action.type) {
        case TypeEnumActionTables.SELECTED_DEBT:
            return action.payload
        case TypeEnumActionTables.REMOVING_DEBT:
            return initDebt
        default:
            return state
    }
}

export function updateTableDebtReducer(state: boolean = false, action: TableAction) {
    switch (action.type) {
        case TypeEnumActionTables.UPDATE:
            return !state 
        default:
            return state
    }
}