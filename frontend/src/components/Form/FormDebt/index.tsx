import { useState, useEffect } from "react";
import { findByCPFService } from "services/lessee";
import { getAllStatusFromViewDebt } from "services/status";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Debt } from "types/debt";
import { Lessee } from "types/lessee";
import { Status } from "types/status";
import {
  isValidFieldCPF,
  isValidFieldNumber,
  isValidFieldText,
} from "utils/verifications";

import DatePicker from "react-datepicker";

interface IProps {
  initForm: Debt;
  stateForm: StateFormEnum;
  submit: Function;
  isActivedFieldPassword: boolean;
  isNewRegisterForm: boolean;
}

const FormDebt = (props: IProps) => {
  const [form, setForm] = useState<Debt>(props.initForm);
  const [status, setStatus] = useState<Status[]>([]);
  const [lessee, setLessee] = useState<Lessee>(props.initForm.lessee);
  function changeInput(value: any) {
    setForm((form) => ({ ...form, ...value }));
  }

  function changeInputLessee(value: any) {
    setLessee((lessee) => ({ ...lessee, ...value }));
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
    setLessee(props.initForm.lessee);
    getAllStatusFromViewDebt().then((response) => setStatus(response.data));
  }, [props.initForm]);

  useEffect(() => {
    checkLegend("fieldset-form-debt-lessee", isValidFieldNumber(lessee.id));

    checkLegend(
      "fieldset-form-debt-debt",
      isValidFieldNumber(form.value) &&
        isValidFieldText(form.dueDate?.toString()) &&
        isValidFieldNumber(form.status.id)
    );
  }, [form, lessee]);

  async function submit(event: any) {
    event.preventDefault();
    let newForm: Debt = {
      ...form,
      lessee: lessee,
    };
    setForm({ ...newForm });
    const result = await props.submit(newForm);
    if (result === true) {
      if (props.isNewRegisterForm === true) {
        clearForm();
        clearLesseeFields();
      } else {
        setForm({ ...form });
      }
    }
  }

  async function getLesseeForCPF() {
    if (isValidFieldCPF(lessee.cpf)) {
      findByCPFService(lessee.cpf).then((response) => {
        if (response.data.content && response.data.content?.length > 0) {
          setLessee(response.data.content[0]);
          checkLegend("fieldset-form-debt-lessee", true);
        } else {
          Swal.fire(
            "Oops!",
            "Nenhum registro encontrado com o CPF: " + lessee.cpf,
            "error"
          );
          checkLegend("fieldset-form-debt-lessee", false);
        }
      });
    } else {
      Swal.fire("Oops!", "Digite um cpf valido", "error");
      clearLesseeFields();
      checkLegend("fieldset-form-debt-lessee", false);
    }
  }

  async function clearForm() {
    setForm({ ...props.initForm });
  }

  async function clearLesseeFields() {
    setLessee({ ...props.initForm.lessee });
  }

  function checkLegend(ID: string, active: boolean) {
    const component = document.getElementById(ID);
    if (active) {
      component?.classList.add("fieldset-ok");
    } else {
      component?.classList.remove("fieldset-ok");
    }
  }

  return (
    <form onSubmit={submit}>
      <fieldset id="fieldset-form-debt-lessee">
        <legend>
          <i className="bi bi-card-checklist legend-icon"></i> Locatário
        </legend>
        <hr />
        <div className="row-form-1">
          <div className="form-container l2">
            <label htmlFor="inpturCPF">CPF</label>
            <div className="div-search-form">
              <input
                type="number"
                id="inputCPFSearch"
                placeholder="CPF"
                name="cpf"
                value={lessee.cpf}
                onChange={(e) => changeInputLessee({ cpf: e.target.value })}
                required
              />
              <button
                type="button"
                className="btn btn-secondary"
                onClick={() => getLesseeForCPF()}
              >
                <i className="bi bi-search"></i>
              </button>
            </div>
          </div>
          <div className="form-container l4">
            <label htmlFor="inpturName">Nome</label>
            <input
              type="text"
              id="inputName"
              placeholder="Nome completo"
              name="name"
              value={lessee.name}
              disabled
              required
            />
          </div>
          <div className="form-container l2">
            <label htmlFor="inputRG">RG</label>
            <input
              type="number"
              id="inputRG"
              placeholder="RG"
              name="rg"
              value={lessee.rg}
              disabled
              required
            />
          </div>
        </div>
      </fieldset>
      <fieldset id="fieldset-form-debt-debt">
        <legend>
          <i className="bi bi-card-checklist legend-icon"></i> Débito
        </legend>
        <hr />
        <div className="row-form-1">
          <div className="form-container l2">
            <label htmlFor="inputValueDebt">Valor</label>
            <input
              type="number"
              id="inputValueDebt"
              placeholder="Valor do débito"
              name="value"
              value={form.value}
              onChange={(e) => changeInput({ value: e.target.value })}
              required
            />
          </div>
          <div className="form-container l2">
            <label htmlFor="inputDueDate">Data do vencimento</label>
            <DatePicker
              selected={form.dueDate !== null ? new Date(form.dueDate) : null}
              onChange={(date: Date) => changeInput({ dueDate: date })}
              dateFormat={"dd/MM/yyyy"}
              required
            />
          </div>
          <div className="form-container l2">
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
  );
};

export default FormDebt;
