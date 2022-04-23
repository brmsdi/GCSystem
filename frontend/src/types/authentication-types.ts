export type AuthCpfAndPassword = {
    cpf: string,
    password: string
}

export type EmailRequestCode = {
    state?: StateAuthenticationChange,
    email?: string,
    type?: number,
    code?: string,
    token?: {
        type: string,
        token: string
        newPassword?: string
    }
}

export type EmailRequestCodeAction = {
    type: string,
    payload: EmailRequestCode
}

export enum StateAuthenticationChange {
    WAITINGCODE="INSERINDO CODIGO",
    CHANGINGPASSWORD="ATUALIZANDO SENHA",
    CLEAN="CLEAN",
    INSERTINFO="INSERT-INFO"
}

export type UserAuthenticatedView = {
    name: string;
    role: string;
}

export type UserAuthenticatedViewTypeAction = {
    type: string;
    user: UserAuthenticatedView;
}

export enum UserAuthenticatedViewTypesEnum {
    UPDATE="UPDATE-USER-AUTH"
}

export const ROLE_ADMINISTRATOR = "ADMINISTRADOR";
export const ROLE_ADMINISTRATIVE_ASSISTANT = "ASSISTENTE ADMINISTRATIVO";
export const ROLE_COUNTER = "CONTADOR";