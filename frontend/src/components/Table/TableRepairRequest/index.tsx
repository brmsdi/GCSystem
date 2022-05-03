import Alert from "components/messages";
import ModalRepairRequest from "components/Modal/ModalRepairRequest";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { deleteRepairRequest } from "services/repair-request";
import { selectRepairRequestTableAction, setStateFormRepairRequestAction, updateRepairRequestTableAction } from "store/RepairRequests/repair-requests.actions";
import { selectAllRepairRequests } from "store/RepairRequests/repair-requests.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { PaginationRepairRequest, RepairRequest, RepairRequestEmpty } from "types/repair-request";
import { formatDateForView } from "utils/textFormt";

const TableRepairRequest= () => {
  const dispatch = useDispatch();
  const page: PaginationRepairRequest = useSelector(selectAllRepairRequests);
  const [modalIsOpen, setIsOpen] = useState(false);
  const [repairRequestSelectedFromModal, setRepairRequestSelectedFromModal] = useState<RepairRequest>(RepairRequestEmpty);

  async function clickButtonUpdate(selected: RepairRequest | undefined) {
    let form = document.querySelector(".content-form");
    if (form != null && selected) {
      if (!form.classList.contains('active')) {
        form.classList.toggle('active')
      }
      dispatch(selectRepairRequestTableAction(selected))
      dispatch(setStateFormRepairRequestAction(StateFormEnum.UPDATE))
    }
  }

  async function clickButtonDelete(ID: number) {
    const result = await Swal.fire({
      title: 'Você deseja deletar esse registro?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    })

    if (result.isConfirmed) {
      try {
        const data = await deleteRepairRequest(ID)
        Swal.fire('Êbaa!', '' + data, 'success')
        dispatch(updateRepairRequestTableAction())
      } catch (error: any) {
        if (!error.response) {
          Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
        } else {
          let message = error.response.data.errors[0].message;
          Swal.fire("Oops!", "" + message, "error");
        }
      }
    }
  }

  function plusInformations(repairRequest: RepairRequest) {
    changeModal()
    setRepairRequestSelectedFromModal({...repairRequest})
  }

  function changeModal() {
    setIsOpen(!modalIsOpen)
    if (modalIsOpen === false) setRepairRequestSelectedFromModal(RepairRequestEmpty)
  }

  return (
    <>
    {
      modalIsOpen && repairRequestSelectedFromModal.id !== 0 ? (<ModalRepairRequest 
        modalIsOpen={modalIsOpen}
        openModal={changeModal}
        closeModal={changeModal}
        title={"Informações"}
        item={repairRequestSelectedFromModal} />) : (null)
    }
    
    <div className="table-responsive">
      {
        
        (page.empty === true) ?
          (
            (<Alert msg="Nenhum registro encontrado!" />)
          )
          :
          <table className="table table-striped">
            <thead className="thead-max">
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Nome do locatário</th>
                <th scope="col">Nome do condomínio</th>
                <th scope="col">Nº do apartamento</th>
                <th scope="col">Data de solicitação</th>
                <th scope="col">Tipo de problema</th>
                <th scope="col">Status</th>
                <th scope="col">#</th>
              </tr>
            </thead>
            <tbody>
              {
                page?.content?.map((item: RepairRequest) => {
                  return <ItemTable 
                  key={item.id} 
                  item={item} 
                  toogleClass={clickButtonUpdate} 
                  clickButtonDelete={clickButtonDelete} 
                  plusInformations={plusInformations} />;
                })
              }
            </tbody>
          </table>
      }

    </div>
    </>
  )
}

interface IProps {
  item: RepairRequest, 
  toogleClass: Function, 
  clickButtonDelete: Function,
  plusInformations: Function
}
const ItemTable = (props: IProps) => {
  let item = props.item;
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td>{item.id}</td>
      <th className="thead-min">Nome do locatário</th>
      <td>{item.lessee.name}</td>
      <th className="thead-min">Nome do condmínio</th>
      <td>{item.condominium.name}</td>
      <th className="thead-min">Nº do apartamento</th>
      <td>{item.apartmentNumber}</td>
      <th className="thead-min">Data de solicitação</th>
      <td>{formatDateForView(item.date)}</td>
      <th className="thead-min">Tipo de problema</th>
      <td>{item.typeProblem.name}</td>
      <th className="thead-min">Status</th>
      <td>{item.status.name}</td>
      <th className="thead-min">Opções</th>
      <td>
        <button
          id="btn-table-repair-request-update"
          type="button"
          aria-label="Atualizar essa solicitação de reparo"
          title="Atualizar essa solictação de reparo"
          className="btn btn-primary btn-table-options"
          onClick={(e) => props.toogleClass(item)}><span aria-hidden="true" ><i className="bi bi-clipboard-data"></i></span>
        </button>
        <button
          id="btn-table-repair-request-delete"
          type="button"
          aria-label="Deletar essa solicitação de reparo"
          title="Deletar essa solicitação de reparo"
          className="btn btn-danger btn-table-options"
          onClick={() => props.clickButtonDelete(item.id)}><span aria-hidden="true"><i className="bi bi-trash"></i></span>
        </button>        
        <button
          id="btn-table-repair-request-plus-info"
          type="button"
          aria-label="Mais informações dessa solicitação de reparo"
          title="Mais informações dessa solicitação de reparo"
          className="btn btn-secondary btn-table-options"
          onClick={() => props.plusInformations(item)}><span aria-hidden="true"><i className="bi bi-three-dots"></i></span>
        </button>    
      </td>
    </tr>
  )
}

export default TableRepairRequest;