package com.kotlinmediconnect.hospital

import com.kotlinmediconnect.model.Hospital
import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/HospitalRegistrationServlet")
class HospitalRegistrationServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        if (request.session.getAttribute("empId") == null) {
            response.sendRedirect("LoginServlet")
            return
        }
        request.getRequestDispatcher("/WEB-INF/jsp/hospital/H101/hospitalRegistration.jsp").forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        request.characterEncoding = "UTF-8"
        val tabyouinid = request.getParameter("tabyouinid")
        val tabyouinmei = request.getParameter("tabyouinmei")
        val tabyouinaddres = request.getParameter("tabyouinaddres")
        val tabyouintel = request.getParameter("tabyouintel")
        val tabyouinshihonkin = request.getParameter("tabyouinshihonkin").toInt()
        val kyukyu = request.getParameter("kyukyu").toInt()
        if (checkHospital(tabyouinid)) {
            registerHospital(Hospital(tabyouinid, tabyouinmei, tabyouinaddres, tabyouintel, tabyouinshihonkin, kyukyu))
            request.getRequestDispatcher("/WEB-INF/jsp/hospital/H101/registrationComplete.jsp").forward(request, response)
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/hospital/H101/registrationError.jsp").forward(request, response)
        }
    }

    fun checkHospital(tabyouinid: String?): Boolean {
        var tabyouinSQLId: String? = null
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                val pStmt = connection.prepareStatement("SELECT * FROM tabyouin WHERE tabyouinid = ?")
                pStmt.setString(1, tabyouinid)
                val resultSet = pStmt.executeQuery()
                if (resultSet.next()) {
                    tabyouinSQLId = resultSet.getString("tabyouinid")
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return tabyouinSQLId == null
    }

    fun registerHospital(hospital: Hospital) {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                val preparedStatement =
                    connection.prepareStatement("INSERT INTO tabyouin (tabyouinid, tabyouinmei, tabyouinaddres, tabyouintel, tabyouinshihonkin, kyukyu) VALUES (?, ?, ?, ?, ?, ?)")
                preparedStatement.setString(1, hospital.tabyouinid)
                preparedStatement.setString(2, hospital.tabyouinmei)
                preparedStatement.setString(3, hospital.tabyouinaddres)
                preparedStatement.setString(4, hospital.tabyouintel)
                preparedStatement.setInt(5, hospital.tabyouinshihonkin)
                preparedStatement.setInt(6, hospital.kyukyu)
                preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
