import { useDispatch } from "react-redux";
import { saveOrderService } from "services/order-service";
import { updateOrderServiceTableAction } from "store/OrderServices/order-services.actions";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { OrderService, OrderServiceEmpty } from "types/order-service";
import FormTemplate from "..";

let initForm: OrderService = OrderServiceEmpty;

const FormNewOrderService = () => {
  const dispatch = useDispatch();
  async function submit(form: OrderService) {
    try {
      const result = await saveOrderService(form);
      await Swal.fire('Ebaa!', result, 'success')
      dispatch(updateOrderServiceTableAction())
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

export default FormNewOrderService;