import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { changePassword } from "services/Authentication";
import insertRequestCodeInfo from "store/Authentication/Authentication.actions";
import { selectStateChangePassword } from "store/Authentication/Authentication.selectors";
import Swal from "sweetalert2";
import { EmailRequestCode, StateAuthenticationChange } from "types/AuthenticationTypes";
import { LOGIN_URL } from "utils/urls";

const RecoverPasswordChange = () => {
  let nav = useNavigate();
  const dispatch = useDispatch(); 
  const stateChangePassword: EmailRequestCode = useSelector(selectStateChangePassword);
  const[password, setPassword] = useState({
    newPassword: "",
    repeatPassword: "",
  });

  const changeNewPassword = (value: any) => {
    setPassword((password) => ({ ...password, ...value }));
  };

  async function submitPassword(event: any) {
    event.preventDefault();
    await verifyCurrentState()
    if (password.newPassword === password.repeatPassword && stateChangePassword.token) {
      try 
      {
        stateChangePassword.token.newPassword = password.newPassword;
        const message = await changePassword(stateChangePassword)
        await Swal.fire("Eeeba!", message, "success")
        dispatch(insertRequestCodeInfo(StateAuthenticationChange.INSERTINFO, {}))
        nav(LOGIN_URL)
      } catch(error : any) {
        if (error.response) {
          let message = error.response.data.errors[0].message;
          Swal.fire('Oops!', '' + message, 'error')
        } else {
          Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
        }
      }
    } else {
      Swal.fire('Oops!', 'As senhas não correspondem', 'error')
      return
    }
  }

  async function verifyCurrentState() {
    if (!(stateChangePassword.state === StateAuthenticationChange.CHANGINGPASSWORD)) {
      nav(LOGIN_URL)
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
