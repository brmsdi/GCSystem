import TableItem from "components/Table/TableItem";
import { useState, useEffect } from "react";
import { listAllCondominiums } from "services/condominium";
import { findByCPFService } from "services/lessee";
import { getAllStatusFromViewRepairRequest } from "services/status";
import { getAllTypeProblem } from "services/type-problem";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Condominium } from "types/condominium";
import { Item } from "types/item";
import { Lessee } from "types/lessee";
import { RepairRequest } from "types/repair-request";
import { Status } from "types/status";
import { TypeProblem } from "types/type-problem";
import { formatDate } from "utils/textFormt";
import { isValidFieldCPF, isValidFieldNumber, isValidFieldText, isSelected } from "utils/verifications";

interface IProps {
  initForm: RepairRequest;
  stateForm: StateFormEnum;
  submit: Function;
  isNewRegisterForm: boolean;
}

const FormTemplate = (props: IProps) => {
  const [form, setForm] = useState<RepairRequest>(props.initForm);
  const [status, setStatus] = useState<Status[]>([]);
  const [typeProblems, setTypeProblems] = useState<TypeProblem[]>([]);
  const [lessee, setLessee] = useState<Lessee>(props.initForm.lessee)
  const [condominiums, setCondominiums] = useState<Condominium[]>([]);
  const [item, setItem] = useState<Item>({
    description: '',
    quantity: 0,
    value: 0
  });
  const [addedItems, setAddedItems] = useState<Item[]>(props.initForm.items ? props.initForm.items : []);

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

  function changeTypeProblem(value: number) {
    for (var index = 0; index < typeProblems.length; index++) {
      let typeProblemSelected = typeProblems.at(index);
      if (typeProblemSelected?.id === value) {
        setForm({ ...form, typeProblem: typeProblemSelected })
        break
      }
    }
  }

  function changeInputLessee(value: any) {
    setLessee(lessee => ({ ...lessee, ...value }));
  }

  function changeCondominium(value: number) {
    for (var index = 0; index < condominiums.length; index++) {
      let condominiumSelected = condominiums.at(index);
      if (condominiumSelected?.id === value) {
        setForm({ ...form, condominium: condominiumSelected });
        break;
      }
    }
  }

  function changeInputItem(value: any) {
    setItem(item => ({...item, ...value}))
  }

  useEffect(() => {
    setForm((form) => ({ ...form, ...props.initForm }));
    setLessee(props.initForm.lessee)
    setAddedItems(props.initForm.items ? props.initForm.items : [])
    listAllCondominiums().then(response => setCondominiums(response.data));
    getAllTypeProblem().then(response => setTypeProblems(response.data))
    getAllStatusFromViewRepairRequest().then(response => setStatus(response.data));
  }, [props.initForm]);

  useEffect(() => {
    
    checkLegend('fieldset-lessee', 
    isValidFieldNumber(lessee.id))

    checkLegend('fieldset-condominium', 
    isValidFieldNumber(form.condominium.id) 
    && isValidFieldText(form.apartmentNumber))
    
    checkLegend('fieldset-repair-informations', isValidFieldText(form.problemDescription)
    && isValidFieldText(form.date) 
    && isValidFieldNumber(form.typeProblem.id)
    && isValidFieldNumber(form.status.id))

    checkLegend('fieldset-items', isValidFieldText(item.description) 
    && isValidFieldNumber(item.value)
    && isValidFieldNumber(item.quantity))
  },[form, lessee, item])

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
          clearLesseeFields()
          checkLegend("fieldset-lessee", false)
        }
      })
    } else {
      Swal.fire('Oops!', 'Digite um cpf valido', 'error')
      clearLesseeFields()
      checkLegend("fieldset-lessee", false)
    }
  }

  async function submit(event: any) {
    event.preventDefault();
    if (!isSelected(form.typeProblem)) {
      Swal.fire('Oops!', 'Selecione o campo tipo de problema', 'error')
      return
    } 

    if (!isSelected(form.status)) {
      Swal.fire('Oops!', 'Selecione o campo status', 'error')
      return
    } 
   
    if (!isValidFieldNumber(lessee.id)) {
      Swal.fire('Oops!', 'Busque por um solicitante', 'error')
      return
    } 

    if (!isSelected(form.condominium)) {
      Swal.fire('Oops!', 'Selecione o campo condomínio', 'error')
      return
    } 

    let newForm = {
      ...form,
      lessee: lessee,
      items: addedItems
    }

    setForm({...newForm})
    const result = await props.submit(newForm);
    if (result === true) {
      if (props.isNewRegisterForm === true) {
        clearForm();
        clearItem();
        clearAddedItems();
        clearLesseeFields();
      } else {
        setForm({ ...form });
      }
    }
  }

  function addItem() {
    if (isValidFieldText(item.description) && isValidFieldNumber(item.quantity) && isValidFieldNumber(item.value)) {
      let currentItems : Item[]= addedItems;
      currentItems.unshift(item)
      setAddedItems([...addedItems])   
      clearItem();
      return
    }
    Swal.fire('Oops!', "Preencha todos os campos relacionado a itens", 'error')
  }

  function removeItem(_item: Item) {
      let currentItems : Item[] = addedItems;
      for (let index = 0; index < currentItems.length; index++) {
        let itemIndex = currentItems.at(index)
        if (_item.id) {
          if (itemIndex && _item.id === itemIndex.id) {
            currentItems.splice(index, 1)
            setAddedItems([...currentItems])
            break
          } 
        } else {
          if (itemIndex && _item.description === itemIndex.description) {
            currentItems.splice(index, 1)
            setAddedItems([...currentItems])
            break
          }
        }
        
    }
  }

  function clearForm() {
    setForm({ ...props.initForm });
  }

  async function clearLesseeFields() {
    setLessee({ ...props.initForm.lessee });
  }

  async function clearItem() {
    setItem({
      description: '',
      quantity: 0,
      value: 0
    })
  }

  async function clearAddedItems() {
    setAddedItems([])
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
      <fieldset id="fieldset-repair-informations">
        <legend>
          <i className="bi bi-card-checklist legend-icon"></i> Reparo
        </legend>
        <hr />
        <div className="row-form-1">
          <div className="form-container l4">
            <label htmlFor="inputProblemDescription">
              Descrição do problema
            </label>
            <input
              type="text"
              id="inputProblemDescription"
              placeholder="Descreva o problema..."
              name="problemDescription"
              value={form.problemDescription}
              onChange={(e) =>
                changeInput({ problemDescription: e.target.value })
              }
              required
            />
          </div>
          <div className="form-container l2">
            <label htmlFor="inputDate">Data de solicitação</label>
            <input
              type="date"
              id="inputDate"
              name="date"
              value={form.date.length > 0 ? formatDate(form.date) : form.date}
              onChange={(e) => changeInput({ date: e.target.value })}
              required
            />
          </div>
          <div className="form-container l2">
            <label htmlFor="inputTypeProblem">Tipo de problema</label>
            <select
              id="inputTypeProblem"
              name="typeProblem"
              value={form.typeProblem.id ? form.typeProblem.id : 0}
              onChange={(e) => changeTypeProblem(parseInt(e.target.value))}
            >
              <option key={0} value={0}></option>
              {typeProblems.map((typeProblem) => (
                <option key={typeProblem.id} value={typeProblem.id}>
                  {typeProblem.name}
                </option>
              ))}
            </select>
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
      <fieldset id="fieldset-lessee">
        <legend>
          <i className="bi bi-card-checklist legend-icon"></i> Solicitante
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
      <fieldset id="fieldset-condominium">
        <legend>
          <i className="bi bi-card-checklist legend-icon"></i> Condomínio
        </legend>
        <hr />
        <div className="row-form-1">
          <div className="form-container l2">
            <label htmlFor="inputCondominium">Condomínio</label>
            <select
              id="inputCondominium"
              name="condominium"
              value={form.condominium.id ? form.condominium.id : 0}
              onChange={(e) => changeCondominium(parseInt(e.target.value))}
            >
              <option key={0} value={0}></option>
              {condominiums.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.name}
                </option>
              ))}
            </select>
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
                required
              />
            </div>
        </div>
      </fieldset>
      <fieldset id="fieldset-items">
        <legend>
          <i className="bi bi-card-checklist legend-icon"></i> Itens(Opcional)
        </legend>
        <hr />
        <div className="row-form-1">
          <div className="form-container l4">
            <label htmlFor="inputItemDescription">Descrição do item</label>
            <input
              type="text"
              id="inputItemDescription"
              placeholder="Descrição do item..."
              name="description"
              value={item.description}
              onChange={(e) => changeInputItem({ description: e.target.value })}
            />
          </div>
          <div className="form-container l3">
            <label htmlFor="inputQuantity">Quantidade</label>
            <input
              type="number"
              id="inputQuantity"
              placeholder="Quantidade"
              name="quantity"
              value={item.quantity}
              onChange={(e) => changeInputItem({ quantity: e.target.value })}
            />
          </div>
          <div className="form-container l3">
            <label htmlFor="inputValue">Valor unitário</label>
            <input
              type="number"
              id="inputValue"
              placeholder="Valor"
              name="value"
              value={item.value}
              onChange={(e) => changeInputItem({ value: e.target.value })}
            />
          </div>
          <div className="form-container button-add-item l3">
            <button
              id="id-button-add-item"
              type="button"
              aria-label="Adicionar esse item"
              title="Adicionar esse item"
              className="btn btn-secondary"
              onClick={() => addItem()}
            >
              Adicionar
            </button>
          </div>
        </div>
        <div className="row-form-1">
          <div className="form-container l100">
            <div className="content-table"> 
              <TableItem item={addedItems} removeItem={removeItem} />
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
  );
};

export default FormTemplate;
