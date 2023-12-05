import PageMessage from "components/Loader/PageLoading";
import { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { requestCode } from "services/authentication";
import insertRequestCodeInfo from "store/Authentication/login.actions";
import Swal from "sweetalert2";
import { EmailRequestCode, StateAuthenticationChange } from "types/authentication-types";
import { URISRequestTypes } from "types/request-uri-change-password";
import { LOGIN_URL, RECOVER_PASSWORD_SEND_CODE_URL } from "utils/urls";

const RecoverPasswordSendCodeEmail = () => {
  let nav = useNavigate();
  const dispatch = useDispatch();
  const [sending, setSending] = useState(false);
  const [form, setForm] = useState<EmailRequestCode>({
    email: '',
    requestType: URISRequestTypes[0]
  });
  const [selectedRequestTypeID, setSelectedRequestTypeID] = useState<number>(URISRequestTypes[0].id);

  function changeForm(value: any) {
    setForm((form) => ({ ...form, ...value }))
  }

  async function submit(event: any) {
    event.preventDefault();
    try {
      setSending(true)
      let requestType = URISRequestTypes.find(item  => item.id === selectedRequestTypeID)
      if (requestType === undefined) {
        setSending(false)
        return
      }
      await requestCode(requestType.requestCodeURI, form)
      form.state = StateAuthenticationChange.WAITINGCODE;
      form.requestType = requestType
      dispatch(insertRequestCodeInfo(StateAuthenticationChange.INSERTINFO, form));
      nav(LOGIN_URL + RECOVER_PASSWORD_SEND_CODE_URL);
    }
    catch (error: any) {
      setSending(false)
      if (error.response) {
        const errors = error.response.data.errors;
        Swal.fire('oops!', errors[0].message, 'error')
      } else {
        Swal.fire('Oops!', 'Sem conexão com o servidor!', 'error');
      }
    }
  }
  if (sending) return <PageMessage title="Enviando código para o E-mail" />
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
            onChange={(e) => changeForm({ email: e.target.value })}
            required />
        </div>
        <div>
          <span>Tipo de usuário</span>
        </div>
        <div>
          <select onChange={(e) => setSelectedRequestTypeID(Number.parseInt(e.target.value))} >
            {
              URISRequestTypes.map(item => (
                <option
                key={item.id}
                value={item.id}>{item.name}</option>
              ))
            }
          </select>
        </div>
        <div>
          <button type="submit" className="btn btn-sm btn-outline-secondary">
            Enviar
          </button>
        </div>
        <div>
          <button className="btn btn-sm btn-outline-secondary" 
          onClick={() => nav(-1)} >
            <i data-feather="log-in"></i> Cancelar
          </button>
        </div>
      </form>
    </div>
  );
};

export default RecoverPasswordSendCodeEmail;
