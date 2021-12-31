import { useSelector } from "react-redux";
import { selectStateForm } from "store/Employees/Employees.selectors";

const FormEmployee = () => {

  var stateForm = useSelector(selectStateForm);

  
  return (
    <form>
      <div className="row">
        <div className="form-group col-md-6">
          <label htmlFor="inpturName">Nome</label>
          <input
            type="text"
            className="form-control"
            id="inputName"
            placeholder="Nome completo"
            disabled={!stateForm.active}
          />
        </div>
        <div className="form-group col-md-2">
          <label htmlFor="inputRG">RG</label>
          <input
            type="text"
            className="form-control"
            id="inputRG"
            placeholder="RG"
            disabled={!stateForm.active}
          />
        </div>
        <div className="form-group col-md-3">
          <label htmlFor="inputCPF">CPF</label>
          <input
            type="number"
            className="form-control"
            id="inputCPF"
            placeholder="CPF"
            disabled={!stateForm.active}
          />
        </div>
      </div>
      <div className="row">
        <div className="form-group col-md-2">
          <label htmlFor="inputBirth">Data de Nascimento</label>
          <input type="date" className="form-control" id="inputBirth" disabled={!stateForm.active}/>
        </div>
        <div className="form-group col-md-4">
          <label htmlFor="inputEmail">E-mail</label>
          <input
            type="email"
            className="form-control"
            id="inputEmail"
            placeholder="E-mail"
            disabled={!stateForm.active}
          />
        </div>
        <div className="form-group col-md-2">
          <label htmlFor="inputOffice">Cargo</label>
          <select id="inputOffice" className="form-control" disabled={!stateForm.active}>
            <option>Administrador</option>
            <option>Contador</option>
          </select>
        </div>
        <div className="form-group col-md-3">
          <label htmlFor="inputSpecialty">Especialidade</label>
          <select id="inputSpecialty" className="form-control" disabled={!stateForm.active}>
            <option></option>
            <option>Eletricista</option>
          </select>
        </div>
      </div>
      <div className="row">
        <div className="form-group col-md-2">
          <label htmlFor="inputHiring">Data de contratação</label>
          <input type="date" className="form-control" id="inputHiring" disabled={!stateForm.active} />
        </div>
        <div className="form-group col-md-3">
          <label htmlFor="inputPassword">Senha de acesso</label>
          <input
            type="password"
            className="form-control"
            id="inputPassword"
            placeholder="Senha"
            disabled={!stateForm.active}
          />
        </div>
      </div>
      <div className="row btns">
        <div className="form-group col-md-6">
          <button type="submit" className="btn btn-success">
            Salvar
          </button>
          <button type="button" className="btn btn-secondary">
            Cancelar
          </button>
        </div>
      </div>
    </form>
  );
};

export default FormEmployee;