const SearchEmployee = () => {
  return (
      <div>
    <form>
      <div className="form-row">
        <div className="form-group col-md-3">
          <label for="inputCPF">Buscar</label>
          <input
            type="number"
            className="form-control"
            id="inputCPF"
            placeholder="CPF"
          />
        </div>
        <button type="button" className="btn btn-outline-secondary">GET</button>
      </div>
    </form>
    </div>
  );
};

export default SearchEmployee;
