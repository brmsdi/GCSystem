import { Link } from "react-router-dom";

const AccountInformation = () => {

  return (
    <div 
    id="id-account-info" 
    className="account-info"
    >
      <div className="account-info-user">
        <div className="account-info-user-icon">
          <span>
            <i className="bi bi-person"></i>
          </span>
        </div>
        <div className="account-info-role">
          <span>Administrador</span>
        </div>
        <div>
          <span>Wisley Bruno Marques França</span>
        </div>
      </div>
      <div className="account-info-options">
          <Link to={""}>
          <i className="bi bi-box-arrow-right"></i> Sair
          </Link>
      </div>
    </div>
  );
};

export default AccountInformation;
