package com.claro.gdt.le.job.controllers;



import com.claro.gdt.le.job.models.SGTException;
import com.claro.gdt.le.job.models.enums.ApiResponseType;
import com.claro.gdt.le.job.models.responses.ApiResponse;
import com.claro.gdt.le.job.services.IDictionaryDAO;
import com.claro.gdt.le.job.services.SutelDataService;
import com.claro.gdt.le.job.utils.ApiResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.claro.gdt.le.job.utils.Constants.BASE_URL;


@RestController
@RequestMapping(BASE_URL + "/test")
public class TestEndpointsController {

    @Autowired
    private IDictionaryDAO dictionaryServices;

    @Autowired
    private SutelDataService sutelDataService;


    @GetMapping("/validate/{fileName}/{type}")
    public ResponseEntity<ApiResponse> getValidation(@PathVariable(name = "fileName") String file,
                                                     @PathVariable(name = "type") String type) throws SGTException {
        return ResponseEntity.ok(ApiResponseUtils.success(ApiResponseType.GEVENEU_PERSISTENCE,"success",
                sutelDataService.getNewValidation(file,type)));
    }

    @GetMapping("/dictionary")
    public ResponseEntity<ApiResponse> getDictionary() throws SGTException{
        return ResponseEntity.ok(ApiResponseUtils.success(ApiResponseType.GEVENEU_PERSISTENCE,"DictionaryList",
                dictionaryServices.getAllDictionaryTags()));
    }

    @GetMapping("/tags")
    public ResponseEntity<ApiResponse> getTagsValues() throws SGTException{
        return ResponseEntity.ok(ApiResponseUtils.success(ApiResponseType.GEVENEU_PERSISTENCE,"getAllTags",
                dictionaryServices.getAllTags()));
    }

}
