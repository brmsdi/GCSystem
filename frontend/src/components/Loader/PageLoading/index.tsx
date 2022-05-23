import loadingIMG from "assets/img/loading8.svg";
interface IProps {
  title: string;
  imageForTitle?: any;
}
const PageMessage = (props: IProps) => {
  return (
    <div className="page-loading">
      <h1 className="page-loading-title">{props.title}</h1>
      <div>
        <img
          src={props.imageForTitle ? props.imageForTitle : loadingIMG}
          alt="Carregando..."
        />
      </div>
    </div>
  );
};
export default PageMessage;
