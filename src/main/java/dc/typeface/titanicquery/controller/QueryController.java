package dc.typeface.titanicquery.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dc.typeface.titanicquery.services.StringToSqlService;

@RestController
@RequestMapping("api/v1/query")
public class QueryController {

    @Autowired
    private StringToSqlService service;

    @GetMapping
    public List<Map<String, Object>> getResult(@RequestParam("query") String query) {
        return service.getData(query);
    }

}
