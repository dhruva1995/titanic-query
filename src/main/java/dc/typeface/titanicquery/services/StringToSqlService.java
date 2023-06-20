package dc.typeface.titanicquery.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class StringToSqlService {

    private static final String url = "https://www.eversql.com/api/generateSQLFromText";

    @Autowired
    private RestTemplate restTemplate;

    private String createQuery = """
            CREATE TABLE `passengers` (
                `passenger_id` int NOT NULL,
                `survived` boolean DEFAULT NULL,
                `seat_class` int DEFAULT NULL,
                `name` varchar(255) DEFAULT NULL,
                `gender` ENUM('male', 'female'),
                `age` double DEFAULT NULL,
                `siblings` int DEFAULT NULL,
                `Parch` int DEFAULT NULL,
                `ticket` varchar(255) DEFAULT NULL,
                `fare` double DEFAULT NULL,
                `cabin` varchar(255) DEFAULT NULL,
                `embarked` ENUM('Q', 'S', 'C'),
                PRIMARY KEY (`passenger_id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            """;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getData(String text) {
        String query = textToSQL(text);
        int length = query.length();
        query = query.substring(1, length - 1);
        log.info("SQL generated as: {}", query);
        if (query.contains("FROM")) {
            return queryDatabase(query);
        } else {
            return Arrays.asList(Map.of("error", query));
        }

    }

    public String textToSQL(final String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("prompt", text);
        map.add("schema", createQuery);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return response.getBody();

    }

    public List<Map<String, Object>> queryDatabase(String query) {
        return jdbcTemplate.queryForList(query);
    }

}
