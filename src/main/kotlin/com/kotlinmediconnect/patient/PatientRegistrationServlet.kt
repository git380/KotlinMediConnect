package com.kotlinmediconnect.patient

import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/PatientRegistrationServlet")
class PatientRegistrationServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
        request.getRequestDispatcher("/WEB-INF/jsp/patient/P101/patientRegistration.jsp").forward(request, response)

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        request.characterEncoding = "UTF-8"
        val patId = request.getParameter("patId")
        val hokenexp = request.getParameter("hokenexp")
        if (nullPatient(patId)) {
            if (LocalDate.parse(hokenexp, DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(LocalDate.now())) {
                try {
                    DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                        val preparedStatement = connection.prepareStatement("INSERT INTO patient (patid, patfname, patlname, hokenmei, hokenexp) VALUES (?, ?, ?, ?, ?)")
                        preparedStatement.setString(1, patId)
                        preparedStatement.setString(2, request.getParameter("patFname"))
                        preparedStatement.setString(3, request.getParameter("patLname"))
                        preparedStatement.setString(4, request.getParameter("hokenmei"))
                        preparedStatement.setString(5, hokenexp)
                        preparedStatement.executeUpdate()
                    }
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
                request.getRequestDispatcher("/WEB-INF/jsp/patient/P101/registrationOK.jsp").forward(request, response)
            } else {
                response.contentType = "text/html; charset=UTF-8"
                response.writer.println("新しい日付を入力してください。")
            }
        } else {
            response.contentType = "text/html; charset=UTF-8"
            response.writer.println("IDが一致しています。")
        }
    }

    private fun nullPatient(patid: String?): Boolean {
        var patSQLId: String? = null
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                val pStmt = connection.prepareStatement("SELECT * FROM patient WHERE patid = ?")
                pStmt.setString(1, patid)
                val resultSet = pStmt.executeQuery()
                if (resultSet.next()) patSQLId = resultSet.getString("patid")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return patSQLId == null
    }
}