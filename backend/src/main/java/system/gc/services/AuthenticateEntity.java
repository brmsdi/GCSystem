package system.gc.services;

public interface AuthenticateEntity<T> {
    T getAuthenticationByCPF(String cpf);
}