import { useDispatch } from "react-redux";
import { saveEmployee } from "services/employee";
import { updateEmployeeTableAction } from "store/Employees/employees.actions";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Employee } from "types/employee";
import FormTemplate from "..";

let initForm: Employee = {
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
  status: {
    id: 1,
    name: 'Ativo'
  },
  password: ''
}

const FormNewEmployee = () => {
  const dispatch = useDispatch();
  async function submit(form: Employee) {
    try {
      const result = await saveEmployee(form);
      await Swal.fire('Ebaa!', result, 'success')
      dispatch(updateEmployeeTableAction())
      return true
    } catch (error: any) {
      const errors = error.response.data.errors
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor!!", "error");
      }   
      else if (errors) {
        Swal.fire('oops!', errors[0].message, 'error')
      }  
      else {
        Swal.fire("Oops!", "" + error.response.data, "error");
      }
    }
  }
  return (
    <FormTemplate
      initForm={initForm}
      stateForm={StateFormEnum.SAVING}
      submit={submit}
      isActivedFieldPassword={true}
      isNewEmployeeForm={true} />
  )
}

export default FormNewEmployee;