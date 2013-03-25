package eu.zeigermann.mortgage.server.customer;


import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class CustomerRestEndpoint extends HttpServlet {
	
	private Logger logger = Logger.getLogger(CustomerRestEndpoint.class.getName());

//	private CustomerService customerService = new MapCustomerService(); 
	private CustomerService customerService = new MemcacheCustomerService(); 

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
		Customer customer = gson.fromJson(req.getReader(), Customer.class);
		if (customer.id == -1) {
			sendError(resp);
		} else {
			customerService.save(customer);
			sendSuccess(resp);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Performing DELETE");
		Customer customer = parseCustomer(req);
		if (customer.id == -1) {
			sendError(resp);
		} else {
			customerService.delete(customer);
			sendSuccess(resp);
		}
	}

	private void sendSuccess(HttpServletResponse resp) throws IOException {
		completeCall(resp, "Success");
	}

	private void sendError(HttpServletResponse resp) throws IOException {
		completeCall(resp, "Error");
	}


	private void completeCall(HttpServletResponse resp, String response) throws IOException {
		logger.info("Writing back: " + response);
		resp.getWriter().write(response);
		setCORSHeaders(resp);
	}

	@Override
	// does both REST / CORS calls as well as jsonp
	//http://devlog.info/2010/03/10/cross-domain-ajax/
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Performing GET");
		Collection<Customer> customers = customerService.getAll();
		String callback = req.getParameter("callback");
		passResult(resp, customers, callback);
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
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "ACCEPT, ORIGIN, X-REQUESTED-WITH, CONTENT-TYPE");
	}
	
	private void setJsonpHeaders(HttpServletResponse resp) {
		resp.setHeader("Content-Type", "text/javascript");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
	}

	private Customer parseCustomer(HttpServletRequest req) {
		final Customer customer = new Customer();
		final String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.length() == 0) {
			return customer;
		}
		String parameterString = pathInfo;
		if (parameterString.startsWith("/")) {
			parameterString = parameterString.substring(1);
		}
		logger.info("Parsing incoming string: " + parameterString);
		String[] split = parameterString.split("/");
		if (split.length == 0) {
			return customer;
		}
		if (split.length >= 1) {
			customer.id = Integer.parseInt(split[0]);
		}
		if (split.length >= 2) {
			customer.name = split[1];
		}
		return customer;
	}

	private void passResult(HttpServletResponse resp, Object result,
			String callback) {
		try {
			String json = mapper.writeValueAsString(result);
			if (callback != null) {
				json = callback+"(" + json + ");";
			}
			logger.info("Result: " + json);
			resp.setHeader("Content-Type", "application/json");
			resp.getWriter().write(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
