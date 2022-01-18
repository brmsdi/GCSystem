import Alert from "components/messages";
import { useState } from "react";
import { Link } from "react-router-dom";

const RecoverPasswordChange = () => {
  const [password, setPassord] = useState({
    newPassword: "",
    repeatPassword: "",
  });

  const changeNewPassword = (value: any) => {
    setPassord((password) => ({ ...password, ...value }));
  };

  function submitPassword(event: any) {
    event.preventDefault();
  }

  return (
    <div className="content-login animate-down">
      <form onSubmit={submitPassword}>
        <div>
          <h2>System</h2>
          <span>Atualizar senha de acesso</span>
        </div>
        <Alert msg="As senhas não correspondem" />
        <div>
          <input
            type="password"
            name="newPassword"
            placeholder="Nova senha"
            value={password.newPassword}
            onChange={(e) => changeNewPassword({ newPassword: e.target.value })}
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
