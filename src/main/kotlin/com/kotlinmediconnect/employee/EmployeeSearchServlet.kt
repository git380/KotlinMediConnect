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
        if (employeeList.isEmpty()) {
            response.contentType = "text/html; charset=UTF-8"
            response.writer.println("従業員が見つかりません。")
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

    private val employeeList: List<Employee>
        get() {
            val employeeList: MutableList<Employee> = ArrayList()
            try {
                DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                    val resultSet =
                        connection.prepareStatement("SELECT * FROM employee WHERE emprole != 0").executeQuery()
                    while (resultSet.next()) {
                        employeeList.add(
                            Employee(
                                resultSet.getString("empid"),
                                resultSet.getString("empfname"),
                                resultSet.getString("emplname")
                            )
                        )
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return employeeList
        }
}