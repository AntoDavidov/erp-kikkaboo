package nl.fontys.s3.erp;

import nl.fontys.s3.erp.configuration.security.token.impl.AccessTokenEncoderDecoderImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ErpApplicationTests {

    @MockBean
    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;

    @Test
    void contextLoads() {
    }

}
