import { useDispatch, useSelector } from "react-redux";
import { updateRepairRequest } from "services/repair-request";
import { updateRepairRequestTableAction } from "store/RepairRequests/repair-requests.actions";
import { selectStateSelectedRepairRequest } from "store/RepairRequests/repair-requests.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { RepairRequest } from "types/repair-request";
import FormTemplate from "..";

const FormUpdateRepairRequest = () => {
  const dispatch = useDispatch();
  const selectedRepairRequest: RepairRequest = useSelector(
    selectStateSelectedRepairRequest
  );
  async function submit(form: RepairRequest) {
    try {
      const result = await updateRepairRequest(form);
      await Swal.fire("Ebaa!", result, "success");
      dispatch(updateRepairRequestTableAction());
      return true;
    } catch (error: any) {
      const errors = error.response.data.errors;
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
      } else if (errors) {
        Swal.fire("oops!", errors[0].message, "error");
      } else {
        Swal.fire("Oops!", "" + error.response.data, "error");
      }
    }
  }
  return (
    <FormTemplate
      initForm={selectedRepairRequest}
      stateForm={StateFormEnum.EDITING}
      submit={submit}
      isNewRegisterForm={false}
    />
  );
};

export default FormUpdateRepairRequest;
