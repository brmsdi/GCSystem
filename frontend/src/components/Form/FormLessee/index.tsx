import { useState, useEffect } from "react";
import { StateFormEnum } from "types/action";
import { Lessee } from "types/lessee";
import { formatDate } from "utils/textFormt";

interface IProps { 
  initForm: Lessee, 
  stateForm: StateFormEnum, 
  submit: Function, 
  isActivedFieldPassword: boolean, 
  isNewRegisterForm: boolean}

const FormTemplate = (props: IProps) => {
  const [form, setForm] = useState<Lessee>(props.initForm)

  function changeInput(value: any) {
    setForm(form => ({ ...form, ...value }))
  }

  useEffect(() => {
    setForm(form => ({ ...form, ...props.initForm }))
  }, [props.initForm])

  async function submit(event: any) {
    event.preventDefault();
    const result = await props.submit(form) 
    if (result === true) {
      if (props.isNewRegisterForm === true) {
        setForm({ ...props.initForm })
      } else {
        setForm({ ...form })
      }
    }
  }

  async function clearField() {
    setForm({...props.initForm})
  }
  return (
    <form onSubmit={submit}>
      <div className="row-form-1">
        <div className="form-container l4">
          <label htmlFor="inpturName">Nome</label>
          <input
            type="text"
            id="inputName"
            placeholder="Nome completo"
            name="name"
            value={form.name}
            onChange={(e) => changeInput({ name: e.target.value })}
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
            value={form.rg}
            onChange={(e) => changeInput({ rg: e.target.value })}
            required
          />
        </div>
        <div className="form-container l2">
          <label htmlFor="inputCPF">CPF</label>
          <input
            type="number"
            id="inputCPF"
            placeholder="CPF"
            name="cpf"
            value={form.cpf}
            onChange={(e) => changeInput({ cpf: e.target.value })}
            required
          />
        </div>
        <div className="form-container l2">
          <label htmlFor="inputBirth">Nascimento</label>
          <input
            type="date"
            id="inputBirth"
            name="birthDate"
            value={form.birthDate.length > 0 ? formatDate(form.birthDate) : form.birthDate}
            onChange={(e) => changeInput({ birthDate: e.target.value })}
            required
          />
        </div>
      </div>
      <div className="row-form-1">
        <div className="form-container l4">
          <label htmlFor="inputEmail">E-mail</label>
          <input
            type="email"
            id="inputEmail"
            placeholder="E-mail"
            name="email"
            value={form.email}
            onChange={(e) => changeInput({ email: e.target.value })}
            required
          />
        </div>
        <div className="form-container l2">
            <label htmlFor="inputContactNumber">Nº contato</label>
            <input
              type="number"
              id="inputContactNumber"
              placeholder="Número"
              name="contactNumber"
              value={form.contactNumber}
              onChange={(e) => changeInput({ contactNumber: e.target.value })}
              required
              minLength={11}
            />
          </div>
        {props.isActivedFieldPassword === true ? (
          <div className="form-container l4">
            <label htmlFor="inputPassword">Senha de acesso</label>
            <input
              type="password"
              id="inputPassword"
              placeholder="Senha"
              name="password"
              value={form.password}
              onChange={(e) => changeInput({ password: e.target.value })}
              required={props.isActivedFieldPassword}
              minLength={8}
            />
          </div>
        )
          :
          (null)
        }
      </div>
      <div className="row-form-1">
        <div className="form-container l4 btns">
          <button type="submit" className="btn btn-success">
            Salvar
          </button>
          <button type="button" className="btn btn-secondary"
          onClick={clearField}>
             { props.isActivedFieldPassword === true ? 'Limpar' : 'Restaurar' }           
          </button>
        </div>
      </div>
    </form>
  )
}

export default FormTemplate;