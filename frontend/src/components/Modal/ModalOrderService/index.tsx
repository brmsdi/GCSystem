import Modal from 'react-modal'
import { customStyles } from 'types/modal';
import { OrderService } from 'types/order-service';
import { statusColor } from 'utils/format-color';

Modal.setAppElement('#root')

interface IProps {
    modalIsOpen: boolean;
    openModal: Function;
    closeModal: Function;
    title: string;
    item: OrderService
}
const ModalOrderService = (props: IProps) => {
    let item = props.item;
    function afterOpenModal() {
        // references are now sync'd and can be accessed.
        //subtitle.style.color = 'black';
    }

    function closeModal() {
      props.closeModal()
    }
    return (
      <Modal
        isOpen={props.modalIsOpen}
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
            <span className="modal-title">Data: </span>
            <span>{item.generationDate}</span>
          </div> 
          <div>
            <span className="modal-title">Status: </span>
            <span className={"span-value " + statusColor(item.status.name)}>{item.status.name.toUpperCase()}</span>
          </div>
        </section>
        <section className="modal-section-item">
          <label className="modal-label">Funcionários responsáveis</label>
          <div className="modal-header-title">
            <span className="modal-title">Nome</span>
          </div>
          {item.employees?.map((item) => (
            <div key={item.id}>
              <span className="modal-item-description">{item.name}</span>
            </div>
          ))}
        </section>
        <section className="modal-section-comand">
            <button className="btn btn-secondary" onClick={closeModal}>
                Fechar
            </button>
        </section>
        
      </Modal>
    );
}

export default ModalOrderService;