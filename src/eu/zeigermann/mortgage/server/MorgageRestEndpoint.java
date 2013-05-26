package eu.zeigermann.mortgage.server;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.zeigermann.mortgage.server.mortgage.MortgageRestEndpoint;


@SuppressWarnings("serial")
public class MorgageRestEndpoint extends HttpServlet {
	private Logger logger = Logger.getLogger(MortgageRestEndpoint.class
			.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String origin = req.getHeader("Origin");
		logger.info("Performing GET with Origin: " + origin);
		setCORSHeaders(resp);
		List<String> parameters = getParameters(req);
		if (parameters.size() == 4) {
			MortgageCalculator mortgageCalculator = new MortgageCalculator();
			MortgageData data = new MortgageData();
			data.price = new BigDecimal(parameters.get(0));
			data.down = new BigDecimal(parameters.get(1));
			data.interest = new BigDecimal(parameters.get(2));
			data.term = new BigDecimal(parameters.get(3));
			MortgageResult result = mortgageCalculator.calculateMortgage(data);
			resp.getWriter().write(result.stringify());
		} else {
			resp.getWriter().write("Error");
		}
	}

	private List<String> getParameters(HttpServletRequest req) {
		final String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.length() == 0) {
			return Collections.emptyList();
		}
		String parameterString = pathInfo;
		if (parameterString.startsWith("/")) {
			parameterString = parameterString.substring(1);
		}
		String[] split = parameterString.split("/");
		List<String> parameters = Arrays.asList(split);
		return parameters;
	}
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String origin = req.getHeader("Origin");
		logger.info("Performing OPTIONS with Origin: " + origin);
		setCORSHeaders(resp);
	}

	// http://en.wikipedia.org/wiki/Cross-Origin_Resource_Sharing
	// https://developer.mozilla.org/en-US/docs/HTTP/Access_control_CORS?redirectlocale=en-US&redirectslug=HTTP_access_control#Access-Control-Allow-Headers
	private void setCORSHeaders(HttpServletResponse resp) {
		// allow for cross site origin
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods",
				"HEAD, GET, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers",
				"ACCEPT, ORIGIN, X-REQUESTED-WITH, CONTENT-TYPE");
	}

}
