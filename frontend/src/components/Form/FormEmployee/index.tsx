import { useState, useEffect} from "react";
import { getAllRoles } from "services/Role";
import { getAllSpecialties } from "services/Specialty";
import Swal from "sweetalert2";
import { Employee, Role, Specialty } from "types/Employee";

const FormEmployee = () => {
  const[form, setForm] = useState<Employee>({
    name: '',
    rg: '',
    cpf: '',
    birthDate: '',
    email: '',
    hiringDate: '',
    role: {
      id: 1,
      name: 'Administrador'
    }, 
    specialties: [{
      id: 1,
      name: 'Desenvolvedor de Software'
    }],
    status: {
      id: 1,
      name: 'Ativo'
    },
    password: ''
  })
  const [roles, setRoles] = useState<Role[]>([]) 
  const [specialties, setSpecialties] = useState<Specialty[]>([]) 

  function changeInput(value: any) {
    setForm(form => ({...form, ...value}))
  }

  function changeRole(value: number) {
    for (var index = 0; index < roles.length; index++) {
      let roleSelected = roles.at(index); 
      if (roleSelected?.id === value ) {
        setForm({...form, role: roleSelected })
        break
      }
    }
  }

  function changeSpecialty(value: number) {
    for (var index = 0; index < specialties.length; index++) {
      let specialtySelected = specialties.at(index); 
      if (specialtySelected?.id === value ) {
        setForm({...form, specialties: [specialtySelected]})
        break
      }
    }
  }

  function submit(event: any) {
    event.preventDefault()
    console.log(form)
  }

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
      
    } catch (error : any) {
      if (!error.response) {
        Swal.fire('Oops!', 'Sem conexão com o servidor!', 'error')
      }      
    } 
  }, [])

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
          />
        </div>
        <div className="form-container l2">
          <label htmlFor="inputBirth">Nascimento</label>
          <input 
          type="date" 
          id="inputBirth" 
          name="birthDate"
          value={form.birthDate}
          onChange={(e) => changeInput({ birthDate: e.target.value })}
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
          value={form.hiringDate}
          onChange={(e) => changeInput({ hiringDate: e.target.value })} />
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
          />
        </div>
      </div>
      <div className="row-form-1">
          <div className="form-container l4 btns">
            <button type="submit" className="btn btn-success">
              Salvar
            </button>
            <button type="button" className="btn btn-secondary">
              Cancelar
            </button>
          </div>
      </div>

    </form>
  );
};

export default FormEmployee;