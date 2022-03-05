import { EmailRequestCode, EmailRequestCodeAction, stateAuthenticationChange } from "types/Login";

export default function updateAuthenticationChangeStateReducer(state: EmailRequestCode = {type: -1, email: ''}, action: EmailRequestCodeAction) {
    switch(action.type)
    {
        case stateAuthenticationChange.WAITINGCODE:
            action.payload.state = stateAuthenticationChange.WAITINGCODE;
            action.payload.code = '';
            state = action.payload;
            return state
        case stateAuthenticationChange.CHANGINGPASSWORD:
            action.payload.state = stateAuthenticationChange.CHANGINGPASSWORD;
            state = action.payload;
            return state
        default:
            return state
    }
}