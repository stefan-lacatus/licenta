package ro.pub.acse.sapd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SapdApplication.class)
@WebAppConfiguration
public class SapdApplicationTests {

    @Test
    public void contextLoads() {
    }

}
