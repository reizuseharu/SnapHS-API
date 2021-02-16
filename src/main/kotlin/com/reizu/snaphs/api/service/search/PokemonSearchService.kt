package com.reizu.snaphs.api.service.search

import com.reizu.core.api.service.SearchService
import com.reizu.snaphs.api.entity.Pokemon
import org.springframework.stereotype.Service

@Service
class PokemonSearchService : SearchService<Pokemon>()
