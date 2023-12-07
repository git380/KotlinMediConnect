package com.kotlinmediconnect.treatment

import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/TreatmentConfirmationServlet")
class TreatmentConfirmationServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val patId = request.getParameter("patId")
        val medicineId = request.getParameter("medicineId")
        val quantity = request.getParameter("quantity").toInt()
        val impDate = request.getParameter("impDate")
        request.setAttribute("patId", patId)
        request.setAttribute("medicineId", medicineId)
        request.setAttribute("quantity", quantity)
        request.setAttribute("impDate", impDate)
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                val statement = connection.prepareStatement("INSERT INTO treatment (patid, medicineid, quantity, impdate) VALUES (?, ?, ?, ?)")
                statement.setString(1, patId)
                statement.setString(2, medicineId)
                statement.setInt(3, quantity)
                statement.setString(4, impDate)
                statement.executeUpdate()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        request.getRequestDispatcher("/WEB-INF/jsp/treatment/D103/treatmentConfirmation.jsp").forward(request, response)
    }
}
