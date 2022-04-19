import { useDispatch } from "react-redux";
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action";
import { TEXT_MENU_ITEM_ID_HOME } from "utils/menu-items";

const HomeView = () => {
  const dispatch = useDispatch();
  dispatch(changeSelectedSubMenuAsideAction(TEXT_MENU_ITEM_ID_HOME))
  return (
    <main className="content-main animate-right">
    <div className="home-header">
        <h1>{"GCSystem"}</h1>
    </div>
    
    <div>
      <p>home</p>
    </div>
    </main>
  )
}

export default HomeView;
