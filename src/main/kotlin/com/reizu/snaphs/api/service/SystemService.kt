package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.input.System as SystemInput
import com.reizu.snaphs.api.dto.output.System as SystemOutput
import com.reizu.snaphs.api.entity.System
import com.reizu.snaphs.api.service.seek.SystemSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SystemService {

    @Autowired
    private lateinit var systemSeekService: SystemSeekService

    fun create(systemInput: SystemInput): SystemOutput {
        return systemInput.run {
            val system = System(
                name = name,
                isEmulated = isEmulated
            )
            val createdSystem: System = systemSeekService.create(system)

            createdSystem.output
        }
    }

    fun findAll(search: String?): Iterable<SystemOutput> {
        return systemSeekService.findAllActive(search = search).map { system -> system.output }
    }

}
