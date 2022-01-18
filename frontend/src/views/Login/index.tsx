import Alert from "components/messages";
import { useState } from "react";
import { Link, Outlet, useNavigate} from "react-router-dom";
import { RECOVER_PASSWORD_URL } from "utils/urls";

const Login = () => {

  let nav = useNavigate();
  
  const[cpf, setCPF] = useState('');
  function changeCPFInput(value : string) {
    if(value.length <= 11 ) {
        setCPF(value)
    }
  }

  function submit(event : any)  {
    event.preventDefault();
    nav('/');
  }
  return (
    <div className="content-login animate-down">
      <form onSubmit={submit}>
        <div>
          <h2>System</h2>
          <span>Autenticar-se no sistema</span>
        </div>
        <Alert msg="CPF ou senha inválida" />
        <div>
          <input
            type="number"
            placeholder="CPF"
            id="cpf"
            name="cpf"
            value={cpf}
            onChange={(e) => changeCPFInput(e.target.value)}
            required
          />
        </div>
        <div>
          <input
            id="senha"
            name="senha"
            type="password"
            placeholder="SENHA"
            required
          />
        </div>
        <div id="password-forgot-it">
          <Link to={RECOVER_PASSWORD_URL}>Esqueci minha senha</Link>
        </div>
        <div>
          <button type="submit" className="btn btn-sm btn-outline-secondary">
            <i data-feather="log-in"></i> ENTRAR
          </button>
        </div>
      </form>
      <Outlet />
      </div>
  )
}
export default Login;
