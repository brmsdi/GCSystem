import { useDispatch } from "react-redux";
import { saveCondominium } from "services/condominium";
import { updateCondominiumTableAction } from "store/Condominium/condiminiums.actions";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Condominium } from "types/condominium";
import FormTemplate from "..";

let initForm: Condominium = {
  name: '',
  description: '',
  numberApartments: 0,
  status: {
      id: 0,
      name: ''
  },
  localization: {
      number: '',
      localization: {
          name: '',
          road: '',
          zipCode: ''
      }
  }
}

const FormNewCondominium = () => {
  const dispatch = useDispatch();
  async function submit(form: Condominium) {
    try {
      const result = await saveCondominium(form);
      await Swal.fire('Ebaa!', result, 'success')
      dispatch(updateCondominiumTableAction())
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

export default FormNewCondominium;