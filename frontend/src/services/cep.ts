import axios from "axios"

export const getZipCodeService = (cep: string) => {
    return axios.get(`https://viacep.com.br/ws/${cep}/json/`)
}