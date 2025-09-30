package com.example.a03_03

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a03_03.ui.theme._03_03Theme
import kotlinx.coroutines.launch

data class Contact(val name: String)
data class Alphabetical(val letter: Char, val phones: List<Contact>)
@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _03_03Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PhoneListScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhoneListScreen(modifier: Modifier = Modifier) {
    val listState: LazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // used gemini for this part
    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex >= 10 // Condition changed to >= 10
        }
    }
    // alphabetical list of contacts/Provide at least 50 contacts
    val phoneDataByAlphabetical = remember {
        listOf(
            Alphabetical('A', listOf(
                Contact("Aaron"),
                Contact("Abigail"),
                Contact("Adam"),
                Contact("Adrian"),
                Contact("Aiden"),
            )),
            Alphabetical('B', listOf(
                Contact("Barbara"),
                Contact("Bethany"),
                Contact("Blake"),
                Contact("Brandon")
            )),
            Alphabetical('C', listOf(
                Contact("Caleb"),
                Contact("Cameron"),
            )),
            Alphabetical('D', listOf(
                Contact("Daisy"),
                Contact("Daniel"),
                Contact("David"),
                Contact("Dylan")
            )),
            Alphabetical('E', listOf(
                Contact("Edward"),
                Contact("Eleanor"),
                Contact("Elijah"),
                Contact("Elizabeth"),
                Contact("Emma")
            )),
            Alphabetical('F', listOf(
                Contact("Faith"),
                Contact("Felix"),
            )),
            Alphabetical('G', listOf(
                Contact("Gabriel")
            )),
            Alphabetical('I', listOf(
                Contact("Ibrahim"),
                Contact("Ingrid"),
                Contact("Isabella"),
            )),
            Alphabetical('J', listOf(
                Contact("Jaden"),
                Contact("James"),
                Contact("Jared"),
                Contact("Jeffrey"),
                Contact("Jeremy"),
                Contact("Jessica")

            )),
            Alphabetical('K', listOf(
                Contact("Karina"),
                Contact("Karl"),
                Contact("Kathleen"),
                Contact("Kenneth"),
                Contact("Kirk")
            )),
            Alphabetical('L', listOf(
                Contact("Leo"),
                Contact("Levi"),
                Contact("Liam"),
                Contact("Lila"),
                Contact("Lily"),
                Contact("Lucas"),
                Contact("Lucy"),
                Contact("Luis"),
                Contact("Luna"),
                Contact("Lydia")
            )),
            Alphabetical('M', listOf(
                Contact("Maddox"),
                Contact("Madison"),
                Contact("Marcus")
            )),
        )
    }


    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Contacts",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState
            ) {
                phoneDataByAlphabetical.forEach { alpa ->
                    // Use stickyHeader to keep the current letter visible as the list scrolls.
                    stickyHeader(key = alpa.letter) {
                        LetterHeader(name = alpa.letter)
                    }
                    items(
                        items = alpa.phones,
                        key = { phone -> "${alpa.letter}-${phone.name}" }
                    ) { phone ->
                        ContactName(contactName = phone.name)
                    }
                }
            }
        }
        if (showButton) {
            // Add a “Scroll to Top” floating action button (FAB) that appears only when the user has scrolled past item 10.
            FloatingActionButton(
                onClick = {
                    // Use animateScrollToItem() with coroutines.
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
            ) {
                Text("Scroll to top")
            }
        }
    }
}


@Composable
fun LetterHeader(name: Char) {
    Text(
        text = name.toString(),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun ContactName(contactName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = contactName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PhoneListScreenPreview() {
    _03_03Theme {
        PhoneListScreen(Modifier.fillMaxSize())
    }
}