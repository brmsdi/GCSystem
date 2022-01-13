import { getAllEmployeesMock } from 'mocks/EmployeesMock'; 
import { Action, CurrentStateForm, StateFormEnum, StateFormAction } from 'types/Action';
import { Employee } from "types/Employee";

export default function getAll(state: Employee[] = getAllEmployeesMock(1), action: Action) {

    switch(action.type)
    {
        case 'GET-ALL-EMPLOYEES':
            let page = action.payload?.page ? action.payload?.page : 1; 
            return getAllEmployeesMock(page);
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

export function setCurrentPagination(state: number = 1, action: { type: string, currentPage: number } ) {
    switch(action.type) {
        case 'SET-CURRENT-PAGINATION-TABLE-EMPLOYEES':
            console.log(action.currentPage)
            return action.currentPage;
        default:
            return state;
    }
}