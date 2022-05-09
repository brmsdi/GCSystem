import { Condominium } from "types/condominium"
import { Employee } from "types/employee"
import { RepairRequest } from "types/repair-request"
import { Status } from "types/status"
import { TypeProblem } from "types/type-problem"

export const isValidFieldCPF = (value: any) => {
    return (value.length === 11)
}

export const isValidZipCode = (value: string) => {
    return (value.length === 8)
}

export const isValidFieldText = (value: string | undefined) => {
    return (value !== undefined && value.length > 0)
}

export const isValidFieldNumber = (value: number | undefined) => {
    return (value !== undefined && value > 0)
}

export const isValidFieldDay = (value: number | undefined) => {
    return (value !== undefined && value > 0 && value < 31)
}

export const isSelected = (combo: Status | TypeProblem | Condominium) => {
    return combo.id !== undefined && combo.id > 0 && combo.name.length > 0; 
}

export const isEmpty = (item: RepairRequest[] | Employee[]) => {
    return item.length === 0
}
