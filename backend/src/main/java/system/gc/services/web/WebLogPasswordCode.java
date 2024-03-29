package system.gc.services.web;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import system.gc.dtos.TokenDTO;
import system.gc.entities.LogChangePassword;
import system.gc.entities.Status;
import system.gc.exceptionsAdvice.exceptions.CodeChangePasswordInvalidException;
import system.gc.security.token.JWTService;
import system.gc.services.web.impl.WebLogPasswordCodeService;
import system.gc.services.web.impl.WebStatusService;
import system.gc.utils.TextUtils;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static system.gc.utils.TextUtils.*;

/**
 * <p>Esta interface declara os métodos necessários para que usuários possam efetuar a troca de senha de acesso ao sistema.</p>
 * <p>1. O usuário cadastrado teŕa que informar o endereço de email para receber um código de troca de senha.</p>
 * <p>2. O usuário teŕa que validar o código que foi recebido no email informado na etapa 1.</p>
 * <p>3. Após a validação, será gerado um token e o usuário poderá cadastrar uma nova senha.</p>
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
public interface WebLogPasswordCode<E,REPOSITORY extends WebChangePasswordEntity<E>> {
    /**
     * <p>Verifica se uma requisição de troca de senha está com o status aberto para continuar o fluxo.</p>
     * @param ID ID do registro no banco de dados
     * @param repository Repo onde será checado os dados
     * @param statusID ID do status necessário para a requisição ser valida
     * @return Entidade correspondente
     */
    default Optional<E> checkIfThereISAnOpenRequest(Integer ID, REPOSITORY repository, Integer statusID) {
        return repository.checkIfThereISAnOpenRequest(ID, statusID);
    }

    /**
     * <P>Este método gera um código de 6 digitos.</p>
     * @return String - Código de 6 digitos
     */
    default String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int index = 0; index < 3; index++) {
            int currentNumber = random.nextInt(10, 99);
            code.append(currentNumber);
        }
        return new String(code);
    }

    /**
     * <p>Registra um novo log de troca de senha.</p>
     * @param entity Esta entidade representa o usuário que solicitou a troca de senha.
     * @param status Status atual do LogChangePassword que será criado
     * @param webLogPasswordCodeService Serviço utilizado para salvar LogChangePassword no banco de dados.
     * @return LogChangePassword - Registro criado no banco de dados
     */
    default LogChangePassword startProcess(E entity, Status status, WebLogPasswordCodeService webLogPasswordCodeService) {
        String code = generateCode();
        LogChangePassword logChangePassword = new LogChangePassword(entity, code, status, new Date(), Short.parseShort("0"));
        return webLogPasswordCodeService.save(logChangePassword);
    }

    /**
     * <p>Verifica se o token e extrai as informações para validar o email e o código.</p>
     * @param token Token que sera decodificado
     * @param repository Repo para acesso aos dados
     * @param statusID ID do status, necessário para validar o código.
     * @return Optional<E>
     */
    default Optional<E> verifyTokenForChangePassword(String token, REPOSITORY repository, Integer statusID) {
        try {
            DecodedJWT tokenDecoded = JWTService.isValid(token);
            String email = tokenDecoded.getClaim("EMAIL").asString();
            Integer ID = Integer.valueOf(tokenDecoded.getClaim("ID").asString());
            String code = tokenDecoded.getClaim("CODE").asString();
            return repository.findRecordToChangePassword(email, ID, code, statusID);
        } catch (JWTVerificationException | NumberFormatException exception) {
            throw new CodeChangePasswordInvalidException(exception.getMessage());
        }
    }

    /**
     * <p>Verifica se o E-mail existe no banco de dados.</p>
     * @param email Endereço de E-mail para verificação
     * @param repository Repo no qual será feita a busca pelo registro de acordo com o E-mail
     * @return Entidade a qual o E-mail corresponde
     * @throws EntityNotFoundException Esta exceção será lançada se não houver um registro que corresponda ao endreço de email informado.
     */
    default E verifyEmail(String email, REPOSITORY repository) {
        return repository.findByEMAIL(email).orElseThrow(() -> new EntityNotFoundException(messageSource().getMessage("TEXT_ERROR_REGISTER_WITH_EMAIL_NOT_FOUND", null, LocaleContextHolder.getLocale())));
    }

    /**
     * <p>Esta rotina irá gerar um código e encaminhar para o endereço de e-mail informado.</p>
     * @param email Endereço de E-mail para qual o código gerado será enviado
     * @return true se o código for gerado com sucesso.
     */
    @Transactional
    boolean generateCodeForChangePassword(String email);

    /**
     * <p>Busca um <strong>LogChangePassword</strong> associado ao endereço de e-mail informado.</p>
     * @param email Endereço de email que está associado ao LogChangePassword registrado.
     * @param statusID Utilizado para categorizar a consulta aos dados.
     * @throws EntityNotFoundException Esta exceção será lançada se não houver LogChangePassword associado ao endereço de email informado.
     * @return LogChangePassword - registro correspondente
     */
    LogChangePassword getLogChangePassword(String email, Integer statusID) throws EntityNotFoundException;

    /**
     * <p>Validar código enviado pelo cliente.</p>
     * <p>O código tem validade de <strong>5 minutos</strong> e após a terceira tentativa invalida, será necessário a criação de um novo código.</p>
     * @param email Endereço de E-mail para onde o código foi enviado inicialmente.
     * @param code Código a ser validado.
     * @param webStatusService Serviço necessário para atualizar o <strong>status</strong> dos registros.
     * @param webLogPasswordCodeService Serviço necessário para buscar dados.
     * @throws CodeChangePasswordInvalidException Esta exceção será lançada se o código enviado estiver errado ou o número máximo de tentativas invalidas for atingido.
     * @return TokenDTO - retorna um token valido para troca de senha de acesso do usuário.
     */
    default TokenDTO validateCode(String email, String code, WebStatusService webStatusService, WebLogPasswordCodeService webLogPasswordCodeService)
    {
        Status waitingStatus = webStatusService.findByName(STATUS_WAITING);
        LogChangePassword logChangePassword = getLogChangePassword(email, waitingStatus.getId());
        Date currentDate = new Date();
        Date passwordCodeDate = logChangePassword.getDate();
        long time = currentDate.getTime() - passwordCodeDate.getTime();
        long minutesPast = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS);
        short attempts = logChangePassword.getNumberOfAttempts();
        short minutesAttempts = 5;
        short maxNumberOfAttempts = 3;
        attempts++;
        if (minutesPast <= minutesAttempts && logChangePassword.getNumberOfAttempts() < maxNumberOfAttempts) {
            if (logChangePassword.getCode().equals(code)) {
                logChangePassword.setStatus(webStatusService.findByName(STATUS_VALID));
                logChangePassword.setNumberOfAttempts(attempts);
                webLogPasswordCodeService.save(logChangePassword);
                return createTokenDTO(logChangePassword, email);
            } else {
                logChangePassword.setNumberOfAttempts(attempts);
                if (attempts == 3) {
                    webLogPasswordCodeService.updateStatusCode(logChangePassword, webStatusService.findByName(STATUS_INVALID));
                    throw new CodeChangePasswordInvalidException(messageSource().getMessage("TEXT_ERROR_EXCEEDED_NUMBER_OF_ATTEMPTS", null, LocaleContextHolder.getLocale()));
                }
            }
            webLogPasswordCodeService.save(logChangePassword);
            throw new CodeChangePasswordInvalidException(messageSource().getMessage("TEXT_ERROR_CODE_INVALID",
                    null, LocaleContextHolder.getLocale()));
        }
        webLogPasswordCodeService.updateStatusCode(logChangePassword, webStatusService.findByName(STATUS_INVALID));
        throw new CodeChangePasswordInvalidException(messageSource().getMessage("TEXT_ERROR_INFORMATION_NOT_MATCH", null, LocaleContextHolder.getLocale()));
    }

    /**
     * <p>Validar código enviado pelo cliente.</p>
     * @param email Endereço de E-mail para onde o código foi enviado inicialmente.
     * @param code Código a ser validado.
     * @return TokenDTO - token valido para troca de senha de acesso do usuário.
     */
    TokenDTO validateCode(String email, String code);

    /**
     * <p>Cria uma String que representa um token JWT, contendo um endereço de email, ID do <strong>LogChangePassword</strong> registrado no banco de dados e o código que foi validado.</p>
     * <p>Para que a troca de senha seja realizada com sucesso, este token terá que ser reenviado na requisição em que a nova senha será criada.</p>
     * @param email Endereço de E-mail para qual o token é valido.
     * @param ID ID do <strong>LogChangePassword</strong> no banco de dados.
     * @param code Código recém validado pelo sistema
     * @return String - token JWT para troca de senha de acesso do usuário.
     */
    default String createTokenChangePassword(String email, Integer ID, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("EMAIL", email);
        params.put("ID", String.valueOf(ID));
        params.put("CODE", code);
        return JWTService.createTokenJWT(params, TextUtils.TIME_TOKEN_CHANGE_PASSWORD_EXPIRATION);
    }

    /**
     * <p>Cria um objeto TokenDTO com os dados do <strong>logChangePassword</strong>.</p> 
     * @param logChangePassword Contém os dados necessários para gerar o token.
     * @param email Endereço de E-mail para incluir no Token
     * @return TokenDTO - token valido para troca de senha de acesso do usuário
     */
    TokenDTO createTokenDTO(LogChangePassword logChangePassword, String email);

    /**
     * <p>Rotina para atualizar a senha no banco de dados.</p>
     * @param token Token valido para efetuar a troca de senha.
     * @param newPassword Nova senha a ser inserida no banco de dados.
     */
    @Transactional
    void changePassword(String token, String newPassword);

    MessageSource messageSource();
}
