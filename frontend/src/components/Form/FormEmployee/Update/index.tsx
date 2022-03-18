import { useSelector } from "react-redux";
import { updateEmployee } from "services/Employee";
import { selectStateSelectedEmployee } from "store/Employees/Employees.selectors";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/Action";
import { Employee } from "types/Employee";
import FormTemplate from "..";

const FormUpdateEmployee = () => {
  const selectedEmployee: Employee = useSelector(selectStateSelectedEmployee)
  async function submit(form: Employee) {
    try {
      const result = await updateEmployee(form);
      Swal.fire('Ebaa!', result, 'success')
    } catch (error: any) {
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
      } else {
        Swal.fire("Oops!", "" + error.response.data, "error");
      }
    }
  }
  return (
    <FormTemplate
      initForm={selectedEmployee}
      stateForm={StateFormEnum.EDITING}
      submit={submit}
      isEditablePassword={true} />
  )
}

export default FormUpdateEmployee;