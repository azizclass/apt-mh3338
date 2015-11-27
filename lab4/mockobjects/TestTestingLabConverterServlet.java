import junit.framework.*;
import com.mockobjects.servlet.*;

public class TestTestingLabConverterServlet extends TestCase {

    public void test_empty_parameter() throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        assertEquals("<html><head><title>No Temperature</title>"
                + "</head><body><h2>Need to enter a temperature!"
                + "</h2></body></html>\n", response.getOutputStreamContents());
    }

    // Temperature inputs that are not valid should return Got a NumberFormatException on (input string)
    public void test_invalid_parameter() throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("farenheitTemperature", "invalid!");
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        assertEquals("<html><head><title>Bad Temperature</title>"
                + "</head><body><h2>Need to enter a valid temperature!"
                + "Got a NumberFormatException on invalid!" 
                + "</h2></body></html>\n", response.getOutputStreamContents());
    }

    // Temperature inputs are floating point numbers in decimal notation (i.e.,
    // 97 or -3.14, but not 9.73E2)
    // Temperature inputs that are not valid should return Got a NumberFormatException on (input string)
    public void test_invalid_parameter2() throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("farenheitTemperature", "9.73E2");
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        assertEquals("<html><head><title>Bad Temperature</title>"
                + "</head><body><h2>Need to enter a valid temperature!"
                + "Got a NumberFormatException on 9.73E2" 
                + "</h2></body></html>\n", response.getOutputStreamContents());
    }

    // Temperature inputs are floating point numbers in decimal notation (i.e.,
    // 97 or -3.14, but not 9.73E2)
    // Temperature results should be 2 places of precision for temperatures from
    // 0 to 212 degrees Farenheit, inclusive, and 1 place of precision
    // otherwise.
    public void test_valid_input_integer() throws Exception {
    TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("farenheitTemperature", "97");
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        assertEquals("<html><head><title>Temperature Converter Result</title>"
                     + "</head><body><h2>97 Farenheit = 36.11 Celsius </h2>\n" 
                     + "<p><h3>The temperature in Austin is 451 degrees Farenheit</h3>\n"
                     + "</body></html>\n", response.getOutputStreamContents());
    }

    // Temperature inputs are floating point numbers in decimal notation (i.e.,
    // 97 or -3.14, but not 9.73E2)
    // Temperature results should be 2 places of precision for temperatures from
    // 0 to 212 degrees Farenheit, inclusive, and 1 place of precision
    // otherwise.
    public void test_valid_input_integer2() throws Exception {
    TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("farenheitTemperature", "-3.14");
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        assertEquals("<html><head><title>Temperature Converter Result</title>"
                     + "</head><body><h2>-3.14 Farenheit = -19.5 Celsius </h2>\n" 
                     + "<p><h3>The temperature in Austin is 451 degrees Farenheit</h3>\n"
                     + "</body></html>\n", response.getOutputStreamContents());
    }
}
