const FormEmployee = () => {
  return (
    <form>
      <div className="row-form-1">
        <div className="form-container l4">
          <label htmlFor="inpturName">Nome</label>
          <input
            type="text"
            id="inputName"
            placeholder="Nome completo"
          />
        </div>
        <div className="form-container l2">
          <label htmlFor="inputRG">RG</label>
          <input
            type="text"
            id="inputRG"
            placeholder="RG"
          />
        </div>
        <div className="form-container l2">
          <label htmlFor="inputCPF">CPF</label>
          <input
            type="number"
            id="inputCPF"
            placeholder="CPF"
          />
        </div>
        <div className="form-container l2">
          <label htmlFor="inputBirth">Nascimento</label>
          <input type="date" id="inputBirth" />
        </div>
      </div>
      <div className="row-form-1">
        <div className="form-container l4">
          <label htmlFor="inputEmail">E-mail</label>
          <input
            type="email"
            id="inputEmail"
            placeholder="E-mail"
          />
        </div>
        <div className="form-container l2">
          <label htmlFor="inputOffice">Cargo</label>
          <select id="inputOffice" >
            <option>Administrador</option>
            <option>Contador</option>
          </select>
        </div>
        <div className="form-container l2">
          <label htmlFor="inputSpecialty">Especialidade</label>
          <select id="inputSpecialty" >
            <option>Eletricista</option>
          </select>
        </div>
        <div className="form-container l2">
          <label htmlFor="inputHiring">Contratação</label>
          <input type="date" id="inputHiring" />
        </div>
      </div>
      <div className="row-form-1">
        <div className="form-container l2">
          <label htmlFor="inputPassword">Senha de acesso</label>
          <input
            type="password"
            className="form-control"
            id="inputPassword"
            placeholder="Senha"
          />
        </div>
      </div>
      <div className="row-form-1">
          <div className="form-container l4 btns">
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