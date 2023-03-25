import { useDispatch, useSelector } from "react-redux";
import { updateDebt } from "services/debt";
import { updateDebtTableAction } from "store/Debts/debts.actions";
import { selectStateSelectedDebt } from "store/Debts/debts.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Debt } from "types/debt";
import FormDebt from "..";

const FormUpdateDebt = () => {
  const dispatch = useDispatch();
  const selectedDebt: Debt = useSelector(selectStateSelectedDebt);
  async function submit(form: Debt) {
    try {
      await updateDebt(form);
      await Swal.fire("Ebaa!", "Registro atualizado com sucesso", "success");
      dispatch(updateDebtTableAction());
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
    <FormDebt
      initForm={selectedDebt}
      stateForm={StateFormEnum.EDITING}
      submit={submit}
      isActivedFieldPassword={false}
      isNewRegisterForm={false}
    />
  );
};

export default FormUpdateDebt;
