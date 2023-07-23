package com.djordjije11.libraryappapi.controller.request.bookcopy;

import com.djordjije11.libraryappapi.model.BookCopyStatus;
import javax.annotation.Nullable;

public record BookCopyRequestQueryParams(
    @Nullable
    BookCopyStatus status
) {
}
