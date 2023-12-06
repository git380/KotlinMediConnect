package com.kotlinmediconnect.login

import com.kotlinmediconnect.SaltUserPassword
import com.kotlinmediconnect.model.Account
import com.kotlinmediconnect.model.Login
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
        val empPasswd = request.getParameter("empPasswd")
        val result = findByLogin(Login(empId, empPasswd))
        if (result) {
            val session = request.session
            session.setAttribute("empId", empId)
            val dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/loginOK.jsp")
            dispatcher.forward(request, response)
        } else {
            val dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/error.jsp")
            dispatcher.forward(request, response)
        }
    }

    fun findByLogin(login: Login): Boolean {
        var account: Account? = null
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { conn ->
                val sql = "SELECT * FROM employee WHERE empid = ? AND emppasswd = ? AND emprole = 0"
                val pStmt = conn.prepareStatement(sql)
                pStmt.setString(1, login.empId)
                pStmt.setString(2, SaltUserPassword().getDigest(login.empId, login.emppasswd))
                val rs = pStmt.executeQuery()
                if (rs.next()) {
                    val empid = rs.getString("empid")
                    val empfname = rs.getString("empfname")
                    val emplname = rs.getString("emplname")
                    val emppasswd = rs.getString("emppasswd")
                    val emprole = rs.getInt("emprole")
                    account = Account(empid, empfname, emplname, emppasswd, emprole)
                    println("id:" + empid + "empfname:" + empfname + "emplname:" + emplname + "emppasswd:" + emppasswd + "role:" + emprole)
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
        return account != null
    }
}