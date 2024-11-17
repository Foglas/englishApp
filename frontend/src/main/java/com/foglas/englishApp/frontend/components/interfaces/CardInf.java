package com.foglas.englishApp.frontend.components.interfaces;

public interface CardInf {
    void flip();

    void onCorrect(Runnable operation);
    void onIncorrect(Runnable operation);
}
