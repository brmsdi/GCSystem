import { useState } from "react";
import { useDispatch } from "react-redux";
import { paginationContractTableAction } from "store/Contracts/contracts.actions";
import Swal from "sweetalert2";
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";
import { isValidFieldCPF } from "utils/verifications";

const SearchContracts = () => {
  const dispatch = useDispatch()
  const[CPF, setCPF] = useState('')
  const[paginationState, setPaginationState] = useState<PaginationTableAction>({
    type: StatePaginationEnum.SETCURRENTPAGINATIONTABLECONTRACTS,
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
      dispatch(paginationContractTableAction({
        ...paginationState,
        search: undefined
      }))             
    }
  }
  
  function submit(event: any) {
    event.preventDefault();
    if (isValidFieldCPF(CPF))
    {
      dispatch(paginationContractTableAction(paginationState))
    } else {
      Swal.fire('Ooop!', 'Digite um CPF válido.', 'error')
      document.getElementById('inputCPFSearch')?.focus();
    }
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
          required
        />
        <button type="submit" className="btn btn-secondary">
          <i className="bi bi-search"></i>
        </button>
      </div>
    </form>
  )
}

export default SearchContracts;
