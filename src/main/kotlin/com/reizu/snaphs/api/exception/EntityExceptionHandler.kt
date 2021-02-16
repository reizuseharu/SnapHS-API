package com.reizu.snaphs.api.exception

import com.reizu.snaphs.api.exception.BaseResponseEntityExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class EntityExceptionHandler : BaseResponseEntityExceptionHandler()
