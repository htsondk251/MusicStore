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
/*
		HttpSession session = request.getSession();
		if (requestURI.endsWith("catalog")) {
			List<Product> products = ProductDB.selectProducts();
			session.setAttribute("products", products);
			url = "/index.jsp";
		} else 

		} else 
*/
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
//		System.out.println("show: " + requestURI + ", length: " + requestURI.length());

		//parse it to get the productCode
		String code = requestURI.substring(requestURI.length() - 4);

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
		User userInSession = (User) session.getAttribute("user");
		String email = request.getParameter("email");
		String requestURI = request.getRequestURI();

		String code = requestURI.substring(requestURI.length() - 11, requestURI.length() - 7);

		//if not, check if there is a cookie for that user's email address
		if (userInSession == null) {
			Cookie[] cookies = request.getCookies();

			for(Cookie cookie : cookies) {
				System.out.println(cookie.getName() + ": " + cookie.getValue());
			}

			String cookieValue = CookieUtil.getCookieValue(cookies, "userEmailCookie");

			System.out.println(cookieValue);

			if (cookieValue.equals(email)) {
				//read that User obj from the DB
				User userInDB = UserDB.selectUser(email);
				if (userInDB != null) {
					//otherwise, write the data for the download to the DB					
					Download download = new Download();

					download.setUser(userInDB);
					download.setDownloadDate(new Date());
					download.setProductCode(code);
					DownloadDB.insert(download);
				}
			}
			//if cannot, return a URL displaying the JSP for the Register page
			return ("/catalog/register.jsp");			
		} 
		//return a URL for a JSP page letting the use listen to sound files
		return ("/catalog/" + code + "/sound.jsp");
		
	}


	public String registerUser(HttpServletRequest request, HttpServletResponse response) {
		//get user data from the request object parameter
		String email = request.getParameter("email");
		System.out.println("register: " + email);
/*
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String companyName = request.getParameter("CompanyName");
		String address1 = request.getParameter("Address1");
		String address2 = request.getParameter("Address2");
		String city = request.getParameter("City");
		String state = request.getParameter("State");
		String zip = request.getParameter("Zip");
		String country = request.getParameter("Country");
		String userId = request.getParameter("Country");
*/
		//check whether the email address already exists in the database
		System.out.println(UserDB.emailExists(email));
		if (UserDB.emailExists(email)) {
			//update the user data in the database
			User user = UserDB.selectUser(email);
			System.out.println("check: " + user.getEmail());

			user.setLastName(request.getParameter("lastName"));
			user.setFirstName(request.getParameter("firstName"));
/*
			user.setCompanyName(request.getParameter("CompanyName"));
			user.setAddress1(request.getParameter("address1"));
			user.setAddress2(request.getParameter("address2"));
			user.setCity(request.getParameter("city"));
			user.setState(request.getParameter("state"));
			user.setZip(request.getParameter("zip"));
			user.setCountry(request.getParameter("Country"));
*/
			System.out.println("update: " + UserDB.update(user));
		} else {
			//otherwise, create a new User object & set the data from the request & insert new row into DB
			User user = new User();
			user.setEmail(request.getParameter("email"));
			user.setLastName(request.getParameter("lastName"));
			user.setFirstName(request.getParameter("firstName"));
/*
			user.setCompanyName(request.getParameter("CompanyName"));
			user.setAddress1(request.getParameter("address1"));
			user.setAddress2(request.getParameter("address2"));
			user.setCity(request.getParameter("city"));
			user.setState(request.getParameter("state"));
			user.setZip(request.getParameter("zip"));
			user.setCountry(request.getParameter("country"));
*/
			System.out.println("insert: " + UserDB.insert(user));
		}
		//add a cookie to the user's browser
		Cookie c = new Cookie("userEmailCookie", email);
		c.setMaxAge(60*60*24*365*2);
		c.setPath("/");
		response.addCookie(c);

		//return a URL for a JSP that lets the user listen to sound files
		String requestURI = request.getRequestURI();

		String code = requestURI.substring(requestURI.length()-20, requestURI.length() - 16);
		System.out.println("/catalog/" + code + "/sound.jsp");
		return ("/catalog/" + code + "/sound.jsp");
	}
}


/*
if (userInSession == null) {
	//find cookie named user's email
	if (have cookie named user's email) {
		//read User from db
		if (useInDB != null) {
			//write Download to DB
		}
	}
	return ("/register.jsp");
}
return ("/sound.jsp");
*/