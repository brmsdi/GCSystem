export type Action = {
    type: string;
    payload?: {
        page: number
    }
}

export type StateFormAction = {
    type: string;
    activity: string;
}

export type CurrentStateForm = {
    activity: string;
    active: boolean;
    
}

export enum StateFormEnum {
    NOACTION = "NOACTION",
    EDITING = "EDITING",
    SAVING = "SAVING",
    NEW = "Novo",
    UPDATE = "Atualizar"
}

export type AuthAction = {
    type: string;
    isLogged?: boolean;
}