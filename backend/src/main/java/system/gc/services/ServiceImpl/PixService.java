package system.gc.services.ServiceImpl;

import br.com.gerencianet.gnsdk.Gerencianet;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import system.gc.configuration.GenerateTXID;
import system.gc.entities.payment.Charge;
import system.gc.entities.payment.Credentials;
import system.gc.entities.payment.PaginationPixDueCharge;
import system.gc.entities.payment.QRCode;
import system.gc.gerencianet.GerenciaNETInitialize;
import java.util.HashMap;

/**
 * @author Wisley Bruno Marques França
 */
@Service
@Log4j2
public class PixService {
    @Autowired
    private Environment environment;

    @Autowired
    private Credentials credentials;

    private GerenciaNETInitialize gerenciaNETInitialize;

    /**
     * Gerar novas cobranças pix
     * @param body corpo da requisição exigidos pela API do gerencianet
     * @param information Informações adionais da cobrança
     * @return Objeto JSON da cobrança
     */
    public Charge pixCreateDueCharge(JSONObject body, JSONArray information, GenerateTXID generateTXID) throws Exception {
        body.put("infoAdicionais", information);
        return pixCreateDueCharge(body, generateTXID);
    }

    /**
     * Gerar novas cobranças pix com data de valicade
     * @param body corpo da requisição exigidos pela API do gerencianet
     * @return Objeto JSON da cobrança
     */

    public Charge pixCreateDueCharge(JSONObject body, GenerateTXID generateTXID) throws Exception
    {
        Gerencianet gerencianet = new GerenciaNETInitialize().createDefaultChargePixSystemGerenciaNetWithOptions(credentials);
        body.put("chave", environment.getProperty("CHAVE"));
        JSONObject response = gerencianet.call("pixCreateDueCharge", prepareParams(generateTXID), body);
        return new Gson().fromJson(response.toString(), Charge.class);
    }

    /**
     * Listar cobranças pix em um determinado intervalo de tempo
     * @param params parametros exigidos pela API do gerencianet
     * @return Objetos JSON das cobranças pix
     */
    public PaginationPixDueCharge pixListDueCharges(HashMap<String, String> params, JSONObject jsonObject) throws Exception {
        Gerencianet gerencianet = new GerenciaNETInitialize().createDefaultChargePixSystemGerenciaNetWithOptions(credentials);
        return new Gson().fromJson(gerencianet.call("pixListDueCharges", params, jsonObject).toString(), PaginationPixDueCharge.class);
    }

    /**
     * Consultar detalhes de uma cobrança por id
     * @param params parametros exigidos pela API do gerencianet
     * @return Objeto JSON com os detalhes da cobrança
     */
    public Charge pixDetailsDueCharge(HashMap<String, String> params, JSONObject jsonObject) throws Exception {
        Gerencianet gerencianet = new GerenciaNETInitialize().createDefaultChargePixSystemGerenciaNetWithOptions(credentials);
        return new Gson().fromJson(gerencianet.call("pixDetailDueCharge", params, jsonObject).toString(), Charge.class);
    }

    /**
     * Atualizar informações da divida
     * @param params parametros exigidos pela API do gerencianet. ID do location
     * @return informações da divida atualizadas
     */
    public Charge updateChargePix(HashMap<String, String> params, JSONObject body) throws Exception {
        Gerencianet gerencianet = new GerenciaNETInitialize().createDefaultChargePixSystemGerenciaNetWithOptions(credentials);
        return new Gson().fromJson(gerencianet.call("pixUpdateDueCharge", params, body).toString(), Charge.class);
    }

    /**
     * Gerar QRCODE Para pagamento
     * @param params parametros exigidos pela API do gerencianet. ID do location
     * @return pix copia/cola e QRCODE com codificação base64
     */
    public QRCode generateQRCode(HashMap<String, String> params, JSONObject jsonObject) throws Exception {
        Gerencianet gerencianet = new GerenciaNETInitialize().createDefaultChargePixSystemGerenciaNetWithOptions(credentials);
        return new Gson().fromJson(gerencianet.call("pixGenerateQRCode", params, jsonObject).toString(), QRCode.class);
    }

    public HashMap<String, String> prepareParams(GenerateTXID generateTXID)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("txid", generateTXID.generateTXID());
        return params;
    }
}
