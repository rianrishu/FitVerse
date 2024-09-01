package com.rianrishu.fitverse.ui.signup

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.rianrishu.fitverse.R
import com.rianrishu.fitverse.utils.Resource

@Preview
@Composable
fun SignUpScreen(
    onClickSignIn: () -> Unit = {},
) {
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    var registerState = signUpViewModel.registerState
    val context = LocalContext.current

    var googleSignInClient: GoogleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
    }

    var googleSignUpLauncher: ActivityResultLauncher<Intent> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                it.data?.let { it1 -> signUpViewModel.onSignUpWithGooglePressed(it1) }
            }
        }


    LaunchedEffect(key1 = signUpViewModel.registerState) {
        when (registerState) {
            is Resource.Empty -> Unit
            is Resource.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }

            is Resource.Error -> {
                Toast.makeText(context, registerState.message, Toast.LENGTH_SHORT).show()
            }

            is Resource.Success -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.lt_blue)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_illustration),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .padding(dimensionResource(R.dimen.padding_normal))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                        topEnd = dimensionResource(id = R.dimen.auth_bg_corner_radius)
                    )
                )
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(id = R.string.let_s_create_your_account),
                style = MaterialTheme.typography.headlineSmall,
                color = colorResource(id = R.color.black),
                modifier = Modifier.padding(
                    dimensionResource(R.dimen.padding_normal),
                    dimensionResource(R.dimen.auth_bg_corner_radius),
                    dimensionResource(R.dimen.padding_normal),
                    dimensionResource(R.dimen.padding_normal)
                )
            )

            Text(
                text = stringResource(id = R.string.registerSubHead),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.gray4),
                textAlign = TextAlign.Center,
            )

            TextField(
                value = signUpViewModel.username,
                onValueChange = signUpViewModel::onUsernameTextChange,
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.username_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Leading Icon",
                        tint = colorResource(id = R.color.authTextFieldIconTint),
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.input_icon_size))
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.bar_width),
                        end = dimensionResource(R.dimen.bar_width),
                        bottom = dimensionResource(R.dimen.size0dp),
                        top = dimensionResource(R.dimen.auth_bg_corner_radius)
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                            topEnd = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                            bottomStart = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                            bottomEnd = dimensionResource(R.dimen.auth_bg_corner_radius)
                        )
                    )
                    .background(colorResource(id = R.color.gray2))
            )

            TextField(
                value = signUpViewModel.email,
                onValueChange = signUpViewModel::onEmailTextChange,
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.email_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Leading Icon",
                        tint = colorResource(id = R.color.authTextFieldIconTint),
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.input_icon_size))
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.bar_width),
                        vertical = dimensionResource(R.dimen.size10dp)
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                            topEnd = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                            bottomStart = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                            bottomEnd = dimensionResource(R.dimen.auth_bg_corner_radius)
                        )
                    )
                    .background(colorResource(id = R.color.gray2))
            )

            TextField(
                value = signUpViewModel.password,
                onValueChange = signUpViewModel::onPasswordTextChange,
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.password_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "Leading Icon",
                        tint = colorResource(id = R.color.authTextFieldIconTint),
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.input_icon_size))
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.bar_width),
                        end = dimensionResource(R.dimen.bar_width),
                        bottom = dimensionResource(R.dimen.bar_width),
                        top = dimensionResource(R.dimen.size0dp)
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                            topEnd = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                            bottomStart = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                            bottomEnd = dimensionResource(R.dimen.auth_bg_corner_radius)
                        )
                    )
                    .background(colorResource(id = R.color.gray2))
            )

            Button(
                onClick = { signUpViewModel.onSignUpButtonPressed() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.bar_width),
                        end = dimensionResource(R.dimen.bar_width),
                        bottom = dimensionResource(R.dimen.bar_width),
                        top = dimensionResource(R.dimen.size0dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.lt_darkpblue)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up_label),
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.bar_width),
                        end = dimensionResource(R.dimen.bar_width),
                        bottom = dimensionResource(R.dimen.bar_width),
                        top = dimensionResource(R.dimen.size0dp)
                    )
            ) {
                // Divider Line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.border_width))
                        .background(colorResource(id = R.color.dividerColor))
                )

                // Text in the center
                Text(
                    text = "OR",
                    style = MaterialTheme.typography.bodyMedium, // Equivalent to Subtitle2
                    color = Color.Black,
                    modifier = Modifier
                        .background(colorResource(id = R.color.authBottomBgColor))
                        .padding(horizontal = dimensionResource(id = R.dimen.one_space))
                )
            }

            Text(
                text = stringResource(id = R.string.signUpSocial),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.gray4),
                modifier = Modifier.padding(
                    dimensionResource(R.dimen.padding_normal),
                    dimensionResource(R.dimen.size0dp),
                    dimensionResource(R.dimen.padding_normal),
                    dimensionResource(R.dimen.padding_normal)
                )
            )

            Button(
                onClick = { googleSignUpLauncher.launch(googleSignInClient.signInIntent) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.bar_width),
                        end = dimensionResource(R.dimen.bar_width),
                        bottom = dimensionResource(R.dimen.bar_width),
                        top = dimensionResource(R.dimen.size0dp)
                    )
                    .border(
                        width = dimensionResource(id = R.dimen.border_width),
                        color = colorResource(id = R.color.lt_darkpblue),
                        shape = RoundedCornerShape(
                            topStart = dimensionResource(R.dimen.auth_bg_corner_radius),
                            topEnd = dimensionResource(R.dimen.auth_bg_corner_radius),
                            bottomStart = dimensionResource(R.dimen.auth_bg_corner_radius),
                            bottomEnd = dimensionResource(R.dimen.auth_bg_corner_radius)
                        )
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up_google_label),
                    color = colorResource(id = R.color.lt_darkpblue),
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.one_space))) {
                Text(
                    text = stringResource(id = R.string.already_have_an_account),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = colorResource(
                            id = R.color.gray4
                        )
                    ),
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.one_space))
                )
                Text(
                    text = stringResource(id = R.string.sign_in_label),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = colorResource(id = R.color.lt_darkpblue)
                    ),
                    modifier = Modifier.clickable(
                        onClick = onClickSignIn
                    )
                )
            }
        }
    }
}
