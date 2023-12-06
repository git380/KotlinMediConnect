package com.kotlinmediconnect.employee

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


@WebServlet("/RegisterServlet")
class RegisterServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        if (request.session.getAttribute("empId") == null) {
            response.sendRedirect("LoginServlet")
            return
        }
        request.getRequestDispatcher("/WEB-INF/jsp/employee/E101/register.jsp").forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        request.characterEncoding = "UTF-8"
        val empId = request.getParameter("empId")
        val fName = request.getParameter("fName")
        val lName = request.getParameter("lName")
        val empPasswd = request.getParameter("empPasswd")
        val empRole = request.getParameter("empRole").toInt()
        if (nullEmployee(empId)) {
            insertAccount(Account(empId, fName, lName, empPasswd, empRole))
            println("$empId $fName $lName $empPasswd $empRole")
            request.getRequestDispatcher("/WEB-INF/jsp/employee/E101/registrationComplete.jsp").forward(request, response)
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/employee/E101/registrationError.jsp").forward(request, response)
        }
    }

    fun nullEmployee(empid: String?): Boolean {
        var empSQLId: String? = null
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                val pStmt = connection.prepareStatement("SELECT * FROM employee WHERE empid = ?")
                pStmt.setString(1, empid)
                val resultSet = pStmt.executeQuery()
                if (resultSet.next()) {
                    empSQLId = resultSet.getString("empid")
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return empSQLId == null
    }

    fun insertAccount(account: Account) {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                val preparedStatement =
                    connection.prepareStatement("INSERT INTO employee (empid, empfname, emplname, emppasswd, emprole) VALUES (?, ?, ?, ?, ?)")
                preparedStatement.setString(1, account.empId)
                preparedStatement.setString(2, account.empFName)
                preparedStatement.setString(3, account.empLName)
                preparedStatement.setString(4, SaltUserPassword().getDigest(account.empId, account.empPasswd))
                preparedStatement.setInt(5, account.empRole)
                preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}

