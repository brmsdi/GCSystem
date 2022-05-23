import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { paginationCondominiumTableAction } from "store/Condominiums/condiminiums.actions";
import { selectCurrentPaginationTableCondominiums } from "store/Condominiums/condiminiums.selectors";
import { PaginationTableAction } from "types/pagination";
import SearchItem from "../search-item";

const SearchCondominium = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableAction = useSelector(
    selectCurrentPaginationTableCondominiums
  );

  const [paginationState, setPaginationState] = useState<PaginationTableAction>(
    { ...currentPaginationTable }
  );

  const [name, setName] = useState(
    paginationState.search ? paginationState.search : ""
  );

  async function changeSearchValue(name: string) {
    await setName(name);
    if (name.length > 0) {
      setPaginationState({
        ...paginationState,
        search: name,
      });
    } else {
      dispatch(
        paginationCondominiumTableAction({
          ...paginationState,
          search: undefined,
        })
      );
    }
  }

  function submit() {
    dispatch(paginationCondominiumTableAction(paginationState));
  }
  return (
    <SearchItem
      typeValue="text"
      placeHolder="Buscar condomínio"
      value={name}
      submit={submit}
      changeSearchValue={changeSearchValue}
    />
  );
};

export default SearchCondominium;
