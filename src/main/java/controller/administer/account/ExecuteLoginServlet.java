package controller.administer.account;

import dao.UsersDao;
import entity.User;
import modelUtil.Failure;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/administer/account/execute-login")
public class ExecuteLoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ExecuteLoginServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        logger.info("Received login request for userName: " + userName);

        UsersDao usersDao = new UsersDao();
        try {
            User user = usersDao.getOneByUserName(userName);
            if (user == null) {
                logger.warning("User not found: " + userName);
                response.sendRedirect("/administer/account/login?error=invalid");
                return;
            }

            String hashedPassword = hashPassword(password);
            logger.info("Hashed input password: " + hashedPassword);
            logger.info("Stored password: " + user.getPassword());

            if (!hashedPassword.equals(user.getPassword())) {
                logger.warning("Password mismatch for user: " + userName);
                response.sendRedirect("/administer/account/login?error=invalid");
                return;
            }

            // セッションスコープにuserIdを登録
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getUserId());

            logger.info("Login successful for user: " + userName);
            response.sendRedirect("/administer/post/home");
        } catch (Exception e) {
            logger.severe("Error during login process: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("/administer/account/login?error=system");
        }
    }

    private String hashPassword(String password) throws Failure {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new Failure("パスワードのハッシュ化に失敗しました。");
        }
    }
}
