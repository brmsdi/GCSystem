import { useDispatch, useSelector } from "react-redux";
import { updateEmployee } from "services/employee";
import { updateEmployeeTableAction } from "store/Employees/employees.actions";
import { selectStateSelectedEmployee } from "store/Employees/employees.selectors";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Employee } from "types/employee";
import FormTemplate from "..";

const FormUpdateEmployee = () => {
  const dispatch = useDispatch()
  const selectedEmployee: Employee = useSelector(selectStateSelectedEmployee)
  async function submit(form: Employee) {
    try {
      const result = await updateEmployee(form);
      await Swal.fire('Ebaa!', result, 'success')
      dispatch(updateEmployeeTableAction())
      return true
    } catch (error: any) {
      const errors = error.response.data.errors
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
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
      initForm={selectedEmployee}
      stateForm={StateFormEnum.EDITING}
      submit={submit}
      isActivedFieldPassword={false}
      isNewEmployeeForm={false} />
  )
}

export default FormUpdateEmployee;