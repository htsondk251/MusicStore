package music.util;

import java.util.*;
import javax.servlet.*;

import music.business.*;
import music.data.*;

public class MusicStoreContextListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("ServletContextListener started");
		ServletContext sc = event.getServletContext();

		String custServEmail = sc.getInitParameter("custServEmail");
		sc.setAttribute("custServEmail", custServEmail);

		GregorianCalendar currentDate = new GregorianCalendar();
		int currentYear = currentDate.get(Calendar.YEAR);
		sc.setAttribute("currentYear", currentYear);

		ArrayList<Product> products = ProductDB.selectProducts();

		sc.setAttribute("products", products);
	}


	public void contextDestroyed(ServletContextEvent event) {
	}
}