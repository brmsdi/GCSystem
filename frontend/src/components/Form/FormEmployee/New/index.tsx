import { useDispatch } from "react-redux";
import { saveEmployee } from "services/Employee";
import { updateTableAction } from "store/Employees/Employees.actions";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/Action";
import { Employee } from "types/Employee";
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
  specialties: [{
    id: 1,
    name: 'Desenvolvedor de Software'
  }],
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
      dispatch(updateTableAction())
    } catch (error: any) {
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor!!", "error");
      } else {
        Swal.fire("Oops!", "" + error.response.data, "error");
      }
    }
  }
  return (
    <FormTemplate
      initForm={initForm}
      stateForm={StateFormEnum.SAVING}
      submit={submit}
      isEditablePassword={false} />
  )
}

export default FormNewEmployee;