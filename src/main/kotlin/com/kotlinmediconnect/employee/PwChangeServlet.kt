package com.kotlinmediconnect.employee

import com.kotlinmediconnect.SaltUserPassword
import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/PwChangeServlet")
class PwChangeServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        if (request.session.getAttribute("empId") == null) {
            response.sendRedirect("LoginServlet")
            return
        }
        request.getRequestDispatcher("/WEB-INF/jsp/employee/pwChange.jsp").forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val empId = request.session.getAttribute("empId") as String
        val empPasswd = request.getParameter("empPasswd1")
        passwordChange(empId, empPasswd)
        println("$empId $empPasswd")
        request.getRequestDispatcher("/WEB-INF/jsp/employee/changeOK.jsp").forward(request, response)
    }

    fun passwordChange(empId: String?, empPasswd: String?) {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                val statement = connection.prepareStatement("UPDATE employee SET emppasswd = ? WHERE empid = ? ")
                statement.setString(1, SaltUserPassword().getDigest(empId, empPasswd))
                statement.setString(2, empId)
                statement.executeUpdate()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}