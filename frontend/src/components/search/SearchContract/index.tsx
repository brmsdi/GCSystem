import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { paginationContractTableAction } from "store/Contracts/contracts.actions";
import { selectCurrentPaginationTableContracts } from "store/Contracts/contracts.selector";
import Swal from "sweetalert2";
import { PaginationTableAction } from "types/pagination";
import { isValidFieldCPF } from "utils/verifications";
import SearchItem from "../search-item";

const SearchContracts = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableAction = useSelector(
    selectCurrentPaginationTableContracts
  );
  
  const [paginationState, setPaginationState] = useState<PaginationTableAction>(
    { ...currentPaginationTable }
  );

  const [CPF, setCPF] = useState(
    paginationState.search ? paginationState.search : ""
  );

  async function changeCPFValue(CPF: string) {
    await setCPF(CPF);
    if (CPF.length === 11) {
      setPaginationState({
        ...paginationState,
        search: CPF.length === 11 ? CPF : undefined,
      });
    } else if (CPF.length === 0) {
      dispatch(
        paginationContractTableAction({
          ...paginationState,
          search: undefined,
        })
      );
    }
  }

  function submit() {
    if (isValidFieldCPF(CPF)) {
      dispatch(paginationContractTableAction(paginationState));
    } else {
      Swal.fire("Ooop!", "Digite um CPF válido.", "error");
      document.getElementById("inputCPFSearch")?.focus();
    }
  }
  return (
    <SearchItem
      typeValue="number"
      placeHolder="Digite o cpf"
      value={CPF}
      submit={submit}
      changeSearchValue={changeCPFValue}
    />
  );
};

export default SearchContracts;
