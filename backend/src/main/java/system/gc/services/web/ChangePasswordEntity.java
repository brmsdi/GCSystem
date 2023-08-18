package system.gc.services.web;

import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface ChangePasswordEntity<T> {
    /**
     * <p>Busca um registro por um endereço de e-mail.</p>
     * @param email Endereço de e-mail para realizar a busca.
     * @return Registro encotrado no banco de dados.
     */
    Optional<T> findByEMAIL(String email);

    /**
     * <p>Verifica se uma solicitação está em aberto.</p>
     * @param ID ID da solicitação
     * @param statusID ID do status
     * @return O resgistro encontrado de acordo com o status.
     */
    Optional<T> checkIfThereISAnOpenRequest(Integer ID, Integer statusID);

    /**
     * <p>Buscar o registro para troca de senha.</p>
     * @param email Endereço de email para qual o registro pertence.
     * @param ID ID LogChangePassword registrado no banco.
     * @param code Codigo do LogChangePassword.
     * @param statusID ID do status
     * @return Registro encontrado de acordo com os parâmetros.
     */
    Optional<T> findRecordToChangePassword(String email, Integer ID, String code, Integer statusID);
}
