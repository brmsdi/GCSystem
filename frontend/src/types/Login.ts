export type AuthCpfAndPassword = {
    cpf: string,
    password: string
}

export type EmailRequestCode = {
    state?: stateAuthenticationChange,
    email: string,
    type: number,
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

export enum stateAuthenticationChange {
    WAITINGCODE="INSERINDO CODIGO",
    CHANGINGPASSWORD="ATUALIZANDO SENHA"
}