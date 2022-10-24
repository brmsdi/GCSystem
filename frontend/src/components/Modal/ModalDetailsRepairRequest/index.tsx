import Modal from "react-modal";
import { customStyles } from "types/modal";
import { RepairRequest } from "types/repair-request";
import { formatCoinPTBRForView } from "utils/coin-format";
import { statusColor } from "utils/format-color";
import { formatDateForView } from "utils/text-format";

Modal.setAppElement("#root");

interface IProps {
  modalIsOpen: boolean;
  openModal: Function;
  closeModal: Function;
  title: string;
  item: RepairRequest;
}
const ModalDetailsRepairRequest = (props: IProps) => {
  let item = props.item;
  function afterOpenModal() {
    // references are now sync'd and can be accessed.
    //subtitle.style.color = 'black';
  }

  function calculateExpenses(): number {
    if (!item.items) return 0;
    return item.items?.reduce(
      (previous, item) => item.quantity * item.value + previous,
      0
    );
  }

  function closeModal() {
    props.closeModal();
  }
  return props.modalIsOpen === true ? (
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
          <span className="modal-title">Descrição: </span>
          <span>{item.problemDescription}</span>
        </div>
        <div>
          <span className="modal-title">Data de solicitação: </span>
          <span>{formatDateForView(item.date)}</span>
        </div>
        <div>
          <span className="modal-title">Status: </span>
          <span className={"span-value " + statusColor(item.status.name)}>
            {item.status.name.toUpperCase()}
          </span>
        </div>
      </section>
      <section className="modal-section-item">
        <label className="modal-label">Itens utilizados</label>
        <div className="modal-header-title">
          <span className="modal-title">Descrição</span>
          <span className="modal-title">Quantidade</span>
          <span className="modal-title">Preço</span>
        </div>
        {item.items?.map((item) => (
          <div key={item.id}>
            <span className="modal-item-1">{item.description}</span>
            <span className="modal-item-2">{item.quantity}</span>
            <span className="modal-item-3">
              {formatCoinPTBRForView(item.value)}
            </span>
          </div>
        ))}
        <div className="modal-expenditure">
          <span className="modal-item-description modal-title">Despesas: </span>
          <span className="modal-item-value">
            {formatCoinPTBRForView(calculateExpenses())}
          </span>
        </div>
      </section>
      <section className="modal-section-comand">
        <button className="btn btn-secondary" onClick={closeModal}>
          Fechar
        </button>
      </section>
    </Modal>
  ) : null;
};

export default ModalDetailsRepairRequest;
