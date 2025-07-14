package ru.work_mate.rickandmorty.ui.screens.characters_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.work_mate.rickandmorty.domain.model.CharacterFilter
import ru.work_mate.rickandmorty.domain.model.CharacterGender
import ru.work_mate.rickandmorty.domain.model.CharacterSpecies
import ru.work_mate.rickandmorty.domain.model.CharacterStatus

@Composable
fun CharacterFilterSection(
    filter: CharacterFilter,
    isExpanded: Boolean,
    onFilterChange: (CharacterFilter) -> Unit,
    onClearFilter: () -> Unit,
    onToggleExpanded: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Поле поиска с кнопкой разворачивания
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = filter.name ?: "",
                onValueChange = { name ->
                    onFilterChange(filter.copy(name = name.ifBlank { null }))
                },
                modifier = Modifier.weight(1f),
                placeholder = { Text(
                    text = if (isExpanded) "Search by name" else "Name",
                ) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (!filter.name.isNullOrEmpty() || isExpanded || filter.hasAdvancedFilters()) {
                        IconButton(
                            onClick = { onClearFilter() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Кнопка разворачивания/сворачивания
            IconButton(
                onClick = onToggleExpanded,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.FilterList,
                    contentDescription = if (isExpanded) "Collapse filters" else "Expand filters"
                )
            }
        }

        // Расширенные фильтры
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Фильтр по типу
                OutlinedTextField(
                    value = filter.type ?: "",
                    onValueChange = { type ->
                        onFilterChange(filter.copy(type = type.ifBlank { null }))
                    },
                    label = { Text("Type") },
                    placeholder = { Text("Enter character type") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Фильтр по статусу
                FilterGroup(
                    title = "Status",
                    options = CharacterStatus.entries.map { it.name },
                    selectedOption = filter.status?.name,
                    onOptionSelected = { selected ->
                        val status = CharacterStatus.entries.find { it.name == selected }
                        onFilterChange(filter.copy(status = status))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Фильтр по видам
                FilterGroup(
                    title = "Species",
                    options = CharacterSpecies.entries.map { it.name },
                    selectedOption = filter.species?.name,
                    onOptionSelected = { selected ->
                        val species = CharacterSpecies.entries.find { it.name == selected }
                        onFilterChange(filter.copy(species = species))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Фильтр по полу
                FilterGroup(
                    title = "Gender",
                    options = CharacterGender.entries.map { it.name },
                    selectedOption = filter.gender?.name,
                    onOptionSelected = { selected ->
                        val gender = CharacterGender.entries.find { it.name == selected }
                        onFilterChange(filter.copy(gender = gender))
                    }
                )
            }
        }

        // Индикатор активных фильтров
        if (filter.hasAdvancedFilters() && !isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Advanced filters applied",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterGroup(
    title: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Опция "Any"
                FilterChip(
                    selected = selectedOption == null,
                    onClick = { onOptionSelected(null) },
                    label = { Text("Any") }
                )

                // Остальные опции
                options.forEach { option ->
                    FilterChip(
                        selected = selectedOption == option,
                        onClick = {
                            onOptionSelected(if (selectedOption == option) null else option)
                        },
                        label = { Text(option) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterPreview() {
    CharacterFilterSection(
        filter = CharacterFilter(),
        isExpanded = false,
        onFilterChange = {},
        onClearFilter = {},
        onToggleExpanded = {}
    )
}