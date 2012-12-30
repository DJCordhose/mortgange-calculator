package eu.zeigermann.mortgage.server;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.zeigermann.mortgage.server.MortgageCalculator.MortgageData;
import eu.zeigermann.mortgage.server.MortgageCalculator.MortgageResult;

@SuppressWarnings("serial")
public class MorgageRestEndpoint extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<String> parameters = getParameters(req);
		if (parameters.size() == 4) {
			MortgageCalculator mortgageCalculator = new MortgageCalculator();
			MortgageData data = new MortgageCalculator.MortgageData();
			data.price = new BigDecimal(parameters.get(0));
			data.down = new BigDecimal(parameters.get(1));
			data.interest = new BigDecimal(parameters.get(2));
			data.term = new BigDecimal(parameters.get(3));
			MortgageResult result = mortgageCalculator.calculateMortgage(data);
			
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

}
