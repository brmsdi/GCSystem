import { useState } from "react";
import { useDispatch } from "react-redux";
import { paginationRepairRequestTableAction } from "store/RepairRequests/repair-requests.actions";
import Swal from "sweetalert2";
import { PaginationTableAction, StatePaginationEnum } from "types/pagination";
import { isValidFieldCPF } from "utils/verifications";
import SearchItem from "../search-item";

const SearchRepairRequest = () => {
  const dispatch = useDispatch()
  const[CPF, setCPF] = useState('')
  const[paginationState, setPaginationState] = useState<PaginationTableAction>({
    type: StatePaginationEnum.SET_CURRENT_PAGINATION_TABLE_REPAIR_REQUESTS,
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
      dispatch(paginationRepairRequestTableAction({
        ...paginationState,
        search: undefined
      }))             
    }
  }
  
  function submit() {
    if (isValidFieldCPF(CPF))
    {
      dispatch(paginationRepairRequestTableAction(paginationState))
    } else {
      Swal.fire('Ooop!', 'Digite um CPF válido.', 'error')
      document.getElementById('inputCPFSearch')?.focus();
    }
  }
  return (
    <SearchItem typeValue="number" placeHolder="Digite o cpf" value={CPF} submit={submit} changeSearchValue={changeCPFValue} />
  )
}

export default SearchRepairRequest;
