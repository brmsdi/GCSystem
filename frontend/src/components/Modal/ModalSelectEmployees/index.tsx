import Modal from 'react-modal'
import { useDispatch } from 'react-redux';
import { changeStateModalOrderServiceEmployees } from 'store/OrderServices/order-services.actions';
import { customStyles } from 'types/modal';
import ModalTableEmployees from '../ModalTableEmployees';

Modal.setAppElement('#root')

interface IPropsModalSelectEmployees {
    title: string;
    modalIsOpen: boolean;
}

const ModalSelectEmployees = (props: IPropsModalSelectEmployees) => {
    const dispatch = useDispatch()

    function afterOpenModal() {
        // references are now sync'd and can be accessed.
        //subtitle.style.color = 'black';
    }
    
    function closeModal() {
        dispatch(changeStateModalOrderServiceEmployees({isOpen: false}))
    }

    return props.modalIsOpen === true ? (
      <Modal
        isOpen={props.modalIsOpen}
        onAfterOpen={afterOpenModal}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="Modal">
        <h2 className="modal-h2">{props.title}</h2>
        <hr />
        <section className="modal-section">
          <div className="content-table">
            <ModalTableEmployees />
          </div>
        </section>
      </Modal>
    ) : null;
 }

 export default ModalSelectEmployees;