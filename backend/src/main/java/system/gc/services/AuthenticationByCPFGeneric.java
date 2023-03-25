package system.gc.services;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
public interface AuthenticationByCPFGeneric<E, REPOSITORY extends AuthenticateEntity<E>> {

    /**
     * <p>Este método realiza a busca pelos dados do usuário pelo CPF.</p>
     * @param username Dado para realizar login
     * @return Entidade correspondente ao tipo de autenticação.
     */
    default E authentication(String username, REPOSITORY repository) {
        return repository.getAuthenticationByCPF(username);
    }
}
