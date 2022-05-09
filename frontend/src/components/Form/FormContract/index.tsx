import { useState, useEffect } from "react";
import { listAllCondominiums } from "services/condominium";
import { findByCPFService } from "services/lessee";
import { getAllStatusFromViewContract } from "services/status";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Condominium } from "types/condominium";
import { Contract } from "types/contract";
import { Lessee } from "types/lessee";
import { Status } from "types/status";
import { formatDate, formatLocalizationViewInformation } from "utils/textFormt";
import { isValidFieldCPF, isValidFieldDay, isValidFieldNumber, isValidFieldText } from "utils/verifications";

interface IProps {
  initForm: Contract;
  stateForm: StateFormEnum;
  submit: Function;
  isActivedFieldPassword: boolean;
  isNewRegisterForm: boolean;
}

const FormTemplate = (props: IProps) => {
  const [form, setForm] = useState<Contract>(props.initForm);
  const [condominiums, setCondominiums] = useState<Condominium[]>([]);
  const [condominiumSelectedView, setCondominiumSelectedView] = useState(
    formatLocalizationViewInformation(form.condominium.localization)
  );
  const [status, setStatus] = useState<Status[]>([]);
  const [lessee, setLessee] = useState<Lessee>(props.initForm.lessee)
  
  function changeInput(value: any) {
    setForm((form) => ({ ...form, ...value }));
  }

  function changeCondominium(value: number) {
    for (var index = 0; index < condominiums.length; index++) {
      let condominiumSelected = condominiums.at(index);
      if (condominiumSelected?.id === value) {
        setForm({ ...form, condominium: condominiumSelected });
        setCondominiumSelectedView(
          formatLocalizationViewInformation(condominiumSelected.localization)
        );
        break;
      }
    }
  }

  function changeInputLessee(value: any) {
    setLessee(lessee => ({ ...lessee, ...value }));
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
    setLessee(props.initForm.lessee)
    setCondominiumSelectedView(
      formatLocalizationViewInformation(props.initForm.condominium.localization)
    );
    listAllCondominiums().then(response => setCondominiums(response.data));
    getAllStatusFromViewContract().then(response => setStatus(response.data));
  }, [props.initForm]);

  useEffect(() => {
    checkLegend('fieldset-lessee', 
    isValidFieldNumber(lessee.id))

    checkLegend('fieldset-condominium', 
    isValidFieldNumber(form.condominium.id) 
    && isValidFieldText(form.apartmentNumber))

    checkLegend('fieldset-contract', isValidFieldNumber(form.contractValue) 
    && isValidFieldDay(form.monthlyPaymentDate) 
    && isValidFieldDay(form.monthlyDueDate)
    && isValidFieldText(form.contractDate)
    && isValidFieldText(form.contractExpirationDate) 
    && isValidFieldNumber(form.status.id))
  },[form, lessee])

  async function submit(event: any) {
    event.preventDefault();
    let newForm: Contract = {
      ...form,
      lessee: lessee
    }
    setForm({...newForm})
    const result = await props.submit(newForm)
    if (result === true) {
      if (props.isNewRegisterForm === true) {
        clearForm();
        clearLesseeFields()
        clearCondominiumSelectedView();
      } else {
        setForm({ ...form });
      }
    }
  }

  async function getLesseeForCPF () {
    if(isValidFieldCPF(lessee.cpf)) {
      findByCPFService(lessee.cpf)
      .then(response => {
        if (response.data.content && response.data.content?.length > 0)
        {
          setLessee(response.data.content[0])
          checkLegend("fieldset-lessee", true)
        } else {
          Swal.fire('Oops!', 'Nenhum registro encontrado com o CPF: ' + lessee.cpf, 'error')
          checkLegend("fieldset-lessee", false)
        }
      })
    } else {
      Swal.fire('Oops!', 'Digite um cpf valido', 'error')
      clearLesseeFields()
      checkLegend("fieldset-lessee", false)
    }
  }

  async function clearForm() {
    setForm({ ...props.initForm });
  }

  async function clearLesseeFields() {
    setLessee({ ...props.initForm.lessee });
  }

  async function clearCondominiumSelectedView() {
    setCondominiumSelectedView('')
  }

  function checkLegend(ID: string, active: boolean) {
    const component = document.getElementById(ID)
    if (active) {
      component?.classList.add('fieldset-ok')
    } else {
      component?.classList.remove('fieldset-ok')
    }
  }

  return (
    <form onSubmit={submit}>
      <fieldset id="fieldset-lessee">
        <legend><i className="bi bi-card-checklist legend-icon"></i> Locatário</legend>
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
                onChange={(e) => changeInputLessee({cpf: e.target.value})}
                required/>
              <button 
              type="button"
              className="btn btn-secondary"
              onClick={() => getLesseeForCPF()}>
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
              required/>
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
              required/>
          </div>
        </div>
      </fieldset>
      <fieldset id="fieldset-condominium">
        <legend><i className="bi bi-card-checklist legend-icon"></i> Condomínio</legend>
        <hr />
        <div className="row-form-1">
          <div className="form-container l2">
            <label htmlFor="inputCondominium">Condomínio</label>
            <select
              id="inputCondominium"
              name="condominium"
              value={form.condominium.id ? form.condominium.id : 0}
              onChange={(e) => changeCondominium(parseInt(e.target.value))}>
              <option key={0} value={0}></option>
              {condominiums.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.name}
                </option>
              ))}
            </select>
          </div>
          <div className="form-container l4">
            <label htmlFor="ínputCondominiumInfo">Endereço</label>
            <input
              type="text"
              id="ínputCondominiumInfo"
              placeholder="Endereço"
              name="name"
              value={condominiumSelectedView}
              disabled/>
          </div>
          <div className="form-container l2">
            <label htmlFor="inputApartmentNumber">Nº apartamento</label>
            <input
              type="text"
              id="inputApartmentNumber"
              placeholder="Nº apartamento"
              name="apartmentNumber"
              value={form.apartmentNumber}
              onChange={(e) =>
                changeInput({ apartmentNumber: e.target.value })
              }
              required/>
          </div>
        </div>
      </fieldset>
      <fieldset id="fieldset-contract">
        <legend><i className="bi bi-card-checklist legend-icon"></i> Contrato</legend>
        <hr />
        <div className="row-form-1">
          <div className="form-container f4">
            <label htmlFor="inputValueContract">Valor do contrato</label>
            <input
              type="number"
              id="inputValueContract"
              placeholder="Valor do contrato"
              name="contractValue"
              value={form.contractValue}
              onChange={(e) => changeInput({contractValue: e.target.value})}
              required/>
          </div>
          <div className="form-container f4">
            <label htmlFor="inputMonthlyPaymentDate">Dia de pagamento</label>
            <input
              type="number"
              id="inputMonthlyPaymentDate"
              placeholder="Dia de pagamento mensal"
              name="monthlyPaymentDate"
              value={form.monthlyPaymentDate}
              onChange={(e) => changeInput({monthlyPaymentDate: e.target.value})}
              required/>
          </div>
          <div className="form-container f4">
            <label htmlFor="inputMonthlyDueDate">Dia de vencimento</label>
            <input
              type="number"
              id="inputMonthlyDueDate"
              placeholder="Dia de vencimento mensal"
              name="monthlyDueDate"
              value={form.monthlyDueDate}
              onChange={(e) => changeInput({monthlyDueDate: e.target.value})}
              required />
          </div>
          <div className="form-container f4">
            <label htmlFor="inputContractDate">Data do contrato</label>
            <input
              type="date"
              id="inputContractDate"
              name="contractDate"
              value={form.contractDate.length > 0 ? formatDate(form.contractDate) : form.contractDate}
              onChange={(e) => changeInput({contractDate: e.target.value })}
              required />
          </div>
        </div>
        <div className="row-form-1">
          <div className="form-container f4">
            <label htmlFor="inputContractExpirationDate">
              Data de validade
            </label>
            <input
              type="date"
              id="inputContractExpirationDate"
              name="contractExpirationDate"
              value={form.contractExpirationDate.length > 0 ? formatDate(form.contractExpirationDate) : form.contractExpirationDate}
              onChange={(e) => changeInput({contractExpirationDate: e.target.value })}
              required />
          </div>
          <div className="form-container f4">
            <label htmlFor="inputStatus">Status</label>
            <select
              id="inputStatus"
              name="status"
              value={form.status.id ? form.status.id : 0}
              onChange={(e) => changeStatus(parseInt(e.target.value))}>
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
            onClick={clearForm}>
            Limpar
          </button>
        </div>
      </div>
    </form>
  );
};

export default FormTemplate;