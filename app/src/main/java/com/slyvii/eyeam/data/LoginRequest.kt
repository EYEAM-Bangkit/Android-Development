package com.slyvii.eyeam.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("returnSecureToken")
	val returnSecureToken: Boolean = true
)
