package pw.ry4n.proxy;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.http.HttpFields;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoggingHttpProxyServletTest {
	@Mock
	HttpServletRequest request;
	@Mock
	Request proxyRequest;
	@Mock
	HttpServletResponse response;
	@Mock
	ServletOutputStream responseOutputStream;
	@Mock
	Response proxyResponse;
	@Mock
	org.slf4j.Logger logger;
	@Mock
	org.eclipse.jetty.util.log.Logger _log;

	@InjectMocks
	LoggingHttpProxyServlet servlet;

	@Before
	public void setUp() {
		// mockito does not inject static classes so set our logger mock
		LoggingHttpProxyServlet.logger = logger;
	}

	@Test
	public void testCustomizeProxyRequest() {
		HttpFields httpFields = mock(HttpFields.class);
		when(proxyRequest.getHeaders()).thenReturn(httpFields);

		servlet.customizeProxyRequest(proxyRequest, request);

		// verify expected header fields were removed
		verify(proxyRequest, atLeastOnce()).getHeaders();
		verify(httpFields).remove("Via");
		verify(httpFields).remove("X-Forwarded-For");
		verify(httpFields).remove("X-Forwarded-Proto");
		verify(httpFields).remove("X-Forwarded-Host");
		verify(httpFields).remove("X-Forwarded-Server");
	}

	@Test
	public void testOnResponseContent() throws IOException, ServletException {
		when(response.getOutputStream()).thenReturn(responseOutputStream);

		servlet.onResponseContent(request, response, proxyResponse,
				new byte[1], 0, 0);

		// verify our logger was called
		verify(logger, times(1)).debug(anyString());
	}
}
