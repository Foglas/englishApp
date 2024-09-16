package com.foglas.englishApp.frontend.components.interfaces;

import com.foglas.englishApp.dto.InputWordDto;

import java.util.List;
import java.util.function.Supplier;

public interface CardInf {
    void flip();

    void setActionToCorrAnswBtn();
    void setActionToBadAnswBtn();
}
