package system.gc.services;

import system.gc.dtos.AuthenticateDTO;

public interface AuthenticationByCPFGeneric<DTO extends AuthenticateDTO<DTO, E>, E, REPOSITORY extends AuthenticateEntity<E>> {

    /**
     * @param username Informação de login
     * @return 'DTO' DTo da entidade correspondente ao tipo de autenticação. Employee ou Lessee!
     */
    default DTO authentication(String username, DTO entityDTO, REPOSITORY repository) {
        E result = repository.getAuthenticationByCPF(username);
        if (result == null) {
            return null;
        }
        return entityDTO.initAuthenticate(result);
    }


}
