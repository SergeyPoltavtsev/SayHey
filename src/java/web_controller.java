/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.Users;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.UsersFacade;
import session.UsersManager;

/**
 *
 * @author Sergey
 */
@WebServlet(name = "web_controller", loadOnStartup=1, 
        urlPatterns = {"/registration"})
public class web_controller extends HttpServlet {
    @EJB
    UsersFacade usersFacade;
    @EJB
    UsersManager userManager;

    @Override
    public void init() throws ServletException {
        List<Users> users = usersFacade.findAll();
        getServletContext().setAttribute("users", usersFacade.findAll()); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userPath=request.getServletPath();
        if (null != userPath)
            switch (userPath) {
            /*case "/main":
                String login = null;
                Enumeration<String> params = request.getParameterNames();
                while (params.hasMoreElements()) {
                    String param = params.nextElement();
                    login="login".equals(param)?request.getParameter(param):login;
                }
                try{
                    Users user = usersFacade.find(login);
                    request.setAttribute("user", user);
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;*/
            case "/registration":
                String userlogin=null;
                String pass=null;
                String pass2=null;
                Enumeration<String> parameters = request.getParameterNames();
                while (parameters.hasMoreElements()) {
                    String parameter = parameters.nextElement();
                    switch (parameter) {
                    case "login":
                        userlogin=request.getParameter(parameter);
                        break;
                    case "password":
                        pass=request.getParameter(parameter);
                        break;
                    case "password2":
                        pass2=request.getParameter(parameter);
                        break;
                    }
                }
                Integer codeOperation=userManager.addUser(userlogin, pass, pass2);
                if (codeOperation!=0)
                    {request.setAttribute("notif", "Operation code: "+codeOperation);}
                else
                    {request.setAttribute("notif", "User "+userlogin+" has created");}
        }
        
        request.getRequestDispatcher("/WEB-INF/views"+userPath+".jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
