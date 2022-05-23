interface IProps {
  message: string;
  quantity: number;
  format?: boolean;
  icon: string;
  activity: string;
}
const BoxInformations = (props: IProps) => {
  const formatTitle = (_quantity: number, _message: string) => {
    if (_quantity === 0 || _quantity === 1) {
      return _message;
    } else {
      return _message.concat("s");
    }
  };
  return (
    <div className="box-informations">
      <span className={props.activity}>
        <i className={props.icon}></i>{" "}
        {props.format
          ? formatTitle(props.quantity, props.message)
          : props.message}
      </span>
      <span>-</span>
      <span className={`quantity-value ${props.activity}`}>
        {props.quantity}
      </span>
    </div>
  );
};

export default BoxInformations;
