package com.example.week2latihanno2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val VALID_LENGTH = 1
    private val LONG_LENGTH = 2
    private val SHORT_LENGTH = 0

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btn: Button
    private lateinit var msgText: TextView

    lateinit var validUsers: List<User>

    private fun isValidUser(user: User): Boolean {
        for (validUserName in validUsers) {
            when{
                user.name.equals(validUserName.name, ignoreCase = true) && user.password == validUserName.password -> return true
            }
        }
        return false
    }

    //Check string length
    private fun isNameLong(username: String): Int {
        return when {
            username.length < 6 -> SHORT_LENGTH
            username.length > 15 -> LONG_LENGTH
            else -> VALID_LENGTH
        }
    }

    private fun isPassLong(password: String): Int {
        return when {
            password.length < 8 -> SHORT_LENGTH
            password.length > 20 -> LONG_LENGTH
            else -> VALID_LENGTH
        }
    }

    private fun isValidChar(username: String): Boolean {
        return !username.contains("[^A-Za-z0-9]".toRegex()) && username.contains("[A-Za-z]".toRegex()) && username.contains(
            "[0-9]".toRegex()
        )
    }

    private fun isValidPassChar(password: String): Boolean {
        return !password.contains("[^A-Za-z0-9!@#$%^&*.\\-_]".toRegex()) && password.contains("[A-Za-z]".toRegex()) && password.contains("[0-9]".toRegex()) && password.contains("[!@#$%^&*.\\-_]".toRegex())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initValidUsers()
        initView()

        btn.setOnClickListener {
            val userInput = User(username.text.toString(), password.text.toString())

            if (!checkUser(userInput)) return@setOnClickListener
            message(userInput.name, true)
        }
    }

    private fun checkUser(userInput: User): Boolean {
        val validUserChar = isValidChar(userInput.name)
        val validPassChar = isValidPassChar(userInput.password)
        val usernameLength = isNameLong(userInput.name) //0 artinya kependekan, 1 artinya sempurna, 2 artinya kepanjangan
        val passwordLength = isPassLong(userInput.password) //sama kyk di atas
        val validName = isValidUser(userInput)

        return when {
            !validUserChar -> {
                message(getString(R.string.username_char), false); false
            }
            !validPassChar -> {
                message(getString(R.string.pass_char), false); false
            }
            usernameLength == 0 -> {
                message(getString(R.string.username_tooshort), false); false
            }
            usernameLength == 2 -> {
                message(getString(R.string.username_toolong), false); false
            }
            passwordLength == 0 -> {
                message(getString(R.string.pass_tooshort), false); false
            }
            passwordLength == 2 -> {
                message(getString(R.string.pass_toolong), false); false
            }
            !validName -> {
                message(getString(R.string.invalid_user), false)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun initView() {
        username = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)
        btn = findViewById(R.id.btn_submit)
        msgText = findViewById(R.id.message)
    }

    private fun initValidUsers() {
        validUsers = listOf(
            User("global1234", "gL0b@l123@"),
            User("loyalty5678", "l0y@lTyS67B"),
            User("indonesia1945", "inD0n355!a19AS"),
            User("alfagift2023", "alF@g1fTz0zE")
        )
    }

    @SuppressLint("SetTextI18n")
    private fun message(msg: String, mode: Boolean) {
        if (!mode) {
            msgText.setTextColor(getColor(R.color.red))
            msgText.text = "Login Gagal:\n$msg"
        }else {
            msgText.setTextColor(getColor(R.color.green))
            msgText.text = "Login Berhasil:\nSelamat datang $msg"
        }
    }
}