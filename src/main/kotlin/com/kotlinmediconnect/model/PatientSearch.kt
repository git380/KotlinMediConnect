package com.kotlinmediconnect.model

data class PatientSearch(
    val patid: String?,
    val patfname: String?,
    val patlname: String?,
    val hokenmei: String?,
    val hokenexp: String?
)