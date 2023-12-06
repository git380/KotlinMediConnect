package com.kotlinmediconnect.employee

import com.kotlinmediconnect.model.Employee
import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/EmployeeSearchServlet")
class EmployeeSearchServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        if (request.session.getAttribute("empId") == null) {
            response.sendRedirect("LoginServlet")
            return
        }
        if (employeeList.isEmpty()) {
            request.getRequestDispatcher("/WEB-INF/jsp/employee/E102/employeeError.jsp").forward(request, response)
        } else {
            request.setAttribute("employeeList", employeeList)
            request.getRequestDispatcher("/WEB-INF/jsp/employee/E102/employeeList.jsp").forward(request, response)
        }
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        request.setAttribute("empId", request.getParameter("empId"))
        request.getRequestDispatcher("/WEB-INF/jsp/employee/E102/employeeUpdate.jsp").forward(request, response)
    }

    val employeeList: List<Employee>
        get() {
            val employeeList: MutableList<Employee> = ArrayList()
            try {
                DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                    val resultSet = connection.prepareStatement("SELECT * FROM employee WHERE emprole != 0").executeQuery()
                    while (resultSet.next()) {
                        val empId = resultSet.getString("empid")
                        val empFname = resultSet.getString("empfname")
                        val empLname = resultSet.getString("emplname")
                        val employee = Employee(empId, empFname, empLname)
                        employeeList.add(employee)
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return employeeList
        }
}