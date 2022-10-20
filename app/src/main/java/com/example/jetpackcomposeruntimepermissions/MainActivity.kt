package com.example.jetpackcomposeruntimepermissions

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpackcomposeruntimepermissions.ui.theme.JetpackComposeRuntimePermissionsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeRuntimePermissionsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RuntimePermissionScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RuntimePermissionScreen() {

    val context = LocalContext.current
    val singlePermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val multiplePermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    ) { permissionStateMap ->
        if (!permissionStateMap.containsValue(false)) {
            Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permissions Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            singlePermissionState.launchPermissionRequest()
        }) {
            Text(text = "For Camera Permission")
        }
        Button(onClick = {
            multiplePermissionsState.launchMultiplePermissionRequest()
        }) {
            Text(text = "For Storage Permission")
        }
    }
}