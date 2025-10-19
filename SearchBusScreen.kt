package com.example.ticketbook.ui.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

// Data class for dummy bus info
data class BusInfo(val name: String, val time: String, val type: String, val price: Double, val availableSeats: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBusScreen() {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF3E5F5), // Consistent with other screens
            Color(0xFFB3E5FC),
            Color(0xFFD1C4E9)
        )
    )

    // State management
    val cities = listOf("Dhaka", "Chittagong", "Sylhet", "Rajshahi", "Khulna", "Barisal", "Rangpur")
    var fromCity by remember { mutableStateOf("") }
    var toCity by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var selectedBusType by remember { mutableStateOf("Any") }
    var showResults by remember { mutableStateOf(false) }

    val dummyBuses = listOf(
        BusInfo("Hanif Enterprise", "8:00 AM", "AC", 1200.0, 15),
        BusInfo("Green Line", "9:30 AM", "Non-AC", 800.0, 25),
        BusInfo("Shohag Paribahan", "10:15 AM", "AC", 1250.0, 10),
        BusInfo("ENA Transport", "11:00 AM", "Non-AC", 850.0, 30)
    )

    // Filtered list based on selection
    val filteredBuses = dummyBuses.filter {
        selectedBusType == "Any" || it.type == selectedBusType
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Find Your Bus",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF283593)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Search Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CityDropdown(
                        label = "From",
                        cities = cities,
                        selectedCity = fromCity,
                        onCitySelected = { fromCity = it }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CityDropdown(
                        label = "To",
                        cities = cities,
                        selectedCity = toCity,
                        onCitySelected = { toCity = it }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    DatePickerField(
                        date = date,
                        onDateSelected = { date = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    BusTypeSelector(
                        selectedType = selectedBusType,
                        onTypeSelected = { selectedBusType = it }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { showResults = true },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = fromCity.isNotBlank() && toCity.isNotBlank() && date.isNotBlank()
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Search Buses")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Results Section
            if (showResults) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item {
                        Text(
                            "Available Buses",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF283593)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(filteredBuses) { bus ->
                        BusResultCard(bus = bus)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDropdown(
    label: String,
    cities: List<String>,
    selectedCity: String,
    onCitySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedCity,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            cities.forEach { city ->
                DropdownMenuItem(
                    text = { Text(city) },
                    onClick = {
                        onCitySelected(city)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun DatePickerField(date: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    // Use a Box to make the whole field clickable
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                        onDateSelected("$dayOfMonth/${month + 1}/$year")
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
    ) {
        OutlinedTextField(
            value = date,
            onValueChange = {},
            label = { Text("Date of Journey") },
            readOnly = true,
            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Select Date") },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = false // Optional: disables keyboard/selection, makes sure only click works
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusTypeSelector(selectedType: String, onTypeSelected: (String) -> Unit) {
    val types = listOf("Any", "AC", "Non-AC")
    Column(horizontalAlignment = Alignment.Start) {
        Text("Bus Type", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(8.dp))
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            types.forEachIndexed { index, type ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = types.size),
                    onClick = { onTypeSelected(type) },
                    selected = type == selectedType
                ) {
                    Text(type)
                }
            }
        }
    }
}

@Composable
fun BusResultCard(bus: BusInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Commute,
                contentDescription = "Bus",
                tint = Color(0xFF5E35B1),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(bus.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Departure: ${bus.time}", fontSize = 14.sp)
                Text("Type: ${bus.type}", fontSize = 14.sp)
                Text("Seats Available: ${bus.availableSeats}", fontSize = 14.sp, color = Color(0xFF0D47A1))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("${bus.price} BDT", fontWeight = FontWeight.Bold, color = Color(0xFFBF360C), fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* Handle booking logic */ }) {
                    Text("Book")
                }
            }
        }
    }
}
