package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ArtSpaceTheme { ArtSpaceScreen() } }
    }
}

private data class Artwork(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val artist: Int,
    @StringRes val year: Int
)

private val artworks = listOf(
    Artwork(R.drawable.art_golden_horizon, R.string.golden_horizon, R.string.artist_golden, R.string.year_golden),
    Artwork(R.drawable.art_moonlit_peaks, R.string.moonlit_peaks, R.string.artist_moonlit, R.string.year_moonlit),
    Artwork(R.drawable.art_quiet_valley, R.string.quiet_valley, R.string.artist_valley, R.string.year_valley)
)

@Composable
fun ArtSpaceScreen() {
    var artworkId by rememberSaveable { mutableStateOf(1) }
    val artwork = artworks[artworkId - 1]
    val onPrevious = { artworkId = if (artworkId == 1) artworks.size else artworkId - 1 }
    val onNext = { artworkId = if (artworkId == artworks.size) 1 else artworkId + 1 }

    BoxWithConstraints(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
        if (maxWidth > maxHeight && maxWidth >= 600.dp) {
            Row(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    ArtworkWall(artwork.image, artwork.title)
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    ArtworkDescriptor(artwork.title, artwork.artist, artwork.year)
                    DisplayController(onPrevious = onPrevious, onNext = onNext)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    ArtworkWall(artwork.image, artwork.title)
                }
                ArtworkDescriptor(artwork.title, artwork.artist, artwork.year)
                DisplayController(onPrevious = onPrevious, onNext = onNext)
            }
        }
    }
}

@Composable
fun ArtworkWall(@DrawableRes artwork: Int, @StringRes title: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.sizeIn(maxWidth = 420.dp, maxHeight = 420.dp)
            .aspectRatio(1f).border(8.dp, Color.White),
        shadowElevation = 8.dp
    ) {
        Image(
            painter = painterResource(artwork),
            contentDescription = stringResource(title),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ArtworkDescriptor(
    @StringRes title: Int,
    @StringRes artist: Int,
    @StringRes year: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(
                R.string.artwork_credit,
                stringResource(artist),
                stringResource(year)
            ),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onPrevious) { Text(stringResource(R.string.previous)) }
        Button(onClick = onNext) { Text(stringResource(R.string.next)) }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme { ArtSpaceScreen() }
}
