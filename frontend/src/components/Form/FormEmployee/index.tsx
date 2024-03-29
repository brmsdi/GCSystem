import { useState, useEffect } from "react";
import { getAllRoles } from "services/role";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Employee } from "types/employee";
import { Role } from "types/role";

import DatePicker from "react-datepicker"

interface IProps { initForm: Employee, 
  stateForm: StateFormEnum, 
  submit: Function, 
  isActivedFieldPassword: boolean, 
  isNewEmployeeForm: boolean
}

const FormTemplate = (props: IProps) => {
  const [form, setForm] = useState<Employee>(props.initForm)
  const [roles, setRoles] = useState<Role[]>([])

  function changeInput(value: any) {
    setForm(form => ({ ...form, ...value }))
  }

  function changeRole(value: number) {
    for (var index = 0; index < roles.length; index++) {
      let roleSelected = roles.at(index);
      if (roleSelected?.id === value) {
        setForm({ ...form, role: roleSelected })
        break
      }
    }
  }

  useEffect(() => {
    setForm(form => ({ ...form, ...props.initForm }))
  }, [props.initForm])

  useEffect(() => {
    try {
      getAllRoles()
        .then(response => {
          setRoles(response.data)
        })

    } catch (error: any) {
      if (!error.response) {
        Swal.fire('Oops!', 'Sem conexão com o servidor!', 'error')
      }
    }
  }, [])

  async function submit(event: any) {
    event.preventDefault();
    const result = await props.submit(form) 
    if (result === true) {
      if (props.isNewEmployeeForm === true) {
        setForm({ ...props.initForm })
      } else {
        setForm({ ...form })
      }
    }
  }

  async function clearForm() {
    setForm({...props.initForm})
  }

  return (
    <form onSubmit={submit}>
      <div className="row-form-1">
        <div className="form-container l4">
          <label htmlFor="inputName">Nome</label>
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
          <DatePicker 
          selected={form.birthDate !== null ? new Date(form.birthDate) : null} 
          onChange={(date: Date) => changeInput({ birthDate: date })}
          dateFormat={"dd/MM/yyyy"}
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
          <label htmlFor="inputOffice">Cargo</label>
          <select
            id="inputOffice"
            name="role"
            value={form.role.id ? form.role.id : 0}
            onChange={(e) => changeRole(parseInt(e.target.value))}
          >
            <option key={0} value={0}></option>
            {
              roles.map(role => (
                <option 
                key={role.id} 
                value={role.id}>{role.name}</option>
              ))
            }
          </select>
        </div>
        <div className="form-container l2">
          <label htmlFor="inputHiring">Contratação</label>
          <DatePicker 
          selected={form.hiringDate !== null ? new Date(form.hiringDate) : null} 
          onChange={(date: Date) => changeInput({ hiringDate: date })}
          dateFormat={"dd/MM/yyyy"}
          required
          />
        </div>
        {props.isActivedFieldPassword === true ? (
          <div className="form-container l2">
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
          onClick={clearForm}>
             { props.isActivedFieldPassword === true ? 'Limpar' : 'Restaurar' }           
          </button>
        </div>
      </div>
    </form>
  )
}

export default FormTemplate;

/*<button
            type="button"
            className="btn btn-secondary"
            onClick={() => toogleClass()}>
            Voltar
          </button> */