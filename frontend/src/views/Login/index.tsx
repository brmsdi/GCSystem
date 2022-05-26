import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { Link, Outlet, useNavigate } from "react-router-dom";
import insertRequestCodeInfo, { setUserAuthenticated } from "store/Authentication/authentication.actions";
import Swal from "sweetalert2";
import { AuthCpfAndPassword, StateAuthenticationChange, UserAuthenticatedViewTypesEnum } from "types/authentication-types";
import { clearAuth, setAuthorization } from "utils/http";
import { HOME_URL, RECOVER_PASSWORD_URL } from "utils/urls";
import { autheticate, clearToken, setToken } from "services/authentication";
import PageMessage from "components/Loader/PageLoading";

const Login = () => {
  let nav = useNavigate();
  const dispatch = useDispatch();
  clearAuth();
  clearToken();
  useEffect(() => {
    dispatch(setUserAuthenticated(UserAuthenticatedViewTypesEnum.UPDATE, { name: '', role: '' }))
    dispatch(insertRequestCodeInfo(StateAuthenticationChange.INSERTINFO, {}))
  }, [dispatch])
  const [auth, setAuth] = useState<AuthCpfAndPassword>({
    cpf: "",
    password: "",
  })
  const [isLoading, setIsLoading] = useState(false);

  function changeInput(value: any) {
    setAuth((auth) => ({ ...auth, ...value }));
  }

  async function submit(event: any) {
    event.preventDefault();
    try {
      setIsLoading(true)
      const result = await autheticate(auth);
      setIsLoading(false)
      setToken(result);
      setAuthorization(result)
      nav(HOME_URL)
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
      setIsLoading(false)
    }
  }
  return isLoading === true ? (
    <PageMessage title="Autenticando" />
  )
  : 
  (
    <div className="content-login animate-down">
      <form onSubmit={submit}>
        <div>
          <h2>GC</h2>
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
          <button 
          type="submit" 
          className="btn btn-sm btn-outline-secondary">
            <i data-feather="log-in"></i> ENTRAR
          </button>
        </div>
      </form>
      <Outlet />
    </div>
  )
}
export default Login;
