import { useState } from "react";
import { useDispatch } from "react-redux";
import { paginationTableAction } from "store/Employees/Employees.actions";
import { PaginationTableAction, statePaginationEnum } from "types/Pagination";

const SearchEmployee = () => {
  const dispatch = useDispatch()
  const[CPF, setCPF] = useState('')
  const[paginationState, setPaginationState] = useState<PaginationTableAction>({
    type: statePaginationEnum.SETCURRENTPAGINATIONTABLEEMPLOYEES,
    currentPage: 1,
    search: undefined
  })

  async function changeCPFValue(CPF: string) {
    await setCPF(CPF)
    if (CPF.length === 11) {
      setPaginationState({
        ...paginationState,
        search: CPF.length === 11 ? CPF : undefined
      })

    } else if (CPF.length === 0) {
      dispatch(paginationTableAction({
        ...paginationState,
        search: undefined
      }))             
    }
  }
  
  function submit(event: any) {
    event.preventDefault();
    dispatch(paginationTableAction(paginationState))
  }
  return (
    <form onSubmit={submit}>
      <div className="div-search">
        <input
          type="number"
          id="inputCPFSearch"
          placeholder="CPF"
          name='cpf'
          value={CPF}
          onChange={(e) => changeCPFValue(e.target.value)}
        />
        <button type="submit" className="btn btn-secondary">
          <i className="bi bi-search"></i>
        </button>
      </div>
    </form>
  )
}

export default SearchEmployee;
