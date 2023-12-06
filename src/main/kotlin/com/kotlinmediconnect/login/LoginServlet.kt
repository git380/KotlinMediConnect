package com.kotlinmediconnect.login

import com.kotlinmediconnect.SaltUserPassword
import com.kotlinmediconnect.model.Account
import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/LoginServlet")
class LoginServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
        request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response)

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        request.characterEncoding = "UTF-8"
        val empId = request.getParameter("empId")
        if (findByLogin(empId, request.getParameter("empPasswd"))) {
            request.session.setAttribute("empId", empId)
            request.getRequestDispatcher("/WEB-INF/jsp/login/loginOK.jsp").forward(request, response)
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/login/error.jsp").forward(request, response)
        }
    }

    private fun findByLogin(empId:String, empPasswd:String): Boolean {
        var account: Account? = null
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { conn ->
                val pStmt = conn.prepareStatement("SELECT * FROM employee WHERE empid = ? AND emppasswd = ? AND emprole = 0")
                pStmt.setString(1, empId)
                pStmt.setString(2, SaltUserPassword().getDigest(empId, empPasswd))
                val rs = pStmt.executeQuery()
                if (rs.next()) {
                    account = Account(
                        rs.getString("empid"),
                        rs.getString("empfname"),
                        rs.getString("emplname"),
                        rs.getString("emppasswd"),
                        rs.getInt("emprole")
                    )
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
        return account != null
    }
}