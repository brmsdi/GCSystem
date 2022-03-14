import Aside from "components/Aside";

const TemplateApp = (props: {page: any}) => {
    function headerOpenOrClose() {
        document.getElementById('header-aside')?.classList.toggle('open')
      }
      return (
        <div className="content">
          <header id="header-aside">
            <div id="headerEvent" onClick={() => headerOpenOrClose()}></div>
            <Aside />
          </header>
         {props.page}
        </div>
      )
}


export default TemplateApp;