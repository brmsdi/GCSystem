import ContentLoader from "react-content-loader"

const TableLoaderTemplate = (props) => {
    return (
        <ContentLoader 
        speed={2}
        width={'100%'}
        height={300}
        backgroundColor="#f3f3f3"
        foregroundColor="#ecebeb"
        title="Carregando"
        {...props}
      >
        <rect x="3" y="19" rx="0" ry="0" width="95%" height="21" /> 
        <rect x="3" y="44" rx="0" ry="0" width="95%" height="21" /> 
        <rect x="3" y="123" rx="0" ry="0" width="95%" height="21" /> 
        <rect x="3" y="70" rx="0" ry="0" width="95%" height="21" /> 
        <rect x="3" y="95" rx="0" ry="0" width="95%" height="21" />
      </ContentLoader>
    )
}

export default TableLoaderTemplate;