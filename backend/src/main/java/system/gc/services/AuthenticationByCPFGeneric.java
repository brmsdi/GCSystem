package system.gc.services;


public interface AuthenticationByCPFGeneric<E, REPOSITORY extends AuthenticateEntity<E>> {

    /**
     * @param username Informação de login
     * @return Entidade correspondente ao tipo de autenticação. Employee ou Lessee!
     */
    default E authentication(String username, REPOSITORY repository) {
        return repository.getAuthenticationByCPF(username);
    }
}
