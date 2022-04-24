import { Status } from "types/status"

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

export const statusIsSelected = (status: Status) => {
    return status.id !== undefined && status.id > 0 && status.name.length > 0; 
  }