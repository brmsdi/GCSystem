
// ENTRADA: 125.45
// SAÍDA: R$ 124,45
export const formatCoinPTBRForView = (value: number, locale: string = 'pt-br', style: string = 'currency', currency: string = 'BRL') => {
    return value.toLocaleString(locale, {style, currency})
}
