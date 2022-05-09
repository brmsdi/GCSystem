import { useState } from "react";
import { useDispatch } from "react-redux";
import { paginationOrderServiceTableAction } from "store/OrderServices/order-services.actions";
import Swal from "sweetalert2";
import { PaginationTableActionSearchPerNumber, StatePaginationEnum } from "types/pagination";
import { isValidFieldText } from "utils/verifications";
import SearchItem from "../search-item";

const SearchOrderService = () => {
  const dispatch = useDispatch()
  const[ID, setID] = useState('')
  const[paginationState, setPaginationState] = useState<PaginationTableActionSearchPerNumber>({
    type: StatePaginationEnum.SET_CURRENT_PAGINATION_TABLE_ORDER_SERVICES,
    currentPage: 1,
    search: undefined
  })

  async function changeIDValue(ID: number) {
    if (ID > 0) {
      setID('' + ID)
      setPaginationState({
        ...paginationState,
        search: ID 
      })

    } else {
      dispatch(paginationOrderServiceTableAction({
        ...paginationState,
        search: undefined
      }))             
    }
  }
  
  function submit() {
    if (isValidFieldText(ID))
    {
      dispatch(paginationOrderServiceTableAction(paginationState))
    } else {
      Swal.fire('Ooop!', 'Digite um ID válido.', 'error')
      document.getElementById('inputIDSearch')?.focus();
    }
  }
  return (
    <SearchItem typeValue="number" placeHolder="Digite o ID da OS" value={"" + ID} submit={submit} changeSearchValue={changeIDValue} />
  )
}

export default SearchOrderService;
