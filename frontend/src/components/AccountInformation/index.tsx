import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { selectUserAuthenticated } from "store/Authentication/Authentication.selectors";
import { UserAuthenticatedView } from "types/AuthenticationTypes";

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
          <Link to={"/login"}>
          <i className="bi bi-box-arrow-right"></i>
          </Link>
      </div>
    </div>
  );
};

export default AccountInformation;
