package com.foglas.englishApp.frontend.components.interfaces;

import com.foglas.englishApp.dto.InputWordDto;
import com.foglas.englishApp.frontend.components.Card;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface CardInf {
    void flip();

    void setActionToCorrAnswBtn(Runnable operation);
    void setActionToBadAnswBtn(Runnable operation);
}
