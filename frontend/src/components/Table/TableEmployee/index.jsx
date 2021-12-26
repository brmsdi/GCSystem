const TableEmployee = () => {
    return(
        <div class="table-responsive">
        <table class="table table-striped">
            <thead>
                <tr>
                <th scope="col">#</th>
                <th scope="col">Nome</th>
                <th scope="col">RG</th>
                <th scope="col">CPF</th>
                <th scope="col">E-mail</th>
                <th scope="col">Cargo</th>
                <th scope="col">Especialidade</th>
                <th scope="col">Contratação</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <th scope="row">1</th>
                <td>Wisley Bruno Marques Franca</td>
                <td>1235265</td>
                <td>02356325536</td>
                <td>srmarquesms@gmail.com</td>
                <td>Analista de sistemas</td>
                <td>Desenvolvedor</td>
                <td>01/01/2022</td>
                </tr>
                <tr>
                <th scope="row">2</th>
                <td>Wisley Bruno Marques Franca</td>
                <td>1235265</td>
                <td>02356325536</td>
                <td>srmarquesms@gmail.com</td>
                <td>Analista de sistemas</td>
                <td>Desenvolvedor</td>
                <td>01/01/2022</td>
                </tr>
                
            </tbody>
        </table>
        </div>
        
    )
}

export default TableEmployee;