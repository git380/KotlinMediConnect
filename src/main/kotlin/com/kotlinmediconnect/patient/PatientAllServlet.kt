package com.kotlinmediconnect.patient

import com.kotlinmediconnect.model.PatientSearch
import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/PatientAllServlet")
class PatientAllServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.characterEncoding = "UTF-8"
        request.setAttribute("patients", searchPatient)
        request.getRequestDispatcher("/WEB-INF/jsp/patient/P102/patientAll.jsp").forward(request, response)
    }

    private val searchPatient: List<PatientSearch>
        get() {
            val patients: MutableList<PatientSearch> = ArrayList()
            try {
                DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                    val resultSet = connection.prepareStatement("SELECT * FROM patient").executeQuery()
                    while (resultSet.next()) {
                        patients.add(
                            PatientSearch(
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
            return patients
        }
}