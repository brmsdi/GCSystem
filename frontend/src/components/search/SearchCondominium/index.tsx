import { useState } from "react";
import { useDispatch } from "react-redux";
import { paginationCondominiumTableAction } from "store/Condominiums/condiminiums.actions";
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";
import SearchItem from "../search-item";

const SearchCondominium = () => {
  const dispatch = useDispatch()
  const[name, setName] = useState('')
  const[paginationState, setPaginationState] = useState<PaginationTableAction>({
    type: StatePaginationEnum.SETCURRENTPAGINATIONTABLECONDOMINIUMS,
    currentPage: 1,
    search: undefined
  })

  async function changeSearchValue(name: string) {
    await setName(name)
    if (name.length > 0) {
      setPaginationState({
        ...paginationState,
        search: name
      })

    } else {
      dispatch(paginationCondominiumTableAction({
        ...paginationState,
        search: undefined
      }))             
    }
  }
  
  function submit() {
    dispatch(paginationCondominiumTableAction(paginationState));
  }
  return (
    <SearchItem typeValue="text" placeHolder="Buscar condomínio" value={name} submit={submit} changeSearchValue={changeSearchValue} />
    /*
    <form onSubmit={submit}>
      <div className="div-search">
        <input
          type="text"
          id="inputName"
          placeholder="Nome"
          name="name"
          value={name}
          onChange={(e) => changeSearchValue(e.target.value)}
          required
        />
        <button type="submit" className="btn btn-secondary">
          <i className="bi bi-search"></i>
        </button>
      </div>
    </form>
    */
  )
}

export default SearchCondominium;
