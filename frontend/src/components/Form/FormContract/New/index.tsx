import { useDispatch } from "react-redux";
import { saveContract } from "services/contract";
import { updateContractTableAction } from "store/Contracts/contracts.actions";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Contract, ContractEmpty } from "types/contract";
import FormTemplate from "..";

let initForm: Contract = ContractEmpty;

const FormNewContract = () => {
  const dispatch = useDispatch();
  async function submit(form: Contract) {
    try {
      const result = await saveContract(form);
      await Swal.fire("Ebaa!", result, "success");
      dispatch(updateContractTableAction());
      return true;
    } catch (error: any) {
      const errors = error.response.data.errors;
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor!!", "error");
      } else if (errors) {
        Swal.fire("oops!", errors[0].message, "error");
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
      isActivedFieldPassword={true}
      isNewRegisterForm={true}
    />
  );
};

export default FormNewContract;
