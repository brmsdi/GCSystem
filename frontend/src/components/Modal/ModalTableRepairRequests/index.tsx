import Alert from "components/messages";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  findAllPerOrderServiceAndStatus,
  findAllToModalOrderService,
} from "services/repair-request";
import { selectStateSelectedOrderService } from "store/OrderServices/order-services.selector";
import {
  changeStateModalOrderServiceRepairRequests,
  selectedRepairRequestsOrderServiceAction,
} from "store/RepairRequests/repair-requests.actions";
import { selectSelectedRepairRequestsOrderService } from "store/RepairRequests/repair-requests.selector";
import { OrderService } from "types/order-service";
import { RepairRequest } from "types/repair-request";
import { formatDateForView } from "utils/text-format";

const ModalTableRepairRequests = () => {
  const dispatch = useDispatch();
  const selectedRepairRequestsOld: RepairRequest[] = useSelector(
    selectSelectedRepairRequestsOrderService
  );
  const orderServiceSelected: OrderService = useSelector(
    selectStateSelectedOrderService
  );
  const [listRepairRequests, setListRepairRequests] = useState<RepairRequest[]>(
    []
  );
  const [selectedRepairRequests, setSelectedRepairRequests] = useState<
    RepairRequest[]
  >([...selectedRepairRequestsOld]);

  useEffect(() => {
    if (orderServiceSelected.id) {
      findAllPerOrderServiceAndStatus(orderServiceSelected.id).then(
        (response) => setListRepairRequests(response.data)
      );
    } else {
      findAllToModalOrderService().then((response) =>
        setListRepairRequests(response.data)
      );
    }
  }, [orderServiceSelected]);

  function addSelectedRepairRequests(_repairRequests: RepairRequest) {
    if (_repairRequests.id) {
      let currentSelectedRepairRequests: RepairRequest[] =
        selectedRepairRequests;
      currentSelectedRepairRequests.unshift(_repairRequests);
      setSelectedRepairRequests([...currentSelectedRepairRequests]);
    }
  }

  function removeSelectedRepairRequest(
    _currentSelectedRepairRequests: RepairRequest[],
    _index: number
  ) {
    _currentSelectedRepairRequests.splice(_index, 1);
    setSelectedRepairRequests([..._currentSelectedRepairRequests]);
  }

  function changeSelect(_item: RepairRequest) {
    if (_item.id) {
      let isRemove = false;
      let indexPosition = 0;
      let currentSelectedRepairRequests: RepairRequest[] =
        selectedRepairRequests;
      for (
        let index = 0;
        index < currentSelectedRepairRequests.length;
        index++
      ) {
        let itemIndex = currentSelectedRepairRequests.at(index);
        if (itemIndex && _item.id === itemIndex.id) {
          isRemove = true;
          indexPosition = index;
          break;
        }
      }

      if (isRemove === true) {
        removeSelectedRepairRequest(
          currentSelectedRepairRequests,
          indexPosition
        );
      } else {
        addSelectedRepairRequests(_item);
      }
    }
  }

  function isSelected(_repairRequests: RepairRequest) {
    let isSelected = false;
    for (let index = 0; index < selectedRepairRequests.length; index++) {
      let itemIndex = selectedRepairRequests.at(index);
      if (_repairRequests.id === itemIndex?.id) {
        isSelected = true;
        break;
      }
    }
    return isSelected;
  }

  function save() {
    dispatch(
      selectedRepairRequestsOrderServiceAction([...selectedRepairRequests])
    );
    dispatch(changeStateModalOrderServiceRepairRequests({ isOpen: false }));
  }

  function cancel() {
    //dispatch(selectedRepairRequestsOrderServiceAction(selectedRepairRequestsOld))
    dispatch(changeStateModalOrderServiceRepairRequests({ isOpen: false }));
  }

  return (
    <div className="table-responsive">
      {listRepairRequests.length === 0 ? (
        <Alert msg="Nenhum registro encontrado!" />
      ) : (
        <table className="table table-striped">
          <thead className="thead-max">
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Descrição</th>
              <th scope="col">Tipo</th>
              <th scope="col">Data</th>
              <th scope="col">Selecionar</th>
            </tr>
          </thead>
          <tbody>
            {listRepairRequests?.map((item: RepairRequest) => {
              return (
                <ItemTable
                  key={item.id}
                  item={item}
                  changeSelect={changeSelect}
                  isSelected={isSelected(item)}
                />
              );
            })}
          </tbody>
        </table>
      )}
      <section className="modal-section-comand">
        <button className="btn btn-success" onClick={() => save()}>
          Salvar
        </button>
        <button className="btn btn-secondary" onClick={() => cancel()}>
          cancelar
        </button>
      </section>
    </div> // end table-responsive
  );
};

interface IPropsItemTable {
  item: RepairRequest;
  isSelected: boolean;
  changeSelect: Function;
}

const ItemTable = (props: IPropsItemTable) => {
  let item = props.item;
  console.log(props.isSelected);
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td>{item.id}</td>
      <th className="thead-min">Descrição</th>
      <td>{item.problemDescription}</td>
      <th className="thead-min">Tipo</th>
      <td>{item.typeProblem.name}</td>
      <th className="thead-min">Data</th>
      <td>{formatDateForView(item.date)}</td>
      <th className="thead-min">Selecionar</th>
      <td>
        <div className="div-checkbox">
          <input
            type="checkbox"
            aria-label="Checkbox for following text input"
            onChange={() => props.changeSelect(item)}
            checked={props.isSelected}
          />
        </div>
      </td>
    </tr>
  );
};

export default ModalTableRepairRequests;
