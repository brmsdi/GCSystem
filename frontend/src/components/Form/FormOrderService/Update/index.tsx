import { useDispatch, useSelector } from "react-redux";
import { updateOrderService } from "services/order-service";
import { updateOrderServiceTableAction } from "store/OrderServices/order-services.actions";
import { selectStateSelectedOrderService } from "store/OrderServices/order-services.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { OrderService } from "types/order-service";
import FormTemplate from "..";

const FormUpdateOrderService = () => {
  const dispatch = useDispatch()
  const selectedOrderService: OrderService = useSelector(selectStateSelectedOrderService)
  
  async function submit(form: OrderService) {
    try {
      const result = await updateOrderService(form);
      await Swal.fire('Ebaa!', result, 'success')
      dispatch(updateOrderServiceTableAction())
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
      initForm={selectedOrderService}
      stateForm={StateFormEnum.EDITING}
      submit={submit}
      isNewRegisterForm={false} />
  )
}

export default FormUpdateOrderService;