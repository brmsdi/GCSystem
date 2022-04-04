
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

export type TableAction = {
    type: string
}

export enum TypeEnumActionTables {
    SELECTED="SELECTED_EMPLOYEE",
    REMOVED="REMOVENDO",
    UPDATE="UPDATE"
}