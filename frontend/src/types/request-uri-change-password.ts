import { REQUEST_EMPLOYEES, REQUEST_PASSWORD_CHANGE, REQUEST_REQUEST_CODE, REQUEST_VALIDATE_CODE } from "utils/requests";

export type UriRequestType = {
    id: number;
    requestCodeURI: string;
    requestSendCode: string;
    requestPasswordChange: string;
    name: string;
}

export const URISRequestTypes: UriRequestType[] = [
    {
        id: 0,
        requestCodeURI: `${REQUEST_EMPLOYEES}/${REQUEST_REQUEST_CODE}`,
        requestSendCode: `${REQUEST_EMPLOYEES}/${REQUEST_VALIDATE_CODE}`,
        requestPasswordChange: `${REQUEST_EMPLOYEES}/${REQUEST_PASSWORD_CHANGE}`,
        name: "Funcionário"
    }
]