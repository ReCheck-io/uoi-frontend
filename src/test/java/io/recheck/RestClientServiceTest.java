package io.recheck;

import io.recheck.rest.RestClientService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("local")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class RestClientServiceTest {

    @Autowired
    private RestClientService restClientService;

//    @Test
//    void newUoi() {
//        restClientService.newUoi(new NewUoiDTO("BG", LEVEL.UNIT, ""));
//    }
//
//    @Test
//    void updateProperties() {
//        UOINode body = restClientService.newUoi(new NewUoiDTO("BG", LEVEL.UNIT, "")).getBody();
//        restClientService.updateProperties(new UpdatePropertiesDTO(body.getUoi(), "kk1", "vv1"));
//    }
//
//    @Test
//    void makeRelationship() {
//        UOINode body1 = restClientService.newUoi(new NewUoiDTO("BG", LEVEL.UNIT, "")).getBody();
//        UOINode body2 = restClientService.newUoi(new NewUoiDTO("BG", LEVEL.UNIT, "")).getBody();
//        restClientService.makeRelationship(new UpdateRelationshipDTO(body1.getUoi(), body2.getUoi()));
//    }
//
//    @Test
//    void searchByUoi() {
//        UOINode body = restClientService.newUoi(new NewUoiDTO("BG", LEVEL.UNIT, "")).getBody();
//        restClientService.searchByUoi(new SearchByUoiModel(body.getUoi()));
//    }
//
//    @Test
//    void searchByProperties() {
//        UOINode body = restClientService.newUoi(new NewUoiDTO("BG", LEVEL.UNIT, "")).getBody();
//        restClientService.updateProperties(new UpdatePropertiesDTO(body.getUoi(), "kk1", "vv1"));
//        restClientService.searchByProperties(new SearchByPropertiesModel("kk1", "vv1", true));
//    }
}