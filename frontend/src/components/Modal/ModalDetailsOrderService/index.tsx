import Modal from 'react-modal'
import { useDispatch, useSelector } from 'react-redux';
import { detailsModalOrderService } from 'store/OrderServices/order-services.actions';
import { selectDetailsModalOrderService } from 'store/OrderServices/order-services.selector';
import { customStyles } from 'types/modal';
import { OrderService, OrderServiceEmpty } from 'types/order-service';
import { statusColor } from 'utils/format-color';
import { formatDateForView } from 'utils/textFormt';

Modal.setAppElement('#root')

interface IProps {
    title: string;
}

const ModalDetailsOrderService = (props: IProps) => {
    const dispatch = useDispatch()
    const orderServiceSelectedFromModal : OrderService = useSelector(selectDetailsModalOrderService)
    function afterOpenModal() {
        // references are now sync'd and can be accessed.
        //subtitle.style.color = 'black';
    }

    function closeModal() {
      dispatch(detailsModalOrderService({...OrderServiceEmpty}))
    }
    return orderServiceSelectedFromModal.id ? (
      <Modal
        isOpen={true}
        onAfterOpen={afterOpenModal}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="Modal"
      >
        <h2 className="modal-h2">{props.title}</h2>
        <hr />
        <section className="modal-section">
          <label className="modal-label">Ordem de serviço</label>
          <div>
            <span className="modal-title">Data de abertura: </span>
            <span>{formatDateForView(orderServiceSelectedFromModal.generationDate)}</span>
          </div> 
          <div>
            <span className="modal-title">Status: </span>
            <span className={"span-value " + statusColor(orderServiceSelectedFromModal.status.name)}>{orderServiceSelectedFromModal.status.name.toUpperCase()}</span>
          </div>
        </section>
        <section className="modal-section-item">
          <label className="modal-label">Reparos</label>
          <div className="modal-header-title">
            <span className="modal-title">ID</span>
            <span className="modal-title">Descrição</span>
            <span className="modal-title">Tipo de problema</span>
          </div>
          {orderServiceSelectedFromModal.repairRequests?.map((item) => (
            <div key={orderServiceSelectedFromModal.id}>
              <span className="modal-item-2">{item.id}</span>
              <span className="modal-item-1">{item.problemDescription}</span>
              <span className="modal-item-3">{item.typeProblem.name}</span>
            </div>
          ))}
        </section>
        <section className="modal-section-item">
          <label className="modal-label">Funcionários responsáveis</label>
          <div className="modal-header-title">
            <span className="modal-title">Nome</span>
          </div>
          {orderServiceSelectedFromModal.employees?.map((item) => (
            <div key={orderServiceSelectedFromModal.id}>
              <span className="modal-item-1">{item.name}</span>
            </div>
          ))}
        </section>
        <section className="modal-section-comand">
            <button className="btn btn-secondary" onClick={closeModal}>
                Fechar
            </button>
        </section>
        
      </Modal>
    ) : null
}

export default ModalDetailsOrderService;