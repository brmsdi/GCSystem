export const isValidFieldCPF = (value: any) => {
    return (value.length === 11)
}

export const isValidZipCode = (value: string) => {
    if(value.length === 8) {
        return true
    } 
    return false
}