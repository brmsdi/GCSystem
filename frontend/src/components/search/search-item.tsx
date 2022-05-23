interface IProps {
  value: string;
  submit: Function;
  changeSearchValue: Function;
  placeHolder: string;
  typeValue: string;
}

const SearchItem = (props: IProps) => {
  function submit(event: any) {
    event.preventDefault();
    props.submit();
  }

  return (
    <form onSubmit={submit}>
      <div className="div-search">
        <input
          id="input-search"
          type={props.typeValue}
          placeholder={props.placeHolder}
          value={props.value}
          onChange={(e) => props.changeSearchValue(e.target.value)}
          required
        />
        <button
          id="btn-search-value"
          type="submit"
          aria-label="Buscar registro"
          title="Buscar registro"
          className="btn btn-secondary"
        >
          <i className="bi bi-search"></i>
        </button>
      </div>
    </form>
  );
};

export default SearchItem;
