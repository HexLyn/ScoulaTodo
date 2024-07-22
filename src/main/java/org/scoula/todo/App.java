package org.scoula.todo;

import java.awt.*;

public abstract class App {
    Menu menu;
    public App() {

    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    protected void init() {
        // 필요한 초기화 작업 수행
    }
    public void run() {
        init();
    }
}
