import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { paginationOrderServiceTableAction } from "store/OrderServices/order-services.actions";
import { selectCurrentPaginationTableOrderServices } from "store/OrderServices/order-services.selector";
import Swal from "sweetalert2";
import {
  PaginationTableActionSearchPerNumber
} from "types/pagination";
import { isValidFieldText } from "utils/verifications";
import SearchItem from "../search-item";

const SearchOrderService = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableActionSearchPerNumber = useSelector(
    selectCurrentPaginationTableOrderServices
  );
  const [paginationState, setPaginationState] =
    useState<PaginationTableActionSearchPerNumber>({...currentPaginationTable});

    const [ID, setID] = useState(
      paginationState.search ? "" + paginationState.search : ""
    );

  async function changeIDValue(ID: number) {
    if (ID > 0) {
      setID("" + ID);
      setPaginationState({
        ...paginationState,
        search: ID,
      });
    } else {
      setID("" + ID);
      dispatch(
        paginationOrderServiceTableAction({
          ...paginationState,
          search: undefined,
        })
      );
    }
  }

  function submit() {
    if (isValidFieldText(ID)) {
      dispatch(paginationOrderServiceTableAction(paginationState));
    } else {
      Swal.fire("Ooop!", "Digite um ID válido.", "error");
      document.getElementById("inputIDSearch")?.focus();
    }
  }
  return (
    <SearchItem
      typeValue="number"
      placeHolder="Digite o ID da OS"
      value={"" + ID}
      submit={submit}
      changeSearchValue={changeIDValue}
    />
  );
};

export default SearchOrderService;
