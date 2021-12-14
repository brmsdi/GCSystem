import { Link } from "react-router-dom";

const Aside = () => {
    return(
        <div>
            <aside>
                <div className="aside-header">
                    <span className="icon">icon</span>
                    <span>System</span>
                </div>
                <div className="aside-body">
                    <div className="aside-title">
                        <p>Gerenciar</p>
                        <ul>
                            <li>
                                <Link to="">
                                    <span>icon</span>
                                    <span>Condomínio</span>
                                </Link>
                            </li>
                            <li>
                                <Link to="">
                                    <span>icon</span>
                                    <span>Funcionário</span>
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </aside>
        </div>
    )
}

export default Aside;