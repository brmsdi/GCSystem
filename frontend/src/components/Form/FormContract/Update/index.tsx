import { useDispatch, useSelector } from "react-redux";
import { updateContract } from "services/contract";
import { updateContractTableAction } from "store/Contracts/contracts.actions";
import { selectStateSelectedContract } from "store/Contracts/contracts.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Contract } from "types/contract";
import FormTemplate from "..";

const FormUpdateContract = () => {
  const dispatch = useDispatch();
  const selectedContract: Contract = useSelector(selectStateSelectedContract);
  async function submit(form: Contract) {
    try {
      const result = await updateContract(form);
      await Swal.fire("Ebaa!", result, "success");
      dispatch(updateContractTableAction());
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
      initForm={selectedContract}
      stateForm={StateFormEnum.EDITING}
      submit={submit}
      isActivedFieldPassword={false}
      isNewRegisterForm={false}
    />
  );
};

export default FormUpdateContract;
