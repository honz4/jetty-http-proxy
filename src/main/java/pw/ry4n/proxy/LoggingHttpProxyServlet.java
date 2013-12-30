package pw.ry4n.proxy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple HTTP Proxy. It removes several request headers added by
 * {@link ProxyServlet} and logs any response content to the configured
 * {@code logger}.
 * 
 * @author Ryan Powell
 */
public class LoggingHttpProxyServlet extends ProxyServlet {
	private static final long serialVersionUID = -4815965522272190029L;

	protected static Logger logger = LoggerFactory
			.getLogger(LoggingHttpProxyServlet.class);

	@Override
	protected void customizeProxyRequest(Request proxyRequest,
			HttpServletRequest request) {
		proxyRequest.getHeaders().remove("Via");
		proxyRequest.getHeaders().remove("X-Forwarded-For");
		proxyRequest.getHeaders().remove("X-Forwarded-Proto");
		proxyRequest.getHeaders().remove("X-Forwarded-Host");
		proxyRequest.getHeaders().remove("X-Forwarded-Server");
	}

	@Override
	protected void onResponseContent(HttpServletRequest request,
			HttpServletResponse response, Response proxyResponse,
			byte[] buffer, int offset, int length) throws IOException {
		// log content
		logger.debug(new String(buffer, offset, length));

		// continue on to default behavior
		super.onResponseContent(request, response, proxyResponse, buffer,
				offset, length);
	}
}
