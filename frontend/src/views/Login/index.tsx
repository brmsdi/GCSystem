import { useState } from "react";
import { useDispatch } from "react-redux";
import { Link, Outlet, useNavigate } from "react-router-dom";
import { autheticate, clearToken, setToken } from "services/Authentication";
import insertRequestCodeInfo from "store/Authentication/Authentication.actions";
import Swal from "sweetalert2";
import { AuthCpfAndPassword, stateAuthenticationChange } from "types/Login";
import { clearAuth, setAuthorization } from "utils/http";
import { EMPLOYEES_HOME_URL, RECOVER_PASSWORD_URL } from "utils/urls";

const Login = () => {
  let nav = useNavigate();
  const dispatch = useDispatch();
  clearAuth();
  clearToken();
  dispatch(insertRequestCodeInfo(stateAuthenticationChange.INSERTINFO, {}));
  const[auth, setAuth] = useState<AuthCpfAndPassword>({
    cpf: "",
    password: "",
  })
  function changeInput(value: any) {
    setAuth((auth) => ({ ...auth, ...value }));
  }
  
  async function submit(event: any) {
    event.preventDefault();
    try {
      const result = await autheticate(auth);
      await setToken(result);
      await setAuthorization(result)
      nav(EMPLOYEES_HOME_URL)
    } catch (error: any) {
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
      } else if (
        error.response.status === 401 ||
        error.response.status === 403
      ) {
        Swal.fire("oops!", "Cpf ou senha inválidos", "error");
      } else {
        Swal.fire("oops!", "" + error.response.status, "error");
      }
    }
  }
  return (
    <div className="content-login animate-down">
      <form onSubmit={submit}>
        <div>
          <h2>System</h2>
          <span>Autenticar-se no sistema</span>
        </div>
        <div>
          <input
            type="number"
            placeholder="CPF"
            id="cpf"
            name="cpf"
            value={auth.cpf}
            onChange={(e) => changeInput({ cpf: e.target.value })}
            required
          />
        </div>
        <div>
          <input
            id="senha"
            name="password"
            type="password"
            placeholder="SENHA"
            value={auth.password}
            onChange={(e) => changeInput({ password: e.target.value })}
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
