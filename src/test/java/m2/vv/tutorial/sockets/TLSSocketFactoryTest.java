package m2.vv.tutorial.sockets;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TLSSocketFactoryTest {

    /**
     * Test when the edge case when the both supported and enabled protocols are null.
     */
    @Test
    public void preparedSocket_NullProtocols()  {
        TLSSocketFactory f = new TLSSocketFactory();
        f.prepareSocket(new SSLSocket() {

            public String[] getSupportedProtocols() {
                return null;
            }

            public String[] getEnabledProtocols() {
                return null;
            }

            public void setEnabledProtocols(String[] protocols) {
                fail();
            }
        });
    }

    @Test
    public void typical()  {
        TLSSocketFactory f = new TLSSocketFactory();
        f.prepareSocket(new SSLSocket() {
            @Override
            public String[] getSupportedProtocols() {
                return shuffle(new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"});
            }
            @Override
            public String[] getEnabledProtocols() {
                return shuffle(new String[]{"SSLv3", "TLSv1"});
            }
            @Override
            public void setEnabledProtocols(String[] protocols) {
                assertTrue(Arrays.equals(protocols, new String[] {"TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3" }));
                //If some different bug prevents the invocation of setEnabledProtocols the test will not fail.
            }
        });
    }


    private String[] shuffle(String[] in) {
        List<String> list = new ArrayList<String>(Arrays.asList(in));
        Collections.shuffle(list);
        return list.toArray(new String[0]);
    }





}