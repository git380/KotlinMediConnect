package com.kotlinmediconnect.employee

import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/EmployeeUpdateServlet")
class EmployeeUpdateServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.setAttribute("empId", request.getParameter("empId"))
        request.getRequestDispatcher("/WEB-INF/jsp/employee/E102/employeeUpdateConfirm.jsp").forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        request.characterEncoding = "UTF-8"
        if (updateEmployee(request.getParameter("empId"), request.getParameter("firstName"), request.getParameter("lastName")))
            response.sendRedirect("EmployeeUpdateServlet")
        else
            request.getRequestDispatcher("/WEB-INF/jsp/employee/E102/error.jsp").forward(request, response)
    }

    private fun updateEmployee(empId: String, empFname: String, empLname: String): Boolean {
        if (empFname.isEmpty() || empLname.isEmpty()) return false
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                val statement = connection.prepareStatement("UPDATE employee SET empfname = ?, emplname = ? WHERE empid = ?")
                statement.setString(1, empFname)
                statement.setString(2, empLname)
                statement.setString(3, empId)
                statement.executeUpdate()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return true
    }
}

