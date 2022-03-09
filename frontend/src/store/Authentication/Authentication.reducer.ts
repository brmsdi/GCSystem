import { EmailRequestCode, EmailRequestCodeAction, stateAuthenticationChange } from "types/Login";

export default function updateAuthenticationChangeStateReducer(state: EmailRequestCode = {}, action: EmailRequestCodeAction) {
    switch(action.type)
    {
        case stateAuthenticationChange.INSERTINFO:
            state = action.payload;
            return state
        default:
            return state
    }
}