import { useDispatch, useSelector } from "react-redux";
import { updateCondominium } from "services/condominium";
import { updateCondominiumTableAction } from "store/Condominium/condiminiums.actions";
import { selectStateSelectedCondominium } from "store/Condominium/condiminiums.selectors";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Condominium } from "types/condominium";
import FormTemplate from "..";

const FormUpdateCondominium = () => {
  const dispatch = useDispatch()
  const selectedCondominium: Condominium = useSelector(selectStateSelectedCondominium)
  async function submit(form: Condominium) {
    try {
      const result = await updateCondominium(form);
      await Swal.fire('Ebaa!', result, 'success')
      dispatch(updateCondominiumTableAction())
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
      initForm={selectedCondominium}
      stateForm={StateFormEnum.EDITING}
      submit={submit}
      isActivedFieldPassword={false}
      isNewRegisterForm={false} />
  )
}

export default FormUpdateCondominium;