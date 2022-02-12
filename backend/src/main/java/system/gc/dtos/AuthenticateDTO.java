package system.gc.dtos;

public interface AuthenticateDTO<DTO, E> {

    /**
     * @return 'DTO' DTO da entidade correspondente ao tipo de autenticação. Instancia com senha para gerenciamento interno do ProviderManager.
     */
    DTO initAuthenticate(E e);
}
