import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { paginationLesseeTableAction } from "store/Lessees/lessees.actions";
import { selectCurrentPaginationTableLessees } from "store/Lessees/lessees.selector";
import Swal from "sweetalert2";
import { PaginationTableAction } from "types/pagination";
import { isValidFieldCPF } from "utils/verifications";
import SearchItem from "../search-item";

const SearchLessee = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableAction = useSelector(
    selectCurrentPaginationTableLessees
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
        paginationLesseeTableAction({
          ...paginationState,
          search: undefined,
        })
      );
    }
  }

  function submit() {
    if (isValidFieldCPF(CPF)) {
      dispatch(paginationLesseeTableAction(paginationState));
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

export default SearchLessee;
