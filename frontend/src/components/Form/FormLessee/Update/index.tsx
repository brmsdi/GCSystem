import { useDispatch, useSelector } from "react-redux";
import { updateLessee } from "services/lessee";
import { updateLesseeTableAction } from "store/Lessees/lessees.actions";
import { selectStateSelectedLessee } from "store/Lessees/lessees.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Lessee } from "types/lessee";
import FormTemplate from "..";

const FormUpdateLessee = () => {
  const dispatch = useDispatch();
  const selectedLessee: Lessee = useSelector(selectStateSelectedLessee);
  async function submit(form: Lessee) {
    try {
      const result = await updateLessee(form);
      await Swal.fire("Ebaa!", result, "success");
      dispatch(updateLesseeTableAction());
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
      initForm={selectedLessee}
      stateForm={StateFormEnum.EDITING}
      submit={submit}
      isActivedFieldPassword={false}
      isNewRegisterForm={false}
    />
  );
};

export default FormUpdateLessee;
