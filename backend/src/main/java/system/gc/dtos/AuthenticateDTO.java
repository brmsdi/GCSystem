package system.gc.dtos;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface AuthenticateDTO<DTO, E> {

    /**
     * @return 'DTO' DTO da entidade correspondente ao tipo de autenticação. Instancia com senha para gerenciamento interno do ProviderManager.
     */
    DTO initAuthenticate(E e);
}
