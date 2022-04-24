import PageMessage from "components/Loader/PageLoading";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { validateCode } from "services/authentication";
import insertRequestCodeInfo from "store/Authentication/authentication.actions";
import { selectStateChangePassword } from "store/Authentication/authentication.selectors";
import Swal from "sweetalert2";
import { EmailRequestCode, StateAuthenticationChange } from "types/authentication-types";
import { LOGIN_URL, RECOVER_PASSWORD_CHANGE_URL } from "utils/urls";

const RecoverPasswordSendCode = () => {
  let nav = useNavigate();
  const dispatch = useDispatch();
  const stateChangePassword: EmailRequestCode = useSelector(selectStateChangePassword);
  const [sending, setSending] = useState(false);
  const [form, setForm] = useState<EmailRequestCode>({
    code: ''
  })
  function changeCode(value: any) {
    setForm(form => ({ ...form, ...value }))
  }

  async function submit(event: any) {
    event.preventDefault();
    try {
      setSending(true)
      await verifyCurrentState();
      let data = await validateCode({ ...stateChangePassword, ...form })
      let newForm: EmailRequestCode = {
        ...stateChangePassword,
        ...form,
        state: StateAuthenticationChange.CHANGINGPASSWORD,
        token: {
          type: data.type,
          token: data.token
        }
      }
      dispatch(insertRequestCodeInfo(StateAuthenticationChange.INSERTINFO, newForm))
      nav(LOGIN_URL + RECOVER_PASSWORD_CHANGE_URL);
    }
    catch (error: any) {
      setSending(false)
      if (error.response) {
        let data = error.response.data;
        if (data.error) {
          Swal.fire('Oops!', '' + data.error, 'error')
        } else if (error.response.data.errors[0]) {
          let message = error.response.data.errors[0].message;
          Swal.fire('Oops!', '' + message, 'error')
        } else {
          Swal.fire('Oops!', 'Erro desconhecido!', 'error')
        }
      } else {
        Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
      }
    }
  }

  async function verifyCurrentState() {
    if (!(stateChangePassword.state === StateAuthenticationChange.WAITINGCODE)) {
      nav(LOGIN_URL)
    }
  }

  if (sending) return <PageMessage title="Validando código" />

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
            onChange={(e) => changeCode({ code: e.target.value })}
            required />
        </div>
        <div>
          <button type="submit" className="btn btn-sm btn-outline-secondary">
            Enviar
          </button>
        </div>
      </form>
    </div>
  )
}

export default RecoverPasswordSendCode;