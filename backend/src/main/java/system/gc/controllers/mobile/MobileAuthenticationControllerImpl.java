package system.gc.controllers.mobile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.gc.controllers.AuthenticationController;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@RestController
@Slf4j
@RequestMapping(value = API_V1_MOBILE + "/validate")
public class MobileAuthenticationControllerImpl implements AuthenticationController {
}