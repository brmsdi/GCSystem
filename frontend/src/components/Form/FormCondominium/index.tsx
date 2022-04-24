import { useState, useEffect } from "react";
import { getZipCodeService } from "services/cep";
import { getAllStatus } from "services/status";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Condominium } from "types/condominium";
import { Localization } from "types/localization";
import { LocalizationCondominium } from "types/LocalizationCondominium";
import { Status } from "types/status";
import { isValidZipCode, statusIsSelected } from "utils/verifications";

interface IProps {
  initForm: Condominium;
  stateForm: StateFormEnum;
  submit: Function;
  isActivedFieldPassword: boolean;
  isNewRegisterForm: boolean;
}

const FormTemplate = (props: IProps) => {
  const [form, setForm] = useState<Condominium>(props.initForm);
  const [status, setStatus] = useState<Status[]>([]);
  const [localization, setLocalization] = useState<Localization>(
    props.initForm.localization.localization
  );
  const [localizationNumber, setLocalizationNumber] = useState<LocalizationCondominium>(props.initForm.localization);
  const [zipCode, setZipCode] = useState("");
  const [zipCodeProcessing, setZipCodeProcessing] = useState(false);
  function changeInput(value: any) {
    setForm((form) => ({ ...form, ...value }));
  }

  function changeStatus(value: number) {
    for (var index = 0; index < status.length; index++) {
      let statusSelected = status.at(index);
      if (statusSelected?.id === value) {
        setForm({ ...form, status: statusSelected });
        break;
      }
    }
  }

  async function changeLocalization(value: any) {
    setLocalization((localization) => ({ ...localization, ...value }));
  }

  function changeLocalizationNumber(value: any) {
    setLocalizationNumber((localizationNumber) => ({
      ...localizationNumber,
      ...value,
    }));
  }

  async function changeZipCode(value: any) {
    if (value.length <= 8) {
      setZipCode(value);
      if (isValidZipCode(value)) {
        getZipCodeInformation(value);
      } else {
        clearFieldsLocalization();
      }
    }
  }

  useEffect(() => {
    setForm((form) => ({ ...form, ...props.initForm }));
    setLocalization(props.initForm.localization.localization);
    setLocalizationNumber(props.initForm.localization);
    setZipCode(
      props.initForm.localization.localization.zipCode === ""
        ? ""
        : props.initForm.localization.localization.zipCode
    );
  }, [props.initForm]);

  useEffect(() => {
    try {
      getAllStatus().then((response) => {
        setStatus(response.data);
      });
    } catch (error: any) {
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
      }
    }
  }, []);

  async function submit(event: any) {
    event.preventDefault();
    if (!statusIsSelected(form.status)) {
      Swal.fire('Oops!', 'Selecione o campo status', 'error')
      return
    } 
    setLocalizationNumber({
      ...localizationNumber,
      localization: localization,
    });
    let newLocalizationCondominium: LocalizationCondominium = {
      ...localizationNumber,
      localization: localization,
    };
    let newForm: Condominium = {
      ...form,
      localization: newLocalizationCondominium,
    };
    setForm({ ...newForm });
    const result = await props.submit(newForm);
    if (result === true) {
      if (props.isNewRegisterForm === true) {
        clearForm();
        clearFieldsLocalizationCondominium();
        clearFieldsLocalization();
        setZipCode("");
      } else {
        setForm({ ...form });
      }
    }
  }

  function getZipCodeInformation(zipCode: any) {
    setZipCodeProcessing(true);
    getZipCodeService(zipCode)
      .then((response) => {
        if (response.data.erro) {
          setZipCode("");
          Swal.fire("Oops!", "CEP invalido. Tente novamente!", "error");
          setZipCodeProcessing(false);
          return;
        }
        let newLocalization = {
          ...localization,
          zipCode: zipCode,
          road: response.data.logradouro,
          name: response.data.bairro,
        };
        setLocalization({ ...newLocalization });
        setZipCodeProcessing(false);
      })
      .catch((error) => {
        console.log(error);
        setZipCodeProcessing(false);
      });
  }

  async function clearField() {
    clearForm();
    clearFieldsLocalizationCondominium();
    clearFieldsLocalization();
    clearFieldZipCode();
  }

  function clearForm() {
    setForm({ ...props.initForm });
  }

  function clearFieldsLocalizationCondominium() {
    let initLocalizationCondominium: LocalizationCondominium =
      props.initForm.localization;
    setLocalizationNumber({ ...initLocalizationCondominium });
  }

  function clearFieldsLocalization() {
    let initLocalization: Localization =
      props.initForm.localization.localization;
    setLocalization({ ...initLocalization });
  }

  function clearFieldZipCode() {
    setZipCode('')
  }

  return (
    <form onSubmit={submit}>
      <div className="row-form-1">
        <div className="form-container f4">
          <label htmlFor="inpturName">Nome</label>
          <input
            type="text"
            id="inputName"
            placeholder="Nome do condomínio"
            name="name"
            value={form.name}
            onChange={(e) => changeInput({ name: e.target.value })}
            required
          />
        </div>
        <div className="form-container f4">
          <label htmlFor="inputDescription">Descrição</label>
          <input
            type="text"
            id="inputDescription"
            placeholder="Descrição"
            name="description"
            value={form.description}
            onChange={(e) => changeInput({ description: e.target.value })}
            required
          />
        </div>
        <div className="form-container f4">
          <label htmlFor="inputNumberApartments">Nº apartamentos</label>
          <input
            type="number"
            id="inputNumberApartments"
            placeholder="Nº apartamentos"
            name="numberApartments"
            value={form.numberApartments}
            onChange={(e) => changeInput({ numberApartments: e.target.value })}
            required
          />
        </div>
        <div className="form-container f4">
          <label htmlFor="inputStatus">Status</label>
          <select
            id="inputStatus"
            name="status"
            value={form.status.id ? form.status.id : 0}
            onChange={(e) => changeStatus(parseInt(e.target.value))}
          >
            <option key={0} value={0}></option>
            {status.map((item) => (
              <option key={item.id} value={item.id}>
                {item.name}
              </option>
            ))}
          </select>
        </div>
      </div>
      <div className="row-form-1">
        <div className="form-container f4">
          <label htmlFor="inputNumber">CEP</label>
          <input
            type="number"
            id="inputZipCode"
            placeholder="CEP..."
            name="zipCode"
            value={zipCode}
            onChange={(e) => changeZipCode(e.target.value)}
            required
            disabled={zipCodeProcessing}
          />
        </div>
        <div className="form-container f4">
          <label htmlFor="inputNumber">Rua</label>
          <input
            type="text"
            id="inputRoad"
            placeholder="RUA..."
            name="road"
            value={localization.road}
            onChange={(e) => changeLocalization({ road: e.target.value })}
            disabled
            required
          />
        </div>
        <div className="form-container f4">
          <label htmlFor="inputDistrict">Bairro</label>
          <input
            type="text"
            id="inputDistrict"
            placeholder="nome do bairro..."
            name="name"
            value={localization.name}
            onChange={(e) => changeLocalization({ name: e.target.value })}
            required
            disabled
          />
        </div>
        <div className="form-container f4">
          <label htmlFor="inputNumber">Número</label>
          <input
            type="number"
            id="inputNumber"
            placeholder="Número"
            name="number"
            value={localizationNumber.number}
            onChange={(e) =>
              changeLocalizationNumber({ number: e.target.value })
            }
            required
          />
        </div>
      </div>
      <div className="row-form-1">
        <div className="form-container l4 btns">
          <button type="submit" className="btn btn-success">
            Salvar
          </button>
          <button
            type="button"
            className="btn btn-secondary"
            onClick={clearField}
          >
            {props.isActivedFieldPassword === true ? "Limpar" : "Restaurar"}
          </button>
        </div>
      </div>
    </form>
  );
};

export default FormTemplate;
