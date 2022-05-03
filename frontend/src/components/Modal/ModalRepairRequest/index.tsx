import Modal from 'react-modal'
import { RepairRequest } from 'types/repair-request';
import { formatCoinPTBRForView } from 'utils/coin-format';
import { formatDateForView } from 'utils/textFormt';

Modal.setAppElement('#root')

const customStyles = {
    content: {
        top: '50%',
        left: '50%',
        right: '20%',
        bottom: 'auto',
        marginRight: '-10%',
        transform: 'translate(-50%, -50%)',
    },
};

interface IProps {
    modalIsOpen: boolean;
    openModal: Function;
    closeModal: Function;
    title: string;
    item: RepairRequest
}
const ModalRepairRequest = (props: IProps) => {
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
        <h2>{props.title}</h2>
        <hr />
        <section className="modal-section">
          <label className="modal-label">Reparo</label>
          <div>
            <span className="modal-title">Solicitante: </span>
            <span>{item.lessee.name}</span>
          </div>
          <div>
            <span className="modal-title">Condomínio: </span>
            <span>{item.condominium.name}</span>
          </div>
          <div>
            <span className="modal-title">Apartamento: </span>
            <span>{item.apartmentNumber}</span>
          </div>
          <div>
            <span className="modal-title">Data de solicitação: </span>
            <span>{formatDateForView(item.date)}</span>
          </div>
        </section>
        <section className="modal-section-item">
          <label className="modal-label">Itens utilizados</label>
          <div>
            <span className="modal-item-description modal-title">Nome</span>
            <span className="modal-item-quantity modal-title">Quantidade</span>
            <span className="modal-item-value modal-title">Preço</span>
          </div>
          {item.items?.map((item) => (
            <div key={item.id}>
              <span className="modal-item-description">{item.description}</span>
              <span className="modal-item-quantity">{item.quantity}</span>
              <span className="modal-item-value">
                {formatCoinPTBRForView(item.value)}
              </span>
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

export default ModalRepairRequest;