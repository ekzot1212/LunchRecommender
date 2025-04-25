package com.example.lunchrecommender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lunchrecommender.ui.theme.LunchRecommenderTheme
import com.example.lunchrecommender.ui.viewmodel.MenuViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LunchRecommenderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LunchRecommenderApp()
                }
            }
        }
    }
}

@Composable
fun LunchRecommenderApp(
    viewModel: MenuViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("전체") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "오늘 뭐 먹지?",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 32.dp)
        )

        // 카테고리 선택
        CategoryDropdown(
            categories = uiState.categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        // 결과 표시
        Text(
            text = uiState.currentMenu,
            style = MaterialTheme.typography.bodyLarge
        )

        // 추천 버튼
        Button(
            onClick = { viewModel.recommendMenu(selectedCategory) }
        ) {
            Text("메뉴 추천받기")
        }

        // 메뉴 추가 버튼
        Button(
            onClick = { showAddDialog = true }
        ) {
            Text("메뉴 추가하기")
        }

        // 메뉴 추가 다이얼로그
        if (showAddDialog) {
            AddMenuDialog(
                categories = uiState.categories.filter { it != "전체" },
                onDismiss = { showAddDialog = false },
                onMenuAdd = { menuName, category ->
                    viewModel.addNewMenu(menuName, category)
                    showAddDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedCategory,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMenuDialog(
    categories: List<String>,
    onDismiss: () -> Unit,
    onMenuAdd: (String, String) -> Unit
) {
    var menuName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("새로운 메뉴 추가") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 카테고리 선택
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = {}
                ) {
                    TextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("카테고리") }
                    )
                }

                // 메뉴 이름 입력
                TextField(
                    value = menuName,
                    onValueChange = { menuName = it },
                    label = { Text("메뉴 이름") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onMenuAdd(menuName, selectedCategory) }
            ) {
                Text("추가")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
} 