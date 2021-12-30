const SearchEmployee = () => {
  return (
    <form>
      <div className="div-search">
        <input
          type="number"
          className="form-control"
          id="inputCPFSearch"
          placeholder="CPF"
        />
        <button type="submit" className="btn btn-secondary btn-lg active">
          <ion-icon name="search-outline"></ion-icon>
        </button>
      </div>
    </form>
  );
};

export default SearchEmployee;
