package system.gc.services;

public interface AuthenticateEntityByCPF<T> {
    T getAuthentication(String cpf);
}
