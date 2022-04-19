import { CurrentStateForm, StateFormAction, StateFormEnum, TableAction, TypeEnumActionTables } from "types/action";
import { ActionContract, Contract, ContractEmpty, PaginationContract, PaginationContractEmpty, SelectedContractAction } from "types/contract";
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";

let initPagination : PaginationContract = PaginationContractEmpty;
let initContract : Contract = ContractEmpty;

export default function getAllContractsReducer(state: PaginationContract = initPagination, action: ActionContract) {
    switch(action.type)
    {
        case 'GET-ALL-CONTRACTS':
            return state;
        case 'UPDATE-TABLE-CONTRACT':
            if (action.payload?.pagination) {
                return action.payload?.pagination;
            }
            return state;
        default:
            return state;
    }
}

export function setStateFormContractReducer(currentStateForm: CurrentStateForm = {activity: StateFormEnum.NOACTION, active: false} , action: StateFormAction ) {
    switch(action.type) {
        case 'SET-STATE-FORM-CONTRACT':
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

export function setCurrentPaginationContractReducer(state: PaginationTableAction = {type: StatePaginationEnum.SETCURRENTPAGINATIONTABLECONTRACTS, currentPage: 1, search: undefined }, action: PaginationTableAction) {
    switch(action.type) {
        case StatePaginationEnum.SETCURRENTPAGINATIONTABLECONTRACTS:
            return action;
        default:
            return state;
    }
}

export function stateSelectionContractReducer(state: Contract = initContract, action: SelectedContractAction) {
    switch (action.type) {
        case TypeEnumActionTables.SELECTED_CONTRACT:
            return action.payload
        case TypeEnumActionTables.REMOVING_CONTRACT:
            return initContract
        default:
            return state
    }
}

export function updateTableContractReducer(state: boolean = false, action: TableAction) {
    switch (action.type) {
        case TypeEnumActionTables.UPDATE:
            return !state 
        default:
            return state
    }
}