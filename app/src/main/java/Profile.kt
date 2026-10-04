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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Profile(
    navController: NavHostController
) {

    val sharedPreferences = navController.context.getSharedPreferences(
        "LittleLemon",
        Context.MODE_PRIVATE
    )

    val firstName = sharedPreferences.getString(
        "firstName",
        ""
    )

    val lastName = sharedPreferences.getString(
        "lastName",
        ""
    )

    val email = sharedPreferences.getString(
        "email",
        ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Header
        Image(
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Profile information:"
        )

        Text(
            text = "First name: $firstName"
        )

        Text(
            text = "Last name: $lastName"
        )

        Text(
            text = "Email: $email"
        )

        Button(
            onClick = {

                sharedPreferences.edit()
                    .clear()
                    .apply()

                navController.navigate(
                    Onboarding.route
                ) {
                    popUpTo(Profile.route) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    // Preview intentionally left empty because
    // Profile requires a NavHostController.
}