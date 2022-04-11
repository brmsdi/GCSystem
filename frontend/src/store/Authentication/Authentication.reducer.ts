import { EmailRequestCode, EmailRequestCodeAction, StateAuthenticationChange, UserAuthenticatedView, UserAuthenticatedViewTypeAction, UserAuthenticatedViewTypesEnum } from "types/authentication-types";

export default function updateAuthenticationChangeStateReducer(state: EmailRequestCode = {}, action: EmailRequestCodeAction) {
    switch(action.type)
    {
        case StateAuthenticationChange.INSERTINFO:
            return action.payload
        default:
            return state
    }
}

export function userAuthenticatedViewReducer(state: UserAuthenticatedView = {name: '', role: ''}, action: UserAuthenticatedViewTypeAction) {
    switch (action.type) {
        case UserAuthenticatedViewTypesEnum.UPDATE:
            return action.user
        default:
            return state;
    }
}