import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { validateCode } from "services/Authentication";
import insertRequestCodeInfo from "store/Authentication/Authentication.actions";
import { selectStateChangePassword } from "store/Authentication/Authentication.selectors";
import Swal from "sweetalert2";
import { EmailRequestCode, stateAuthenticationChange } from "types/Login";
import { LOGIN_URL, RECOVER_PASSWORD_CHANGE_URL } from "utils/urls";

const RecoverPasswordSendCode = () => {
  let nav = useNavigate();
  const dispatch = useDispatch();
  const stateChangePassword: EmailRequestCode = useSelector(selectStateChangePassword);
  if (!(stateChangePassword.state === stateAuthenticationChange.WAITINGCODE)) {
    nav(LOGIN_URL)
  }
  const[form, setForm] = useState<EmailRequestCode>({
    ...stateChangePassword,
    code: ''
  });
  function changeCode(value: any) {
    setForm(form => ({...form, ...value}))
  }
/*
  function submit(event : any) {
    event.preventDefault();
    validateCode(form)
    .then(response => {
      let newForm: EmailRequestCode = {
        ...form,
        state: stateAuthenticationChange.CHANGINGPASSWORD,
        token: {
          type: response.data.type,
          token: response.data.token
        }
      }

      dispatch(insertRequestCodeInfo(stateAuthenticationChange.INSERTINFO, newForm))
      nav(LOGIN_URL + RECOVER_PASSWORD_CHANGE_URL);
    })
    .catch(error => {
      if (error.response) {
        let message = error.response.data.errors[0].message;
        Swal.fire('Oops!', '' + message, 'error')
      } else {
        Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
      }
    })
  }
  */

  async function submit(event : any) {
    event.preventDefault();
    try {
      
      let data = validateCode(form)
      
      let newForm: EmailRequestCode = {
        ...form,
        state: stateAuthenticationChange.CHANGINGPASSWORD,
        token: {
          type: data.type,
          token: data.token
        }
      }

      dispatch(insertRequestCodeInfo(stateAuthenticationChange.INSERTINFO, newForm))
      nav(LOGIN_URL + RECOVER_PASSWORD_CHANGE_URL);
    }
    catch(error : any) {
      if (error.response) {
        let message = error.response.data.errors[0].message;
        Swal.fire('Oops!', '' + message, 'error')
      } else {
        Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
      }
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
            <input 
            type="number" 
            name="code" 
            value={form.code} 
            placeholder="Código de acesso"
            onChange={(e) => changeCode({code: e.target.value}) } 
            required/>
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
  