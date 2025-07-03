package digifyE2E.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.microsoft.playwright.Page;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static digifyE2E.playwright.DigifyPlaywrightWrapper.page;

public class FileUtils {

    public static Map<String, String> getUserCredentialsFromJsonFile(String userType) {
        File file = new File(new File("src/main/resources/testUserDetails/" + EnvUtils.getTestEnv() + "_creds.json").getAbsolutePath());
        Reader reader = null;
        try {
            reader = new FileReader(file);
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject adminUser = json.getAsJsonObject(userType);
            String username = adminUser.get("username").getAsString();
            String password = adminUser.get("password").getAsString();
            Map<String, String> credentialsMap = new HashMap<>();
            credentialsMap.put("username", username);
            credentialsMap.put("password", password);
            return credentialsMap;
        } catch (FileNotFoundException e) {
            System.out.println(Arrays.stream(e.getStackTrace()));
            return null;
        }
    }


    public static Map<String, Double> getPlanDetailsFromJsonFile(String planType) {
        File file = new File("src/main/resources/planDetails/planPrice.json");
        try (Reader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject planAddOn = json.getAsJsonObject(planType);
            Map<String, Double> planDetailsMap = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : planAddOn.entrySet()) {
                planDetailsMap.put(entry.getKey(), entry.getValue().getAsDouble());
            }
            return planDetailsMap;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public static Map<String, String> getFeatureComparisonTable(String featureType) {
        File file = new File("src/main/resources/planDetails/featureComparisonTablePricingPage.json");

        try (Reader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject featureList = json.getAsJsonObject("featureComparison").getAsJsonObject(featureType);
            Map<String, String> features = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : featureList.entrySet()) {
                features.put(entry.getKey(), entry.getValue().getAsString());
            }
            return features;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public static Map<String, String> getPlanInfoFromJsonFile(String planType) {
        String fileName = switch (planType.toLowerCase()) {
            case "pro" -> "proPlanCard.json";
            case "team" -> "teamPlanCard.json";
            case "enterprise" -> "enterprisePlanCard.json";
            default -> throw new IllegalArgumentException("please enter valid plan type" + planType);
        };
        File file = new File("src/main/resources/planDetails/" + fileName);

        try (Reader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            Map<String, String> planCardMap = new HashMap<>();

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                if (entry.getValue() != null && !entry.getValue().isJsonNull()) {
                    planCardMap.put(entry.getKey(), entry.getValue().getAsString());
                }
            }
            return planCardMap;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found during reading: " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + fileName, e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Invalid JSON format in file: " + fileName, e);
        }
    }

    public static Path takeScreenshot(String id) throws IOException {
        Path targetDir = Paths.get("target/screenshots");
        Files.createDirectories(targetDir);
        Path screenshotPath = targetDir.resolve(id + ".png");
        page().screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
        return screenshotPath;
    }
}