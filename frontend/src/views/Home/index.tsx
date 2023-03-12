import { useDispatch } from "react-redux";
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action";
import { TEXT_MENU_ITEM_ID_HOME } from "utils/menu-items";
import Balloon from "components/Balloon";

const HomeView = () => {
  const dispatch = useDispatch();
  dispatch(changeSelectedSubMenuAsideAction(TEXT_MENU_ITEM_ID_HOME))
  return (
    <main className="content-main animate-right">
      <div className="home-header">
        <h1>{"Página inicial"}</h1>
      </div>
      <div className="home">
        <div className="text-title-gc">
          <p>SISTEMA PARA GERENCIAMENTO DE CONDOMÍNIOS</p>
        </div>
        <div>
          <p>Desenvolvido por Wisley Bruno Marques França</p>
        </div>
        <div>
          <p className="p-contact"><i className="bi bi-telephone">&nbsp;</i>Contato: 92 99107-1491</p>
          <p className="p-contact"><i className="bi bi-envelope-at">&nbsp;</i>Email: srmarquesms@gmail.com</p>
        </div>
        <div className="footer-home">
          <div className="footer-balloons">
            <Balloon
              id={"balloon-wpp"}
              title={"WhatsApp"}
              link="https://wa.me/5592991071491"
              icon="bi bi-whatsapp"
            />
            <Balloon
              id={"balloon-email"}
              title={"Email"}
              link="mailto:srmarquesms@gmail.com"
              icon="bi bi-envelope"
            />
            <Balloon
              id={"balloon-github"}
              title={"GitHub"}
              link="https://github.com/brmsdi/GCSystem"
              icon="bi bi-github"
            />
          </div>
        </div>
      </div>
    </main>
  );
}

export default HomeView;
