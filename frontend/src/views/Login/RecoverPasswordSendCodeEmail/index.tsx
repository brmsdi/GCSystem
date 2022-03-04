import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { EmailRequestCode } from "types/Login";
import { REQUEST_LOGIN } from "utils/requests";
import { LOGIN_URL, RECOVER_PASSWORD_SEND_CODE_URL } from "utils/urls";

const RecoverPasswordSendCodeEmail = () => {
  let nav = useNavigate();

  const[form, setForm] = useState<EmailRequestCode>({
    email: '',
    type: 0
  });

  function changeForm(value : any) {
    setForm((form) => ({...form, ...value}))
    console.log(form)
  }

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
          <input type="email" 
          name="email" 
          value={form.email} 
          placeholder="E-mail" 
          onChange={(e) => changeForm({email: e.target.value})} 
          required />
        </div>
        <div>
          <span>Tipo de usuário</span>
        </div>
        <div>
          <select onChange={(e) => changeForm({type: e.target.value})} >
            <option value={0}>Funcionário</option>
            <option value={1}>Locatário</option>
          </select>
        </div>
        <div>
          <button type="submit" className="btn btn-sm btn-outline-secondary">
            Enviar
          </button>
        </div>
        <div>
          <Link to={REQUEST_LOGIN} className="btn btn-sm btn-outline-secondary">
            <i data-feather="log-in"></i> Cancelar
          </Link>
        </div>
      </form>
    </div>
  );
};

export default RecoverPasswordSendCodeEmail;
