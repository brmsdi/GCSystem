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