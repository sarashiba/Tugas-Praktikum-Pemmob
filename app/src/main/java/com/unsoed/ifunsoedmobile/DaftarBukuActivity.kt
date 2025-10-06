package com.unsoed.ifunsoedmobile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsoed.ifunsoedmobile.data.model.BookDoc
import com.unsoed.ifunsoedmobile.databinding.ActivityDaftarBukuBinding
import com.unsoed.ifunsoedmobile.ui.adapter.BookAdapter
import com.unsoed.ifunsoedmobile.ui.adapter.OnBookClickListener
import com.unsoed.ifunsoedmobile.ui.fragment.BookDetailFragment
import com.unsoed.ifunsoedmobile.viewmodel.MainViewModel

class DaftarBukuActivity : AppCompatActivity(), OnBookClickListener {

    private lateinit var binding: ActivityDaftarBukuBinding

    private val viewModel: MainViewModel by viewModels()

    private val adapter = BookAdapter(emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.books.observe(this) {
            adapter.setData(it)
        }

        viewModel.fetchBooks("kotlin programming")

    }

    override fun onBookClick(book: BookDoc) {
        book.let { b->
            BookDetailFragment(
                title = b.title ?: "No Title",
                author = b.authorName?.joinToString(", ") ?: "Unknown Author",
                year = b.firstPublishYear?.toString() ?: "-",
                coverId = b.coverId ?: 0
            ).show(supportFragmentManager, BookDetailFragment::class.java.simpleName)
        }
    }
}