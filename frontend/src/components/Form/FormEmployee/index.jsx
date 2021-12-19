const FormEmployee = () => {
  return (
    <div>
    <form>
      <div className="form-row">
        <div className="form-group col-md-6">
          <label for="inpturName">Nome</label>
          <input
            type="text"
            className="form-control"
            id="inputName"
            placeholder="Nome completo"
          />
        </div>
        <div className="form-group col-md-3">
          <label for="inputRG">RG</label>
          <input
            type="text"
            className="form-control"
            id="inputRG"
            placeholder="RG"
          />
        </div>
        <div className="form-group col-md-3">
          <label for="inputCPF">CPF</label>
          <input
            type="number"
            className="form-control"
            id="inputCPF"
            placeholder="CPF"
          />
        </div>
        <div className="form-group col-md-3">
          <label for="inputBirth">Data de Nascimento</label>
          <input
            type="date"
            className="form-control"
            id="inputBirth"
          />
        </div>
      </div>
      <div className="form-group col-md-5">
        <label for="inputEmail">E-mail</label>
        <input
          type="email"
          className="form-control"
          id="inputEmail"
          placeholder="E-mail"
        />
      </div>
      <div className="form-row">
        <div className="form-group col-md-3">
          <label for="inputOffice">Cargo</label>
          <select id="inputOffice" className="form-control">
            <option>Adm</option>
            <option>Adm 2</option>
            <option>Adm 3</option>
          </select>
        </div>
        <div className="form-group col-md-3">
          <label for="inputSpecialtyy">Especialidade</label>
          <select id="inputSpecialtyy" className="form-control">
            <option>Eletricista</option>
          </select>
        </div>
      </div>
      <div className="form-row">
        <div className="form-group col-md-3">
        <label for="inputHiring">Data de contratação</label>
          <input
            type="date"
            className="form-control"
            id="inputHiring"
          />
        </div>
      </div>
      <div className="form-row">
        <button type="submit" className="btn btn-outline-primary">Salvar</button>
        <button type="button" className="btn btn-outline-secondary">Cancelar</button>
      </div>      
    </form>
    </div>
  );
};

export default FormEmployee;
