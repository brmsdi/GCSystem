import ContentLoader from "react-content-loader"

const PaginationLoading = (props: any) => {
  
 return (
   <nav aria-label="...">
     <ul className="pagination pagination-sm justify-content-center">
       <ContentLoader
         speed={2}
         width={"100%"}
         height={"100%"}
         viewBox="0 0 800 200"
         backgroundColor="#f3f3f3"
         foregroundColor="#ecebeb"
         {...props}
       >
       <rect x="4" y="2" rx="0" ry="0" width="800" height="100%" />
       </ContentLoader>
     </ul>
   </nav>
 );
}

export default PaginationLoading
