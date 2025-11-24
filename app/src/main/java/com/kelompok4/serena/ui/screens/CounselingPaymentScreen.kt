package com.kelompok4.serena.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentSummaryScreen() {
    var selectedPaymentMethod by remember { mutableStateOf("BCA Virtual Account") }
    var selectedTab by remember { mutableStateOf("Virtual Account") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ringkasan Pembayaran",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Reservation Section
            ReservationSection()

            Spacer(modifier = Modifier.height(8.dp))

            // Payment Summary Section
            PaymentSummarySection()

            Spacer(modifier = Modifier.height(8.dp))

            // Payment Method Section
            PaymentMethodSection(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                selectedPaymentMethod = selectedPaymentMethod,
                onPaymentMethodSelected = { selectedPaymentMethod = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Payment Section
            BottomPaymentSection()
        }
    }
}

@Composable
fun ReservationSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Reservasi",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Mohon periksa kembali apakah ada kesalahan dalam detail yang dimasukkan.",
                fontSize = 13.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Doctor Image
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE3F2FD))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Dr. Laura Azzura, S.Psi.",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sen, Sep 11, 10.00",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "Education",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "S1 Psikologi Universitas Brawijaya",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentSummarySection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Ringkasan pembayaran",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price Breakdown
            PaymentRow(label = "Harga", amount = "10.000")
            Spacer(modifier = Modifier.height(8.dp))
            PaymentRow(label = "Biaya Servis", amount = "6.000")
            Spacer(modifier = Modifier.height(8.dp))
            PaymentRow(label = "Diskon Pengguna Pertama", amount = "-16.000", isDiscount = true)

            Spacer(modifier = Modifier.height(12.dp))

            // Info Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF1F8F4)
            ) {
                Text(
                    text = "Biaya Servis termasuk ke dalam biaya aplikasi",
                    fontSize = 12.sp,
                    color = Color(0xFF5F7D6B),
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = Color(0xFFE0E0E0))

            Spacer(modifier = Modifier.height(12.dp))

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Rp0",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun PaymentRow(label: String, amount: String, isDiscount: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = amount,
            fontSize = 14.sp,
            color = if (isDiscount) Color(0xFF2D7D5F) else Color.Gray
        )
    }
}

@Composable
fun PaymentMethodSection(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    selectedPaymentMethod: String,
    onPaymentMethodSelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Pilih metode pembayaran",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Payment Method Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PaymentMethodTab(
                    text = "E-Wallet",
                    isSelected = selectedTab == "E-Wallet",
                    onClick = { onTabSelected("E-Wallet") }
                )
                PaymentMethodTab(
                    text = "Virtual Account",
                    isSelected = selectedTab == "Virtual Account",
                    onClick = { onTabSelected("Virtual Account") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Payment Options
            PaymentOption(
                bankName = "BCA Virtual Account",
                icon = "BCA",
                isSelected = selectedPaymentMethod == "BCA Virtual Account",
                onClick = { onPaymentMethodSelected("BCA Virtual Account") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            PaymentOption(
                bankName = "BNI Virtual Account",
                icon = "BNI",
                isSelected = selectedPaymentMethod == "BNI Virtual Account",
                onClick = { onPaymentMethodSelected("BNI Virtual Account") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            PaymentOption(
                bankName = "BSI Virtual Account",
                icon = "BSI",
                isSelected = selectedPaymentMethod == "BSI Virtual Account",
                onClick = { onPaymentMethodSelected("BSI Virtual Account") }
            )
        }
    }
}

@Composable
fun PaymentMethodTab(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0xFF2D7D5F) else Color.Transparent,
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isSelected) Color(0xFF2D7D5F) else Color(0xFFE0E0E0)
        )
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            color = if (isSelected) Color.White else Color.Gray,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun PaymentOption(bankName: String, icon: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = Color(0xFFE0E0E0)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Bank Logo Placeholder
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = icon,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = bankName,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF2D7D5F),
                    unselectedColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun BottomPaymentSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Total yang harus dibayar",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp0",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .width(140.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2D7D5F)
                )
            ) {
                Text(
                    text = "Bayar",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun PaymentSummaryScreenPreview() {
    PaymentSummaryScreen()
}

