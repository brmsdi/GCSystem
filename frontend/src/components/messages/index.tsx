const Alert = (props: { msg: string }) => {
  return (
    <div id="alert">
      <span>{props.msg}</span>
    </div>
  );
};

export default Alert;
