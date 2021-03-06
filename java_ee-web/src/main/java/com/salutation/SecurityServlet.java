package com.salutation;

import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * Created by khayapro on 2016/06/04
 */
@WebServlet(name = "SecurityServlet", urlPatterns = {"/SecurityServlet"})
public class SecurityServlet extends HttpServlet {

    @EJB
    private VoucherManager voucherManager;

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Security Servlet</title>");
            out.println("</head>");
            out.println("<body>");

            voucherManager.createVoucher("Sams Billing", "Cape Town", BigDecimal.valueOf(5045.23d));
            voucherManager.submit();
            final boolean approve = voucherManager.approve();
            if(approve) {
                out.println("<h3> Voucher name: " + voucherManager.getName() + "</h3>");
                out.println("<h3> Voucher approved</h3>");
            } else {
                out.println("<h3> Voucher name: " + voucherManager.getName() + "</h3>");
                out.println("<h3> Voucher not approved</h3>");
            }
            out.println("</body>");
            out.println("</html>");
        } catch (EJBAccessException e){
            System.out.println("EJB ACCESS EXCEPTION WAS THROWN");
        } finally {
            out.close();

        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
