import Alert from "components/messages";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { findAllToModalOrderService } from "services/employee";
import { changeStateModalOrderServiceEmployees, selectedEmployeesOrderServiceAction } from "store/OrderServices/order-services.actions";
import { selectSelectedEmployeesOrderService } from "store/OrderServices/order-services.selector";
import { Employee } from "types/employee";

const ModalTableEmployees = () => {
  const dispatch = useDispatch()
  const selectedEmployeesOld : Employee[] = useSelector(selectSelectedEmployeesOrderService)
  const [listEmployees, setListEmployees] = useState<Employee[]>([]);
  const [selectedEmployees, setSelectedEmployees] = useState<Employee[]>([...selectedEmployeesOld]);

  useEffect(() => {
    findAllToModalOrderService().then(response => setListEmployees(response.data))
  }, [])

  function addSelectedEmployee(_employee: Employee) {
    if (_employee.id) {
      let currentSelectedEmployees : Employee[] = selectedEmployees;
      currentSelectedEmployees.unshift(_employee)
      setSelectedEmployees([...currentSelectedEmployees])
    }
  }

  function removeSelectedEmployee(_currentSelectedEmployees: Employee[], _index: number) {
    _currentSelectedEmployees.splice(_index, 1)
    setSelectedEmployees([..._currentSelectedEmployees])
  }

  function changeSelect(_item: Employee) {
    if (_item.id) {
      let isRemove = false;
      let indexPosition = 0;
      let currentSelectedEmployees: Employee[] = selectedEmployees;
      for (let index = 0; index < currentSelectedEmployees.length; index++) {
        let itemIndex = currentSelectedEmployees.at(index)
        if (itemIndex && _item.id === itemIndex.id) {
          isRemove = true;
          indexPosition = index;
          break;
        }
      }

      if (isRemove === true) {
        removeSelectedEmployee(currentSelectedEmployees, indexPosition)
      } else {
        addSelectedEmployee(_item)
      }
    }
  }

  function isSelected(_employee: Employee) {
    let isSelected = false;
    for (let index = 0; index < selectedEmployees.length; index++) {
      let itemIndex = selectedEmployees.at(index)
      if (_employee.id === itemIndex?.id) {
        isSelected = true;
        break
      }
    }
    return isSelected
  }

  function save() {
    dispatch(selectedEmployeesOrderServiceAction([...selectedEmployees]))
    dispatch(changeStateModalOrderServiceEmployees({isOpen: false}))
  }

  function cancel() {
    //dispatch(selectedEmployeesOrderServiceAction(selectedEmployeesOld))
    dispatch(changeStateModalOrderServiceEmployees({isOpen: false}))
  }
  
  return (
    <div className="table-responsive">
      {listEmployees.length === 0 ? (
        <Alert msg="Nenhum registro encontrado!" />
      ) : (
        <table className="table table-striped">
          <thead className="thead-max">
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Nome</th>
              <th scope="col">CPF</th>
              <th scope="col">Cargo</th>
              <th scope="col">Selecionar</th>
            </tr>
          </thead>
          <tbody>
            {listEmployees?.map((item: Employee) => {
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
}

interface IPropsItemTable {
  item: Employee;
  isSelected: boolean;
  changeSelect: Function;
}

const ItemTable = (props: IPropsItemTable) => {
  let item = props.item;
  console.log(props.isSelected)
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td>{item.id}</td>
      <th className="thead-min">Nome</th>
      <td>{item.name}</td>
      <th className="thead-min">CPF</th>
      <td>{item.cpf}</td>
      <th className="thead-min">Cargo</th>
      <td>{item.role.name}</td>
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
}

export default ModalTableEmployees;