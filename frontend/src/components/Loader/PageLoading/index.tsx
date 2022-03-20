import loadingIMG from 'assets/img/loading.svg'
const PageLoading = (props:{title: string}) => {
    return (
        <div className="page-loading">
            <h1 className="page-loading-title">
                { props.title }
            </h1>
            <div>
                <img src={loadingIMG} alt="Carregando..."/>
            </div>
        </div>
    )
}
export default PageLoading;