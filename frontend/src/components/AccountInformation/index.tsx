import { useSelector } from "react-redux";
import { NavLink } from "react-router-dom";
import { selectUserAuthenticated } from "store/Authentication/authentication.selectors";
import { UserAuthenticatedView } from "types/authentication-types";
import { LOGIN_URL } from "utils/urls";

const AccountInformation = () => {
  const userAuthenticated: UserAuthenticatedView = useSelector(selectUserAuthenticated)
  return (
    <div 
    id="id-account-info" 
    className="account-info"
    >
      <div className="account-info-user animate-opac">
        <div className="account-info-user-icon">
          <span>
            <i className="bi bi-person"></i>
          </span>
        </div>
        <div className="account-info-role">
          <span>{userAuthenticated.role}</span>
        </div>
        <div>
          <span>{userAuthenticated.name}</span>
        </div>
      </div>
      <div className="account-info-options animate-opac">
          <NavLink to={LOGIN_URL}>
          <i className="bi bi-box-arrow-right"></i>
          </NavLink>
      </div>
    </div>
  );
};

export default AccountInformation;
