package system.gc.controllers.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.gc.dtos.EmployeeDTO;
import system.gc.security.UserDetailsConvert;
import system.gc.services.mobile.MobileEmployeeService;

import static system.gc.utils.TextUtils.API_V1_MOBILE;

@RestController
@RequestMapping(value = API_V1_MOBILE + "/employees")
public class MobileEmployeeController {

    @Autowired
    private MobileEmployeeService mobileEmployeeService;

    @GetMapping(value = "account")
    public ResponseEntity<EmployeeDTO> myAccount() {
        UserDetailsConvert userDetailsConvert = (UserDetailsConvert) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return ResponseEntity.ok(mobileEmployeeService.myAccount(userDetailsConvert.getUsername()));
    }
}