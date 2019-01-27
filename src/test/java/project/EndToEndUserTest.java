package project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import project.dto.LoginDTO;
import project.dto.UserDTO;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EndToEndUserTest {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public EndToEndUserTest() {
        this.restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

    @Test(expected = HttpClientErrorException.class)
    public void endToEndTest() throws IOException {
        final HttpHeaders httpHeaders = loginTest();
        getAllUserTest(httpHeaders);
        logoutTest(httpHeaders);
        getAllUserTest(httpHeaders);
    }

    public final HttpHeaders loginTest() throws JsonProcessingException {
        final String loginUrl = "http://localhost:8080/user/login";
        final LoginDTO loginDTO = new LoginDTO("igor@gmail.com", "new");
        final String requestJson = objectMapper.writeValueAsString(loginDTO);
        final HttpEntity<String> urlHeaders = getUrlPostHeaders(requestJson);
        final ResponseEntity<String> result = restTemplate.exchange(loginUrl, HttpMethod.POST, urlHeaders, String.class);
        final HttpHeaders headers = getHeaders();
        final List<String> setCookie = result.getHeaders().get(HttpHeaders.SET_COOKIE);
        headers.add("Cookie", setCookie.get(1));
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        return headers;
    }

    public void getAllUserTest(final HttpHeaders httpHeaders) throws IOException {
        final String uri = "http://localhost:8080/user/all";
        final HttpEntity<String> urlGetHeaders = getHttpEntity(httpHeaders);
        final ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, urlGetHeaders, String.class);
        final String resultBody = result.getBody();
        final List<UserDTO> userDTOS = objectMapper.readValue(resultBody, objectMapper.getTypeFactory()
                .constructParametricType(List.class, UserDTO.class));
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertTrue(userDTOS.size() > 0);
        System.out.println(result);
    }

    public void logoutTest(final HttpHeaders httpHeaders) {
        final String uri = "http://localhost:8080/user/logout";
        final HttpEntity<String> urlGetHeaders = getHttpEntity(httpHeaders);
        final ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, urlGetHeaders, String.class);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    private static HttpEntity<String> getHttpEntity(final HttpHeaders headers) {
        return new HttpEntity<>("params", headers);
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private static HttpEntity<String> getUrlPostHeaders(final String requestJson) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(requestJson, headers);
    }
}
