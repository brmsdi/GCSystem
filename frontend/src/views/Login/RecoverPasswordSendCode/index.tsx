import { useNavigate } from "react-router-dom";
import { LOGIN_URL, RECOVER_PASSWORD_CHANGE_URL } from "utils/urls";

const RecoverPasswordSendCode = () => {

  let nav = useNavigate();

  function submit(event : any) {
    event.preventDefault();
    nav(LOGIN_URL + RECOVER_PASSWORD_CHANGE_URL);
  }
    return (
      <div className="content-login animate-down">
        <form id="form-send-code" onSubmit={submit}>
          <div>
            <h2>Recuperação de senha</h2>
            <span>
              Digite o código de verificação enviado para o seu E-mail. 
            </span>
          </div>
          <div>
            <input type="number" placeholder="Código de acesso" required/>
          </div>
          <div>
            <button type="submit" className="btn btn-sm btn-outline-secondary">
              Enviar
            </button>
          </div>
        </form>
      </div>
    );
  };
  
  export default RecoverPasswordSendCode;
  