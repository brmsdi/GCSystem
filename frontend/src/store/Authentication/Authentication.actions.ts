import { EmailRequestCode, EmailRequestCodeAction, UserAuthenticatedView, UserAuthenticatedViewTypeAction } from "types/authentication-types";

export default function insertRequestCodeInfo(type: string, payload: EmailRequestCode) {
    let action: EmailRequestCodeAction = {
        type: type,
        payload: payload
    }
    return action;
}

export function setUserAuthenticated(type: string, payload: UserAuthenticatedView) {
    let action: UserAuthenticatedViewTypeAction = {
        type: type,
        user: payload
    }
    return action;
}