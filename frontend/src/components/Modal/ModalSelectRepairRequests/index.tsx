import Modal from "react-modal";
import { useDispatch } from "react-redux";
import { changeStateModalOrderServiceRepairRequests } from "store/RepairRequests/repair-requests.actions";
import { customStyles } from "types/modal";
import ModalTableRepairRequests from "../ModalTableRepairRequests";

Modal.setAppElement("#root");

interface IPropsModalSelectRequests {
  title: string;
  modalIsOpen: boolean;
}

const ModalSelectRepairRequests = (props: IPropsModalSelectRequests) => {
  const dispatch = useDispatch();
  function afterOpenModal() {
    // references are now sync'd and can be accessed.
    //subtitle.style.color = 'black';
  }

  function closeModal() {
    dispatch(changeStateModalOrderServiceRepairRequests({ isOpen: false }));
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
        <div className="content-table">
          <ModalTableRepairRequests />
        </div>
      </section>
    </Modal>
  ) : null;
};

export default ModalSelectRepairRequests;
