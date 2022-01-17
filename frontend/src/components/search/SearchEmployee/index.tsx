const SearchEmployee = () => {
  return (
    <form>
      <div className="div-search">
        <input
          type="number"
          id="inputCPFSearch"
          placeholder="CPF"
        />
        <button type="submit" className="btn btn-secondary">
          <i className="bi bi-search"></i>
        </button>
      </div>
    </form>
  );
};

export default SearchEmployee;
