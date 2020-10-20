package com.home.jetquotesdemo.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.home.jetquotesdemo.R
import com.home.jetquotesdemo.model.MainBean
import com.home.jetquotesdemo.presenter.MainPresenter
import com.husseinala.neon.core.Neon
import com.husseinala.neon.core.Transformation
import com.husseinala.neon.core.centerCrop
import com.husseinala.neon.glide.ProvideGlideLoader

class MainActivity : AppCompatActivity() {

    private val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val glide = Glide.with(applicationContext)
        setContent { initContent(glide) }
    }

    @Composable
    private fun initContent(glide: RequestManager) {
        ProvideGlideLoader(glide) {
            Scaffold(
                backgroundColor = Color(0xFFF5F5F5),
                bodyContent = {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (topImage, lazyColumnFor,
                            bottomBarImage) = createRefs()
                        buildTopImage(topImage)
                        buildLazyColumnFor(lazyColumnFor, topImage)
                        buildBottomImage(bottomBarImage)
                    }
                })
        }
    }

    @Composable
    private fun ConstraintLayoutScope.buildTopImage(topImage: ConstrainedLayoutReference) {
        Image(
            imageResource(id = R.drawable.icon_top),
            modifier = Modifier.fillMaxWidth().constrainAs(topImage) {
                top.linkTo(parent.top)
            }
        )
    }

    @Composable
    private fun ConstraintLayoutScope.buildLazyColumnFor(
        lazyColumnFor: ConstrainedLayoutReference,
        topImage: ConstrainedLayoutReference
    ) {
        presenter.getMainBeanList()?.let { list ->
            LazyColumnFor(items = list,
                modifier = Modifier.fillMaxWidth().constrainAs(lazyColumnFor) {
                    top.linkTo(topImage.bottom)
                }) { bean ->
                val context = ContextAmbient.current
                buildLazyColumnForCard(context, bean)
            }
        }
    }

    @Composable
    private fun buildLazyColumnForCard(
        context: Context,
        bean: MainBean
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)
        ) {
            Row(modifier = Modifier.height(150.dp).padding(16.dp).clickable(onClick = {
                Toast.makeText(context, bean.name, Toast.LENGTH_SHORT).show()
            }).background(Color.White)) {
                Neon(
                    url = bean.url.toString(),
                    transformation = Transformation.centerCrop(),
                    modifier = Modifier.weight(1f)
                        .align(alignment = Alignment.CenterVertically)
                        .fillMaxSize()
                )
                buildLazyColumnForCardText(bean)
            }
        }
    }

    @Composable
    private fun RowScope.buildLazyColumnForCardText(bean: MainBean) {
        Column(
            modifier = Modifier.Companion.weight(2f).align(Alignment.CenterVertically)
        ) {
            Text(
                text = bean.name.toString(),
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
                    .padding(16.dp, 0.dp, 0.dp, 0.dp)
            )
            Text(
                text = bean.address.toString(),
                style = MaterialTheme.typography.body2,
                color = Color(0xFF565656),
                modifier = Modifier.align(Alignment.Start)
                    .padding(16.dp, 16.dp, 0.dp, 0.dp)
            )
        }
    }

    @Composable
    private fun ConstraintLayoutScope.buildBottomImage(bottomBarImage: ConstrainedLayoutReference) {
        Image(
            imageResource(id = R.drawable.icon_bottom_bar),
            modifier = Modifier.width(170.dp).constrainAs(bottomBarImage) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }
        )
    }
}