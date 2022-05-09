
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
    SELECTED_EMPLOYEE="SELECTED_EMPLOYEE",
    SELECTED_LESSEE="SELECTED_LESSEE",
    SELECTED_CONDOMINIUM="SELECTED_CONDOMINIUM",
    SELECTED_CONTRACT="SELECTED_CONTRACT",
    SELECTED_DEBT="SELECTED_DEBT",
    SELECTED_REPAIR_REQUEST="SELECTED_REPAIR_REQUEST",
    SELECTED_ORDER_SERVICE="SELECTED_ORDER_SERVICE",
    REMOVING_EMPLOYEE="REMOVING_EMPLOYEE",
    REMOVING_LESSEE="REMOVING_LESSEE",
    REMOVING_CONDOMINIUM="REMOVING_CONDOMINIUM",
    REMOVING_CONTRACT="REMOVING_CONTRACT",
    REMOVING_DEBT="REMOVING_DEBT",
    REMOVING_REPAIR_REQUEST="REMOVING_REPAIR_REQUEST",
    REMOVING_ORDER_SERVICE="REMOVING_ORDER_SERVICE",
    UPDATE="UPDATE"
}

export type ModalStateInformations = {
    isOpen: boolean
}