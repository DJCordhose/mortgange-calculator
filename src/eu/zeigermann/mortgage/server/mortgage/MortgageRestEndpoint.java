package eu.zeigermann.mortgage.server.mortgage;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import eu.zeigermann.mortgage.server.MortgageData;
import eu.zeigermann.mortgage.server.Result;

@SuppressWarnings("serial")
public class MortgageRestEndpoint extends HttpServlet {

	private Logger logger = Logger.getLogger(MortgageRestEndpoint.class
			.getName());

	private MortgageService customerService = new MortgageMapService();
	// private CustomerService customerService = new MemcacheCustomerService();

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Performing POST");
		doSave(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Performing PUT");
		doSave(req, resp);
	}

	private void doSave(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Gson gson = new Gson();
		MortgageData mortgage = gson.fromJson(req.getReader(),
				MortgageData.class);
		MortgageData saved = customerService.save(mortgage);
		sendResponse(req, resp, saved, true);
	}

	private void sendError(HttpServletRequest req, HttpServletResponse resp) {
		sendResponse(req, resp, null, false);
	}

	private void sendSuccess(HttpServletRequest req, HttpServletResponse resp) {
		sendResponse(req, resp, null, true);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Performing DELETE");
		int id = parseId(req);
		if (id == -1) {
			sendError(req, resp);
		} else {
			customerService.delete(id);
			sendSuccess(req, resp);
		}
	}

	@Override
	// does both REST / CORS calls as well as jsonp
	// http://devlog.info/2010/03/10/cross-domain-ajax/
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Performing GET");
		int id = parseId(req);
		Object payload;
		if (id == -1) {
			payload = customerService.getAll();
		} else {
			payload = customerService.get(id);
		}
		sendResponse(req, resp, payload);
	}

	private void sendResponse(HttpServletRequest req, HttpServletResponse resp,
			Object payload) {
		sendResponse(req, resp, payload, true);
	}

	private void sendResponse(HttpServletRequest req, HttpServletResponse resp,
			Object payload, boolean success) {
		String callback = req.getParameter("callback");
		passResult(resp, payload, callback, success);
		if (callback == null) {
			setCORSHeaders(resp);
		} else {
			setJsonpHeaders(resp);
		}
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
				"HEAD, POST, GET, PUT, DELETE, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers",
				"ACCEPT, ORIGIN, X-REQUESTED-WITH, CONTENT-TYPE");
	}

	private void setJsonpHeaders(HttpServletResponse resp) {
		resp.setHeader("Content-Type", "text/javascript");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
	}

	private int parseId(HttpServletRequest req) {
		final String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.length() == 0) {
			return -1;
		}
		String parameterString = pathInfo;
		if (parameterString.startsWith("/")) {
			parameterString = parameterString.substring(1);
		}
		logger.info("Parsing incoming string: " + parameterString);
		String[] split = parameterString.split("/");
		if (split.length == 0) {
			return -1;
		}
		if (split.length >= 1) {
			return Integer.parseInt(split[0]);
		}
		return -1;
	}

	private <T> void passResult(HttpServletResponse resp, Object payload,
			String callback) {
		passResult(resp, payload, callback, true);
	}

	private <T> void passResult(HttpServletResponse resp, Object payload,
			String callback, boolean success) {
		try {
			Result<Object> result = new Result<Object>();
			result.payload = payload;
			result.success = success;
			String json = mapper.writeValueAsString(result);
			if (callback != null) {
				json = callback + "(" + json + ");";
			}
			logger.info("Result: " + json);
			resp.setHeader("Content-Type", "application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
