import { LocalizationCondominium } from "types/localization-condominium";

export const formatDate = (value: string) => {
    let date = new Date(value),
        day  = date.getDate().toString().padStart(2, '0'),
        month  = (date.getMonth()+1).toString().padStart(2, '0'),
        year  = date.getFullYear();
    return `${year}-${month}-${day}`;
}

export const formatDateForView = (value: string) => {
    let date = new Date(value),
        day  = date.getDate().toString().padStart(2, '0'),
        month  = (date.getMonth()+1).toString().padStart(2, '0'),
        year  = date.getFullYear();
    return `${day}/${month}/${year}`;
}

export const formatLocalizationViewInformation = (localization: LocalizationCondominium) => {
    if(localization.number === '0') return '';
    return localization.localization.road + ', ' + localization.number + ' - ' + localization.localization.name;
}