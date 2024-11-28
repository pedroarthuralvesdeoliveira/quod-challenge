package br.com.fiap.quod.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.quod.R
import br.com.fiap.quod.auth.Auth
import br.com.fiap.quod.auth.AuthScreenViewModel
import br.com.fiap.quod.biometria.digital.BiometricFingerprintAuthentication
import br.com.fiap.quod.biometria.digital.BiometricHelper
import br.com.fiap.quod.biometria.facial.BiometricFaceAuthentication
import br.com.fiap.quod.documentoscopia.FaceComparisonComponent
import br.com.fiap.quod.scoreantifraude.AntiFraudScreenViewModel
import br.com.fiap.quod.scoreantifraude.AntiFraude
import br.com.fiap.quod.simswap.CheckSIMSwap
import br.com.fiap.quod.simswap.CheckSIMSwapViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SIMSwap : Screen("simswap")
    object FacialBiometry : Screen("facial_biometry")
    object DigitalBiometry : Screen("digital_biometry")
    object DocumentAnalysis : Screen("document_analysis")
    object CadastralAuthentication : Screen("cadastral_authentication")
    object AntifraudScore : Screen("antifraud_score")
}

@Composable
fun MenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .background(color = Color(0xFF333333))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun Menu(biometricHelper: BiometricHelper) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.FacialBiometry.route) { BiometricFaceAuthentication(
            biometricHelper = biometricHelper,
            onSuccess = {},
            onError = {}
        ) }
        composable(Screen.DigitalBiometry.route) { BiometricFingerprintAuthentication(
            biometricHelper = biometricHelper,
            onSuccess = {},
            onError = {}
        ) }
        composable(Screen.DocumentAnalysis.route) { FaceComparisonComponent() }
        composable(Screen.SIMSwap.route) { CheckSIMSwap(
            checkSIMSwapViewModel = CheckSIMSwapViewModel()
        ) }
        composable(Screen.CadastralAuthentication.route) { Auth(
            authScreenViewModel = AuthScreenViewModel()
        ) }
        composable(Screen.AntifraudScore.route) { AntiFraude(
            antiFraudScreenViewModel = AntiFraudScreenViewModel()
        ) }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuItem(
                icon = ImageVector.vectorResource(id = R.drawable.face_24px),
                label = "Biometria Facial",
                onClick = { navController.navigate(Screen.FacialBiometry.route) },
                modifier = Modifier.size(100.dp)
            )
            MenuItem(
                icon = ImageVector.vectorResource(id = R.drawable.baseline_fingerprint_24),
                label = "Biometria Digital",
                onClick = { navController.navigate(Screen.DigitalBiometry.route) },
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuItem(
                icon = ImageVector.vectorResource(id = R.drawable.sim_card_24px),
                label = "SIM Swap",
                onClick = { navController.navigate(Screen.SIMSwap.route) },
                modifier = Modifier.size(100.dp)
            )
            MenuItem(
                icon = ImageVector.vectorResource(id = R.drawable.document_scanner_24px),
                label = "Análise de documento",
                onClick = { navController.navigate(Screen.DocumentAnalysis.route) },
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuItem(
                icon = ImageVector.vectorResource(id = R.drawable.lock_24px),
                label = "Autenticação Cadastral",
                onClick = { navController.navigate(Screen.CadastralAuthentication.route) },
                modifier = Modifier.size(100.dp)
            )
            MenuItem(
                icon = ImageVector.vectorResource(id = R.drawable.readiness_score_24px),
                label = "Score Antifraude",
                onClick = { navController.navigate(Screen.AntifraudScore.route) },
                modifier = Modifier.size(100.dp)
            )
        }
    }
}