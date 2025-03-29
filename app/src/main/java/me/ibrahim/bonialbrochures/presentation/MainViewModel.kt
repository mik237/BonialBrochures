package me.ibrahim.bonialbrochures.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import me.ibrahim.bonialbrochures.domain.repositories.BrochuresRepository
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    brochuresRepository: BrochuresRepository
) : ViewModel() {

    val brochures = brochuresRepository.getBrochures().cachedIn(viewModelScope)
}