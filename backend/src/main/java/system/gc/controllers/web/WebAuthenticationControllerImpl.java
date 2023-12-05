package system.gc.controllers.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import system.gc.controllers.AuthenticationController;

import static system.gc.utils.TextUtils.API_V1_WEB;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@RestController
@Slf4j
@RequestMapping(value = API_V1_WEB + "/validate")
public class WebAuthenticationControllerImpl implements AuthenticationController {}