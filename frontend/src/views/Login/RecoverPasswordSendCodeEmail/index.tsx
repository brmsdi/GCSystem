import { useState } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { requestCode } from "services/Authentication";
import insertRequestCodeInfo from "store/Authentication/Authentication.actions";
import Swal from "sweetalert2";
import { EmailRequestCode, StateAuthenticationChange } from "types/AuthenticationTypes";
import { REQUEST_LOGIN } from "utils/requests";
import { LOGIN_URL, RECOVER_PASSWORD_SEND_CODE_URL } from "utils/urls";

const RecoverPasswordSendCodeEmail = () => {
  let nav = useNavigate();
  const dispatch = useDispatch();

  const[form, setForm] = useState<EmailRequestCode>({
    email: '',
    type: 0
  });

  function changeForm(value : any) {
    setForm((form) => ({...form, ...value}))
  }
/*
  function submit(event : any) {
    event.preventDefault();
    requestCode(form)
    .then(() => {
      form.state = stateAuthenticationChange.WAITINGCODE; 
      dispatch(insertRequestCodeInfo(stateAuthenticationChange.INSERTINFO, form));
      nav(LOGIN_URL + RECOVER_PASSWORD_SEND_CODE_URL);
    })
    .catch(error => {
      if (error.response) {
        const errors = error.response.data.errors;
        Swal.fire('oops!', errors[0].message, 'error')
      } else {
        Swal.fire("oops!", "Sem conexão com o servidor!", "error");
      }
    })
  }
*/

async function submit(event : any) {
  event.preventDefault();
  try {
    form.state = StateAuthenticationChange.WAITINGCODE; 
    await requestCode(form)
    dispatch(insertRequestCodeInfo(StateAuthenticationChange.INSERTINFO, form));
    nav(LOGIN_URL + RECOVER_PASSWORD_SEND_CODE_URL);
  }
  catch(error : any) {
    if (error.response) {
      const errors = error.response.data.errors;
      Swal.fire('oops!', errors[0].message, 'error')
    } else {
      Swal.fire("oops!", "Sem conexão com o servidor!", "error");
    }
  }
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
