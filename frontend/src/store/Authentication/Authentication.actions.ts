import { EmailRequestCode, EmailRequestCodeAction } from "types/Login";

export function insertRequestCodeInfo(type: string, payload: EmailRequestCode) {
    let action: EmailRequestCodeAction = {
        type: type,
        payload: payload
    }

    return action;
}

export default insertRequestCodeInfo;