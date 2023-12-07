package com.kotlinmediconnect.treatment

import com.kotlinmediconnect.model.Medicine
import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/TreatmentSelectionServlet")
class TreatmentSelectionServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.characterEncoding = "UTF-8"
        request.setAttribute("patId", request.getParameter("patId"))
        request.setAttribute("medicines", medicines)
        request.getRequestDispatcher("/WEB-INF/jsp/treatment/D101/treatmentSelection.jsp").forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        request.setAttribute("patId", request.getParameter("patId"))
        request.setAttribute("medicineId", request.getParameter("medicineId"))
        request.setAttribute("quantity", request.getParameter("quantity").toInt())
        request.setAttribute("impDate", request.getParameter("impDate"))
        request.getRequestDispatcher("/WEB-INF/jsp/treatment/D101/treatmentSelectionResult.jsp").forward(request, response)
    }

    private val medicines: ArrayList<Medicine>
        get() {
            val medicines: ArrayList<Medicine> = ArrayList<Medicine>()
            try {
                DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                    val sql = "SELECT * FROM medicine"
                    val statement = connection.prepareStatement(sql)
                    val resultSet = statement.executeQuery()
                    while (resultSet.next()) {
                        medicines.add(
                            Medicine(
                                resultSet.getString("medicineid"),
                                resultSet.getString("medicinename"),
                                resultSet.getString("unit")
                            )
                        )
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return medicines
        }
}