import { useDispatch } from "react-redux";
import { saveLessee } from "services/lessee";
import { updateLesseeTableAction } from "store/Lessees/lessees.actions";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Lessee } from "types/lessee";
import FormTemplate from "..";

let initForm: Lessee = {
  name: '',
  rg: '',
  cpf: '',
  birthDate: '',
  email: '',
  contactNumber: '',
  status: {
    id: 1,
    name: 'Ativo'
  },
  password: ''
}

const FormNewLessee = () => {
  const dispatch = useDispatch();
  async function submit(form: Lessee) {
    try {
      const result = await saveLessee(form);
      await Swal.fire('Ebaa!', result, 'success')
      dispatch(updateLesseeTableAction())
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
      isNewRegisterForm={true} />
  )
}

export default FormNewLessee;