import { Link, useNavigate } from "react-router-dom";
import { LOGIN_URL, RECOVER_PASSWORD_SEND_CODE_URL } from "utils/urls";

const RecoverPasswordSendCodeEmail = () => {
  let nav = useNavigate();

  function submit() {
    nav(LOGIN_URL + RECOVER_PASSWORD_SEND_CODE_URL);
  }
  return (
    <div className="content-login animate-down">
      <form id="form-send-email-forgot" onSubmit={submit}>
        <div>
          <h2>Recuperação de senha</h2>
          <span>
            Digite o E-mail cadastrado no sistema para receber o código de
            redefinição de senha
          </span>
        </div>
        <div>
          <input type="email" placeholder="E-mail" required />
        </div>
        <div>
          <button type="submit" className="btn btn-sm btn-outline-secondary">
            Enviar
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

export default RecoverPasswordSendCodeEmail;
