package system.gc.services;

import system.gc.dtos.AuthenticateDTO;

public interface AuthenticationByCPFGenericImpl<DTO extends AuthenticateDTO<DTO, E>, E, REPOSITORY extends AuthenticateEntityByCPF<E>> {

    /**
     * @param username Informação de login
     * @return 'DTO' DTo da entidade correspondente ao tipo de autenticação. Employee ou Lessee!
     */
    default DTO authentication(String username, DTO entityDTO, REPOSITORY repository) {
        E result = repository.getAuthentication(username);
        if (result == null) {
            return null;
        }
        return entityDTO.initAuthenticate(result);
    }
}
