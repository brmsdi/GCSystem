
// ENTRADA: 125.45
// SAÍDA: R$ 124,45
export const formatCoinPTBRForView = (value: number, locale: string = 'pt-br', style: string = 'currency', currency: string = 'BRL') => {
    return value.toLocaleString(locale, {style, currency})
}

// ENTRADA: 128
// SAÍDA: CENTO E VINTE E OITO
export const formatInFull = (value: number) => {
    const numero = require('numero-por-extenso')
    return numero.porExtenso(value)
}