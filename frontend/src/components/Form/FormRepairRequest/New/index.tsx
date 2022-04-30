import { useDispatch } from "react-redux";
import { saveRepairRequest } from "services/repair-request";
import { updateRepairRequestTableAction } from "store/RepairRequests/repair-requests.actions";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { RepairRequest, RepairRequestEmpty } from "types/repair-request";
import FormTemplate from "..";

let initForm: RepairRequest = RepairRequestEmpty;

const FormNewRepairRequest = () => {
  const dispatch = useDispatch();
  async function submit(form: RepairRequest) {
    try {
      const result = await saveRepairRequest(form);
      await Swal.fire('Ebaa!', result, 'success')
      dispatch(updateRepairRequestTableAction())
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
      isNewRegisterForm={true} />
  )
}

export default FormNewRepairRequest;