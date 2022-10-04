package system.gc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import system.gc.dtos.TypeProblemDTO;
import system.gc.services.ServiceImpl.TypeProblemService;

import java.util.List;

@Controller
@RequestMapping(value = "/types-problem")
@Slf4j
public class TypeProblemController {

    @Autowired
    private TypeProblemService typeProblemService;

    @GetMapping
    public ResponseEntity<List<TypeProblemDTO>> findAll(@RequestParam(name = "sort", defaultValue = "name") String sort) {
        log.info("Listando tipos de problema");
        return ResponseEntity.ok(typeProblemService.findAll(Sort.by(sort)));
    }
}