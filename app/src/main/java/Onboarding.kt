package com.heaven.littlelemon1

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Onboarding(
    navController: NavHostController
) {

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var registrationMessage by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Let's get to know you"
        )

        TextField(
            value = firstName,
            onValueChange = {
                firstName = it
            },
            label = {
                Text("First name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = lastName,
            onValueChange = {
                lastName = it
            },
            label = {
                Text("Last name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

                if (
                    firstName.isBlank() ||
                    lastName.isBlank() ||
                    email.isBlank()
                ) {

                    registrationMessage =
                        "Registration unsuccessful. Please enter all data."

                } else {

                    val sharedPreferences =
                        navController.context.getSharedPreferences(
                            "LittleLemon",
                            Context.MODE_PRIVATE
                        )

                    sharedPreferences.edit()
                        .putString("firstName", firstName)
                        .putString("lastName", lastName)
                        .putString("email", email)
                        .apply()

                    registrationMessage =
                        "Registration successful!"

                    navController.navigate(Home.route) {
                        popUpTo(Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        if (registrationMessage.isNotEmpty()) {
            Text(
                text = registrationMessage
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {

    Onboarding(
        navController = rememberNavController()
    )
}