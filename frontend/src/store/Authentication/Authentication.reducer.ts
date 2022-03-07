import { EmailRequestCode, EmailRequestCodeAction, stateAuthenticationChange } from "types/Login";

export default function updateAuthenticationChangeStateReducer(state: EmailRequestCode = {}, action: EmailRequestCodeAction) {
    switch(action.type)
    {
        case stateAuthenticationChange.INSERTINFO:
            console.log("INFO")
            state = action.payload;
            return state
        case stateAuthenticationChange.WAITINGCODE:
            action.payload.state = stateAuthenticationChange.WAITINGCODE;
            action.payload.code = '';
            state = action.payload;
            return state
        case stateAuthenticationChange.CHANGINGPASSWORD:
            action.payload.state = stateAuthenticationChange.CHANGINGPASSWORD;
            state = action.payload;
            return state
        case stateAuthenticationChange.CLEAN:
            state = {
                type: -1,
                email: '',
                state: stateAuthenticationChange.CLEAN,
                code: '',
                token: {
                    type: '',
                    token: '',
                    newPassword: ''
                }
            }
            return state
        default:
            return state
    }
}