package digifyE2E.restAssured;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import digifyE2E.utils.LogUtils;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.*;

import static digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper.waitForSecondsInApi;
import static digifyE2E.utils.FileUtils.getUserCredentialsFromJsonFile;
import static digifyE2E.utils.LogUtils.infoLog;
import static org.testng.Assert.assertEquals;


public class BaseRestHandler {

    public static final String API_BASE_URL = "https://staging-api.digifyteam.com/";
    public final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static RequestSpecification getRSWithBasicAuth(String userType, String endpoint) {
        Map<String, String> apiCredentials = getUserCredentialsFromJsonFile(userType);
        if (apiCredentials != null) {
            return RestAssured.given()
                    .baseUri(API_BASE_URL + endpoint)
                    .auth().preemptive().basic(apiCredentials.get("username"), apiCredentials.get("password"));
        } else {
            throw new RuntimeException("Failed to load API credentials.");
        }
    }

    public List<MultiPartSpecification> getMultipartSpecifications(File file, String jsonAttributeName, String jsonContent) {
        List<MultiPartSpecification> multipartSpecs = new ArrayList<>();

        if (file != null) {
            MultiPartSpecification filePart = new MultiPartSpecBuilder(file)
                    .controlName("filename")
                    .fileName(file.getName())
                    .mimeType("application/octet-stream") // You can replace with dynamic MIME type
                    .build();
            multipartSpecs.add(filePart);
        }

        if (jsonAttributeName != null && jsonContent != null) {
            MultiPartSpecification jsonPart = new MultiPartSpecBuilder(jsonContent)
                    .controlName(jsonAttributeName)
                    .mimeType("application/json")
                    .build();
            multipartSpecs.add(jsonPart);
        }

        return multipartSpecs;
    }

    public <T> T POST(int responseCode, String endpoint, String userType, Map<String, String> requestForm,
                         List<MultiPartSpecification> multiPartSpecifications, boolean retryOnFailure, Class<T> classO) {
        RequestSpecification request = getRSWithBasicAuth(userType, endpoint);
        if (Objects.nonNull(requestForm)) {
            request.header("Content-Type", "application/x-www-form-urlencoded")
                    .formParams(requestForm);
        }
        if (Objects.nonNull(multiPartSpecifications)) {
            request.header("Content-Type", "multipart/form-data");
            multiPartSpecifications.forEach(request::multiPart);
        }

        Response response = null;
        int retryCount = 0;

        do {
            response = request.post();
            if (response.getStatusCode() == responseCode || !retryOnFailure) {
                break;
            } else {
                retryCount++;
                waitForSecondsInApi(10L * retryCount);
                infoLog(response.getBody().prettyPrint());
                infoLog("Failed to get correct status code, retrying after " + (10L * retryCount) + " second wait");
            }
        } while (retryCount < 3);

        assertEquals(
                response.getStatusCode(),
                responseCode,
                "Invalid response code returned by endpoint " + API_BASE_URL + endpoint + "\n"
        );

        try {
            return OBJECT_MAPPER.readValue(response.getBody().asString(), classO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T GET(int responseCode, String endpoint, String guid, String userType, boolean retryOnFailure, Class<T> classO) {
        String fullEndpoint = endpoint + "/" + guid;
        RequestSpecification request = getRSWithBasicAuth(userType, fullEndpoint);

        Response response = null;
        int retryCount = 0;
        while (retryCount < 2) {
            response = request.get();
            if (response.getStatusCode() == responseCode || !retryOnFailure) {
                break;
            } else {
                retryCount++;
                waitForSecondsInApi(10L * retryCount);
                infoLog("Failed to get correct status code, retrying after " + (10L * retryCount) + " second wait");
            }
        }
        assertEquals(response.getStatusCode(),
                responseCode, "Invalid response code returned by endpoint " + API_BASE_URL + endpoint + "\n"
        );
        try {
            return OBJECT_MAPPER.readValue(response.getBody().asString(), classO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> convertDataTableToMap(DataTable dataTable) {
        Map<String, String> settings = new HashMap<>();
        dataTable.asMaps(String.class, String.class).forEach(row -> {
            String key = row.get("attribute");
            String value = row.get("value");
            settings.put(key, value);
        });
        infoLog("Converted Settings Map: " + settings);
        return settings;
    }
}
