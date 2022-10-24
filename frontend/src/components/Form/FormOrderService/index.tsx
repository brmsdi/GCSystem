import ModalSelectEmployees from "components/Modal/ModalSelectEmployees";
import ModalSelectRepairRequests from "components/Modal/ModalSelectRepairRequests";
import TableOrderServiceEmployees from "components/Table/TableOrderServiceEmployees";
import TableOrderServiceRepairRequest from "components/Table/TableOrderServiceRepairRequest";
import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getAllStatusFromViewOrderService } from "services/status";
import {
  changeStateModalOrderServiceEmployees,
  selectedEmployeesOrderServiceAction,
} from "store/OrderServices/order-services.actions";
import {
  selectSelectedEmployeesOrderService,
  selectStateModalOrderServiceEmployees,
} from "store/OrderServices/order-services.selector";
import {
  changeStateModalOrderServiceRepairRequests,
  selectedRepairRequestsOrderServiceAction,
} from "store/RepairRequests/repair-requests.actions";
import {
  selectSelectedRepairRequestsOrderService,
  selectStateModalOrderServiceRepairRequests,
} from "store/RepairRequests/repair-requests.selector";
import Swal from "sweetalert2";
import { ModalStateInformations, StateFormEnum } from "types/action";
import { Employee } from "types/employee";
import { OrderService } from "types/order-service";
import { RepairRequest } from "types/repair-request";
import { Status } from "types/status";
import { formatDate } from "utils/text-format";
import { isValidFieldText, isSelected, isEmpty } from "utils/verifications";

interface IProps {
  initForm: OrderService;
  stateForm: StateFormEnum;
  submit: Function;
  isNewRegisterForm: boolean;
}

const FormTemplate = (props: IProps) => {
  const dispatch = useDispatch();
  const [form, setForm] = useState<OrderService>(props.initForm);
  const [status, setStatus] = useState<Status[]>([]);
  const selectedRepairRequests: RepairRequest[] = useSelector(
    selectSelectedRepairRequestsOrderService
  );
  const selectedEmployees: Employee[] = useSelector(
    selectSelectedEmployeesOrderService
  );
  const stateModalOrderServiceEmployees: ModalStateInformations = useSelector(
    selectStateModalOrderServiceEmployees
  );
  const stateModalOrderServiceRepairRequests: ModalStateInformations =
    useSelector(selectStateModalOrderServiceRepairRequests);

  function changeInput(value: any) {
    setForm((form) => ({ ...form, ...value }));
  }

  function changeStatus(value: number) {
    for (var index = 0; index < status.length; index++) {
      let statusSelected = status.at(index);
      if (statusSelected?.id === value) {
        setForm({ ...form, status: statusSelected });
        break;
      }
    }
  }

  useEffect(() => {
    setForm((form) => ({ ...form, ...props.initForm }));
    getAllStatusFromViewOrderService().then((response) =>
      setStatus(response.data)
    );
    dispatch(
      selectedRepairRequestsOrderServiceAction(props.initForm.repairRequests)
    );
    dispatch(selectedEmployeesOrderServiceAction(props.initForm.employees));
  }, [props.initForm, dispatch]);

  useEffect(() => {
    checkLegend(
      "fieldset-order-service",
      isValidFieldText(form.generationDate) &&
        isValidFieldText(form.reservedDate) &&
        isSelected(form.status)
    );

    checkLegend("fieldset-repair-request", selectedRepairRequests.length > 0);
    checkLegend("fieldset-employees", selectedEmployees.length > 0);
  }, [form, selectedRepairRequests, selectedEmployees]);

  async function submit(event: any) {
    event.preventDefault();

    if (!isSelected(form.status)) {
      Swal.fire("Oops!", "Selecione o campo status", "error");
      return;
    }

    if (isEmpty(selectedRepairRequests)) {
      Swal.fire(
        "Oops!",
        "Adicione solicitações de reparo a essa ordem de serviço",
        "error"
      );
      return;
    }

    if (isEmpty(selectedEmployees)) {
      Swal.fire(
        "Oops!",
        "Adicione funcionários a essa ordem de serviço. Eles serão responsáveis por executar os reparos.",
        "error"
      );
      return;
    }

    let newForm = {
      ...form,
      repairRequests: selectedRepairRequests,
      employees: selectedEmployees,
    };

    setForm({ ...newForm });
    const result = await props.submit(newForm);
    if (result === true) {
      if (props.isNewRegisterForm === true) {
        clearForm();
        clearSelectedRepairRequests();
        clearSelectedEmployees();
      } else {
        setForm({ ...form });
      }
    }
  }

  function clearForm() {
    setForm({ ...props.initForm });
  }

  function clearSelectedRepairRequests() {
    dispatch(
      selectedRepairRequestsOrderServiceAction([
        ...props.initForm.repairRequests,
      ])
    );
  }

  function clearSelectedEmployees() {
    dispatch(
      selectedEmployeesOrderServiceAction([...props.initForm.employees])
    );
  }

  function checkLegend(ID: string, active: boolean) {
    const component = document.getElementById(ID);
    if (active) {
      component?.classList.add("fieldset-ok");
    } else {
      component?.classList.remove("fieldset-ok");
    }
  }

  function changeModalSelectRepairRequests() {
    dispatch(
      changeStateModalOrderServiceRepairRequests({
        isOpen: !stateModalOrderServiceRepairRequests.isOpen,
      })
    );
  }

  function changeModalSelectEmployees() {
    dispatch(
      changeStateModalOrderServiceEmployees({
        isOpen: !stateModalOrderServiceEmployees.isOpen,
      })
    );
  }

  return (
    <>
      {
        <ModalSelectRepairRequests
          title={"Reparos"}
          modalIsOpen={stateModalOrderServiceRepairRequests.isOpen}
        />
      }

      {
        <ModalSelectEmployees
          title={"Funcionários"}
          modalIsOpen={stateModalOrderServiceEmployees.isOpen}
        />
      }

      <form onSubmit={submit}>
        <fieldset id="fieldset-order-service">
          <legend>
            <i className="bi bi-file-earmark-zip legend-icon"></i> Reparo
          </legend>
          <hr />
          <div className="row-form-1">
            <div className="form-container f4">
              <label htmlFor="inputGenerationDate">Data</label>
              <input
                type="date"
                id="inputGenerationDate"
                name="generationDate"
                value={
                  form.generationDate.length > 0
                    ? formatDate(form.generationDate)
                    : form.generationDate
                }
                onChange={(e) =>
                  changeInput({ generationDate: e.target.value })
                }
                required
              />
            </div>
            <div className="form-container f4">
              <label htmlFor="inputReservedDate">Data reservada</label>
              <input
                type="date"
                id="inputReservedDate"
                name="reservedDate"
                value={
                  form.reservedDate.length > 0
                    ? formatDate(form.reservedDate)
                    : form.reservedDate
                }
                onChange={(e) => changeInput({ reservedDate: e.target.value })}
                required
              />
            </div>
            <div className="form-container f4">
              <label htmlFor="inputCompletionDate">Data de finalização</label>
              <input
                type="date"
                id="inputCompletionDate"
                name="completionDate"
                value={
                  form.completionDate.length > 0
                    ? formatDate(form.completionDate)
                    : form.completionDate
                }
                onChange={(e) =>
                  changeInput({ completionDate: e.target.value })
                }
              />
            </div>
            <div className="form-container f4">
              <label htmlFor="inputStatus">Status</label>
              <select
                id="inputStatus"
                name="status"
                value={form.status.id ? form.status.id : 0}
                onChange={(e) => changeStatus(parseInt(e.target.value))}
              >
                <option key={0} value={0}></option>
                {status.map((item) => (
                  <option key={item.id} value={item.id}>
                    {item.name}
                  </option>
                ))}
              </select>
            </div>
          </div>
        </fieldset>
        <fieldset id="fieldset-repair-request">
          <legend>
            <i className="bi bi-wrench legend-icon"></i> Solicitações de reparo
          </legend>
          <hr />
          <div className="row-form-1">
            <div className="form-container l100">
              <button
                id="id-button-add-remove-repair-requests"
                type="button"
                aria-label="Adicionar ou remover solicitações de reparo dessa ordem de serviço"
                title="Adicionar ou remover solicitações de reparo dessa ordem de serviço"
                className="btn btn-secondary"
                onClick={() => changeModalSelectRepairRequests()}
              >
                Adicionar
              </button>
            </div>
          </div>
          <div className="row-form-1">
            <div className="form-container l100">
              <div className="content-table">
                <TableOrderServiceRepairRequest item={selectedRepairRequests} />
              </div>
            </div>
          </div>
        </fieldset>
        <fieldset id="fieldset-employees">
          <legend>
            <i className="bi bi-person-badge legend-icon"></i> Funcionários
          </legend>
          <hr />
          <div className="row-form-1">
            <div className="form-container l100">
              <button
                id="id-button-add-remove-employees"
                type="button"
                aria-label="Adicionar ou remover funcionários responsáveis dessa ordem de serviço"
                title="Adicionar ou remover funcionários responsáveis dessa ordem de serviço"
                className="btn btn-secondary"
                onClick={() => changeModalSelectEmployees()}
              >
                Adicionar
              </button>
            </div>
          </div>
          <div className="row-form-1">
            <div className="form-container l100">
              <div className="content-table">
                <TableOrderServiceEmployees item={selectedEmployees} />
              </div>
            </div>
          </div>
        </fieldset>
        <div className="row-form-1">
          <div className="form-container l4 btns">
            <button type="submit" className="btn btn-success">
              Salvar
            </button>
            <button
              type="button"
              className="btn btn-secondary"
              onClick={clearForm}
            >
              Limpar
            </button>
          </div>
        </div>
      </form>
    </>
  );
};

export default FormTemplate;
