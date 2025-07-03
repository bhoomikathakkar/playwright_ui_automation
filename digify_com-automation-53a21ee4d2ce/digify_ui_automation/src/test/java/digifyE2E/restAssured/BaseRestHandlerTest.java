package digifyE2E.restAssured;

import com.fasterxml.jackson.core.JsonProcessingException;
import digifyE2E.restAssured.entities.DocumentSecurityFileUploadResponse;
import digifyE2E.restAssured.entities.PreAuthURLResponse;
import org.testng.annotations.Test;

public class BaseRestHandlerTest {

    @Test
    public void testObjectMapping() throws JsonProcessingException {
        String jsn = "{\n" +
                "        \"Guid\": \"5a0d1b5082eb43a0a6c466be710b5129\",\n" +
                "                \"Link\": \"https://staging-x.digifyteam.com/s/Wg0bUA\",\n" +
                "                \"EmbedLink\": \"https://embed.digify.com/a/#/f/r/5a0d1b5082eb43a0a6c466be710b5129\",\n" +
                "                \"FileName\": \"PdfFile2KB.pdf\",\n" +
                "                \"Status\": {\n" +
                "            \"StatusCode\": 40000,\n" +
                "                    \"StatusMessage\": \"Successfully shared uploaded document\"\n" +
                "        }\n" +
                "    }\"\n" +
                "    }";
        new BaseRestHandler().OBJECT_MAPPER.readValue(jsn, DocumentSecurityFileUploadResponse.class);
    }

    @Test
    public void testJsonAlias() throws JsonProcessingException {
        String jsn = "{\n" +
                "    \"FileName\": \"PdfFile2KB.pdf\",\n" +
                "    \"RecipientUserEmail\": \"jesskuser@maildrop.cc\",\n" +
                "    \"ExpiryTime\": \"2024-11-22T02:46:23.054Z\",\n" +
                "    \"PreAuthURL\": \"https://staging-x.digifyteam.com/a/#/f/pa/129a02cd1f684636abd1873d02773266\",\n" +
                "    \"EmbedLink\": \"https://embed.digify.com/a/#/f/pa/129a02cd1f684636abd1873d02773266\",\n" +
                "    \"Status\": {\n" +
                "        \"StatusCode\": 16000,\n" +
                "        \"StatusMessage\": \"Pre-auth URL generated.\"\n" +
                "    }\n" +
                "}";
        PreAuthURLResponse obj= new BaseRestHandler().OBJECT_MAPPER.readValue(jsn, PreAuthURLResponse.class);
        System.out.println(obj.getFileName());

        String jsn2 = "{\n" +
                "    \"RecipientUserEmail\": \"jesskuser@maildrop.cc\",\n" +
                "    \"ExpiryTime\": \"2024-11-22T02:46:23.054Z\",\n" +
                "    \"PreAuthURL\": \"https://staging-x.digifyteam.com/a/#/f/pa/129a02cd1f684636abd1873d02773266\",\n" +
                "    \"EmbedLink\": \"https://embed.digify.com/a/#/f/pa/129a02cd1f684636abd1873d02773266\",\n" +
                "    \"Status\": {\n" +
                "        \"StatusCode\": 16000,\n" +
                "        \"StatusMessage\": \"Pre-auth URL generated.\"\n" +
                "    }\n" +
                "}";
        PreAuthURLResponse obj2= new BaseRestHandler().OBJECT_MAPPER.readValue(jsn2, PreAuthURLResponse.class);
        System.out.println(obj2.getFileName());
    }
}