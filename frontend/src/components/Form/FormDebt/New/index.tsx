import { useDispatch } from "react-redux";
import { saveDebt } from "services/debt";
import { updateDebtTableAction } from "store/Debts/debts.actions";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Debt, DebtEmpty } from "types/debt";
import FormDebt from "..";

let initForm: Debt = DebtEmpty;

const FormNewDebt = () => {
  const dispatch = useDispatch();
  async function submit(form: Debt) {
    try {
      await saveDebt(form);
      await Swal.fire("Ebaa!", "Cadastro realizado com sucesso", "success");
      dispatch(updateDebtTableAction());
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
    <FormDebt
      initForm={initForm}
      stateForm={StateFormEnum.SAVING}
      submit={submit}
      isActivedFieldPassword={true}
      isNewRegisterForm={true}
    />
  );
};

export default FormNewDebt;
