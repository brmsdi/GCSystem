import { useState, useEffect } from "react";
import { getAllRoles } from "services/Role";
import { getAllSpecialties } from "services/Specialty";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/Action";
import { Employee, Role, Specialty } from "types/Employee";
import { formatDate } from "utils/textFormt";

const FormTemplate = (props: { initForm: Employee, stateForm: StateFormEnum, submit: Function, isEditablePassword: boolean }) => {
  const [form, setForm] = useState<Employee>(props.initForm)
  const [roles, setRoles] = useState<Role[]>([])
  const [specialties, setSpecialties] = useState<Specialty[]>([])

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

  function changeSpecialty(value: number) {
    for (var index = 0; index < specialties.length; index++) {
      let specialtySelected = specialties.at(index);
      if (specialtySelected?.id === value) {
        setForm({ ...form, specialties: [specialtySelected] })
        break
      }
    }
  }

  useEffect(() => {
    setForm(form => ({ ...form, ...props.initForm}))
  }, [props.initForm])

  useEffect(() => {
    try {
      getAllRoles()
        .then(response => {
          setRoles(response.data)
        })

      getAllSpecialties()
        .then(response => {
          setSpecialties(response.data)
        })

    } catch (error: any) {
      if (!error.response) {
        Swal.fire('Oops!', 'Sem conexão com o servidor!', 'error')
      }
    }
  }, [])

  async function submit(event: any) {
    event.preventDefault();
    props.submit(form)
  }

  /*
  function toogleClass() {
    let form = document.querySelector(".content-form");
    if (form != null) {
      form.classList.toggle("active");
      if (form.classList.contains("active")) {
        dispatch(setStateFormAction(props.stateForm))
      } else {
        dispatch(setStateFormAction(StateFormEnum.NOACTION));
        dispatch(removeSelectedEmployeeTableAction())
      }

    }
  } */
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
            type="text"
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
          <label htmlFor="inputOffice">Cargo</label>
          <select
            id="inputOffice"
            name="role"
            onChange={(e) => changeRole(parseInt(e.target.value))}
          >
            {
              roles.map(role => (
                <option key={role.id} value={role.id}>{role.name}</option>
              ))
            }
          </select>
        </div>
        <div className="form-container l2">
          <label htmlFor="inputSpecialty">Especialidade</label>
          <select
            id="inputSpecialty"
            onChange={(e) => changeSpecialty(parseInt(e.target.value))}
          >
            {
              specialties.map(specialty => (
                <option key={specialty.id} value={specialty.id}>{specialty.name}</option>
              ))
            }
          </select>
        </div>
        <div className="form-container l2">
          <label htmlFor="inputHiring">Contratação</label>
          <input
            type="date"
            id="inputHiring"
            name="hiringDate"
            value={form.hiringDate.length > 0 ? formatDate(form.hiringDate) : form.hiringDate}
            onChange={(e) => changeInput({ hiringDate: e.target.value })}
            required />

        </div>
      </div>
      <div className="row-form-1">

        <div className="form-container l2">
          <label htmlFor="inputPassword">Senha de acesso</label>
          <input
            type="password"
            className="form-control"
            id="inputPassword"
            placeholder="Senha"
            name="password"
            value={form.password}
            onChange={(e) => changeInput({ password: e.target.value })}
            disabled={props.isEditablePassword}
            required={props.isEditablePassword}
            minLength={8}
          />
        </div>
      </div>
      <div className="row-form-1">
        <div className="form-container l4 btns">
          <button type="submit" className="btn btn-success">
            Salvar
          </button>

        </div>
      </div>
    </form>
  );
};

export default FormTemplate;

/*<button
            type="button"
            className="btn btn-secondary"
            onClick={() => toogleClass()}>
            Voltar
          </button> */