import { useState, useEffect } from "react";
import { listAllCondominiums } from "services/condominium";
import { findByCPFService } from "services/lessee";
import { getAllStatus } from "services/status";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Condominium } from "types/condominium";
import { Contract } from "types/contract";
import { Lessee } from "types/lessee";
import { Status } from "types/status";
import { formatLocalizationViewInformation } from "utils/textFormt";
import { isValidFieldCPF } from "utils/verifications";

interface IProps {
  initForm: Contract;
  stateForm: StateFormEnum;
  submit: Function;
  isActivedFieldPassword: boolean;
  isNewRegisterForm: boolean;
}

type StageFields = {
  OneIsOK: boolean,
  twoIsOK: boolean,
  ThreeIsOK: boolean
}

const FormTemplate = (props: IProps) => {
  const [form, setForm] = useState<Contract>(props.initForm);
  const [condominiums, setCondominiums] = useState<Condominium[]>([]);
  const [condominiumSelectedView, setCondominiumSelectedView] = useState(
    formatLocalizationViewInformation(form.condominium.localization)
  );
  const [status, setStatus] = useState<Status[]>([]);
  const [stageFields, setStageFields] = useState<StageFields>({
    OneIsOK: true,
    twoIsOK: false,
    ThreeIsOK: false
  })

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
    listAllCondominiums()
    .then((response) => {
      setCondominiums(response.data);
    });

    getAllStatus().then((response) => {
      setStatus(response.data);
    });
  }, [props.initForm]);

  /*
  async function submit(event: any) {
    event.preventDefault();
    const result = await props.submit(form);
    if (result === true) {
      if (props.isNewRegisterForm === true) {
        setForm({ ...props.initForm });
      } else {
        setForm({ ...form });
      }
    }
  } */

  async function submit(event: any) {
    event.preventDefault();
    console.log('ok')
  }

  async function getLesseeForCPF () {
    if(isValidFieldCPF(lessee.cpf)) {
      findByCPFService(lessee.cpf)
      .then(response => {
        if (response.data.content && response.data.content?.length > 0)
        {
          setLessee(response.data.content[0])
          setStageFields({
            OneIsOK: true,
            twoIsOK: true,
            ThreeIsOK: false
          })
          checkLegend("fieldset-lessee", true)
        } else {
          Swal.fire('Oops!', 'Nenhum registro encontrado com o CPF: ' + lessee.cpf, 'error')
          setStageFields({
            OneIsOK: true,
            twoIsOK: false,
            ThreeIsOK: false
          })
          clearLesseeFields()
          checkLegend("fieldset-lessee", false)
        }
      })
    } else {
      Swal.fire('Oops!', 'Digite um cpf valido', 'error')
      setStageFields({
        OneIsOK: true,
        twoIsOK: false,
        ThreeIsOK: false
      })
      clearLesseeFields()
      checkLegend("fieldset-lessee", false)
    }
  }

  async function clearField() {
    setForm({ ...props.initForm });
  }

  async function clearLesseeFields() {
    setLessee({ ...props.initForm.lessee });
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
                disabled={!stageFields.OneIsOK}
                required
              />
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
      <fieldset>
        <legend>Info. condomínio</legend>
        <hr />
        <div className="row-form-1">
          <div className="form-container l2">
            <label htmlFor="inputCondominium">Condomínio</label>
            <select
              id="inputCondominium"
              name="condominium"
              value={form.condominium.id ? form.condominium.id : 0}
              onChange={(e) => changeCondominium(parseInt(e.target.value))}
              disabled={!stageFields.twoIsOK}
            >
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
              disabled
            />
          </div>
          <div className="form-container l2">
            <label htmlFor="inputNumberApartments">Nº apartamento</label>
            <input
              type="text"
              id="inputNumberApartments"
              placeholder="Nº apartamento"
              name="numberApartments"
              value={form.apartmentNumber}
              onChange={(e) =>
                changeInput({ numberApartments: e.target.value })
              }
              disabled={!stageFields.twoIsOK}
              required
            />
          </div>
        </div>
      </fieldset>
      <fieldset>
        <legend>Info. contrato</legend>
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
              disabled={!stageFields.ThreeIsOK}
              required
            />
          </div>
          <div className="form-container f4">
            <label htmlFor="inputMonthlyPaymentDate">Dia de pagamento</label>
            <input
              type="number"
              id="inputMonthlyPaymentDate"
              placeholder="Dia de pagamento mensal"
              name="monthlyPaymentDate"
              value={form.monthlyPaymentDate}
              disabled={!stageFields.ThreeIsOK}
              required
            />
          </div>
          <div className="form-container f4">
            <label htmlFor="inputMonthlyDueDate">Dia de vencimento</label>
            <input
              type="number"
              id="inputMonthlyDueDate"
              placeholder="Dia de vencimento mensal"
              name="monthlyDueDate"
              value={form.monthlyDueDate}
              disabled={!stageFields.ThreeIsOK}
              required
            />
          </div>
          <div className="form-container f4">
            <label htmlFor="inputContractDate">Data do contrato</label>
            <input
              type="date"
              id="inputContractDate"
              name="contractDate"
              value={form.contractDate}
              disabled={!stageFields.ThreeIsOK}
              required
            />
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
              value={form.contractExpirationDate}
              disabled={!stageFields.ThreeIsOK}
              required
            />
          </div>
          <div className="form-container f4">
            <label htmlFor="inputStatus">Status</label>
            <select
              id="inputStatus"
              name="status"
              value={form.status.id ? form.status.id : 0}
              onChange={(e) => changeStatus(parseInt(e.target.value))}
              disabled={!stageFields.ThreeIsOK}
            >
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
            onClick={clearField}
          >
            Limpar
          </button>
        </div>
      </div>
    </form>
  );
};

export default FormTemplate;

/* 
valor do contrato, 
dia de pagamento mensal, 
dia de vencimento mensal, 
prazo de validade do contrato e status do contrato.
*/
