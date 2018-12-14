package org.arf.core;

import org.apache.commons.lang.StringUtils;

import cn.emay.sdk.client.api.Client;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public static void main(String[] args) {
        
        
        String smsSn = "6SDK-EMY-6688-JDQNL";
        String smsKey = "885788";
        if (StringUtils.isEmpty(smsSn) || StringUtils.isEmpty(smsKey)) {
            return;
        }
        try {
            Client client = new Client(smsSn, smsKey);
           /* if (sendTime != null) {
                client.sendScheduledSMS("13360058980", "232323", DateFormatUtils.format(sendTime, "yyyyMMddhhmmss"));
            } else {
                client.sendSMS(mobiles, content, 5);
            }*/
             
        System.out.println(client.sendSMS(new String[]{"15017582447"}, "【俺家智能科技有限公司】验证码232323",5));    
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        
    }
}
