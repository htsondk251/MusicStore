package music.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Date;

import music.business.*;
import music.data.*;
import music.util.*;

public class CatalogController extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "";
		String requestURI = request.getRequestURI();

		if (requestURI.endsWith("register")) {
			url = registerUser(request, response);
		}			

		getServletContext().getRequestDispatcher(url).forward(request, response);	
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url="";
		if (requestURI.endsWith("listen") == false) {
			url = showProduct(request, response);
		} else if (requestURI.endsWith("listen")) {
			url = listen(request, response);
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);	
	}


	public String showProduct(HttpServletRequest request, HttpServletResponse response)  { //throws ServletException, IOException {
		//get path for the request??
		String requestURI = request.getRequestURI();

		//parse it to get the productCode
		String code = requestURI.substring(requestURI.length() - 4);

//		String ode = request.getPathInfo();
//		if (code != null) {
//			code = productCode.substring(1);
//		}

		//read the product for that productCode from the DB
		Product product = ProductDB.selectProduct(code);

		//set the product in the session object
		HttpSession session = request.getSession();
		session.setAttribute("product", product);

		//return a URL for the JSP for that coressponds with that product
		return ("/catalog/" + code + "/index.jsp");
	}

	public String listen(HttpServletRequest request, HttpServletResponse response) {
		//check whether a User obj exists in the session obj
		HttpSession session = request.getSession();				//check thread-safe?	
		User user = (User) session.getAttribute("user");

//		String email = request.getParameter("email");		//request not have any "email" parameter
//		System.out.println("req\'s param: " + email);

		String requestURI = request.getRequestURI();
		String code = requestURI.substring(requestURI.length() - 11, requestURI.length() - 7);

		//if not, check if there is a cookie for that user's email address
		if (user == null) {
			Cookie[] cookies = request.getCookies();
			
			for(Cookie cookie : cookies) {
				System.out.println(cookie.getName() + ": " + cookie.getValue());
			}

			String emailAddress = CookieUtil.getCookieValue(cookies, "userEmailCookie");
//			System.out.println(cookieValue.equals(email));
//			System.out.println(cookieValue);

			if (emailAddress== null || emailAddress.equals("")) {
				return ("/catalog/register.jsp");

			} else {
				//read that User obj from the DB
				user = UserDB.selectUser(emailAddress);
				if (user == null) {
					return ("/catalog/register.jsp");
				}
				session.setAttribute("user", user);
			}
		}
		System.out.println("session user\'s email: " + user.getEmail());
		Product product = (Product) session.getAttribute("product");
		System.out.println("product now: " + product.getCode());
		
		//otherwise, write the data for the download to the DB
		Download download = new Download();

		download.setUser(user);
		download.setDownloadDate(new Date());
		download.setProductCode(code);
		System.out.println("what\'s code: " + code);
		System.out.println(DownloadDB.insert(download));

		//return a URL for a JSP page letting the use listen to sound files
		return ("/catalog/" + code + "/sound.jsp");
		
	}


	public String registerUser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		//get user data from the request object parameter
		String email = request.getParameter("email");
		System.out.println("register: " + email);

		//check whether the email address already exists in the database
		System.out.println(UserDB.emailExists(email));
		User user;
		if (UserDB.emailExists(email)) {
			//update the user data in the database
			user = UserDB.selectUser(email);			
			user.setLastName(request.getParameter("lastName"));
			user.setFirstName(request.getParameter("firstName"));
			user.setEmail(email);
			UserDB.update(user);
		} else {
			//otherwise, create a new User object & set the data from the request & insert new row into DB
			user = new User();
			user.setEmail(request.getParameter("email"));
			user.setLastName(request.getParameter("lastName"));
			user.setFirstName(request.getParameter("firstName"));
			UserDB.insert(user);
		}
		session.setAttribute("user", user);

		//add a cookie to the user's browser
		Cookie c = new Cookie("userEmailCookie", email);
		c.setMaxAge(60*60*24*365*2);
		c.setPath("/");
		response.addCookie(c);

		//return a URL for a JSP that lets the user listen to sound files
		String requestURI = request.getRequestURI();
		String code = requestURI.substring(requestURI.length()-20, requestURI.length() - 16);
		
		return ("/catalog/" + code + "/sound.jsp");
	}
}