import { SubMenuSelectedItemType } from "types/aside";

export function changeSelectedSubMenuAsideAction(id: string) {
   let action: SubMenuSelectedItemType = {
       type: 'SELECTED-ITEM-MENU',
       id: id
   }
   return action
} 