import { useState } from "react";
import { useDispatch } from "react-redux";
import { paginationDebtTableAction } from "store/Debts/debts.actions";
import Swal from "sweetalert2";
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";
import { isValidFieldCPF } from "utils/verifications";
import SearchItem from "../search-item";

const SearchDebts = () => {
  const dispatch = useDispatch()
  const[CPF, setCPF] = useState('')
  const[paginationState, setPaginationState] = useState<PaginationTableAction>({
    type: StatePaginationEnum.SETCURRENTPAGINATIONTABLEDEBTS,
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
      dispatch(paginationDebtTableAction({
        ...paginationState,
        search: undefined
      }))             
    }
  }
  
  function submit() {
    if (isValidFieldCPF(CPF))
    {
      dispatch(paginationDebtTableAction(paginationState))
    } else {
      Swal.fire('Oops!', 'Digite um CPF válido.', 'error')
      document.getElementById('inputCPFSearch')?.focus();
    }
  }
  return (
    <SearchItem typeValue="number" placeHolder="Digite o cpf" value={CPF} submit={submit} changeSearchValue={changeCPFValue} />
  )
}

export default SearchDebts;
