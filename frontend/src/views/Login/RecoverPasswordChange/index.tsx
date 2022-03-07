import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { change } from "services/Authentication";
import insertRequestCodeInfo from "store/Authentication/Authentication.actions";
import { selectStateChangePassword } from "store/Authentication/Authentication.selectors";
import Swal from "sweetalert2";
import { EmailRequestCode, stateAuthenticationChange } from "types/Login";
import { LOGIN_URL } from "utils/urls";

const RecoverPasswordChange = () => {
  let nav = useNavigate();
  const dispatch = useDispatch(); 
  const stateChangePassword: EmailRequestCode = useSelector(selectStateChangePassword);
  if (!(stateChangePassword.state === stateAuthenticationChange.CHANGINGPASSWORD)) {
    nav(LOGIN_URL)
  }

  const[password, setPassword] = useState({
    newPassword: "",
    repeatPassword: "",
  });

  const changeNewPassword = (value: any) => {
    setPassword((password) => ({ ...password, ...value }));
  };

  function submitPassword(event: any) {
    event.preventDefault();
    if (password.newPassword === password.repeatPassword && stateChangePassword.token) {
      stateChangePassword.token.newPassword = password.newPassword;
      change(stateChangePassword)
      .then(response => {
        Swal.fire("Eeeba!", response.data, "success")
        dispatch(insertRequestCodeInfo(stateAuthenticationChange.INSERTINFO, {}))
        nav(LOGIN_URL)
      })
      .catch();
      
    } else {
      Swal.fire('Oops!', 'As senhas não correspondem', 'error')
      return
    }
  }

  return (
    <div className="content-login animate-down">
      <form onSubmit={submitPassword}>
        <div>
          <h2>System</h2>
          <span>Atualizar senha de acesso</span>
        </div>
        <div>
          <input
            type="password"
            name="newPassword"
            placeholder="Nova senha"
            value={password.newPassword}
            onChange={(e) => changeNewPassword({ newPassword: e.target.value })}
            minLength={5}
            maxLength={16}
            required
          />
        </div>
        <div>
          <input
            type="password"
            name="repeatPassword"
            placeholder="Repetir nova senha"
            value={password.repeatPassword}
            onChange={(e) =>
              changeNewPassword({ repeatPassword: e.target.value })
            }
            minLength={5}
            maxLength={16}
            required
          />
        </div>
        <div>
          <button type="submit" className="btn btn-sm btn-outline-secondary">
            <i data-feather="log-in"></i> Confirmar
          </button>
        </div>
        <div>
          <Link to={"/login"} className="btn btn-sm btn-outline-secondary">
            <i data-feather="log-in"></i> Cancelar
          </Link>
        </div>
      </form>
    </div>
  );
};

export default RecoverPasswordChange;
