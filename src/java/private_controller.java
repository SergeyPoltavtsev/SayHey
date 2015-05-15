/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.Messages;
import Entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.MessageManager;
import session.MessagesFacade;
import session.UsersFacade;
import session.UsersManager;

/**
 *
 * @author Sergey
 */
@WebServlet(name = "private_controller", urlPatterns = {"/profile", "/searchPeople"})
@ServletSecurity( @HttpConstraint(rolesAllowed = {"private"}) )
public class private_controller extends HttpServlet {
    @EJB
    UsersFacade usersFacade;
    @EJB
    UsersManager usersManager;
    @EJB
    MessageManager messageManager; 
    @EJB
    MessagesFacade messagesFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void init() throws ServletException {
        List<Users> users = usersFacade.findAll();
        getServletContext().setAttribute("users", users); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userPath=request.getServletPath();
        String login = request.getUserPrincipal().getName();
        Users user = usersFacade.find(login);
                    
        if (null != userPath)
            switch (userPath) {
            case "/profile": 
                //String login = request.getRemoteUser();             
                //String login = request.getUserPrincipal().getName();
                try{
                    request.setAttribute("user", user);
                    
                    List<Users> chatsWith = messagesFacade.findAllChatsForUser(user.getLogin());
                    request.setAttribute("chatsWith", chatsWith);
                    
                    String friendLogin = null;
                    String message = null;
                    Enumeration<String> params = request.getParameterNames();

                    while (params.hasMoreElements()) {
                        String param = params.nextElement();
                        friendLogin="messfrom".equals(param)?request.getParameter(param):friendLogin;
                        message = "newMessage".equals(param)?request.getParameter(param):message;
                    }

                    //open message history
                    if(friendLogin!=null) {
                        request.setAttribute("friend", friendLogin);
                        refreshMessages(request, friendLogin, user.getLogin());
                    }
                    
                    //save new message to DB
                    if (message != null && friendLogin!=null) {
                        Users friend = usersFacade.find(friendLogin);
                        //boolean success=messageManager.addMessage(friend, user, message);
                        refreshMessages(request, friendLogin, user.getLogin());
                    }
                    
                }catch(Exception e){
                    e.printStackTrace();
                }
                request.getRequestDispatcher("/WEB-INF/private/profile.jsp").forward(request, response);
                break;
                
            case "/searchPeople":
                String pattern = "";
                String addRemoveFriendLogin = null;
                Enumeration<String> params = request.getParameterNames();
                
                while (params.hasMoreElements()) {
                    String param = params.nextElement();
                    pattern="loginPattern".equals(param)?request.getParameter(param):pattern;
                    addRemoveFriendLogin = "personLogin".equals(param)?request.getParameter(param):addRemoveFriendLogin;
                }
                
                //add/remove friend
                if(addRemoveFriendLogin!=null) {
                    usersManager.addRemoveFriend(login,addRemoveFriendLogin);
                }
                //update user
                user = usersFacade.find(login);
                
                Map<Users, Boolean> peopleMap = new HashMap<Users, Boolean>();
                List<Users> people = usersFacade.findAllCustomersWithName(login, pattern);
                
                for (Users person : people) {
                    if(user.getFriendsCollection().contains(person)){
                        peopleMap.put(person, false);
                    } else {
                        peopleMap.put(person, true);
                    }
                }
                
                getServletContext().setAttribute("foundPeople", peopleMap);
                getServletContext().setAttribute("loginPattern", pattern);
                request.getRequestDispatcher("/WEB-INF/private/searchPeople.jsp").forward(request, response);
                break;
                
            case "/logout":
                HttpSession session = request.getSession(false);
                if (session!= null){
                    session.invalidate();
                }   
                response.sendRedirect("/");
                break;
            }
        
    }
    
    private void refreshMessages (HttpServletRequest request, String friendLogin, String userLogin ) {
        try{
            Collection<Messages> messages = messagesFacade.findMessageHistory(friendLogin, userLogin);
            request.setAttribute("messages", messages);
        }catch(Exception e){
            e.printStackTrace();
        }
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
