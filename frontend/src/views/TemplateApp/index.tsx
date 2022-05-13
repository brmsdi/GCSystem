import AdministratorAside from "components/Aside/Administrator";
import AdministrativeAssistantAside from "components/Aside/AdministrativeAssistant";
import { useSelector } from "react-redux";
import { selectUserAuthenticated } from "store/Authentication/authentication.selectors";
import { ROLE_ADMINISTRATIVE_ASSISTANT, ROLE_ADMINISTRATOR, ROLE_COUNTER, UserAuthenticatedView } from "types/authentication-types";
import CounterAside from "components/Aside/Accounter";

interface IProps {
  role: string
}

const GetAside = (props: IProps ) => {
  let role =props.role.toUpperCase() 
  if (role === ROLE_ADMINISTRATOR) {
    return <AdministratorAside />;
  } else if (role === ROLE_ADMINISTRATIVE_ASSISTANT) {
    return <AdministrativeAssistantAside />
  } else if (role === ROLE_COUNTER) {
    return <CounterAside />
  }
  return null;
}

const TemplateApp = (props: { page: any }) => {
  const userAuthenticatedView : UserAuthenticatedView = useSelector(selectUserAuthenticated)

  return (
    <div className="content">
      <header id="header-aside">
          <GetAside role={ userAuthenticatedView.role } />      
      </header>
      {props.page}
    </div>
  );
}
export default TemplateApp;

//  <div id="headerEvent" onClick={() => headerOpenOrClose()}></div>