package com.kotlinmediconnect.patient

import com.kotlinmediconnect.model.PatientExpiration
import java.io.IOException
import java.sql.Date
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/PatientExpirationServlet")
class PatientExpirationServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.setAttribute("expiredPatients", expiredInsurancePatients)
        request.setAttribute("date", Date(Date().time))
        if (expiredInsurancePatients.isEmpty()){
            response.contentType = "text/html; charset=UTF-8"
            response.writer.println("保険証期限が過ぎた患者は見つかりませんでした。")
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/patient/P104/patientSearch.jsp").forward(request, response)
        }
    }

    private val expiredInsurancePatients: List<PatientExpiration>
        get() {
            val expiredPatients: MutableList<PatientExpiration> = ArrayList()
            try {
                DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                    val preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE hokenexp < ?")
                    preparedStatement.setDate(1, Date(Date().time))
                    val resultSet = preparedStatement.executeQuery()
                    while (resultSet.next()) {
                        expiredPatients.add(
                            PatientExpiration(
                                resultSet.getString("patid"),
                                resultSet.getString("patfname"),
                                resultSet.getString("patlname"),
                                resultSet.getString("hokenmei"),
                                resultSet.getString("hokenexp")
                            )
                        )
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return expiredPatients
        }
}
