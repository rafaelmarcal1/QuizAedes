package rafaelmarcal.ifsp.edu.quizaedes.services;

import android.app.Application;
import android.content.Intent;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Iniciar o MusicService
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
    }
}