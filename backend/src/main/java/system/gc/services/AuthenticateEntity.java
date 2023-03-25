package system.gc.services;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
public interface AuthenticateEntity<T> {

    /**
     * <p>Autenticar o usuário pelo CPF.</p>
     * @param cpf CPF do usuário a ser autenticado.
     * @return Registro do usuário.
     */
    T getAuthenticationByCPF(String cpf);
}