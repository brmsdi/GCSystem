import { SubMenuSelectedItemType } from "types/aside";
import { TEXT_MENU_ITEM_ID_HOME } from "utils/menu-items";

export function changeSelectedSubMenuAsideReducer(state: string = TEXT_MENU_ITEM_ID_HOME, action: SubMenuSelectedItemType) {
    switch (action.type) {
        case 'SELECTED-ITEM-MENU':
            return action.id
        default:
            return state 
    }
} 